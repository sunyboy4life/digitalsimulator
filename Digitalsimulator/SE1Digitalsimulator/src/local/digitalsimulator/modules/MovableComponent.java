package local.digitalsimulator.modules;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import local.digitalsimulator.main.Theme;
import local.digitalsimulator.main.Window;

@SuppressWarnings("serial")
public class MovableComponent extends JPanel implements MouseListener, MouseMotionListener {

	private Point start;
	private boolean lockMovement = false;
	private boolean lockConnections = false;

	private boolean isSelected = false;
	private boolean isSelectable = true;

	public MovableComponent() {

	}

	public MovableComponent(int width, int height) {

		setPreferredSize(new Dimension(width, height));
		setBackground(Color.GREEN);
		setVisible(true);
		setDoubleBuffered(true);
		addMouseListener(this);
		addMouseMotionListener(this);
	}

	public MovableComponent(int width, int height, int x, int y) {
		this(width, height);
		setLocation(x, y);
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		Point loc = SwingUtilities.convertPoint(this, e.getPoint(), getParent());
		JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
		Rectangle bounds = SwingUtilities.convertRectangle(window, getParent().getBounds(), getParent());

		int deltax = loc.x - start.x;
		int deltay = loc.y - start.y;

		if (!isSelected) {

			Point currNewloc = getLocation();
			currNewloc.translate(deltax, deltay);

			currNewloc.x = Math.max(currNewloc.x, 0);
			currNewloc.y = Math.max(currNewloc.y, 0);

			currNewloc.x = (int) Math.min(currNewloc.x, getParent().getWidth() - getWidth());
			currNewloc.y = (int) Math.min(currNewloc.y, getParent().getHeight() - getHeight());

			if (!bounds.contains(currNewloc))
				return;

			if (lockMovement)
				return;

			Window.getWindow().getModuleManager().setChangedSinceLastSave(true);
			setLocation(currNewloc);
			
			getParent().repaint();
			start = loc;
			return;
		}

		if (SwingUtilities.isLeftMouseButton(e)) {

			ModuleManager manager = Window.getWindow().getModuleManager();
			for (Component mc : manager.getMarkedModules()) {

				Point newloc = mc.getLocation();
				newloc.translate(deltax, deltay);

				newloc.x = Math.max(newloc.x, 0);
				newloc.y = Math.max(newloc.y, 0);

				newloc.x = (int) Math.min(newloc.x, mc.getParent().getWidth() - mc.getWidth());
				newloc.y = (int) Math.min(newloc.y, mc.getParent().getHeight() - mc.getHeight());

				if (!bounds.contains(newloc))
					return;

				if (mc instanceof MovableComponent)
					if (((MovableComponent) mc).isMovementLocked())
						continue;

				Window.getWindow().getModuleManager().setChangedSinceLastSave(true);
				mc.setLocation(newloc);
			}
		}

		getParent().repaint();
		start = loc;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	public void lockMoving() {
		lockMovement = true;
	}

	public boolean isMovementLocked() {
		return lockMovement;
	}

	public void unlockMoving() {
		lockMovement = false;
	}

	public void lockConnections() {
		lockConnections = true;
	}

	public void unlockConnections() {
		lockConnections = false;
	}

	public boolean getConnectionsLocked() {
		return lockConnections;
	}

//	@Override
//	public void mouseClicked(MouseEvent e) {
//		
//		if(e.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(e)) {
//			
//			GateBase gb = (GateBase) e.getComponent();
//			
//			if(!(gb.getParent() instanceof GateContainer))
//				return;
//			
//			Component comp = (Component) gb.copy();
//			comp.setBounds(300, 200, gb.getWidth(), gb.getHeight());
//			
//			WorkSpace ws = MainWindow.getMainWindow().getWorkSpace();
//			ws.add(comp);
//			
//			ws.revalidate();
//			ws.repaint();
//		}
//		
//		if(SwingUtilities.isLeftMouseButton(e)) {
//			if(getParent() instanceof GateContainer) {
//				
//				for(Component c : MainWindow.getMainWindow().getGateContainer().getComponents()) {
//					((GateBase)c).setSelected(false);
//				}
//			}
//		}
//	}

	@Override
	public void mousePressed(MouseEvent e) {
		start = SwingUtilities.convertPoint(this, e.getPoint(), getParent());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		start = null;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	// returns whether the module is selected
	public boolean isSelected() {
		return isSelected;
	}

	// sets if the module is selected
	public void setSelected(boolean selected) {

		if(!isSelectable)
			return;
		
		if (selected == true)
			setBorder(Theme.MODULE_BORDER);
		else
			setBorder(null);
		
		isSelected = selected;
	}
	// for later use, if you want modules to be selectable by clicking on them (can
	// be used to lock selecting specific modules)
	public void setSelectable(boolean selectable) {
		isSelectable = selectable;
	}
	
	public boolean isSelectable() {
		return isSelectable;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		// TODO: implement if necessary
		// For example: add module if user double clicked or clicked once and clicked
		// somewhere onto the workspace
		if (isSelectable)
			setSelected(true);
	}
}
