package local.digitalsimulator.gates;

import local.digitalsimulator.modules.Link;

@SuppressWarnings("serial")
public class NotGate extends Gate {

	public NotGate(int x, int y, int width, int height) {
		super(x, y, width, height);

		MAX_INPUTS = 1;

		content.setText("NOT");
	}

	public NotGate(int x, int y) {
		super(x, y);

		MAX_INPUTS = 1;

		content.setText("NOT");
	}

	@Override
	public void calculate() {

		if (inputs.isEmpty())
			return;

		for (Link output : outputs)
			output.setPowered(!inputs.get(0).isPowered());
	}

	public NotGate clone() {
		return new NotGate(getX(), getY(), getWidth(), getHeight());
	}
}
