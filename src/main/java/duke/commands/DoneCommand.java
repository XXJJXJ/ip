package duke.commands;

import duke.exceptions.DukeFileException;
import duke.exceptions.TaskIsCompleteException;
import duke.exceptions.TaskNotFoundException;
import duke.task.Task;
import duke.util.Storage;
import duke.util.TaskList;
import duke.util.Ui;

/**
 * This is a DoneCommand class that extends Command.
 */
public class DoneCommand extends Command {

    /**
     * This is the constructor of a DoneCommand.
     *
     * @param index An int representing the index of task to be marked done.
     */
    public DoneCommand(int index) {
        super("done", index);
    }

    @Override
    public void execute(TaskList taskList, Storage store, Ui ui)
            throws DukeFileException, TaskIsCompleteException, TaskNotFoundException {
        if (taskList.getSize() - 1 >= this.index && this.index >= 0) {
            taskList.markTask(this.index, store, ui);
        } else {
            throw new TaskNotFoundException(this.index + 1);
        }
    }

    @Override
    public String toString() {
        return this.command + " " + (this.index + 1);
    }
}
