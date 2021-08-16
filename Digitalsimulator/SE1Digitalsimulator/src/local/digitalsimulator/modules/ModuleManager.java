package local.digitalsimulator.modules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import local.digitalsimulator.main.Window;
import local.digitalsimulator.main.DefaultValues;

@SuppressWarnings("serial")
public class ModuleManager implements Serializable {

	ArrayList<Module> modules = new ArrayList<Module>();
	ArrayList<Link> links = new ArrayList<Link>();
	ArrayList<Module> clipboard = new ArrayList<Module>();

	private boolean changedSinceLastSave = false;

	public void deleteMarkedModules() {

		ArrayList<Module> marked = getMarkedModules();
		
		for(Iterator<Module> iter = marked.iterator(); iter.hasNext();) {
			
			Module module = iter.next();
			
			iter.remove();
			modules.remove(module);
			Window.getWindow().getWorkspace().remove(module);
			
			for(Iterator<Link> iterator = module.inputs.iterator(); iterator.hasNext();) {
				
				Link link = iterator.next();
				iterator.remove();
				links.remove(link);
				
				deleteLink(link);
				link.from.outputs.remove(link);
				link.to.inputs.remove(link);
				
				Window.getWindow().getModuleManager().calculateModules();
			}

			for(Iterator<Link> iterator = module.outputs.iterator(); iterator.hasNext();) {
				
				Link link = iterator.next();
				iterator.remove();
				
				deleteLink(link);
				links.remove(link);
				link.from.outputs.remove(link);
				link.to.inputs.remove(link);
				
				Window.getWindow().getModuleManager().calculateModules();
			}
		}
		
		Window.getWindow().getWorkspace().updateModules();
		changedSinceLastSave = true;
	}

	public ArrayList<Module> getMarkedModules() {

		ArrayList<Module> marked = new ArrayList<Module>();

		for(Module module : modules)
			if(module.isSelected())
				marked.add(module);

		return marked;
	}

	public void clear() {

		modules.clear();
		links.clear();

		// TODO: Check if this is needed, maybe unintended behavior
		clipboard.clear();
	}

	public void unmark() {

		for(Module module : modules)
			module.setSelected(false);

		Window.getWindow().setFrom(null);
		Window.getWindow().setTo(null);

		Window.getWindow().getWorkspace().updateModules();
	}

	public void addNewModule(Module module) {

		if(module == null)
			return;

		modules.add(module);
		Window.getWindow().getWorkspace().add(module);
		Window.getWindow().getWorkspace().updateModules();
		changedSinceLastSave = true;
	}

	public void addNewModuleAt(Module module, int x, int y) {

		module.setLocation(x, y);
		modules.add(module);
		Window.getWindow().getWorkspace().add(module);
		Window.getWindow().getWorkspace().updateModules();
		changedSinceLastSave = true;
	}

	public void saveMarkedModulesToClipboard() {

		ArrayList<Module> marked = this.getMarkedModules();

		clipboard.clear();

		for(Module module : marked)
			clipboard.add(module);
	}

	public void pasteModulesFromClipboard() {

		for(Module m : clipboard) {

			Module toPaste = m.clone();

			if (toPaste.getX() + DefaultValues.SMALL_SPACER < Window.getWindow().getWindowFrame().getWidth()
					&& toPaste.getY() + DefaultValues.SMALL_SPACER < Window.getWindow().getWindowFrame().getHeight())
				toPaste.setBounds(toPaste.getX() + DefaultValues.SMALL_SPACER,
						toPaste.getY() + DefaultValues.SMALL_SPACER, toPaste.getWidth(), toPaste.getHeight());

			modules.add(toPaste);
			Window.getWindow().getWorkspace().add(toPaste);
		}

		Window.getWindow().getWorkspace().updateModules();
		changedSinceLastSave = true;
	}

	public void saveModules(ArrayList<Module> modules) {

	}

	public boolean delete(Module module) { // TODO is this needed? we have deleteSelectedModules()

		Window.getWindow().getWorkspace().updateModules();

		Window.getWindow().setFrom(null);
		Window.getWindow().setTo(null);

		return false;
	}

	public void createLink(Module from, Module to) {

		for (Link link : links)
			if (link.from.equals(from) && link.to.equals(to))
				return;

		if (!from.canAcceptOutputs())
			return;

		if (!to.canAcceptInputs())
			return;

		links.add(new Link(from, to));

		Window.getWindow().getWorkspace().updateModules();
		changedSinceLastSave = true;
	}

	public void deleteLink(Link link) {

		links.remove(link);
		Window.getWindow().getWorkspace().updateModules();
		changedSinceLastSave = true;
	}

	public void deleteLink(Module from, Module to) {

		for(Link l : links) {

			if(l.getFrom().equals(from) && l.getTo().equals(to)) {

				links.remove(l);
				Window.getWindow().getWorkspace().updateModules();
				break;
			}
		}

		changedSinceLastSave = true;
	}

	public void loadModules() {

	}

	public boolean isEmpty() {

		return modules.isEmpty();
	}

	public void calculateModules() {

		for(@SuppressWarnings("unused")
		Module m : modules)
			for(Module module : modules)
				module.calculate();
	}

	public ArrayList<Module> getModules() {

		return modules;
	}

	public boolean isChangedSinceLastSave() {
		return changedSinceLastSave;
	}

	public void setChangedSinceLastSave(boolean b) {
		changedSinceLastSave = b;
	}

	public ArrayList<Module> getModulesInClipboard() {
		return clipboard;
	}

	public ArrayList<Link> getLinks() {
		return links;
	}
}
