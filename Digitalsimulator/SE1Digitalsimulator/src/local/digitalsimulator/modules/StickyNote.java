package local.digitalsimulator.modules;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class StickyNote extends Module {

	private static final long serialVersionUID = -5577678444042577118L;
	private JTextArea text = new JTextArea();
	
	public StickyNote(int x, int y, int width, int height) {
		super(x, y, width, height);
		
		content.setText("Note");
		content.setBounds(width/2 + content.getWidth(), 10, width, height);
		
		setLayout(null);
		
		setSelectable(false);
		
		text.setBounds(0, 20, width, height);
		text.setWrapStyleWord(true);
		
		JScrollPane scroll = new JScrollPane(text);
		scroll.setBounds(0, 20, width, height);
		add(scroll);
	}
	
	@Override
	public void calculate() {
	}

	@Override
	public Module clone() {
		return new StickyNote(getX(), getY(), getWidth(), getHeight());
	}
	
	public JTextArea getTextArea() {
		return text;
	}
}
