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
    // The date field is taken care of in the Policy class, start from 1.
    private static final int POSTCODE_FIELD = 1;
    private static final int STORIES_FIELD = 2;

    // This should be updated if the number of fields above change.
    private static final int NUM_OF_FIELDS = STORIES_FIELD + 1;

    // Variables
    private int postCode = INVALID_POSTCODE;
    private int numberOfStories = INVALID_NUMBER_OF_STORIES;




    // Default Constructor
    // PURPOSE: Default creation of inactive home policy.
    // IMPORTS: None.
    // EXPORT:  An instance of a home policy that is considered inactive.
    // REMARKS: None.

    public HomePolicy()
    {
        // All our default values are set in the fields above, just call
        // the parent constructor.
        super();
    }

    // Copy constructor
    // PURPOSE: Takes a HomePolicy instance and returns a seperate copy
    //          of that instance.
    // IMPORT:  HomePolicy instance to copy.
    // EXPORT:  New instance of HomePolicy object.
    // REMARKS: None.

    public HomePolicy( HomePolicy inHomePolicy )
    {
        setDate( inHomePolicy.dateString() );
        setPostCode( inHomePolicy.postCode );
        setNumberOfStories( inHomePolicy.numberOfStories );
    }




    // Alternate Constructor
    // PURPOSE: This constructor is used when reading in the policy from
    //          the file.
    // IMPORTS: inFileLine - single line string of the policy as defined
    //          in assignment spec.
    // EXPORT:  An instance of a home policy, values defined by data read
    //          from file.
    // REMARKS: Tiny bit of code duplication I'm unsure how to remove
    //          here.

    public HomePolicy( String inFileLine )
    {
        super( inFileLine );

        // Break down the string into an array
        String[] fields = Policy.fieldStrings( inFileLine );

        // Check that we have the number of fields we're expecting, if
        // we get more, then it's not so bad, we'll just ignore them.
        if ( fields.length >= NUM_OF_FIELDS )
            {
                setPostCode( Integer.parseInt( fields[POSTCODE_FIELD] ) );
                setNumberOfStories( Integer.parseInt( fields[STORIES_FIELD] ) );
            }
    }

    // Alternate Constructor
    // PURPOSE:    This constructor is used when we're reading in data from
    //             the user and we've confirmed that it's an active policy
    //             and that the data is valid to the best of our ability.
    // IMPORTS:    inDate  - Date of the policy.
    //             inPostCode - Integer of the postcode.
    //             inStories - Number of stories of the house.
    // EXPORTS:    HomePolicy instance with the given values set.
    // Assertions:
    //     Pre: inDate is not a null date, and is valid.
    //          inPostcode is greater than 0.
    //          inStories greater than 0.
    // REMARKS:    None.

    private HomePolicy( PolicyDate inDate, int inPostCode, int inStories )
    {
        setDate( inDate );
        postCode = inPostCode;
        numberOfStories = inStories;
    }




    // NAME:    calculatePremium
    // PURPOSE: Returns the premium amount in dollars.
    // IMPORTS: None.
    // EXPORTS: A double representing the amount of the premium in dollars.
    // REMARKS: Check comments in Policy class.

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




    // NAME:    toString
    // PURPOSE: Return a string in human readable format of the policy.
    // IMPORTS: None.
    // EXPORTS: A string, human readable, possibly with multiple
    //          newlines.
    // REMARKS: Check the toString() comments in Policy class.

    public String toString()
    {
        String result = super.toString();

        if ( isActive() )
            {
                result += "\n" +
                    "Post Code:  " + postCode + "\n" +
                    "Number of stories: " + numberOfStories + "\n" +
                    "Premium: " + this.premiumString();
            }

        return result;
    }




    // NAME:    equals
    // PURPOSE: To compare two HomePolicy instances for equality.
    // IMPORTS: inHomePolicy - HomePolicy we want to compare to.
    // EXPORT:  boolean, true if they are equal.

    @Override public boolean equals( Object inObj )
    {
        boolean result = true;

        // Handle if we get handed in null, or completely different
        // classes.
        if ( !(inObj instanceof HomePolicy) )
            {
                result = false;
            }
        else
            {
                HomePolicy testObject = (HomePolicy) inObj;
                result = testObject.dateString().equals( this.dateString() ) &&
                    testObject.postCode == this.postCode &&
                    testObject.numberOfStories == this.numberOfStories;
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
        String[] resultArray = { this.dateString(),
                                 Integer.toString( this.postCode ),
                                 Integer.toString( this.numberOfStories )};

        return resultArray;
    }
}
