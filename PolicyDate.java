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

public class PolicyDate {
    // Our dateformatter should be the same for all objects.
    private static SimpleDateFormat dateFormatter =
        new SimpleDateFormat( "yyyyMMdd" );

    // The date of the policy, this should always be initalised by a
    // constructor and never be changed once it has been set.
    private Date dateOfPolicy;

    // Constructors
    public PolicyDate( String dateToParse )
    {
        dateFormatter.setLenient( false );
        dateOfPolicy = dateFormatter.parse( dateToParse );
    }

    // Assessors
    // Return the policy date as a string in the format YYYYMMDD.
    public String toString()
    {
        return dateFormatter.format( date );
    }
}
