package duke;

import java.io.FileNotFoundException;
import java.io.IOException;

import duke.command.Command;
import duke.ui.Ui;

/**
 * The main class of the Duke program.
 */
public class Duke {
    private static final String FILE_NAME = "duke.state";

    private TaskList tasks;
    private final Storage storage;
    private final Ui ui;

    /**
     * The constructor for the Duke class.
     *
     * @param fileName file to store state to
     */
    public Duke(String fileName) {
        storage = new Storage(fileName);
        ui = new Ui();
        try {
            tasks = storage.readTaskList();
        } catch (IOException | ClassNotFoundException e) {
            tasks = new TaskList();
        }
    }

    /**
     * Default constructor to launch from JavaFX.
     */
    public Duke() {
        this(FILE_NAME);
    }

    public String getResponse(String input) {
        return "Hello";
    }

    /**
     * The read-eval-print loop of the program.
     */
    // Solution below adapted from https://nus-cs2103-ay2324s2.github.io/website/schedule/week3/project.html#a-moreoop
    public void repl() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (Parser.InvalidCommandType e) {
                ui.showCommandNotFound(e.getCommand());
            } catch (Parser.InvalidCommandData e) {
                ui.showError(e);
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * The main loop.
     *
     * @param args program parameters
     */
    public static void main(String[] args) {
        Duke duke = new Duke();
        boolean successful = false;
        try {
            duke.tasks = duke.storage.readTaskList();
            successful = true;
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find state file \"" + FILE_NAME + "\"");
        } catch (IOException e) {
            System.out.println("Cannot read from state file \"" + FILE_NAME + "\"");
        } catch (ClassNotFoundException e) {
            System.out.println("The data has been corrupted");
        } finally {
            if (!successful) {
                System.out.println("Continuing with no saved state.");
            }
        }

        duke.repl();

        try {
            duke.storage.writeTaskList(duke.tasks);
        } catch (FileNotFoundException e) {
            System.out.println("Cannot find state file \"" + FILE_NAME + "\"");
        } catch (IOException e) {
            System.out.println("Cannot write to state file \"" + FILE_NAME + "\"");
        }
    }
}
