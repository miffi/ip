package duke;

import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;

import duke.command.AddCommand;
import duke.command.Command;
import duke.command.CompleteCommand;
import duke.command.DeleteCommand;
import duke.command.ExitCommand;
import duke.command.FindCommand;
import duke.command.ListCommand;

/**
 * Used to parse user commands.
 */
public class Parser {
    /**
     * Parse one user command.
     * The user command is parsed from a string into a subclass of {@link Command} which can be then executed.
     * @throws InvalidCommandType when the given command (first word) is unknown
     * @throws InvalidCommandData when the inputs of the command aren't as expected
     */
    public static Command parse(String commandText) throws InvalidCommandType, InvalidCommandData {
        Scanner scanner = new Scanner(commandText);
        String type = scanner.next();

        if (Objects.equals(type, "bye")) {
            return new ExitCommand();
        } else if (Objects.equals(type, "list")) {
            return new ListCommand();
        }

        if (!scanner.hasNextLine()) {
            throw new InvalidCommandData();
        }
        String parameter = scanner.nextLine().trim();

        Command command;
        switch (type) {
        case "todo":
            command = new AddCommand(AddCommand.Type.Todo, parseComponents(parameter));
            break;
        case "deadline":
            command = new AddCommand(AddCommand.Type.Deadline, parseComponents(parameter));
            break;
        case "event":
            command = new AddCommand(AddCommand.Type.Event, parseComponents(parameter));
            break;
        case "mark":
            command = new CompleteCommand(parseIndex(parameter), true);
            break;
        case "unmark":
            command = new CompleteCommand(parseIndex(parameter), false);
            break;
        case "delete":
            command = new DeleteCommand(parseIndex(parameter));
            break;
        case "find":
            command = new FindCommand(parameter);
            break;
        default:
            throw new InvalidCommandType(type);
        }
        return command;
    }

    private static int parseIndex(String input) {
        return Integer.parseInt(input.trim()) - 1;
    }

    private static HashMap<String, String> parseComponents(String data) throws InvalidCommandData {
        HashMap<String, StringBuilder> builders = new HashMap<>();

        String key = "DESCRIPTION";
        String[] words = data.split(" +");
        for (String word : words) {
            if (word.startsWith("/")) {
                // Check if the previous key had any data given to it
                if (builders.get(key) == null) {
                    throw new InvalidCommandData(key);
                }

                key = word;
            } else {
                builders.compute(key, (k, v) -> (v == null) ? new StringBuilder(word) : v.append(" ").append(word));
            }
        }

        HashMap<String, String> components = new HashMap<>();
        builders.forEach((k, v) -> components.put(k, v.toString()));
        return components;
    }

    /**
     * Exception when the command is unknown.
     */
    public static class InvalidCommandType extends Exception {
        private final String command;

        /**
         * Creates an exception about an unknown command string.
         * @param command The name of the unknown command
         */
        public InvalidCommandType(String command) {
            super("Command \"" + command + "\" is invalid or not yet implemented.");
            this.command = command;
        }

        public String getCommand() {
            return command;
        }
    }

    /**
     * Exception when the data given to a particular
     * command doesn't match what is expected. The problem
     * can either be that no parameters are given, or that
     * a parameter is missing.
     */
    public static class InvalidCommandData extends Exception {
        public InvalidCommandData() {
            super("Parameters to command not given.");
        }

        public InvalidCommandData(String key) {
            super("No value given to " + key);
        }
    }
}
