package duke.ui;

import java.util.Scanner;

import duke.task.Task;

/**
 * The user interface management class. This class is the centralized interface for input and output to the program.
 */
public class Ui {
    private final Scanner scanner;
    private StringBuilder outputBuffer;

    /**
     * The constructor of the user interface.
     */
    public Ui() {
        scanner = new Scanner(System.in);
        outputBuffer = new StringBuilder();
    }

    /**
     * Flushes the output of the program at that state as a String.
     */
    public String flush() {
        var out = outputBuffer.toString();
        outputBuffer = new StringBuilder();
        return out;
    }

    /**
     * Prints the mascot.
     */
    public void showCat() {
        System.out.println(" |\\ /| ");
        System.out.println("=(O O)=");
        System.out.println(" /   \\ ");
    }

    /**
     * Shows the greeting message.
     */
    public void showWelcome() {
        showLine();
        showCat();
        System.out.println("The cat that lives in your walls pokes its head out.");
        System.out.println("Its waiting for you to ask something.");
        showLine();
    }

    /**
     * Shows the ending message.
     */
    public void showBye() {
        System.out.println("The cat recedes into the wall with a bored look on its face");
    }

    /**
     * Prints a horizontal line on the screen. Used to visually separate sections apart.
     */
    public void showLine() {
        outputBuffer.append("─".repeat(72)).append('\n');
    }

    public void showError(Exception e) {
        outputBuffer.append("The cat tilts its head and hands you an error report:\n").append(e.getMessage());
    }

    public void showNote(String str) {
        outputBuffer.append("The cat hands a note to you, it reads:\n").append(str);
    }

    public void showCommandNotFound(String command) {
        outputBuffer.append("The cat tilts its head. It doesn't know what command \"").append(command).append("\" is.");
    }

    /**
     * Shows an added task.
     *
     * @param task task that was added
     */
    public void showAddedTask(Task task) {
        outputBuffer.append("The cat scratches a mark on the wall and then hands you a receipt:\nAdded task ")
                .append(task.describe());
    }

    /**
     * Reads a command from the user.
     *
     * @return a string with a line of the user's input, or "bye" if input is empty
     */
    public String readCommand() {
        if (!scanner.hasNextLine()) {
            return "bye";
        }
        return scanner.nextLine();
    }
}
