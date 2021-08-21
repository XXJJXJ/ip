package duke;

import duke.exceptions.CommandParamException;
import duke.exceptions.DukeFileException;
import duke.task.Deadline;
import duke.task.Event;
import duke.task.Task;
import duke.task.ToDo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a duke.Storage class that deals with loading tasks
 * from the file and saving tasks in the file.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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
                // input here will definitely be correct and accurate
                // either, todo, deadline, delete, event, done
                // Parser not used here since loading do not require execution again.
                String fullLineOfCommand = s.nextLine();
                Scanner lineSplitter = new Scanner(fullLineOfCommand);
                String command = lineSplitter.next().trim();
                try {
                    if (command.equals("todo")) {
                        String description = lineSplitter.nextLine();
                        taskList.add(new ToDo(description.trim()));

                    } else if (command.equals("deadline")) {
                        String description = lineSplitter.nextLine();
                        String[] parts = description.split("/by");
                        taskList.add(new Deadline(parts[0].trim(), parts[1].trim()));

                    } else if (command.equals("event")) {
                        String description = lineSplitter.nextLine();
                        String[] parts = description.split("/at");
                        taskList.add(new Event(parts[0].trim(), parts[1].trim()));

                    } else if (command.equals("done")) {
                        int indexToMark = lineSplitter.nextInt();
                        taskList.get(indexToMark - 1).markAsDone();

                    } else if (command.equals("delete")) {
                        int indexToDelete = lineSplitter.nextInt();
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

    public void appendCommand(String taskCommand) throws IOException {
        FileWriter fw = new FileWriter(this.filePath, true);
        fw.append(taskCommand + System.lineSeparator());
        fw.close();
    }

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
