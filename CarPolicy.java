// FILE:      CarPolicy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This class handles the insurance policies that take care of
//            cars.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  java.util.Calendar for getting the current year.
// Last Mod:  8th October 2012

import java.util.Calendar;
import io.ConsoleInput;

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
        // All our default values are set in the fields above, just call
        // the parent constructor.
        super();
    }

    // Alternate Constructor
    private CarPolicy( PolicyDate date, String make, String model, int year )
    {
        // Assume the fields are correct for now.
        setDate( date );
        setMake( make );
        setModel( model );
        setYear( year );
    }

    // Alternate Constructor
    //
    // Takes a string, the string is expected to be in the format as
    // defined in the assignment spec. It will ignore any extra fields
    // on the line, and doesn't do any real error checking. Any weird
    // formats in the fields it does expect may give weird behaviour.
    public CarPolicy( String inFileLine )
    {
        // Break down the string into substrings based on the field seperator.
        String[] fields = Policy.fieldStrings( inFileLine );

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
    public String toString()
    {
        // Build the initial string from our parent, the result should
        // either be whatever the no policy text is, or the date of the
        // policy.
        String result = super.toString();

        // We only output the remaining fields if the policy is active,
        // otherwise it will just be whatever the parent class outputs.
        if ( isActive() )
            {
                result += "\n" +
                    "Make: " + carMake + "\n" +
                    "Model: " + carModel + "\n" +
                    "Year: " + manufactureYear + "\n" +
                    "Premium: " + calculatePremium();
            }

        return result;
    }

    // Returns a string array of the fields of this policy, in the order
    // they should be in the text file. This is expected to be called by
    // the fieldFields method of the parent, the Policy class.
    //
    // This was done so I didn't have to duplicate code in the three
    // children classes, and that if the field delimiter changes, it
    // just has to change in one place.
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

    // Prompt the user for the car insurance details.
    //
    // It's hard to say if it's really the right place for this, as it's
    // mixing Input/Output into a class that could be more
    // "pure". However, my argument is that the PolicyManager class
    // shouldn't really know the internals of the policies too much, and
    // that would include know what fields to prompt the user for.
    //
    // However, if you really wanted to get rid of the coupling to the
    // ConsoleInput class and the prompts to the user, etc, you would
    // come up with a scheme where this class would advertise the
    // prompts, etc, basically come up with an internal kind of protocol
    // that represented inputs, validation, etc, needed.
    public static CarPolicy promptForInsurance()
    {
        PolicyDate date = PolicyDate.promptForDate();
        CarPolicy newPolicy = new CarPolicy();

        // No point asking for more fields if the policy isn't active.
        if ( !date.isNullDate() )
            {
                String make = ConsoleInput.readLine( "Please enter make of car" );
                String model = ConsoleInput.readLine( "Please enter model of car" );
                int year = ConsoleInput.readInt( "Please enter year of car" );

                newPolicy = new CarPolicy( date, make, model, year );
            }

        return newPolicy;
    }
}
