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
// Last Mod:  6th September 2012

import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import io.ConsoleInput;

public class PolicyDate {
    // Our dateformatter should be the same for all objects.
    private static SimpleDateFormat dateFormatter =
        new SimpleDateFormat( "yyyyMMdd" );

    // The date of the policy, this should always be initalised by a
    // constructor and never be changed once it has been set.
    private Date dateOfPolicy;

    // Assume we have a null date until it gets set.
    private boolean dateIsNull = true;

    // Constants
    // Value that represents "No Date", hence no policy.
    public static final int NULL_DATE = 0;

    // Constructors
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
                dateFormatter.setLenient( false );
                try {
                    dateOfPolicy = dateFormatter.parse( dateToParse );
                    dateIsNull = false;
                } catch (ParseException e)
                    {
                        // Incorrect date entered, just set the date to
                        // null.
                        dateOfPolicy = null;
                    }
            }
    }

    // Copy constructor
    public PolicyDate( PolicyDate inDate )
    {
        dateOfPolicy = (Date) inDate.dateOfPolicy.clone();
        dateIsNull = inDate.dateIsNull;
    }

    // Returns true if the date is a null date, which is a valid value
    // that represents that there is no date.
    public boolean isNullDate()
    {
        return ( dateIsNull );
    }

    public Date getDate()
    {
        return dateOfPolicy;
    }

    // Assessors
    // Return the policy date as a string in the format YYYYMMDD.
    public String toString()
    {
        // If we have a null date, then be sure to return the null date string instead.
        // This is just a string of the NULL_DATE value.
        String result = Integer.toString( NULL_DATE );
        if ( !dateIsNull )
            {
                result = dateFormatter.format( dateOfPolicy );
            }

        return result;
    }

    // Prompt for date

    // Prompt the user for the date, accept only valid dates, or 0.
    // Otherwise keep asking, return a policy date instance.
    public static PolicyDate promptForDate()
    {
        PolicyDate inDate = null;
        int dateFromUser = -1;

        do
            {
                dateFromUser = ConsoleInput.readInt( "Enter date of policy, YYYYMMDD format. 0 for no policy" );
                inDate = new PolicyDate( Integer.toString( dateFromUser ) );
            } while ( dateFromUser != 0 && inDate.dateIsNull  );

        return inDate;
    }

    // The equals method for comparing two dates,
    @Override public boolean equals( Object inObj )
    {
        boolean result = true;

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

    // If you override equals, you also have to override hashCode, it's
    // part of the general contract with Java objects.
    //
    // Good hashing is the realm of maths.
    @Override public int hashCode()
    {
        // Don't need any sophisticated hash function here.
        int result = 17;

        // We don't care about a loss of precision, we just want a
        // simple hashing function.
        result = 31 * result + (int) dateOfPolicy.getTime();

        if ( !dateIsNull )
            {
                result = 31 * result + 1;
            }

        return result;
    }
}
