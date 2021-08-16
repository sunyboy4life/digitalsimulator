package local.digitalsimulator.gates;

import local.digitalsimulator.modules.Module;

@SuppressWarnings("serial")
public abstract class Gate extends Module {

	public Gate(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	public Gate(int x, int y) {
		super(x, y);
	}
}
