package local.digitalsimulator.main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import local.digitalsimulator.gates.AndGate;
import local.digitalsimulator.gates.Gate;
import local.digitalsimulator.gates.NandGate;
import local.digitalsimulator.gates.NorGate;
import local.digitalsimulator.gates.NotGate;
import local.digitalsimulator.gates.OrGate;
import local.digitalsimulator.gates.XorGate;
import local.digitalsimulator.modules.Indicator;
import local.digitalsimulator.modules.Module;
import local.digitalsimulator.modules.ModuleManager;
import local.digitalsimulator.modules.Switch;

public class Window {

	private JFrame window;
	private ModuleManager moduleManager;
	private Workspace workspace;
	private JPanel selectionPanel;
	private JMenuBar menubar;

	private Module from, to;

	private static Window instance = new Window();

	public Window() {
		moduleManager = new ModuleManager();

		window = new JFrame("Digitalsimulator");

		initMenuBar();
		window.setJMenuBar(menubar);

		initializeKeyListeners();
		initializeWindowListeners();
		initializeSelection();

		workspace = new Workspace();
		workspace.setPreferredSize(new Dimension(DefaultValues.WORKSPACE_WIDTH, DefaultValues.WORKSPACE_HEIGHT));
		workspace.setBounds(0, 0, DefaultValues.WORKSPACE_WIDTH, DefaultValues.WORKSPACE_HEIGHT);
		workspace.setLayout(null);
		workspace.setVisible(true);

		initMainPane();

		window.setResizable(false);
		window.setBounds(0, 0, DefaultValues.FRAME_WIDTH, DefaultValues.FRAME_HEIGHT);
		window.setPreferredSize(new Dimension(DefaultValues.FRAME_WIDTH, DefaultValues.FRAME_HEIGHT));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // this is handled
		window.setLayout(null);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public static Window getWindow() {

		return instance;
	}

	public Module getTo() {
		return to;
	}

	public void setTo(Module to) {
		this.to = to;
	}

	public Module getFrom() {
		return from;
	}

	public void setFrom(Module from) {
		this.from = from;
	}

	public Workspace getWorkspace() {

		return workspace;
	}

	public JFrame getWindowFrame() {

		return window;
	}

	public void setModuleManager(ModuleManager moduleManager) {

		this.moduleManager = moduleManager;
	}

	public ModuleManager getModuleManager() {

		return moduleManager;
	}

	@SuppressWarnings("unused")
	private JMenuBar getMenuBar() {

		return menubar;
	}

	private void initMenuBar() {
		menubar = new JMenuBar();

		JMenuItem load = new JMenuItem("Load");
		load.setMaximumSize(new Dimension(DefaultValues.MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		load.addActionListener((e) -> new Filehandler().loadFile());

		JMenuItem save = new JMenuItem("Save");
		save.setMaximumSize(new Dimension(DefaultValues.MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		save.addActionListener((e) -> new Filehandler().saveFile());

		JMenuItem newFile = new JMenuItem("New");
		newFile.setMaximumSize(new Dimension(DefaultValues.MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		newFile.addActionListener((e) -> workspace.createNewWorkspace());

		JMenuItem newLink = new JMenuItem("Create Link");
		newLink.setMaximumSize(new Dimension(DefaultValues.BIG_MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		newLink.addActionListener((e) -> activateLinkMode());

		JMenuItem deleteLink = new JMenuItem("Delete Link");
		deleteLink.setMaximumSize(new Dimension(DefaultValues.BIG_MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		deleteLink.addActionListener((e) -> activateUnlinkMode());

		JMenuItem simulate = new JMenuItem("Simulate");
		simulate.setMaximumSize(new Dimension(DefaultValues.FRAME_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		simulate.addActionListener((e) -> updateCalculateModules());

		load.setPreferredSize(new Dimension(DefaultValues.MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		save.setPreferredSize(new Dimension(DefaultValues.MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		newFile.setPreferredSize(new Dimension(DefaultValues.MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		newLink.setPreferredSize(new Dimension(DefaultValues.BIG_MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		deleteLink.setPreferredSize(new Dimension(DefaultValues.BIG_MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));
		simulate.setPreferredSize(new Dimension(DefaultValues.BIG_MENUITEM_WIDTH, DefaultValues.MENUITEM_HEIGHT));

		menubar.setPreferredSize(new Dimension(DefaultValues.FRAME_WIDTH, DefaultValues.MENUBAR_HEIGHT));
		menubar.setMaximumSize(new Dimension(DefaultValues.FRAME_WIDTH, DefaultValues.MENUBAR_HEIGHT));

		menubar.add(newFile);
		menubar.add(load);
		menubar.add(save);
		menubar.add(newLink);
		menubar.add(deleteLink);
		menubar.add(simulate);
	}

	private void updateCalculateModules() {

		moduleManager.calculateModules();

		workspace.invalidate();
		workspace.repaint();
	}

	private void initMainPane() {

		// Right pane
		JScrollPane wsScrollPane = new JScrollPane(workspace);
		wsScrollPane.setBounds(DefaultValues.SELECTION_SCROLLPANE_WIDTH + DefaultValues.SMALL_SPACER, 0,
				DefaultValues.FRAME_WIDTH - DefaultValues.SELECTION_SCROLLPANE_WIDTH - DefaultValues.BIG_SPACER,
				DefaultValues.FRAME_HEIGHT - DefaultValues.MENUBAR_HEIGHT - DefaultValues.BIG_SPACER * 2);
		wsScrollPane.getVerticalScrollBar().setUnitIncrement(DefaultValues.SCROLL_SPEED);
		wsScrollPane.getHorizontalScrollBar().setUnitIncrement(DefaultValues.SCROLL_SPEED);

		// Left pane with modules
		JScrollPane selectionScrollPane = new JScrollPane(selectionPanel);
		selectionScrollPane.setBounds(0, 0, DefaultValues.SELECTION_SCROLLPANE_WIDTH,
				DefaultValues.FRAME_HEIGHT - DefaultValues.MENUBAR_HEIGHT - DefaultValues.BIG_SPACER * 2);
		selectionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		selectionScrollPane.getVerticalScrollBar().setUnitIncrement(DefaultValues.SCROLL_SPEED);

		// add the panels
		window.add(selectionScrollPane);
		window.add(wsScrollPane);
	}

	private void initializeKeyListeners() {

		window.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				super.keyPressed(e);

				if (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK && e.getKeyCode() == KeyEvent.VK_C)
					pressCtrlC();
				else if (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK && e.getKeyCode() == KeyEvent.VK_A)
					pressCtrlA();
				else if (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK && e.getKeyCode() == KeyEvent.VK_N)
					pressCtrlN();
				else if (e.getModifiersEx() == KeyEvent.CTRL_DOWN_MASK && e.getKeyCode() == KeyEvent.VK_V)
					pressCtrlV();
				else if (e.getKeyCode() == KeyEvent.VK_SPACE)
					pressSpace();
				else if (e.getKeyCode() == KeyEvent.VK_DELETE)
					pressDel();
			}
		});
	}

	private void initializeWindowListeners() {

		window.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {

				super.windowClosing(e);

				if (moduleManager.isChangedSinceLastSave() && !moduleManager.isEmpty()) {

					int closeConfirmed = JOptionPane.showOptionDialog(window, "Save changes before closing?", "Close",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null,
							new String[] { "Yes", "No", "Cancel" }, "Yes");

					if (closeConfirmed == JOptionPane.YES_OPTION) {

						new Filehandler().saveFile();
						window.dispose();
					} else if (closeConfirmed == JOptionPane.NO_OPTION)
						window.dispose();
				} else
					window.dispose();
			}
		});

	}

	private void initializeSelection() {

		selectionPanel = new JPanel(new FlowLayout());
		selectionPanel.setPreferredSize(new Dimension(DefaultValues.SELECTION_WIDTH, DefaultValues.SELECTION_HEIGHT));
//		
//		// sticky note
//		StickyNote stickynote = new StickyNote(0, 0, 100, 100);
//		stickynote.lockMoving();
//		stickynote.setSelectable(false);
//		stickynote.getTextArea().setEditable(false);
//		stickynote.addMouseListener(new MouseAdapter() {
//
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				super.mouseClicked(e);
//				workspace.setToInsert(new StickyNote(0, 0, 150, 150));
//			}
//		});

//		selectionPanel.add(stickynote);

		// and
		Gate and = new AndGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);
		and.lockMoving();
		and.setSelectable(false);
		and.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				workspace.setToInsert(new AndGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT));
			}
		});

		selectionPanel.add(and);

		// or
		Gate or = new OrGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);
		or.lockMoving();
		or.setSelectable(false);
		or.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				workspace.setToInsert(new OrGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT));
			}
		});

		selectionPanel.add(or);

		// not
		Gate not = new NotGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);

		not.lockMoving();
		not.setSelectable(false);
		not.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				workspace.setToInsert(new NotGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT));
			}
		});

		selectionPanel.add(not);

		// nand
		Gate nand = new NandGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);
		nand.lockMoving();
		nand.setSelectable(false);
		nand.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				workspace.setToInsert(new NandGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT));
			}
		});
		selectionPanel.add(nand);

		// nor
		Gate nor = new NorGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);
		nor.lockMoving();
		nor.setSelectable(false);
		nor.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				workspace.setToInsert(new NorGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT));
			}
		});
		selectionPanel.add(nor);

		// xor
		Gate xor = new XorGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);
		xor.lockMoving();
		xor.setSelectable(false);
		xor.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				workspace.setToInsert(new XorGate(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT));
			}
		});

		selectionPanel.add(xor);

		// switch
		Module switchgate = new Switch(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);
		switchgate.lockMoving();
		switchgate.setSelectable(false);
		switchgate.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				workspace.setToInsert(new Switch(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT));
			}
		});

		selectionPanel.add(switchgate);

		// indicator
		Module indicator = new Indicator(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);
		indicator.lockMoving();
		indicator.setSelectable(false);
		indicator.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				workspace.setToInsert(new Indicator(0, 0, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT));
			}
		});

		selectionPanel.add(indicator);
	}

	public void pressDel() {
		moduleManager.deleteMarkedModules();
	}

	public void pressSpace() {
		moduleManager.unmark();
	}

	public void pressCtrlV() {
		moduleManager.pasteModulesFromClipboard();
	}

	public void pressCtrlC() {
		moduleManager.saveMarkedModulesToClipboard();
	}
	
	public void pressCtrlA() {
		
		ArrayList<Module> modules = moduleManager.getModules();
		
		for(Module module : modules)
			if(module.isSelectable())
				module.setSelected(true);
		
		workspace.updateModules();
	}
	
	public void pressCtrlN() {
		workspace.createNewWorkspace();
	}

	public void activateLinkMode() {

		if (from == null || to == null)
			return;

		if (from.equals(to))
			return;

		moduleManager.createLink(from, to);

		from = null;
		to = null;
	}

	public void activateUnlinkMode() {

		if (from == null || to == null)
			return;

		moduleManager.deleteLink(from, to);
		moduleManager.calculateModules();
	}
}
