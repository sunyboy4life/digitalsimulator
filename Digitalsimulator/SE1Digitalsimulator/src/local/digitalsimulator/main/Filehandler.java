package local.digitalsimulator.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import local.digitalsimulator.modules.Module;
import local.digitalsimulator.modules.ModuleManager;

@SuppressWarnings("serial")
public class Filehandler extends JPanel {

	public void saveFile() {

		JFileChooser chooser = new JFileChooser(".") {
			@Override
			public void approveSelection() {

				File file;

				if (getTypeDescription(getSelectedFile()).equals("DSM-Datei")
						|| getSelectedFile().getName().equals("myWorkspace.dsm")) {
					file = new File(getSelectedFile().toString());
				} else
					file = new File(getSelectedFile() + ".dsm");

				if (file.exists() && getDialogType() == SAVE_DIALOG) {

					int result = JOptionPane.showConfirmDialog(this,
							"File already exists. Do you want to overwrite it?", "File already exists",
							JOptionPane.YES_NO_CANCEL_OPTION);

					switch (result) {

					case JOptionPane.YES_OPTION:
						super.approveSelection();
						break;

					case JOptionPane.NO_OPTION:
						break;

					case JOptionPane.CLOSED_OPTION:
						break;

					case JOptionPane.CANCEL_OPTION:
						break;
					}
				}
				if (!file.exists())
					super.approveSelection();
			}
		};

		FileFilter filter = new FileNameExtensionFilter("DSM (*.dsm)", "dsm");
		chooser.setFileFilter(filter);
		chooser.setSelectedFile(new File("myWorkspace.dsm"));
		int option = chooser.showSaveDialog(Window.getWindow().getWindowFrame());
		// fix for if chosen save first, while file already exists but choose to cancel in the end -> cancels saving
		if (option == JFileChooser.CANCEL_OPTION)
			return;

		
		String extension = "";
		int i = chooser.getSelectedFile().getName().lastIndexOf('.');
		
		if (i > 0) {
		    extension = chooser.getSelectedFile().getName().substring(i+1);
		}
		
		File wsFile;
		// if selected file has dsm extension -> no extension added
		if(extension.equals("dsm")){
			wsFile = new File(chooser.getSelectedFile().toString());
		} else
			wsFile = new File(chooser.getSelectedFile() + ".dsm");

		ModuleManager moduleManager = Window.getWindow().getModuleManager();

		try {

			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(wsFile));
			os.writeObject(moduleManager);
			Window.getWindow().getModuleManager().setChangedSinceLastSave(false);
			os.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadFile() {

		JFileChooser chooser = new JFileChooser(".") {
			public void approveSelection() {

				File file = new File(getSelectedFile().toString());

				if (!file.exists() && getDialogType() == OPEN_DIALOG) {

					Object[] options = { "OK" };
					int result = JOptionPane.showOptionDialog(null, "File not found. Check the filename and try again.",
							"File not found", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options,
							options[0]);

					switch (result) {

					case JOptionPane.OK_OPTION:
						setSelectedFile(null); // prevent FileNotFoundException when canceling after nonexistent file
						break;

					case JOptionPane.CLOSED_OPTION:
						setSelectedFile(null);
						break;
					}

				}
				if (file.exists())
					super.approveSelection();

			}
		};

		FileFilter filter = new FileNameExtensionFilter("DSM (*.dsm)", "dsm");
		chooser.setFileFilter(filter);
		int option = chooser.showOpenDialog(Window.getWindow().getWindowFrame());
		boolean changed = Window.getWindow().getModuleManager().isChangedSinceLastSave();
		boolean emptyWorkspace = Window.getWindow().getModuleManager().isEmpty();

		if (!emptyWorkspace && option == JFileChooser.APPROVE_OPTION && changed) {

			int save = JOptionPane.showConfirmDialog(null, "Save current Workspace before loading new one?", "Save",
					JOptionPane.YES_NO_OPTION);

			if (save == JOptionPane.YES_OPTION)
				new Filehandler().saveFile();
		}

		if (chooser.getSelectedFile() == null)
			return;

		try {

			ObjectInputStream is = new ObjectInputStream(new FileInputStream(chooser.getSelectedFile()));
			ModuleManager moduleManager = (ModuleManager) is.readObject();
			Window.getWindow().setModuleManager(moduleManager);
			Window.getWindow().getWorkspace().removeAll();
			Window.getWindow().getModuleManager().setChangedSinceLastSave(false);
			is.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		addModulesToWorkspace();
	}

	private void addModulesToWorkspace() {

		ArrayList<Module> modules = Window.getWindow().getModuleManager().getModules();
		Workspace workspace = Window.getWindow().getWorkspace();

		for (int i = 0; i < modules.size(); i++)
			workspace.add(modules.get(i));

		workspace.updateModules();
	}
}