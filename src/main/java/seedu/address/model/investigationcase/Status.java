package seedu.address.model.investigationcase;

public enum Status {
    ACTIVE, COLD, CLOSED;

    public static final String MESSAGE_CONSTRAINTS = "Status can only be either active, cold or closed";

    public static Status createStatus(String status) {
        return Status.valueOf(status.toUpperCase());
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        for (Status s : Status.values()) {
            if (s.name().equals(test.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
}
