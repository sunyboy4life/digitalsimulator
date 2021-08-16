package local.digitalsimulator.modules;

import local.digitalsimulator.main.Theme;

@SuppressWarnings("serial")
public class Indicator extends Module {

	public Indicator(int x, int y, int width, int height) {
		super(x, y, width, height);

		MAX_OUTPUTS = 0;
		MAX_INPUTS = 1;
		MIN_INPUTS = 0;
		
		content.setText("LAMP");
	}

	// TODO: talk about what to do with the indicators @Nojo
	@Override
	public void calculate() {

		for (Link link : inputs) {

			if (link.isPowered()) {

				setBackground(Theme.PRIMARY);
				return;
			}
		}

		setBackground(Theme.MODULE_BACKGROUND);
	}

	public Indicator clone() {
		return new Indicator(getX(), getY(), getWidth(), getHeight());
	}
}
