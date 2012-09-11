// FILE:      HomePolicy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Home policies, keeps details of the home insurance policy
//            and will do premium calculation based on postcode.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  11th September 2012

public class HomePolicy extends Policy
{
    // Constants
    // Base premium for all polcies.
    private static final double BASE_PREMIUM = 500.00;

    // Amount to be added for postcodes defined as higher premium.
    private static final double HIGHER_PREMIUM = 200.00;

    // Postcodes that are to pay an extra premium.
    private static final int[] HIGHER_PREMIUM_POSTCODES = { 6011,
                                                            6056,
                                                            6061,
                                                            6112 };

    // Value that represent invalid values, for anything that's not set
    // correctly.
    private static final int INVALID_POSTCODE = -1;
    private static final int INVALID_NUMBER_OF_STORIES = -1;

    // Variables
    private int postCode = INVALID_POSTCODE;
    private int numberOfStories = INVALID_NUMBER_OF_STORIES;

    // Constructor
    public HomePolicy( int date, int stories, int postCode )
    {
        // Assume the fields are correct for now.
        setDate( date );
        setNumberOfStories( stories );
        setPostCode( postCode );
    }

    // Setters
    public void setPostCode( int newPostCode )
    {
        postCode = newPostCode;
    }

    public void setNumberOfStories( int newNumberOfStories )
    {
        numberOfStories = newNumberOfStories;
    }

    // Assessors
    public int getPostCode()
    {
        return postCode;
    }

    public int getNumberOfStories()
    {
        return numberOfStories;
    }

    // Returns the calculation of the home insurance premium based on
    // postcode. If the postcode on a policy matches one of the higher
    // premium postcodes, then we're going to charge more, otherwise
    // just the base premium.
    //
    // TODO: What's the premium of a null policy?
    public double calculatePremium()
    {
        boolean increasedPremium = false;

        // Step through the array that represents higher premium
        // postcodes looking for a match, the moment we find one, be
        // sure we'll add the higher premium.
        int postCodeIndex = 0;
        double result = BASE_PREMIUM;
        do
            {
                if ( HIGHER_PREMIUM_POSTCODES[postCodeIndex] == postCode )
                    {
                        increasedPremium = true;
                        result += HIGHER_PREMIUM;
                    }
                postCodeIndex++;
            } while ( !increasedPremium && postCodeIndex < HIGHER_PREMIUM_POSTCODES.length );

        return result;
    }

    // Returns a string array that represents the order that the policy
    // fields should be ordered in the text file.
    // Check the comment in the Policy class.
    protected String[] policyFields()
    {
        String[] resultArray = { dateString(),
                                 Integer.toString( numberOfStories ),
                                 Integer.toString( postCode ) };

        return resultArray;
    }
}
