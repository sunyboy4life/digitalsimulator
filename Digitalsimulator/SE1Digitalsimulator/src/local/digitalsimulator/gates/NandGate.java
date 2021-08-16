package local.digitalsimulator.gates;

import local.digitalsimulator.modules.Link;

@SuppressWarnings("serial")
public class NandGate extends Gate {

	public NandGate(int x, int y, int width, int height) {
		super(x, y, width, height);

		content.setText("NAND");
	}

	public NandGate(int x, int y) {
		super(x, y);

		content.setText("NAND");
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

			link.setPowered(!result);
		}
	}

	public NandGate clone() {
		return new NandGate(getX(), getY(), getWidth(), getHeight());
	}
}
