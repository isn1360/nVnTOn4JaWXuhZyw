package triangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandParser {

	private int currentIndex = 0;
	private List<String> arguments;

	/**
	 * Sets command string.
	 * @param command String, contains space separated command arguments
	 */
	public void setCommand(final String command) {
		if (command != null && !command.isEmpty()) {
			arguments = Collections.unmodifiableList(Arrays.asList(command.split(" ")));
		} else {
			// no arguments
			arguments = Collections.unmodifiableList(new ArrayList<String>(0));
		}
		reset();
	}

	public void reset() {
		currentIndex = 0;
	}

	/**
	 * Tells if there are more command arguments available.
	 * @return
	 */
	public boolean hasNextArgument() {
		return currentIndex < arguments.size();
	}
	
	/**
	 * Returns next available command argument.
	 * @return String argument or <code>null</code> if no more arguments are available.
	 */
	public String nextArgument() {
		if (!hasNextArgument()) {
			// no more arguments left
			return null;
		}
		return arguments.get(currentIndex++);
	}
	
	/**
	 * Peeks current command argument.
	 * @return
	 */
	public String currentArgument() {
		if (currentIndex >= arguments.size()) {
			return null;
		}
		return arguments.get(currentIndex);
	}
	
	/**
	 * Returns next command line argument converted to double.
	 * 
	 * @return
	 */
	public Double nextArgumentAsDouble() {
		String currentArgument = currentArgument();
		if (currentArgument == null) {
			return null;
		}
		Double value = toDouble(currentArgument);

		// OK, value parsed properly, increase current index and return it
		currentIndex++;
		return value;
	}
	
	private Double toDouble(String doubleString) {
		try {
			return Double.valueOf(doubleString);
		} catch (NumberFormatException e) {
			// throw a little more descriptive number format exception than it is by default
			throw new NumberFormatException(String.format("String '%s' cannot be converted to double value.", doubleString));
		}
	}
	
	/**
	 * Returns parsed arguments. The list is unmodifiable.
	 * @return
	 */
	public List<String> getArguments() {
		return arguments;
	}
	
	public boolean isQuitCommand() {
		return "q".equalsIgnoreCase(getFirstArgument());
	}
	
	public boolean isHelpCommand() {
		return "h".equalsIgnoreCase(getFirstArgument());
	}
	
	private String getFirstArgument() {
		return arguments.stream().findFirst().orElse(null);
	}
	
	
	
}
