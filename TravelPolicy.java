// FILE:      TravelPolicy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This class handles the insurance policies that take care of
//            travel.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  11th September 2012

import io.ConsoleInput;

public class TravelPolicy extends Policy
{
    // Constants
    // Base premium for all policies.
    private static final double BASE_PREMIUM = 100.00;

    // Amount to be added for countries that have a high premium.
    private static final double HIGHER_PREMIUM = 150.00;

    // Countries that result in higher premiums.
    private static final String[] HIGHER_PREMIUM_COUNTRIES = {
        "United States", "Russia" };

    // Position of the fields in the policy file.
    private static final int COUNTRY_FIELD = 1;

    // This should be update if the number of fields above change.
    private static final int NUM_OF_FIELDS = COUNTRY_FIELD + 1;

    // Country that is covered under this policy.
    private String policyCountry = "";




    // Default Constructor
    // PURPOSE: Default creation of inactive travel policy.
    // IMPORTS: None.
    // EXPORT:  An instance of a travel policy that is considered inactive.
    // REMARKS: None.

    public TravelPolicy()
    {
        // All our default values are set in the fields above, just call
        // the parent constructor.
        super();
    }




    // Alternate Constructor
    // PURPOSE: This constructor is used when reading in the policy from
    //          the file.
    // IMPORTS: inFileLine - single line string of the policy as defined
    //          in assignment spec.
    // EXPORT:  An instance of a travel policy, values defined by data read
    //          from file.
    // REMARKS: Tiny bit of code duplication I'm unsure how to remove
    //          here.

    public TravelPolicy( String inFileLine )
    {
        super( inFileLine );

        // Break down the string into an array
        String[] fields = Policy.fieldStrings( inFileLine );

        // Check that we have the number of fields we're expecting, if
        // we get more, then it's not so bad, we'll just ignore them.
        if ( fields.length >= NUM_OF_FIELDS )
            {
                setCountry( fields[COUNTRY_FIELD] );
            }
    }


    // Alternate Constructor
    // PURPOSE:    This constructor is used when we're reading in data from
    //             the user and we've confirmed that it's an active policy
    //             and that the data is valid to the best of our ability.
    // IMPORTS:    inDate           - Date of the policy.
    //             inPolicyCountry  - String of the country name.
    // EXPORTS:    TravelPolicy instance with the given values set.
    // REMARKS:    None.

    private TravelPolicy( PolicyDate inDate, String inPolicyCountry )
    {
        this.setDate( inDate );
        this.policyCountry = inPolicyCountry;
    }

    // Setters
    public void setCountry( String country )
    {
        this.policyCountry = country;
    }



    // NAME:    calculatePremium
    // PURPOSE: Returns the premium amount in dollars.
    // IMPORTS: None.
    // EXPORTS: A double representing the amount of the premium in dollars.
    // REMARKS: Check comments in Policy class.

    public double calculatePremium()
    {
        boolean increasedPremium = false;

        // Step through the array that represents the higher countries,
        // looking for a match, the moment one is found, add the higher
        // premium.
        int countryIndex = 0;
        double result = BASE_PREMIUM;
        do
            {
                if ( HIGHER_PREMIUM_COUNTRIES[countryIndex].equals(                                                               this.policyCountry ) )
                    {
                        increasedPremium = true;
                        result += HIGHER_PREMIUM;
                    }
                countryIndex++;
            } while ( !increasedPremium &&
                      countryIndex < HIGHER_PREMIUM_COUNTRIES.length );

        return result;
    }



    // NAME:    promptForInsurance

    // PURPOSE: Prompts the user for the car insurance details, and
    //          returns a TravelPolicy instance that represents that data.
    // INPUT:  date, country for policy.
    // OUTPUT: Prompts for date, country.
    // IMPORT: None.
    // EXPORT: TravelPolicy instance with values.
    // Assertions:
    //     Post: Either an inactive TravelPolicy (no date set), or an active
    //           TravelPolicy with all fields set best we can.
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
    public static TravelPolicy promptForInsurance()
    {
        PolicyDate date = PolicyDate.promptForDate();
        TravelPolicy newPolicy = new TravelPolicy();

        // No point asking for more fields if the policy isn't active.
        if ( !date.isNullDate() )
            {
                // User entered in a date.
                String country = ConsoleInput.readLine( "Please enter country" );
                newPolicy = new TravelPolicy( date, country );
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
        String result = super.toString();

        // If the policy is active, then give all the details, otherwise
        // just indicate the policy is not active.
        if ( this.isActive() )
            {
                result += "\n" +
                    "Country: " + this.policyCountry + "\n" +
                    "Premium: " + this.premiumString();
            }

        return result;
    }

    // NAME:    equals
    // PURPOSE: To compare two CarPolicy instances for equality.
    // IMPORTS: inCarPolicy - CarPolicy we want to compare to.
    // EXPORT:  boolean, true if they are equal.

    @Override public boolean equals( Object inObj )
    {
        boolean result = true;

        // Handle if we get handed in null, or completely different
        // classes.
        if ( !(inObj instanceof TravelPolicy) )
            {
                result = false;
            }
        else
            {
                TravelPolicy testObject = (TravelPolicy) inObj;
                result = testObject.dateString().equals( this.dateString() ) &&
                    testObject.policyCountry.equals( this.policyCountry );
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
                                 this.policyCountry };

        return resultArray;
    }
}
