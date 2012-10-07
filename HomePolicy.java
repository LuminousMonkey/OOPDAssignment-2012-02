// FILE:      HomePolicy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Home policies, keeps details of the home insurance policy
//            and will do premium calculation based on postcode.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  11th September 2012

import io.ConsoleInput;

public class HomePolicy extends Policy
{
    // Constants
    // Base premium for all policies.
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
        this.setInactive();
        // Number of stores and postcode should already be invalid.
    }

    

    private HomePolicy( PolicyDate inDate, int inPostCode, int inStories )
    {
        setDate( inDate );
        postCode = inPostCode;
        numberOfStories = inStories;
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

        // Break down the string into substrings based on the field
        // separator.  Just takes the string, and returns what should be
        // an array of the separate fields.
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

    // Returns a string that can be used to display the policy out to
    // the user.
    public String toString()
    {
        String result = super.toString();

        if ( isActive() )
            {
                result += "\n" +
                    "Post Code: " + postCode + "\n" +
                    "Number of stories: " + numberOfStories + "\n" +
                    "Premium: " + calculatePremium();
            }

        return result;
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
            } while ( !increasedPremium && postCodeIndex <
                      HIGHER_PREMIUM_POSTCODES.length );

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

    // Prompt the user for the needed details for the insurance.
    //
    // Doing this mixes up the input/output code a little, however, it
    // seems more logical to have the object take care of it's own data
    // collection, since it knows what it needs, and what would be
    // accurate.
    //
    // Returns a home policy instance with the details.
    public static HomePolicy promptForInsurance()
    {
        PolicyDate date = PolicyDate.promptForDate();
        HomePolicy newPolicy = new HomePolicy();

        if (!date.isNullDate() )
            {
                int postCode = ConsoleInput.readInt( "Please enter postcode" );
                int numberOfStories = ConsoleInput.readInt( "Please enter number of stories" );
                newPolicy = new HomePolicy( date, postCode, numberOfStories );
            }

        return newPolicy;
    }
}
