package local.digitalsimulator.gates;

import local.digitalsimulator.modules.Link;

@SuppressWarnings("serial")
public class NorGate extends Gate {

	public NorGate(int x, int y, int width, int height) {
		super(x, y, width, height);

		content.setText("NOR");
	}

	public NorGate(int x, int y) {
		super(x, y);

		content.setText("NOR");
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

			link.setPowered(!result);
		}
	}

	public NorGate clone() {
		return new NorGate(getX(), getY(), getWidth(), getHeight());
	}
}
