package local.digitalsimulator.modules;

import java.awt.event.MouseEvent;

import local.digitalsimulator.main.Theme;

@SuppressWarnings("serial")
public class Switch extends Module {

	boolean active;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
		
		if(active)
			setBackground(Theme.SWITCH_ACTIVE);
		else
			setBackground(Theme.MODULE_BACKGROUND);
	}

	public Switch(int x, int y) {
		super(x, y);

		MAX_INPUTS = 0;

		active = false;
		content.setText("SWITCH");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		
		if(isSelectable())
			active ^= true;
		
		if(active)
			setBackground(Theme.SWITCH_ACTIVE);
		else
			setBackground(Theme.MODULE_BACKGROUND);
	}
	
	public Switch(int x, int y, int width, int height) {
		this(x, y);
		this.setBounds(x, y, width, height);
	}

	@Override
	public void calculate() {
		
		for (Link link : outputs)
			link.setPowered(active);
	}

	public Switch clone() {
		return new Switch(getX(), getY(), getWidth(), getHeight());
	}
}
