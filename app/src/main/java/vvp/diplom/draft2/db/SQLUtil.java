package vvp.diplom.draft2.db;

/**
 * Created by VoVqa on 23.05.2015.
 */
public class SQLUtil {
    public static final String CREATE_TABLE_ = "CREATE TABLE ";
    public static final String DROP_TABLE_IF_EXISTS_ = "DROP TABLE IF EXISTS ";

    public static final String COMMA_SEP = ",";
    public static final String _TEXT = " TEXT";
    public static final String _INTEGER = " INTEGER";
    public static final String _PRIMARY_KEY = " PRIMARY KEY";
    public static final String _TEXT_COMMA = _TEXT + COMMA_SEP;
    public static final String _INTEGER_COMMA = _INTEGER + COMMA_SEP;
    public static final String _INTEGER_PRIMARY_KEY_COMMA = _INTEGER + _PRIMARY_KEY + COMMA_SEP;

    private static final String FOREIGN_KEY_PATTERN = "FOREIGN KEY(%s) REFERENCES %s(%s)";

    public static String formatForeignKey(String childColumn, String parentTable, String parentColumn){
        return String.format(FOREIGN_KEY_PATTERN, childColumn, parentTable, parentColumn);
    }

}
