package duke.task;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * A task that has a start and an end.
 */
public class Event extends Task {
    private final LocalDateTime from;
    private final LocalDateTime to;

    /**
     * Constructs a task with a start and an end.
     * @param description the description of the task
     * @param from the date and time the task starts
     * @param to the date and time the task ends
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        assert from != null : "From must not be null";
        assert to != null : "To must not be null";

        this.from = from;
        this.to = to;
    }

    /**
     * Construct a task with a start and an end from parsed components.
     * The expected components are DESCRIPTION, /from and /to.
     * @throws InvalidComponents when the parsed components don't match the expected components
     */
    public Event(Map<String, String> components) throws InvalidComponents {
        super(components.get("DESCRIPTION"));
        validateComponentKeys(keys("/from", "/to"), components.keySet());
        from = parseDateTime(components.get("/from"));
        to = parseDateTime(components.get("/to"));
    }

    @Override
    public String describe() {
        return super.describe() + "; starts from "
                + from.format(describeTimeFormat) + " and ends at " + to.format(describeTimeFormat);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(from: " + from + " to: " + to + ")";
    }
}
