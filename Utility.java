// Various utility functions
public class Utility
{
    // Delimiter that should be between fields.
    private static final String FIELD_DELIMITER = ":";

    // Given an array, return a string with the field delimiter
    // interposed between the fields and the end.
    public static String interposeFields( String[] fields )
    {
        // Our result string, this is not the best way to make a string
        // like this, but it's assumed we're not using lots of fields,
        // otherwise use StringBuilder.
        String result = "";

        // For each field in the array, put the delimiter between the
        // fields.
        for ( String field : fields )
            {
                result += field + FIELD_DELIMITER;
            }

        return result;
    }
}
