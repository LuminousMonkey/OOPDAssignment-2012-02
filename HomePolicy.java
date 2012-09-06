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
    private int postCode = null;
    private int numberOfStories = null;

    // Setters
    void setPostCode( int newPostCode )
    {
        postCode = newPostCode;
    }

    void setPostCode( String newPostCode )
    {
        postCode = Integer.parseInt( newPostCode );
    }

    void setNumberOfStories( int newNumberOfStories )
    {
        numberOfStories = newNumberOfStories;
    }

    // Assessors
    int getPostCode()
    {
        return postCode;
    }

    int getNumberOfStories()
    {
        return numberOfStories;
    }

    String toString()
    {

    }

    // Returns the calculation of the home insurance premium based on
    // postcode
    double premium()
    {
    }
}
