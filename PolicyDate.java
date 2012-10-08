// FILE:      PolicyDate.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This class handles the date handling for the insurance
//            policies for the OOPD assignment. It will allow parsing
//            and outputting of dates in the format expected.
// REFERENCE: Uses both the Java DateFormat class.
// COMMENTS:  Dates should be immutable, the 20th of August 1922 can't
//            become 16th July 2011.
// REQUIRES:  None.
// Last Mod:  8th October 2012

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import io.ConsoleInput;

public class PolicyDate {
    // Constants
    // Value that represents "No Date", hence no policy.
    public static final int NULL_DATE = 0;

    // Our dateformatter should be the same for all objects.
    private static final SimpleDateFormat DATE_FORMATTER =
        new SimpleDateFormat( "yyyyMMdd" );

    // The date of the policy, this should always be initalised by a
    // constructor and never be changed once it has been set.
    private Date dateOfPolicy = null;

    // Assume we have a null date until it gets set.
    private boolean dateIsNull = true;




    // Default Constructor
    // PURPOSE: Default creation of date, date will be marked as invalid.
    // IMPORTS: None.
    // EXPORT:  An instance of a PolicyDate that is considered inactive.
    // REMARKS: None.

    public PolicyDate()
    {
        // Everything should already be set to the correct values in the
        // field declarations above.
    }




    // Alternate Constructor
    // PURPOSE: This constructor is used to parse strings into dates. If
    //          handed an invalid date string, it will just set the date
    //          to a null date.
    // IMPORT:  dateToParse - String of the date, expected format is
    //                        yyyyMMdd.
    // EXPORT:  PolicyDate instance with either the date given, or marked as
    //          invalid.
    // REMARKS: Any invalid dates are just made into null dates.

    public PolicyDate( String dateToParse )
    {
        if ( Integer.parseInt( dateToParse ) == NULL_DATE )
            {
                // Although not a valid date, it is still a valid value
                // we expect. We don't need to try and parse any
                // further.
                dateOfPolicy = null;
            }
        else
            {
                DATE_FORMATTER.setLenient( false );
                try {
                    dateOfPolicy = DATE_FORMATTER.parse( dateToParse );
                    dateIsNull = false;
                } catch (ParseException e)
                    {
                        // Incorrect date entered, just set the date to
                        // null.
                        dateOfPolicy = null;
                    }
            }
    }




    // Alternate Constructor
    // PURPOSE: This constructor is used to parse an integer into a date. If
    //          handed an invalid date number, will just return a null date.
    // IMPORT:  dateFromInt - Integer that represents the date.
    // EXPORT:  PolicyDate instance with either the date given, or marked as
    //          invalid.
    // REMARKS: Any invalid dates are just made into null dates.
    //          Does no range checking on the integer outside date checking.
    //          Bit of double handing, as we're converting to string, then to
    //          integer again.

    public PolicyDate( int dateFromInt )
    {
        // It's a bit weird, we're moving int to string to int.
        this( Integer.toString( dateFromInt ) );
    }




    // Copy constructor
    // PURPOSE: Offer a method that will return a copy of the given date.
    // IMPORT:  inDate - PolicyDate that we will copy.
    // EXPORT:  New instance of PolicyDave copied from inDate.
    // REMARKS: None.

    public PolicyDate( PolicyDate inDate )
    {
        dateOfPolicy = (Date) inDate.dateOfPolicy.clone();
        dateIsNull = inDate.dateIsNull;
    }




    // NAME: isNullDate
    // PURPOSE: Will return true is the PolicyDate is either not set, or
    //          the result of an invalid date.
    // IMPORT:  None.
    // EXPORT:  Boolean, true is the date is not valid or is null.
    // REMARKS: None

    public boolean isNullDate()
    {
        return ( dateIsNull );
    }




    // NAME: toString
    // PURPOSE: Return the date as a string, output format is the same
    //          as the expected input format.  yyyyDDmm
    // IMPORT: None.
    // EXPORT: String that represents the date.
    // REMARKS: None.

    public String toString()
    {
        // If we have a null date, then be sure to return the null date string instead.
        // This is just a string of the NULL_DATE value.
        String result = Integer.toString( NULL_DATE );
        if ( !dateIsNull )
            {
                result = DATE_FORMATTER.format( dateOfPolicy );
            }

        return result;
    }




    // NAME: promptForDate
    // PURPOSE: Prompt the user for a date, keep requesting until we get
    //          "0", or a valid date.
    // INPUT:   inDate - Date string from user.
    // OUTPUT:  Prompts for date entry.
    // IMPORT:  None.
    // EXPORT:  PolicyDate instance that is a valid date, or explictly a
    //          null date from the user.
    // Assertions
    //     Post: A valid PolicyDate object, or explictily entered null date.

    public static PolicyDate promptForDate()
    {
        PolicyDate inDate = null;
        int dateFromUser = -1;

        do
            {
                dateFromUser =
                    ConsoleInput.readInt( "Enter date of policy, " +
                                          "YYYYMMDD format. 0 for no policy" );
                inDate = new PolicyDate( Integer.toString( dateFromUser ) );
            } while ( dateFromUser != NULL_DATE && inDate.dateIsNull  );

        return inDate;
    }




    // NAME:    equals
    // PURPOSE: To compare two PolicyDate instances for equality.
    // IMPORTS: inObj - Java object we want to compare to.
    // EXPORT:  boolean, true if they are equal.

    @Override public boolean equals( Object inObj )
    {
        boolean result = true;

        // Handle if we get handed in null, or completely different
        // classes.
        if ( !(inObj instanceof PolicyDate) )
            {
                result = false;
            }
        else
            {
                PolicyDate testObject = (PolicyDate) inObj;
                result = testObject.dateOfPolicy.equals( dateOfPolicy ) &&
                    testObject.dateIsNull == dateIsNull;
            }

        return result;
    }
}
