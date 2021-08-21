package duke.exceptions;

/**
 * This is a duke.exceptions.TaskNotFoundException that extends duke.exceptions.DukeException.
 */
public class TaskNotFoundException extends DukeException {

    private final int index;

    public TaskNotFoundException(int index) {
        super("☹ OOPS!!! There is no such task!");
        this.index = index;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + String.format("\n    I can't seem to find the task at number %d !", this.index);
    }

}
