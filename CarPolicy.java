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

    private String carMake = "";
    private String carModel = "";
    private int manufactureYear = INVALID_YEAR;

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
