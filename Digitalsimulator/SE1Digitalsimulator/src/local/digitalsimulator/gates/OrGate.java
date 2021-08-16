package local.digitalsimulator.gates;

import local.digitalsimulator.modules.Link;

@SuppressWarnings("serial")
public class OrGate extends Gate {

	public OrGate(int x, int y, int width, int height) {
		super(x, y, width, height);

		content.setText("OR");
	}

	public OrGate(int x, int y) {
		super(x, y);

		content.setText("OR");
	}

	@Override
	public void calculate() {

		boolean result = false;

		for (Link link : inputs) {

			if (link.isPowered()) {
				result = true;
				break;
			}
		}

		for (Link link : outputs) {

			link.setPowered(result);
		}
	}

	public OrGate clone() {
		return new OrGate(getX(), getY(), getWidth(), getHeight());
	}
}
