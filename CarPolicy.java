// FILE:      CarPolicy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This class handles the insurance policies that take care of
//            cars.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  java.util.Calendar for getting the current year.
// Last Mod:  11th September 2012

import java.util.Calendar;

public class CarPolicy extends Policy
{
    // Constants Value that represents an invalid year for date of
    // manufacture.
    private static final int INVALID_YEAR = -1;

    // Base premium for calculation.
    private static final double BASE_PREMIUM = 1500.00;

    // Aging factor, this is a factor that is multiplied by the age of
    // the car as part of the premium calculations.
    private static final double AGING_FACTOR = 0.2;

    // Fields
    private String carMake = "";
    private String carModel = "";
    private int manufactureYear = INVALID_YEAR;

    // Position of the fields in the policy file.
    private static final int DATE_FIELD = 0;
    private static final int MAKE_FIELD = 1;
    private static final int MODEL_FIELD = 2;
    private static final int YEAR_FIELD = 3;

    // This should be updated if the number of fields above change.
    private static final int NUM_OF_FIELDS = 3;

    // Default constructor
    public CarPolicy()
    {
        setDate( PolicyDate.NULL_DATE );
        // Default values for other fields should be already set in
        // initialisation.
    }

    // Alternate Constructor
    public CarPolicy( int date, String make, String model, int year )
    {
        // Assume the fields are correct for now.
        setDate( date );
        setMake( make );
        setModel( model );
        setYear( year );
    }

    // Takes a single line from the policy file as a string and returns
    // a matching home policy file.
    public CarPolicy( String inFileLine )
    {
        // The format of the string should match the format of our
        // toString function.

        // Break down the string into substrings based on the field seperator.
        String[] fields = Utility.fieldStrings( inFileLine );

        // Fields are all hardcoded.  Date field should always be
        // present, however if it's the only field, then don't bother
        // setting any of the other field values, rely on the default
        // values being set correctly.
        setDate( fields[DATE_FIELD] );

        // Check that we have the number of fields we're expecting, if
        // we get more, then it's not so bad, we'll just ignore them.
        if ( fields.length >= NUM_OF_FIELDS )
            {
                setMake( fields[MAKE_FIELD] );
                setModel( fields[MODEL_FIELD] );
                setYear( Integer.parseInt( fields[YEAR_FIELD] ) );
            }
    }

    // Setters
    public void setMake( String make )
    {
        carMake = make;
    }

    public void setModel( String model )
    {
        carModel = model;
    }

    public void setYear( int year )
    {
        manufactureYear = year;
    }

    // Returns a string that can be used to display the policy out to
    // the user.
    public String displayString()
    {
        String result = "No policy";

        if ( active() )
            {
                return "Date: " + dateString() + "\n" +
                    "Make: " + carMake + "\n" +
                    "Model: " + carModel + "\n" +
                    "Year: " + manufactureYear + "\n" +
                    "Premium: " + calculatePremium();
            }

        return result;
    }

    // Returns a string array of the fields and their order in the text
    // file.
    protected String[] policyFields()
    {
        String[] resultArray = { dateString(),
                                 carMake,
                                 carModel,
                                 Integer.toString( manufactureYear ) };

        return resultArray;
    }

    // Returns the premium of the car insurance following the formula
    // outlined in the assignment.
    public double calculatePremium()
    {
        int carAge = Calendar.getInstance().get( Calendar.YEAR ) -
            manufactureYear;
        return BASE_PREMIUM / ( 1 + AGING_FACTOR * carAge );
    }
}
