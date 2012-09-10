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

    // Value that represent invalid values, for anything that's not set
    // correctly.
    private static final int INVALID_POSTCODE = -1;
    private static final int INVALID_NUMBER_OF_STORIES = -1;

    // Variables
    private PolicyDate date = null;
    private int postCode = INVALID_POSTCODE;
    private int numberOfStories = INVALID_NUMBER_OF_STORIES;

    // Constructor
    public HomePolicy( int date, int stories, int postCode )
    {
        // Assume the fields are correct for now.
        this.setDate( date );
        this.setNumberOfStories( stories );
        this.setPostCode( postCode );
    }

    // Setters
    public void setDate( String dateToParse )
    {
        date = new PolicyDate( dateToParse );
    }

    public void setDate( int dateToParse )
    {
        date = new PolicyDate( Integer.toString( dateToParse ) );
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

    // Returns a string that represents the line that should be written
    // out to the file, single line as per the assignment spec.
    public String toString()
    {
        // To try and make outputting a little more consistent, there is
        // actually an "interpostFields" function that will take an
        // array, and insert the field delimiter character between each
        // string given in that array, with a delimiter at the end.
        //
        // Unfortunately, doing it this way, means we have to massage
        // the array we pass to it. And also because Java only supports
        // defining an array with {} on initialisation we have the code
        // below.
        //
        // String array if everything is right with the world, and this
        // is an active policy.
        String[] stringFields = { date.toString(),
                                  Integer.toString( postCode ),
                                  Integer.toString( numberOfStories ) };

        // No date, so the policy isn't valid/active or just doesn't
        // exist.
        if ( date.isNullDate() )
            {
                // Can't use the syntax sugar of "array = {};" here, so
                // we create an array of length 1, and just put the date
                // in it.
                stringFields = new String[1];
                stringFields[0] = date.toString();
            }

        // The interposeFields method does all the work, once we give it
        // the correct array.
        return Utility.interposeFields( stringFields ) + "\n";
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
}
