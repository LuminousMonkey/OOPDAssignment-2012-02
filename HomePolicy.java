// FILE:      HomePolicy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Home policies, keeps details of the home insurance policy
//            and will do premium calculation based on postcode.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  9th September 2012

public class HomePolicy implements Policy
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

    // Variables
    private PolicyDate date = null;
    private int postCode = null;
    private int numberOfStories = null;

    // Setters
    public void setDate( int dateToParse )
    {
        date = new PolicyDate( dateToParse.toString() );
    }

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

    public String toString()
    {
        return Utility.interposeFields( {date.toString(),
                    postCode.toString(),
                    numberOfStories.toString() } ) + "\n";
    }

    // Returns the calculation of the home insurance premium based on
    // postcode
    double premium()
    {

    }
}
