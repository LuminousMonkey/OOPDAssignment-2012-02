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
    private static final int DATE_FIELD = 0;
    private static final int COUNTRY_FIELD = 1;

    // This should be update if the number of fields above change.
    private static final int NUM_OF_FIELDS = 2;

    // Country that is covered under this policy.
    private String policyCountry = "";

    // Default constructor
    public TravelPolicy()
    {
        // All our default values are set in the fields above, just call
        // the parent constructor.
        super();
    }

    // Alternate Constructor
    private TravelPolicy( PolicyDate date, String policyCountry )
    {
        setDate( date );
        setCountry( policyCountry );
    }

    // Takes a single line from the policy file as a string and returns
    // a matching home policy file.
    public TravelPolicy( String inFileLine )
    {
        String[] fields = Policy.fieldStrings( inFileLine );

        setDate( fields[DATE_FIELD] );

        if ( fields.length >= NUM_OF_FIELDS )
            {
                setCountry( fields[COUNTRY_FIELD] );
            }
    }

    // Setters
    public void setCountry( String country )
    {
        policyCountry = country;
    }

    // Returns a string that can be used to display the policy details
    // out to the user.
    public String toString()
    {
        String result = super.toString();

        // If the policy is active, then give all the details, otherwise
        // just indicate the policy is not active.
        if ( isActive() )
            {
                result += "\n" +
                    "Country: " + policyCountry + "\n" +
                    "Premium: " + calculatePremium();
            }

        return result;
    }

    // Returns the calculation of the travel insurance premium, very
    // simple, just the base premium, then an extra amount is added on
    // if the country matches any of the high premium countries.
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
                if ( HIGHER_PREMIUM_COUNTRIES[countryIndex].equals( policyCountry ) )
                    {
                        increasedPremium = true;
                        result += HIGHER_PREMIUM;
                    }
                countryIndex++;
            } while ( !increasedPremium && countryIndex < HIGHER_PREMIUM_COUNTRIES.length );

        return result;
    }

    // Returns a string array that represents the order that the policy
    // fields should be ordered in the text file.
    // Check the comment in the Policy class.
    protected String[] policyFields()
    {
        String[] resultArray = { dateString(),
                                 policyCountry };

        return resultArray;
    }

    public static TravelPolicy promptForInsurance()
    {
        PolicyDate date = PolicyDate.promptForDate();
        TravelPolicy newPolicy = new TravelPolicy();

        if ( !date.isNullDate() )
            {
                // User entered in a date.
                String country = ConsoleInput.readLine( "Please enter country" );
                newPolicy = new TravelPolicy( date, country );
            }

        return newPolicy;
    }
}
