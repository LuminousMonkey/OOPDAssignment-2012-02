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

    // Position of the fields in the policy file.
    private static final int DATE_FIELD  = 0;
    private static final int POSTCODE_FIELD = 1;
    private static final int STORIES_FIELD = 2;

    // This should be updated if the number of fields above change.
    private static final int NUM_OF_FIELDS = 3;

    // Variables
    private int postCode = INVALID_POSTCODE;
    private int numberOfStories = INVALID_NUMBER_OF_STORIES;

    // Default constructor
    public HomePolicy()
    {
        setDate( PolicyDate.NULL_DATE );
        // Number of stores and postcode should already be invalid.
    }

    // Alternate Constructor
    public HomePolicy( int date, int postCode, int stories )
    {
        // Assume the fields are correct for now.
        setDate( date );
        setPostCode( postCode );
        setNumberOfStories( stories );
    }

    // Copy constructor
    public HomePolicy( HomePolicy inHomePolicy )
    {
        setDate( inHomePolicy.dateString() );
        setPostCode( inHomePolicy.postCode );
        setNumberOfStories( inHomePolicy.numberOfStories );
    }

    // Takes in a single line from the policy file as a string and
    // returns a matching home policy file.
    public HomePolicy( String inFileLine )
    {
        // The format of the string should match the format of our
        // toString function.

        // Break down the string into substrings based on the field seperator.
        String[] fields = Utility.fieldStrings( inFileLine );

        // Fields are all hardcoded.  Date field should always be
        // present, however if it's the only field, then don't bother
        // setting any of the other field values, rely on the default
        // values being set correctly.
        setDate( fields[DATE_FIELD] );

        // Check that we have the number of fields we're expecting, if
        // we get more, then it's not so bad, we'll just ignore them.
        if ( fields.length >= NUM_OF_FIELDS )
            {
                setPostCode( Integer.parseInt( fields[POSTCODE_FIELD] ) );
                setNumberOfStories( Integer.parseInt( fields[STORIES_FIELD] ) );
            }
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
                                 Integer.toString( postCode ),
                                 Integer.toString( numberOfStories )};

        return resultArray;
    }
}
