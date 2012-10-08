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
    // Constants
    // Value that represents an invalid year for date of manufacture.
    private static final int INVALID_YEAR = -1;

    // Base premium for calculation.
    private static final double BASE_PREMIUM = 1500.00;

    // Aging factor, this is a factor that is multiplied by the age of
    // the car as part of the premium calculations.
    // Never make this value -1.0
    private static final double AGING_FACTOR = 0.2;

    // Fields
    private String carMake = "";
    private String carModel = "";
    private int manufactureYear = INVALID_YEAR;

    // Position of the fields in the policy file.
    private static final int MAKE_FIELD = 1;
    private static final int MODEL_FIELD = 2;
    private static final int YEAR_FIELD = 3;

    // This should be updated if the number of fields above change.
    private static final int NUM_OF_FIELDS = YEAR_FIELD + 1;




    // Default Constructor
    // PURPOSE: Default creation of inactive car policy.
    // IMPORTS: None.
    // EXPORT:  An instance of a car policy that is considered inactive.
    // REMARKS: None.

    public CarPolicy()
    {
        // All our default values are set in the fields above, just call
        // the parent constructor.
        super();
    }




    // Alternate Constructor
    // PURPOSE: This constructor is used when reading in the policy from
    //          the file.
    // IMPORT:  inFileLine - single line string of the policy as defined
    //          in assignment spec.
    // EXPORT:  An instance of a car policy, values defined by data read
    //          from file.
    // REMARKS: Tiny bit of code duplication I'm unsure how to remove
    //          here.

    public CarPolicy( String inFileLine )
    {
        super( inFileLine );

        // Break down the string into an array
        String[] fields = Policy.fieldStrings( inFileLine );

        // Check that we have the number of fields we're expecting, if
        // we get more, then it's not so bad, we'll just ignore them.
        if ( fields.length >= NUM_OF_FIELDS )
            {
                this.carMake = fields[MAKE_FIELD];
                this.carModel = fields[MODEL_FIELD];
                this.manufactureYear = Integer.parseInt( fields[YEAR_FIELD] );
            }
    }




    // Alternate Constructor
    // PURPOSE:    This constructor is used when we're reading in data from
    //             the user and we've confirmed that it's an active policy
    //             and that the data is valid to the best of our ability.
    // IMPORTS:    inDate  - Date of the policy.
    //             inMake  - String of the make of the car.
    //             inModel - String of the model of the car.
    //             inYear  - Integer of the year of manufacture.
    // EXPORTS:    CarPolicy instance with the given values set.
    // Assertions:
    //     Pre: date is not a null date, and is valid.
    //          year is valid, can't after current year, or before 1770.
    // REMARKS:    None.

    private CarPolicy( PolicyDate inDate, String inMake, String inModel,
                       int inYear )
    {
        this.setDate( inDate );
        this.carMake = inMake;
        this.carModel = inModel;
        this.manufactureYear = inYear;
    }




    // NAME:    calculatePremium
    // PURPOSE: Returns the premium amount in dollars.
    // IMPORTS: None.
    // EXPORTS: A double representing the amount of the premium in dollars.
    // REMARKS: Check comments in Policy class.

    public double calculatePremium()
    {
        // Just the formula as per assignment spec.

        // Get the age of the car, based off current year and year the
        // car was made.
        int carAge = Calendar.getInstance().get( Calendar.YEAR ) -
            this.manufactureYear;
        return BASE_PREMIUM / ( 1.0 + AGING_FACTOR * carAge );
    }




    // NAME:    promptForInsurance

    // PURPOSE: Prompts the user for the car insurance details, and
    //          returns a CarPolicy instance that represents that data.
    // INPUT:  date, make, model, year for policy.
    // OUTPUT: Prompts for date, make, model, year.
    // IMPORT: None.
    // EXPORT: CarPolicy instance with values.
    // Assertions:
    //     Post: Either an inactive CarPolicy (no date set), or an active
    //           CarPolicy with all fields set best we can.
    // REMARKS: It's hard to say if it's really the right place for
    //          this, as it's mixing Input/Output into a class that could
    //          be more "pure". However, my argument is that the
    //          PolicyManager class shouldn't really know the internals
    //          of the policies too much, and that would include know what
    //          fields to prompt the user for.
    //
    //          However, if you really wanted to get rid of the coupling
    //          to the ConsoleInput class and the prompts to the user,
    //          etc, you would come up with a scheme where this class would
    //          advertise the prompts, etc, basically come up with an
    //          internal kind of protocol that represented inputs,
    //          validation, etc, needed.
    //
    //          Not very much error checking going on, can't verify much.

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




    // NAME:    toString
    // PURPOSE: Return a string in human readable format of the policy.
    // IMPORTS: None.
    // EXPORTS: A string, human readable, possibly with multiple
    //          newlines.
    // REMARKS: Check the toString() comments in Policy class.

    public String toString()
    {
        // Build the initial string from our parent, the result should
        // either be whatever the no policy text is, or the date of the
        // policy.
        String result = super.toString();

        // We only output the remaining fields if the policy is active,
        // otherwise it will just be whatever the parent class outputs.
        if ( this.isActive() )
            {
                result += "\n" +
                    "Make: \t" + this.carMake + "\n" +
                    "Model: \t" + this.carModel + "\n" +
                    "Year: \t" + this.manufactureYear + "\n" +
                    "Premium: " + this.premiumString();
            }

        return result;
    }




    // NAME:    equals
    // PURPOSE: To compare two CarPolicy instances for equality.
    // IMPORTS: inObj - Java object we want to compare to.
    // EXPORT:  boolean, true if they are equal.

    @Override public boolean equals( Object inObj )
    {
        boolean result = true;

        // Handle if we get handed in null, or completely different
        // classes.
        if ( !(inObj instanceof CarPolicy) )
            {
                result = false;
            }
        else
            {
                CarPolicy testObject = (CarPolicy) inObj;
                result = testObject.dateString().equals( this.dateString() ) &&
                    testObject.carMake.equals( this.carMake ) &&
                    testObject.carModel.equals( this.carModel ) &&
                    testObject.manufactureYear == this.manufactureYear;
            }

        return result;
    }

    // NAME:    policyFields
    // PURPOSE: Return an array of strings that represent the policy
    //          values for saving to file.
    // IMPORTS: None.
    // EXPORTS: A string array of the field values, in order they should
    //          be in the text file.
    // REMARKS: Check policyFields() comments in Policy class.

    protected String[] policyFields()
    {
        String[] resultArray = { dateString(),
                                 carMake,
                                 carModel,
                                 Integer.toString( manufactureYear ) };

        return resultArray;
    }
}
