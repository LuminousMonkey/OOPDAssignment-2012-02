// FILE:      TravelPolicy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This class handles the insurance policies that take care of
//            travel.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  11th September 2012

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

    // Country that is covered under this policy.
    private String policyCountry = "";

    // Constructor
    public TravelPolicy( int date, String policyCountry )
    {
        setDate( date );
        setCountry( policyCountry );
    }

    // Setters
    public void setCountry( String country )
    {
        policyCountry = country;
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

    protected String[] policyFields()
    {
        String[] resultArray = { dateString(),
                                 policyCountry };

        return resultArray;
    }
}
