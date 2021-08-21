package duke.task;

import duke.exceptions.CommandParamException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This is a duke.task.Deadline class that extends duke.task.Task.
 */
public class Deadline extends Task {

    protected LocalDate date;
    protected LocalTime time;

    public Deadline(String description, String by) throws CommandParamException {
        super(description);
        String[] dateTime = by.trim().split(" ");
        //index 0 is date, 1 is time
        //date should be yyyy-mm-dd, time should be 2359...
        if (dateTime.length <= 1 || dateTime.length > 2) {
            throw new CommandParamException("deadline");
        }
        try {
            if (dateTime[1].length() < 4) {
                throw new CommandParamException("deadline");
            }
            String timeReformatted = dateTime[1].substring(0, 2) + ":" + dateTime[1].substring(2, 4);
            LocalDate date = LocalDate.parse(dateTime[0].trim());
            LocalTime time = LocalTime.parse(timeReformatted);
            this.date = date;
            this.time = time;
        } catch (DateTimeParseException e) {
            throw new CommandParamException("deadline");
        }
    }

    @Override
    public String fullCommand() {
        return "deadline " + this.description + " /by "
                           + this.date.toString()
                           + " "
                           + this.time.format(DateTimeFormatter.ofPattern("kkmm"));
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + this.date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " "
                + this.time.format(DateTimeFormatter.ofPattern(("h:mma")))
                + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            if (!(o instanceof Deadline)) {
                return false;
            } else {
                Deadline other = (Deadline) o;
                return other.date.equals(this.date) && other.time.equals(this.time);
            }
        } else {
            return false;
        }
    }
}
