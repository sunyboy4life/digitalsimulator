package local.digitalsimulator.main;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class Theme {
	
	// Material Dark Theme
	public static final Color PRIMARY = new Color(0xF09A0A);
	public static final Color PRIMARY_VARIANT = new Color(0x3700B3);
	public static final Color SECONDARY = new Color(0x03DAC6);
	public static final Color BACKGROUND = new Color(0x121212);
	public static final Color SURFACE = new Color(0x121212);
	public static final Color ERROR = new Color(0xCF6679);
	public static final Color ON_PRIMARY = new Color(0x000000);
	public static final Color ON_SECONDARY = new Color(0x000000);
	public static final Color ON_BACKGROUND = new Color(0xFFFFFF);
	public static final Color ON_SURFACE = new Color(0xFFFFFF);
	public static final Color ON_ERROR = new Color(0x000000);
	
	// Modules
	public static final Color MODULE_BACKGROUND = new Color(0x666666);
	public static final Color MODULE_FONT = new Color(0xEAEAEA);
	public static final Color MODULE_SELECTED = new Color(0xABCDEF);
	public static final Border MODULE_BORDER = BorderFactory.createLineBorder(Theme.MODULE_SELECTED, 3);
	
	// Selection drag color
	public static final Color SELECTION_COLOR = new Color(0x0CADFA);
	
	// Switch
	public static final Color SWITCH_ACTIVE = new Color(0x5EC237);
	
	// Connection lines
	public static final Color LINK_INACTIVE = new Color(0xFF5C42);
	public static final Color LINK_ACTIVE = new Color(0x5DE228);
	public static final Color CONNECTION_DOT = new Color(0xA3A3A3);
}
