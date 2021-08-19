package duke.task;

import duke.exceptions.CommandParamException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This is an duke.task.Event class that extends duke.task.Task.
 */
public class Event extends Task {

    protected LocalDate date;
    protected LocalTime time;

    public Event(String description, String at) throws CommandParamException {
        super(description);
        String[] dateTime = at.trim().split(" ");
        //index 0 is date, 1 is time
        //date should be yyyy-mm-dd, time should be 2359 format
        if (dateTime.length <= 1 || dateTime.length > 2) {
            throw new CommandParamException("event");
        }
        try {
            if (dateTime[1].length() < 4) {
                throw new CommandParamException("event");
            }
            String timeReformatted = dateTime[1].substring(0, 2) + ":" + dateTime[1].substring(2, 4);
            LocalDate date = LocalDate.parse(dateTime[0].trim());
            LocalTime time = LocalTime.parse(timeReformatted);
            this.date = date;
            this.time = time;
        } catch (DateTimeParseException e) {
            throw new CommandParamException("event");
        }
    }

    @Override
    public String fullCommand() {
        return "event " + this.description + " /at "
                        + this.date.toString()
                        + " "
                        + this.time.format(DateTimeFormatter.ofPattern("kkmm"));
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (at: "
                + this.date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
                + " "
                + this.time.format(DateTimeFormatter.ofPattern("h:mma"))
                + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (super.equals(o)) {
            if (!(o instanceof Event)) {
                return false;
            } else {
                Event other = (Event) o;
                return other.date.equals(this.date) && other.time.equals(this.time);
            }
        } else {
            return false;
        }
    }
}
