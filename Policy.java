// FILE:      Policy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Base policy interface, defines the minimum methods that need
//            to be defined for insurance policy classes.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  7th October 2012

public abstract class Policy
{
    // Constants
    //
    // Our field seperator when we save to, read, from the file.
    private static final String FIELD_DELIMITER = ":";

    // Variables
    private PolicyDate date = new PolicyDate();

    // Default constructor
    protected Policy()
    {
        // By default a blank policy will have be Inactive
        setInactive();
    }

    // All policies must have a date, hence the policy interface must
    // have a way of having a date set.
    protected void setDate( String dateToParse )
    {
        date = new PolicyDate( dateToParse );
    }

    protected void setDate( PolicyDate inDate )
    {
        date = new PolicyDate( inDate );
    }

    // Accessors Returns the date as a string, the format will match the
    // format that should be expected in the text file.
    protected String dateString()
    {
        String result = Integer.toString( PolicyDate.NULL_DATE );

        if ( date != null )
            {
                result = date.toString();
            }

        return result;
    }

    // setInactive
    //
    // A policy might not be active, this method is to be called if the
    // policy isn't active, otherwise a valid date for the policy must
    // be provided.
    protected void setInactive()
    {
        date = null;
    }

    // isInactive

    // Returns true if the policy is inactive.
    protected boolean isInactive()
    {
        return ( date.isNullDate() );
    }

    // Just a straight inversion of isInactive, created just so statements read better.
    protected boolean isActive()
    {
        return !isInactive();
    }

    // All insurance policies must have a premium that must be
    // calculated, the premium calculation may depend on details in the
    // poilcy, so if this method is called before policy information is
    // set, it must return a null value.
    public abstract double calculatePremium();

    // Since each policy has potentially different formats for the
    // string that is saved to the text file, we need a general way to
    // represent what fields should be output. So, each class needs to
    // have this method. When called, it returns a String array of the
    // fields, in the order they need to be saved in the file.
    protected abstract String[] policyFields();

    // Each policy should provide a method that returns a string that
    // can be used to show the policy details to the user.
    public String fileString()
    {
        // This one method covers outputting the policy fields to the
        // line format expected in the text file, interposeFields takes
        // an array of strings and returns a string with the delimiter
        // between each of the string values, and terminated by a
        // delimiter.
        String[] stringFields = this.policyFields();

        // If the date is null, then we don't have any fields except the
        // null date.
        if ( isInactive() )
            {
                // If the policy isn't active (a date of 0), then all we
                // need to do is have the date field.
                stringFields = new String[1];
                stringFields[0] = this.dateString();
            }

        return interposeFields( stringFields );
    };

    // At the most basic level, a policy just has a date, if there is no
    // date, there is no policy.  Strings returned from this method are
    // not expected to have newlines at the end of the string, but in
    // the middle of the string is fine.
    public String toString()
    {
        String result = "No policy";

        if ( isActive() )
        {
                result = "Date: " + dateString();
        }

        return result;
    }

    // Given a string, return an array of strings broken down by the
    // delimiter.
    public static String[] fieldStrings( String fieldLine )
    {
        return fieldLine.split( FIELD_DELIMITER );
    }

    // interposeFields
    //
    // Takes the array returned from the policyFields method and returns
    // a string with the FIELD_DELIMITER interposed between those
    // fields. The end result being a string that is in the format
    // expected for saving a policy line to the file.
    private static String interposeFields( String[] fields )
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
