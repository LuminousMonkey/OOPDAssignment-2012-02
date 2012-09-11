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

    // Returns the policy as a single line, as expected in the file
    // format.
    public String toString()
    {
        // To try and make outputting a little more consistent, there is
        // actually an "interpostFields" function that will take an
        // array, and insert the field delimiter character between each
        // string given in that array, with a delimiter at the end.
        //
        // Unfortunately, doing it this way, means we have to massage
        // the array we pass to it. And also because Java only supports
        // defining an array with {} on initialisation we have the code
        // below.

        // What fields and their order have to be defined by the class,
        // to query this, call the policyFields method, it should return
        // an array of strings.
        String[] stringFields = this.policyFields();

        if ( date.isNullDate() )
            {
                // Can't use the syntax sugar of "array = {};" here, so
                // we create an array of length 1, and just put the date
                // in it.
                stringFields = new String[1];
                stringFields[0] = date.toString();
            }

        return Utility.interposeFields( stringFields ) + "\n";
    };
}
