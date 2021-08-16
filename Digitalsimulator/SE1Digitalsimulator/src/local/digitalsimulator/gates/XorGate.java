package local.digitalsimulator.gates;

import local.digitalsimulator.modules.Link;

@SuppressWarnings("serial")
public class XorGate extends Gate {

	public XorGate(int x, int y, int width, int height) {
		super(x, y, width, height);

		content.setText("XOR");
	}

	public XorGate(int x, int y) {
		super(x, y);

		content.setText("XOR");
	}

	@Override
	public void calculate() {

		boolean result = false;

		for (Link link : inputs) {

			result ^= link.isPowered();
		}

		for (Link link : outputs) {

			link.setPowered(result);
		}
	}

	public XorGate clone() {
		return new XorGate(getX(), getY(), getWidth(), getHeight());
	}
}
