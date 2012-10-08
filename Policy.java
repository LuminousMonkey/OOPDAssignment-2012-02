// FILE:      Policy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Base policy interface, defines the minimum methods that need
//            to be defined for insurance policy classes.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  8th October 2012

public abstract class Policy
{
    // Constants
    //
    // Our field seperator when we save to, read, from the file.
    private static final String FIELD_DELIMITER = ":";

    // Currency symbol used when displaying premiums.
    private static final String CURRENCY_SYMBOL = "$";

    // Position of fields in the file.
    // The date field is always the first.
    private static final int DATE_FIELD  = 0;

    // Variables
    private PolicyDate date = new PolicyDate();

    // Default Constructor
    // PURPOSE: Default creation for any inactive policy.
    // IMPORTS: None.
    // EXPORT:  A policy object that is inactive, i.e. with no date set.
    // REMARKS: This is expected to be called by all subclasses, as the
    //          policy class handles any date handling.

    protected Policy()
    {
        // By default a blank policy will have be inactive.
        setInactive();
    }


    // Alternate Constructor
    // PURPOSE: This constructor is used when reading in the policy from
    //          the file.
    // IMPORT:  inFileLine - single line string of the policy as defined
    //          in assignment spec.
    // EXPORT:  An instance of a the policy, values defined by data read
    //          from file.
    // REMARKS: No error checking done.

    protected Policy( String inFileLine )
    {
        // Break down the string into substrings based on the field
        // seperator.
        String[] fields = Policy.fieldStrings( inFileLine );

        // Fields are all hardcoded.  Date field should always be
        // present, however if it's the only field, then don't bother
        // setting any of the other field values, rely on the default
        // values being set correctly.
        this.setDate( fields[DATE_FIELD] );
    }




    // NAME:    setDate
    // PURPOSE: Takes a string that represents the date and sets the
    //          date for the policy to that date.
    // IMPORTS: String in the format that the PolicyDate class expects
    //          YYYYMMDD
    // EXPORTS: None.
    // REMARKS: If the date is invalid, then the date (hence the policy)
    //          will be marked as inactive.

    protected void setDate( String dateToParse )
    {
        date = new PolicyDate( dateToParse );
    }




    // NAME:    setDate
    // PURPOSE: Takes a PolicyDate instance and sets the date of the
    //          policy to the given date.
    // IMPORTS: PolicyDate instance to update policy to.
    // EXPORTS: None.
    // REMARKS: If the date is invalid, then the date (hence the policy)
    //          will be marked as inactive.

    protected void setDate( PolicyDate inDate )
    {
        date = new PolicyDate( inDate );
    }




    // NAME:    setInactive
    // PURPOSE: Sets the policy to an inactive state, this means all
    //          other fields should be ignored.
    // IMPORTS: None.
    // EXPORTS: None.
    // REMARKS: Just creates a new PolicyDate, which should be a null
    //          date by default. Since dates define if a policy is active
    //          or not, this makes the policy inactive.

    protected void setInactive()
    {
        date = new PolicyDate();
    }




    // NAME:    dateString
    // PURPOSE: Returns the date of the policy in the format as defined
    //          in the assignment spec. YYYYMMDD.
    // IMPORTS: None.
    // EXPORTS: String in the format YYYYMMDD.
    // REMARKS: None.

    protected String dateString()
    {
        return date.toString();
    }




    // NAME:    isInactive
    // PURPOSE: What it says on the packet, returns true if the policy
    //          isn't active (hasn't got a date set).
    // IMPORTS: None.
    // EXPORTS: Boolean, true is policy is not active, false otherwise.
    // REMARKS: None.

    protected boolean isInactive()
    {
        return ( date.isNullDate() );
    }




    // NAME:    isActive
    // PURPOSE: Returns true if the policy is active (has a date).
    // IMPORTS: None.
    // EXPORTS: Boolean, true if policy is active.
    // REMARKS: Just a convience method, makes code a tiny bit easier to
    //          read than !isInactive()

    protected boolean isActive()
    {
        return !isInactive();
    }




    // NAME:    calculatePremium
    // PURPOSE: All policies must have a premium, but the calculation of
    //          such a premium is done on a class by class basis.
    // IMPORTS: None.
    // EXPORTS: A double representing the amount of the premium in dollars.
    // REMARKS: This method should return a result, even if the other
    //          values for calculating the premium aren't valid, it can
    //          be an invalid value, but shouldn't crash on weird object
    //          states.
    //          Also, this method does no rounding, it's up to the caller
    //          to round to any necessary number of decimal places, etc.

    public abstract double calculatePremium();




    // NAME:    policyFields
    // PURPOSE: Each policy has a shared requirement to save a line
    //          format to a file. To reduce coupling we use the
    //          fileString() method to produce a string for saving
    //          the policy to file. Each policy have different fields
    //          and order which they have to be saved. This method is
    //          a way of getting each class to communicate the fields
    //          that have to be saved, and the order so we can have
    //          one method that generates the line for all classes. See
    //          fileString() for more details.
    // IMPORTS: None.
    // EXPORTS: A string array of the field values in the order they
    //          should be saved in the file.
    //
    // REMARKS: Reduces the need for PolicyFile to know about fields and
    //          their order that they have to be saved in the file. But
    //          implies that each policy must know their format in the file,
    //          rather than the order of the fields being defined in the
    //          class that actually takes care of the file.

    protected abstract String[] policyFields();




    // NAME:    fileString
    // PURPOSE: Returns the single line string that represents the
    //          policy in the text file.
    // IMPORTS: None through parameters, but expects a policyFields()
    //          method that returns an array of the field values in order
    //          they are to be saved in the file.
    // EXPORTS: A string, that represents the policy in a single line as
    //          defined in the assignment spec. Not to be newline terminated.
    // REMARKS: Check the comments on policyFields();

    public String fileString()
    {
        // Get our field values and order they should be.
        String[] stringFields = this.policyFields();

        // If the policy is inactive, then all we are going to do is
        // output the date (which will be just "0").
        if ( this.isInactive() )
            {
                // We just create a new array, with the single field of
                // the date.
                stringFields = new String[1];
                stringFields[0] = this.dateString();
            }

        // Fields require a delimiter character between them, interpose
        // that delimiter between the fields.
        return this.interposeFields( stringFields );
    };




    // NAME:    toString
    // PURPOSE: Return a string in human readable format of the policy.
    // IMPORTS: None.
    // EXPORTS: A string, human readable, possibly with multiple
    //          newlines.
    // REMARKS: The date is common to all policies, so makes sense to
    //          have the string handling for it here. Since the date
    //          defines if a policy is active or not, we have a
    //          "No Policy" string that will be returned.
    //          However! Each subclass still needs to check to see
    //          if the policy is active before trying to add anymore
    //          fields to the string.

    public String toString()
    {
        String result = "No policy";

        if ( isActive() )
        {
                result = "Date:  \t " + dateString();
        }

        return result;
    }




    // NAME:    premiumString
    // PURPOSE: Returns the premium of the policy formatted as a string.
    // IMPORTS: None.
    // EXPORTS: A string, in dollars with currency sign and decimal places.
    // REMARKS: None.

    public String premiumString()
    {
        return String.format( CURRENCY_SYMBOL + "%.2f", this.calculatePremium() );
    }




    // NAME: fieldStrings

    // PURPOSE: Given a single line string of policy field values, split
    //          the string into an array of the seperate fields.
    // IMPORT:  A string of the policy as defined in the assignment spec.
    // EXPORT:  An array of strings, each value in the array representing
    //          a single field as defined in the assignment spec.
    // REMARKS: No error checking or verification, anything weird you
    //          pass in is your own problem.
    public static String[] fieldStrings( String fieldLine )
    {
        return fieldLine.split( FIELD_DELIMITER );
    }




    // NAME:    interposeFields
    // PURPOSE: Creates a single line string for saving a policy to the
    //          file.
    // IMPORTS: An array of strings that represent the field values to
    //          be saved to the file. Must be in order expected for file, as
    //          defined by assignment spec.
    // EXPORTS: Single string, of field values with delimiter interposed
    //          between, and at end of string. Not newline terminated.
    // REMARKS: None.

    private static String interposeFields( String[] fields )
    {
        String result = "";

        // For each field in the array, put the delimiter between the
        // fields.
        // An srray for loop, like fezzes, are cool.
        for ( String field : fields )
            {
                result += field + FIELD_DELIMITER;
            }

        return result;
    }
}
