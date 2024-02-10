package duke.command;

import duke.Storage;
import duke.TaskList;
import duke.task.Task;
import duke.ui.Ui;

/**
 * A command to change the completion status of a task.
 */
public class CompleteCommand extends Command {
    private final int index;
    private final boolean isComplete;

    /**
     * Constructs a command that changes the completion status of a task.
     *
     * @param index      the index of the task to change
     * @param isComplete whether to change the task to completed leave it as pending
     */
    public CompleteCommand(int index, boolean isComplete) {
        this.index = index;
        this.isComplete = isComplete;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        try {
            Task task = tasks.getTask(index);
            task.setStatus(isComplete);
            ui.showNote("Set task to " + task.status() + ":\n  " + task + "\n");
        } catch (TaskList.TaskNotFound e) {
            ui.showError(e);
        }
    }
}
