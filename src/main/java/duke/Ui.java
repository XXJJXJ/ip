package duke;

import duke.task.Task;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is an duke.Ui class that deals with interaction with Users.
 */
public class Ui {

    private final String lineSeparator = "    _______________________________";
    private Scanner s = new Scanner(System.in);

    public void showWelcomeMessage() {
        System.out.println("Hello I'm FullOfBugs. What can I do for you?");
    }

    public void printLine() {
        System.out.println(lineSeparator);
    }

    public void showLoadingError() {
        printLine();
        System.out.println("    Error loading file!!");
        printLine();
    }

    public void showError(String message) {
        printLine();
        System.out.printf("    %s\n", message);
        printLine();
    }

    public void printAddTask(Task t, TaskList tasks) {
        printLine();
        System.out.println("    Got it. I've added this task:");
        System.out.printf("      %s\n", t);
        System.out.printf("    Now you have %d ", tasks.getSize());
        System.out.println((tasks.getSize() <= 1 ? "task" : "tasks") + " in the list.");
        printLine();
    }

    public void printRemoveTask(Task t, int remainingSize) {
        printLine();
        System.out.println("    Noted. I've removed this task:");
        System.out.printf("      %s\n", t);
        System.out.printf("    Now you have %d ", remainingSize);
        System.out.println((remainingSize <= 1 ? "task" : "tasks") + " in the list.");
        printLine();
    }

    public void printMarkTaskDone(Task t) {
        printLine();
        System.out.println("    Nice! I've marked this task as done:");
        System.out.printf("    %s\n", t);
        printLine();
    }

    public void printList(ArrayList<Task> tasks) {
        printLine();
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("    %d. %s\n", i + 1, tasks.get(i));
        }
        printLine();
    }

    /**
     * Prints a list of task that contains a specified keyword.
     *
     * @param tasks  An ArrayList of task that contains the keyword in its description.
     */
    public void printFindTask(ArrayList<Task> tasks) {
        printLine();
        System.out.println("    Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.printf("    %d. %s\n", i + 1, tasks.get(i));
        }
        printLine();
    }

    public void bidFarewell() {
        printLine();
        System.out.println("    Bye. Hope to see you again soon!");
        printLine();
        s.close();
    }

    public String readCommand() {
        String command = s.nextLine();
        return command;
    }

}
