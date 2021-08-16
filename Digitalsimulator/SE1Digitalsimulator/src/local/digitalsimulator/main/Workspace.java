package local.digitalsimulator.main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import local.digitalsimulator.gates.Gate;
import local.digitalsimulator.modules.Link;
import local.digitalsimulator.modules.Module;
import local.digitalsimulator.modules.ModuleManager;

@SuppressWarnings("serial")
public class Workspace extends JPanel {

	private Module toInsert = null;
	
	// selection with mouse drag
	private Point selectionStartPoint = null;
	private Point selectionEndPoint = null;
	private boolean isSelecting = false;
	private Rectangle rect = new Rectangle();
	
	public Workspace() {

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				if (Workspace.this.toInsert == null) {
					Window.getWindow().getModuleManager().unmark();
					return;
				}

				if (!SwingUtilities.isLeftMouseButton(e))
					return;

				
				Window.getWindow().getModuleManager().addNewModuleAt(toInsert, e.getPoint().x - toInsert.getWidth() / 2,
						e.getPoint().y - toInsert.getHeight() / 2);
				
				// out of bounds check, always inserts the element in the left top, no matter where clicked
				if(!Window.getWindow().getWorkspace().getBounds().contains(toInsert.getBounds()))
						toInsert.setLocation(0, 0);
				
				toInsert = null;
			}
		});
		
		addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				super.mousePressed(e);
				
				if(SwingUtilities.isLeftMouseButton(e)) {
					isSelecting = true;
					selectionStartPoint = e.getPoint();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
				
				if(SwingUtilities.isLeftMouseButton(e)) {

					if(isSelecting) {

						Rectangle rect = new Rectangle();
						try {
							rect.setFrameFromDiagonal(selectionEndPoint, selectionStartPoint);
							
							ModuleManager manager = Window.getWindow().getModuleManager();
							manager.unmark();
							
							for(Module module : manager.getModules()) {

								if(rect.intersects(module.getBounds()))
									module.setSelected(true);
							}
						} catch (Exception e2) {
						} finally {
							selectionStartPoint = null;
							selectionEndPoint = null;
							isSelecting = false;
						}
					}
				}
				repaint();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				super.mouseDragged(e);
				
				if(isSelecting) {
					selectionEndPoint = e.getPoint();
					repaint();
				}
			}
		});
	}

	public void createNewWorkspace() {

		if (!Window.getWindow().getModuleManager().isEmpty() && Window.getWindow().getModuleManager().isChangedSinceLastSave()) {

			int save = JOptionPane.showConfirmDialog(null, "Save current Workspace before creating new one?", "Save",
					JOptionPane.YES_NO_OPTION);

			if (save == JOptionPane.YES_OPTION)
				new Filehandler().saveFile();
		}

		Window.getWindow().getModuleManager().clear();
		removeAll();
		updateModules();
	}
	
	public void updateModules() {
		
		revalidate();
		repaint();
	}

	public void setToInsert(Module module) {
		toInsert = module;
	}

	public Module getToInsert() {
		return toInsert;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.white);
		
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_NORMALIZE);
		
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		// visualize links
		for (Link l : Window.getWindow().getModuleManager().getLinks()) {
			
			int xFrom = l.getFrom().getX() + l.getFrom().getWidth() / 2;
			int yFrom = l.getFrom().getY() + l.getFrom().getHeight() / 2;
			int xTo = l.getTo().getX() + l.getTo().getWidth() / 2;
			int yTo = l.getTo().getY() + l.getTo().getHeight() / 2;
			
			if (l.getFrom() instanceof Gate) {
				
				xFrom = l.getFrom().getX() + l.getFrom().getWidth();
				yFrom = l.getFrom().getY() + l.getFrom().getHeight() / 2;
			}
			
			if (l.getTo() instanceof Gate) {
				
				xTo = l.getTo().getX();
				yTo = l.getTo().getY() + l.getTo().getHeight() / 2;
			}
			
			if (l.isPowered())
				g2.setColor(Theme.LINK_ACTIVE);
			else
				g2.setColor(Theme.LINK_INACTIVE);
			
			g2.drawLine(xFrom, yFrom, xTo, yTo);
			g2.setColor(Theme.CONNECTION_DOT);
			g2.fillOval(xFrom - 10, yFrom - 10, 20, 20);
			g2.fillOval(xTo - 10, yTo - 10, 20, 20);
		}
		
		if(isSelecting) {
			if(selectionStartPoint == null || selectionEndPoint == null)
				return;

			g2.setColor(Theme.SELECTION_COLOR);

			if(selectionEndPoint.getX() > selectionStartPoint.getX())
				rect.setFrameFromDiagonal(selectionEndPoint, selectionStartPoint);
			else
				rect.setFrameFromDiagonal(selectionStartPoint, selectionEndPoint);

			g2.setComposite(AlphaComposite.SrcOver.derive(0.2f));
			g2.fill(rect);
		}
	}
}
