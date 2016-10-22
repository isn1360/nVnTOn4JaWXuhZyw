package triangle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App 
{
	public App(final String[] args) {
		// no need for arguments yet
	}
	
	private void showHelp() {
		System.out.println("Command format: '<command> arguments' (supported commands: t - triangle)");
	}
	
	private void showTriangleHelp() {
		System.out.println("Triangle command ('t') format: 't side1 side2 side3' (example: t 1.1 2.2 5.5)");
	}
	
	public int run() {
		
		Scanner inputScanner = null;
		try {
			inputScanner = new Scanner(System.in);
			CommandParser parser = new CommandParser();
			for (;;) {
				
				System.out.print("Enter command (h - help, q - quit): ");
				String input = inputScanner.nextLine();
				
				parser.setCommand(input);
				if (parser.isQuitCommand()) {
					break;
				} else if (parser.isHelpCommand()) {
					// show help and start over again
					showHelp();
					continue;
				} else if (parser.isQuitCommand()) {
					break;
				} 

				String command = parser.nextArgument();
				if ("t".equalsIgnoreCase(command)) {
					// triangle command
					processTriangle(parser);
				} else {
					// unknown command. just continue the loop
					System.out.print("Unknown input: ");
					System.out.println(input);
				}
			};
			
			inputScanner.close();
		} finally {
			if (inputScanner != null) inputScanner.close();
		}
		
		return 0;	// OK
	}
	
	private void processTriangle(final CommandParser parser) {
		try {
			parser.reset(); // start over with arguments
			parser.nextArgument();	// skip 't' command
			
			if (!parser.hasNextArgument() || "h".equalsIgnoreCase(parser.currentArgument())) {
				// no specific commands or h: show help
				showTriangleHelp();
				return;
			}
			
			// should be only three sides, but there may be more on the command line
			List<Double> sides = new ArrayList<Double>();
			while (parser.hasNextArgument()) {
				sides.add(parser.nextArgumentAsDouble());
			}

			Triangle triangle = new Triangle(sides);
			System.out.println(triangle.toString());
			
		} catch (Exception e) {
			// just print the message and continue with next user input
			System.out.print("ERROR: ");
			System.out.println(e.getMessage());
		}
		
	}
	
	
    public static void main(String[] args)
    {
    	try {
    		System.exit(new App(args).run());
    	} catch (Throwable t) {
    		// just in case
    		t.printStackTrace();
    	}
    	System.exit(1);
    }
}
