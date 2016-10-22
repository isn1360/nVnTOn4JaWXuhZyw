package triangle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;


import org.junit.Test;

public class CommandParserTest 
{
	@Test
    public void testNoCommand() {
		CommandParser parser = new CommandParser();
		
		parser.setCommand(null);
		assertEquals(true, parser.getArguments().isEmpty());
		assertNull(parser.nextArgument());
		
		parser.setCommand("");
		assertEquals(true, parser.getArguments().isEmpty());
		assertNull(parser.nextArgument());
    }
	
	@Test
	public void testReset() {
		CommandParser parser = new CommandParser();
		parser.setCommand("A B");
		
		assertEquals("A", parser.nextArgument());
		parser.reset();
		assertEquals("A", parser.nextArgument());
		assertEquals("B", parser.nextArgument());
		parser.reset();
		assertEquals("A", parser.nextArgument());
		assertEquals("B", parser.nextArgument());
	}
	
	@Test
	public void testHasNextArgumentTest() {
		CommandParser parser = new CommandParser();
		parser.setCommand("A B C");
		
		assertTrue(parser.hasNextArgument());
		parser.nextArgument();	// "B C" left
		assertTrue(parser.hasNextArgument());
		parser.nextArgument();	// "C" left
		assertTrue(parser.hasNextArgument());
		parser.nextArgument();
		assertFalse(parser.hasNextArgument());
	}
	
	@Test
	public void testArguments() {
		CommandParser parser = new CommandParser();
		checkArguments(parser, "1 2 3 4", "1", "2", "3", "4");
		checkArguments(parser, "abc def ghi jkl mno", "abc", "def", "ghi", "jkl", "mno");
	}
	
	@Test
	public void testDoubleArguments() {
		CommandParser parser = new CommandParser();
		checkArguments(parser, "1.1 2.2 3.3 4.4", 1.1d, 2.2d, 3.3d, 4.4d);
	}
	
	@Test
	public void testMixedArguments() {
		CommandParser parser = new CommandParser();
		checkArguments(parser, "1.1 ABC 3.3 DEF", 1.1d, "ABC", 3.3d, "DEF");
	}
	
	@Test
	public void testCurrentArgument() {
		CommandParser parser = new CommandParser();
		parser.setCommand("A B C");
		assertEquals("A", parser.currentArgument());
		assertEquals("A", parser.currentArgument());
		parser.nextArgument();
		assertEquals("B", parser.currentArgument());
		assertEquals("B", parser.currentArgument());
		parser.nextArgument();
		assertEquals("C", parser.currentArgument());
		assertEquals("C", parser.currentArgument());
		parser.nextArgument();
		assertEquals(null, parser.currentArgument());
	}
	
	
	
	private void checkNextArgument(CommandParser parser, Object expected) {
		Object nextArgument = null;
		if (expected instanceof String) {
			nextArgument = parser.nextArgument();
		} else if (expected instanceof Double) {
			nextArgument = parser.nextArgumentAsDouble();
		} else if (expected == null) {
			// nextArgument already null
		} else {
			throw new IllegalArgumentException(String.format("Excpected object '%s' of class '%s' not supported.", String.valueOf(expected), expected.getClass().getName()));
		}
		
		assertEquals(expected, nextArgument);
	}
	
	private void checkArguments(CommandParser parser, String command, Object... expectedArguments) {
		parser.setCommand(command);
		for (Object expectedArgument : expectedArguments) {
			checkNextArgument(parser, expectedArgument);
		}
	}
	
	@Test
    public void testHelpCommand() {
		CommandParser parser = new CommandParser();
		checkHelpCommand(parser, "h", true);
		checkHelpCommand(parser, "H", true);
		checkHelpCommand(parser, "h 1 2 3", true);
		checkHelpCommand(parser, "H 1 2 3", true);
		
		checkHelpCommand(parser, "q", false);
		checkHelpCommand(parser, "Q", false);
    }
	
	private void checkHelpCommand(CommandParser parser, String helpCommand, boolean isHelp) {
		parser.setCommand(helpCommand);
		assertEquals(String.format("Command '%s' check failed.", helpCommand), isHelp, parser.isHelpCommand());
	}
	
	@Test
    public void testQuitCommand() {
		CommandParser parser = new CommandParser();
		checkQuitCommand(parser, "q", true);
		checkQuitCommand(parser, "Q", true);
		checkQuitCommand(parser, "q 1 2 3", true);
		checkQuitCommand(parser, "Q 1 2 3", true);
		
		checkQuitCommand(parser, "h", false);
		checkQuitCommand(parser, "H", false);
    }
	
	private void checkQuitCommand(CommandParser parser, String quitpCommand, boolean isQuit) {
		parser.setCommand(quitpCommand);
		assertEquals(String.format("Command '%s' check failed.", quitpCommand), isQuit, parser.isQuitCommand());
	}
	

	
}
