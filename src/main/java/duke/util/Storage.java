package duke.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import duke.exceptions.CommandParamException;
import duke.exceptions.DukeFileException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

/**
 * This is a Storage class that deals with loading tasks
 * from the file and saving tasks in the file.
 */
public class Storage {

    /**
     * This is the private class field of a Storage instance.
     */
    private final String filePath;

    /**
     * This is the constructor for a Storage instance.
     *
     * @param filePath   A string representing the filepath in the operating system.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns an Arraylist of Task loaded from the filePath.
     *
     * @return An ArrayList of Task that was stored in the file located in the filePath.
     * @throws DukeFileException  An exception thrown when there is error reading or creating the file.
     */
    public ArrayList<Task> load() throws DukeFileException {
        try {
            java.nio.file.Path directoryPath = java.nio.file.Paths.get("src", "main", "java", "data");
            File f = new File(this.filePath);
            if (!Files.isDirectory(directoryPath)) {
                //create directory
                Files.createDirectories(directoryPath);
            }
            if (!f.exists()) {
                //create file if it does not exist
                f.createNewFile();
            }
            Scanner s = new Scanner(f);
            ArrayList<Task> taskList = new ArrayList<>();

            while (s.hasNext()) {
                // input received here will definitely be correct and accurate
                // either, todo, deadline, delete, event, done
                String fullLineOfCommand = s.nextLine();
                Scanner lineSplitter = new Scanner(fullLineOfCommand);
                String command = lineSplitter.next().trim();
                try {
                    if (command.equals("todo")) {
                        assert lineSplitter.hasNext() : "Data file is corrupted";
                        String description = lineSplitter.nextLine();
                        assert !description.equals("") : "Data file is corrupted!";
                        taskList.add(new ToDo(description.trim()));

                    } else if (command.equals("deadline")) {
                        assert lineSplitter.hasNext() : "Data file is corrupted";
                        String description = lineSplitter.nextLine();
                        assert !description.equals("") : "Data file is corrupted!";
                        String[] parts = description.split("/by");
                        taskList.add(new Deadline(parts[0].trim(), parts[1].trim()));

                    } else if (command.equals("event")) {
                        assert lineSplitter.hasNext() : "Data file is corrupted";
                        String description = lineSplitter.nextLine();
                        assert !description.equals("") : "Data file is corrupted!";
                        String[] parts = description.split("/at");
                        taskList.add(new Event(parts[0].trim(), parts[1].trim()));

                    } else if (command.equals("done")) {
                        assert lineSplitter.hasNext() : "Data file is corrupted";
                        int indexToMark = lineSplitter.nextInt();
                        assert indexToMark < taskList.size() : "Date file is corrupted";
                        taskList.get(indexToMark - 1).markAsDone();

                    } else if (command.equals("delete")) {
                        assert lineSplitter.hasNext() : "Data file is corrupted";
                        int indexToDelete = lineSplitter.nextInt();
                        assert indexToDelete < taskList.size() : "Data file is corrupted";
                        taskList.remove(indexToDelete - 1);

                    }
                } catch (CommandParamException e) {
                    System.out.println("\tError initiating database.");
                }
            }
            return taskList;

        } catch (IOException e) {
            throw new DukeFileException();
        }
    }

    /**
     * Appends a line of command into the file located at the filePath.
     *
     * @param taskCommand A String representing the input given to the Parser to generate a Task.
     * @throws IOException  An exception thrown when FileWriter is unable to find or append to the file at filePath.
     */
    public void appendCommand(String taskCommand) throws IOException {
        FileWriter fw = new FileWriter(this.filePath, true);
        fw.append(taskCommand + System.lineSeparator());
        fw.close();
    }

    /**
     * Overwrites the file located at the filePath with the minimal commands to regenerate the current Task list.
     *
     * @param taskList An ArrayList of Task to be saved as text in the file located at filePath.
     * @throws IOException An exception thrown when the FileWriter is unable to find or write to the file at filePath.
     */
    public void safeFile(ArrayList<Task> taskList) throws IOException {
        FileWriter fw = new FileWriter(this.filePath);
        for (int i = 0; i < taskList.size(); i++) {
            Task t = taskList.get(i);
            fw.append(t.fullCommand() + System.lineSeparator());
            if (t.isDone()) {
                int index = i + 1;
                fw.append("done " + index + System.lineSeparator());
            }
        }
        fw.close();
    }

}
