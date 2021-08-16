package local.digitalsimulator.modules;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JLabel;

import local.digitalsimulator.main.Theme;
import local.digitalsimulator.main.Window;
import local.digitalsimulator.main.DefaultValues;

@SuppressWarnings("serial")
public abstract class Module extends MovableComponent implements Cloneable {

	protected ArrayList<Link> inputs = new ArrayList<Link>();
	protected ArrayList<Link> outputs = new ArrayList<Link>();

	protected int MIN_INPUTS = 0;
	protected int MIN_OUTPUTS = 0;
	protected int MAX_INPUTS = -1;
	protected int MAX_OUTPUTS = -1;

	public JLabel content = new JLabel("default");

	public Module() {
	}

	public Module(int x, int y, int width, int height) {

		addMouseListener(this);
		addMouseMotionListener(this);
		setBounds(x, y, width, height);
		setPreferredSize(new Dimension(width, height));
		setLocation(x, y);
		setBackground(Theme.MODULE_BACKGROUND);

		content.setForeground(Theme.MODULE_FONT);
		content.setBounds(0, 15, width, height);

		add(content);

		setVisible(true);
	}

	public Module(int x, int y) {
		this(x, y, DefaultValues.MODULE_WIDTH, DefaultValues.MODULE_HEIGHT);
	}

	public void addInput(Link link) {

		if (link == null)
			return;

		if (inputs.size() + 1 > MAX_INPUTS && MAX_INPUTS > 0)
			return;

		inputs.add(link);
	}

	public void deleteInput(Link link) {

		if (link == null)
			return;

		if (inputs.size() - 1 < MIN_INPUTS && MIN_INPUTS > 0)
			return;

		inputs.remove(link);
	}

	public void addOutput(Link link) {

		// TODO: Maybe add boolean return for add / delete so its easier in the UI
		if (link == null)
			return;

		if (outputs.size() + 1 > MAX_OUTPUTS && MAX_OUTPUTS > 0)
			return;

		outputs.add(link);
	}

	public void deleteOutput(Link link) {

		if (link == null)
			return;

		if (outputs.size() - 1 < MIN_OUTPUTS && MIN_OUTPUTS > 0)
			return;

		outputs.remove(link);
	}

	public boolean canAcceptInputs() {

		if (MAX_INPUTS < 0)
			return true;

		if (inputs.size() >= MAX_INPUTS)
			return false;

		return true;
	}

	public boolean canAcceptInputs(int inputs) {

		if (MAX_INPUTS < 0)
			return true;

		if (this.inputs.size() + inputs >= MAX_INPUTS)
			return false;

		return true;
	}

	public boolean canAcceptOutputs() {

		if (MAX_OUTPUTS < 0)
			return true;

		if (outputs.size() >= MAX_OUTPUTS)
			return false;

		return true;
	}

	public boolean canAcceptOutputs(int outputs) {

		if (MAX_OUTPUTS < 0)
			return true;

		if (this.outputs.size() + outputs >= MAX_OUTPUTS)
			return false;

		return true;
	}

	public abstract void calculate();

	public abstract Module clone();

	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);

		if (!selected)
			return;

		Window window = Window.getWindow();

		if (window.getFrom() == null)
			window.setFrom(this);
		else if (window.getTo() == null)
			window.setTo(this);
	}
}
