// FILE:      PolicyDate.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This class handles the date handling for the insurance
//            policies for the OOPD assignment. It will allow parsing
//            and outputting of dates in the format expected.
// REFERENCE: Uses both the Java Calendar and DateFormat classes.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  6th September 2012

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class PolicyDate {
    // Our dateformatter should be the same for all objects.
    private static SimpleDateFormat dateFormatter =
        new SimpleDateFormat( "yyyyMMdd" );

    // Our calendar object to hold the policy date.
    private Calendar date = new Calendar();

    // Constructors
    public PolicyDate()
    {
        // We should make sure that dates are correct.
        date.setLenient( false );
    }

    public PolicyDate( int year, int month, int day )
    {
        date.setLenient( false );
        date.set( year, month, day);
    }

    // Mutators
    public void setDate( int year, int month, int day )
    {
        // Just pass through the values for the Calendar class to
        // handle.  We should be sure to catch exceptions if the date is
        // wrong.
        date.set( year, month, day );
    }

    // Given a string in the format YYYYMMDD, parses and sets the date.
    public static Calendar parse( String toParse )
    {
        return dateFormatter.parse( toParse );
    }

    // Assessors
    // Return the policy date as a string in the format YYYYMMDD.
    public String toString()
    {
        return dateFormatter.format( date );
    }
}
