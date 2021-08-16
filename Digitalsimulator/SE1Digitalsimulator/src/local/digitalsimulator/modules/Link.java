package local.digitalsimulator.modules;

import java.io.Serializable;

import local.digitalsimulator.main.Window;

public class Link implements Serializable {

	private static final long serialVersionUID = 609558741272757989L;
	
	Module from = null, to = null;
	private boolean isPowered = false;

	public Link(Module from, Module to) {

		this.from = from;
		this.to = to;
		
		from.outputs.add(this);
		to.inputs.add(this);
	}

	public void remove() {

		from.deleteOutput(this);
		to.deleteInput(this);
		Window.getWindow().getModuleManager().deleteLink(this);
	}

	public Module getFrom() {

		return from;
	}

	public Module getTo() {

		return to;
	}

	public void setFrom(Module from) {

		this.from = from;
	}

	public void setTo(Module to) {
		
		this.to = to;
	}

	public boolean isPowered() {

		return isPowered;
	}

	public void setPowered(boolean powered) {

		isPowered = powered;
	}
}
