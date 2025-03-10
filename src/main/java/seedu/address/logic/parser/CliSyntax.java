package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_DATE = new Prefix("date/");
    public static final Prefix PREFIX_TIME = new Prefix("time/");
    public static final Prefix PREFIX_DURATION = new Prefix("duration/");
    public static final Prefix PREFIX_PAX = new Prefix("pax/");
    public static final Prefix PREFIX_TABLE = new Prefix("table/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("/r");
    public static final Prefix PREFIX_ID = new Prefix("id/");
}
