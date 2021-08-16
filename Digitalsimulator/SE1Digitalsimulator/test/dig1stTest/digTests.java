package dig1stTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.Test;

import local.digitalsimulator.gates.AndGate;
import local.digitalsimulator.gates.NandGate;
import local.digitalsimulator.gates.NorGate;
import local.digitalsimulator.gates.NotGate;
import local.digitalsimulator.gates.OrGate;
import local.digitalsimulator.gates.XorGate;
import local.digitalsimulator.modules.Indicator;
import local.digitalsimulator.modules.Link;
import local.digitalsimulator.modules.Module;
import local.digitalsimulator.modules.ModuleManager;

public class digTests {

	// Construktor tests ---------------------
	// Gates creation

	// Gates with "reasonable" parameters
//	@Test
//	public void createfunctionalGates() {
//		new AndGate(0, 0);
//		new AndGate(1920, 1080);
//		new AndGate(10000, 5000);
//		new AndGate(200, 200, 500, 500);
//		new AndGate(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
//	}
//
//	// two gates above each other
//	@Test
//	public void createtwoGatesAboveEachOther() {
//		new AndGate(0, 0);
//		new AndGate(0, 0);
//	}
//
//	// Gates with negative location
//	@Test(expected = IllegalArgumentException.class)
//	public void createExceptionGateWithNegativeX() {
//		a = new AndGate(-1, 0);
//
//	}
//
//	@Test(expected = IllegalArgumentException.class)
//	public void createExceptionGateWithNegativeY() {
//		a = new AndGate(0, -1);
//	}
//
//	// "Invisible" gates
//	@Test(expected = IllegalArgumentException.class)
//	public void createExceptionGateInvisible() {
//		new AndGate(100, 100, 0, 0);
//	}
//
//	// Link creation
////Link functional
//	@Test
//	public void createfunctionalLinks() {
//		new Link(a, b);
//	}
//
//	// Links exceptions
//	@Test
//	public void createExceptionLinkWithNulls() {
//		boolean exceptionhappened = false;
//		try {
//			new Link(null, null);
//		} catch (Exception e) {
//			e.getMessage();
//			exceptionhappened = true;
//		}
//		assertTrue(exceptionhappened);
//	}
//
//	@Test
//	public void createExceptionLinkWithDoubles() {
//		boolean exceptionhappened = false;
//		try {
//			new Link(a, a);
//		} catch (Exception e) {
//			e.getMessage();
//			exceptionhappened = true;
//		}
//		assertTrue(exceptionhappened);
//	}

	// Constructor Tests --------------------------

	// Gate logic Test
	@Test
	public void firstANDTest() {

		AndGate y = new AndGate(100, 100);
		Indicator lamp = new Indicator(0, 0, 100, 100);
		Link input1 = new Link(null, y);
		Link input2 = new Link(null, y);
		Link output = new Link(y, lamp);
		y.addInput(input1);
		y.addInput(input2);
		y.addOutput(output);
		y.calculate();
		assertFalse(output.isPowered());
		input1.setPowered(true);
		y.calculate();
		assertFalse(output.isPowered());
		input2.setPowered(true);
		y.calculate();
		assertTrue(output.isPowered());
		input2.setPowered(false);
		y.calculate();
		assertFalse(output.isPowered());
	}

	@Test
	public void firstORTest() {

		OrGate y = new OrGate(100, 100);
		Indicator lamp = new Indicator(0, 0, 100, 100);
		Link input1 = new Link(null, y);
		Link input2 = new Link(null, y);
		Link output = new Link(y, lamp);
		y.addInput(input1);
		y.addInput(input2);
		y.addOutput(output);
		y.calculate();
		assertFalse(output.isPowered());
		input1.setPowered(true);
		y.calculate();
		assertTrue(output.isPowered());
		input2.setPowered(true);
		y.calculate();
		assertTrue(output.isPowered());

	}

	@Test
	public void firstNANDTest() {

		NandGate y = new NandGate(100, 100);
		Indicator lamp = new Indicator(0, 0, 100, 100);
		Link input1 = new Link(null, y);
		Link input2 = new Link(null, y);
		Link output = new Link(y, lamp);
		y.addInput(input1);
		y.addInput(input2);
		y.addOutput(output);
		y.calculate();
		assertTrue(output.isPowered());
		input1.setPowered(true);
		y.calculate();
		assertTrue(output.isPowered());
		input2.setPowered(true);
		y.calculate();
		assertFalse(output.isPowered());
		input2.setPowered(false);
		y.calculate();
		assertTrue(output.isPowered());
	}

	@Test
	public void firstNORTest() {

		NorGate y = new NorGate(100, 100);
		Indicator lamp = new Indicator(0, 0, 100, 100);
		Link input1 = new Link(null, y);
		Link input2 = new Link(null, y);
		Link output = new Link(y, lamp);
		y.addInput(input1);
		y.addInput(input2);
		y.addOutput(output);
		y.calculate();
		assertTrue(output.isPowered());
		input1.setPowered(true);
		y.calculate();
		assertFalse(output.isPowered());
		input2.setPowered(true);
		y.calculate();
		assertFalse(output.isPowered());

	}

	@Test
	public void firstXorTest() {

		XorGate y = new XorGate(100, 100);
		Indicator lamp = new Indicator(0, 0, 100, 100);
		Link input1 = new Link(null, y);
		Link input2 = new Link(null, y);
		Link output = new Link(y, lamp);
		y.addInput(input1);
		y.addInput(input2);
		y.addOutput(output);
		y.calculate();
		assertFalse(output.isPowered());
		input1.setPowered(true);
		y.calculate();
		assertTrue(output.isPowered());
		input2.setPowered(true);
		y.calculate();
		assertFalse(output.isPowered());
		input1.setPowered(false);
		y.calculate();
		assertTrue(output.isPowered());
	}

	@Test
	public void firstNotTest() {

		NotGate y = new NotGate(100, 100);
		Indicator lamp = new Indicator(0, 0, 100, 100);
		Link input1 = new Link(null, y);
		Link output = new Link(y, lamp);
		y.addInput(input1);
		y.addOutput(output);
		y.calculate();
		assertTrue(output.isPowered());
		input1.setPowered(true);
		y.calculate();
		assertFalse(output.isPowered());
		input1.setPowered(false);
		y.calculate();
		assertTrue(output.isPowered());

	}

	// calculate advanced tests

	// TO-DO letztes AssertTrue gibt falsches Ergebnis
	@Test
	public void advancedLogicTest() {

		AndGate a = new AndGate(100, 100);
		AndGate b = new AndGate(100, 100);
		OrGate c = new OrGate(100, 100);
		Indicator lamp = new Indicator(0, 0, 100, 100);

		Link inputAND1 = new Link(null, a);
		Link inputAND2 = new Link(null, a);
		Link outputAND1 = new Link(a, c);
		Link inputAND3 = new Link(null, b);
		Link inputAND4 = new Link(null, b);
		Link outputAND2 = new Link(b, c);
		Link inputOR1 = outputAND1;
		Link inputOR2 = outputAND2;
		Link outputOR = new Link(c, lamp);

		a.addInput(inputAND1);
		a.addInput(inputAND2);
		b.addInput(inputAND3);
		b.addInput(inputAND4);
		c.addInput(inputOR1);
		c.addInput(inputOR2);

		a.addOutput(outputAND1);
		b.addOutput(outputAND2);
		c.addOutput(outputOR);
		
		// check with calculate and and gates false
		inputAND1.setPowered(true);
		inputAND3.setPowered(true);

		a.calculate();
		b.calculate();
		c.calculate();
		lamp.calculate();

		assertFalse(outputOR.isPowered());
		
		// check without calculate and change
		inputAND2.setPowered(true);
		assertFalse(outputOR.isPowered());

		a.calculate();
		b.calculate();
		c.calculate();
		lamp.calculate();

		assertTrue(outputOR.isPowered());
	}

	// marked Modules tests
	@Test
	public void getAndDeleteModules() {
		AndGate x = new AndGate(100, 100);
		AndGate y = new AndGate(150, 100);
		ModuleManager mm = new ModuleManager();
		x.setSelected(true);
		y.setSelected(true);
		mm.addNewModule(null);
		mm.addNewModule(y);
		mm.addNewModule(x);

		ArrayList<Module> mods = mm.getModules();
		assertEquals(2, mods.size());

		mm.deleteMarkedModules();
		mods = mm.getModules();
		assertTrue(mods.isEmpty());
	}

	// Clipboard tests
	@Test
	public void getEmptyClipboardTest() {
		ModuleManager mm = new ModuleManager();
		mm.saveMarkedModulesToClipboard();
		ArrayList<Module> cb = mm.getModulesInClipboard();
		for (Module module : cb) {
			assertEquals(null, module);
		}

	}

	@Test
	public void getFilledClipboardTest() {
		AndGate x = new AndGate(100, 100);
		AndGate y = new AndGate(100, 100);
		ModuleManager mm = new ModuleManager();
		mm.addNewModule(x);
		mm.addNewModule(y);
		x.setSelected(true);
		y.setSelected(true);
		mm.saveMarkedModulesToClipboard();
		ArrayList<Module> cb = mm.getModulesInClipboard();
		int counter = 0;
		for (Module module : cb) {
			if (module == x)
				counter++;
			else if (module == y)
				counter++;
			else
				fail("Wrong Modules in clipboard");

		}
		assertEquals(2, counter);
	}

	@Test
	public void unmarkTest() {
		AndGate x = new AndGate(100, 100);
		AndGate y = new AndGate(100, 100);
		AndGate z = new AndGate(100, 100);
		ModuleManager mm = new ModuleManager();
		mm.addNewModule(x);
		mm.addNewModule(y);
		mm.addNewModule(z);
		x.setSelected(true);
		y.setSelected(true);
		mm.saveMarkedModulesToClipboard();
		ArrayList<Module> cb = mm.getModulesInClipboard();
		mm.unmark();
		mm.saveMarkedModulesToClipboard();
		cb = mm.getModulesInClipboard();
		int counter = 0;
		for (int i = 0; i < cb.size(); i++) {
			counter++;
		}
		assertEquals(0, counter);

	}
}
