package local.digitalsimulator.gates;

import local.digitalsimulator.modules.Link;

@SuppressWarnings("serial")
public class AndGate extends Gate {

	public AndGate(int x, int y, int width, int height) {
		super(x, y, width, height);

		content.setText("AND");
	}

	public AndGate(int x, int y) {
		super(x, y);

		content.setText("AND");
	}

	@Override
	public void calculate() {

		boolean result = true;

		for (Link link : inputs) {

			result &= link.isPowered();

			// optimization
			if (!result)
				break;
		}

		for (Link link : outputs) {

			link.setPowered(result);
		}
	}

	public AndGate clone() {
		return new AndGate(getX(), getY(), getWidth(), getHeight());
	}
}
