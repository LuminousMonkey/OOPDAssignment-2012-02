// Various utility functions
public class Utility
{
    // Delimiter that should be between fields.
    private static final String FIELD_DELIMITER = ":";

    // Given a string, return an array of strings broken down by the
    // delimiter.
    public static String[] fieldStrings( String fieldLine )
    {
        return fieldLine.split( FIELD_DELIMITER );
    }
}
