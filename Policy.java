// FILE:      Policy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Base policy interface, defines the minimum methods that need
//            to be defined for insurance policy classes.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  11th September 2012

public abstract class Policy
{
    // Variables
    private PolicyDate date = null;

    // All policies must have a date, hence the policy interface must
    // have a way of having a date set.
    protected void setDate( String dateToParse )
    {
        date = new PolicyDate( dateToParse );
    }

    protected void setDate( int dateToParse )
    {
        date = new PolicyDate( Integer.toString( dateToParse ) );
    }

    // Accessors Returns the date as a string, the format will match the
    // format that should be expected in the text file.
    protected String dateString()
    {
        return date.toString();
    }

    // Policy Active
    //
    // Will return true if the policy is active (i.e. has a date).
    protected boolean active()
    {
        return !date.isNullDate;
    }

    // All insurance policies must have a premium that must be
    // calculated, the premium calculation may depend on details in the
    // poilcy, so if this method is called before policy information is
    // set, it must return a null value.
    abstract double calculatePremium();

    // Since each policy has potentially different formats for the
    // string that is saved to the text file, we need a general way to
    // represent what fields should be output. So, each class needs to
    // have this method. When called, it returns a String array of the
    // fields, in the order they need to be saved in the file.
    protected abstract String[] policyFields();

    // Each policy should provide a method that returns a string that
    // can be used to show the policy details to the user.
    abstract String displayString();

    // Returns the policy as a single line, as expected in the file
    // format.
    public String toString()
    {
        // This one method covers outputting the policy fields to the
        // line format expected in the text file, interposeFields takes
        // an array of strings and returns a string with the delimiter
        // between each of the string values, and terminated by a
        // delimiter.
        String[] stringFields = this.policyFields();

        // If the date is null, then we don't have any fields except the
        // null date.
        if ( date.isNullDate() )
            {
                // Can't use the syntax sugar of "array = {};" here
                // because that only works with initialisation of
                // variables, so we create an array of length 1, and
                // just put the date in it.
                stringFields = new String[1];
                stringFields[0] = date.toString();
            }

        return Utility.interposeFields( stringFields );
    };
}
