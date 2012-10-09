// FILE:      PolicyHolder.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Handles all the details for the policy holder.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  9th October 2012

public class PolicyHolder
{
    // Constants
    // Our fields for our policy holder is split differenty. The name
    // string is terminated by ",".
    // The phone number will start with "(".
    // These are regular expressions!
    private static final String NAME_TERMINATOR = ",";
    private static final String PHONE_NUMBER_REGEX = "\\(|\\)";

    // Fields
    private String name = "";
    private String address = "";
    private String phoneNumber = "";

    // Insurance policies for the policy holder.
    private HomePolicy homeInsurance = new HomePolicy();
    private CarPolicy carInsurance = new CarPolicy();
    private TravelPolicy travelInsurance = new TravelPolicy();

    // Default Constructor
    // PURPOSE: Default creation of a policy holder.
    // IMPORTS: None.
    // EXPORT:  An instance of a policy holder that is blank.
    // REMARKS: None.

    public PolicyHolder()
    {
        // Fields should have all been set in the field initalisation
        // above.
    }




    // Alternative constructor
    // PURPOSE: Default creation of a policy holder with the given
    //          details.
    // IMPORTS: inName, inAddress, inPhoneNumber - details of the policy
    //          holder.
    // EXPORT:  An instance of a policy holder that has the given fields
    //          set.
    // REMARKS: None.

    public PolicyHolder( String inName, String inAddress,
                         String inPhoneNumber )
    {
        this.name = inName;
        this.address = inAddress;
        this.phoneNumber = inPhoneNumber;
    }




    // Alternative constructor
    // PURPOSE: Creating of a policy holder that is intended to be used
    //          for searching.
    // IMPORTS: inName, inAddress - details of the policy holder.
    // EXPORT:  An instance of a policy holder that has the given fields
    //          set.
    // REMARKS: None.

    public PolicyHolder( String inName, String inAddress )
    {
        name = inName;
        address = inAddress;
        phoneNumber = "";
    }




    // Alternative constructor
    // PURPOSE: Creating a policy holder based on the single line we get
    //          from the file.
    // IMPORTS: inFileLine - Single string that represents from policy
    //          holder from the file.
    // EXPORT:  An instance of a policy holder that has the given fields
    //          set.
    // REMARKS: None.

    public PolicyHolder( String inFileLine )
    {
        // We have our PolicyHolder string, the first "," represents the
        // name of the Policy Holder, with the rest of the string (which
        // may contain more "," characters that are unrelated to the
        // holder's name) being the address and phone number.

        // We then parse the remainer of text as address, and find the phone number.
        String nameAndRemainer[] = inFileLine.split( NAME_TERMINATOR, 2);

        if ( nameAndRemainer.length > 1 )
            {
                // First in the array will be our name, the second in the array
                // will be the address and phone number, so get the phone number,
                String addressAndPhoneNumber[] =
                    nameAndRemainer[1].split( PHONE_NUMBER_REGEX );

                if ( addressAndPhoneNumber.length > 1 )
                    {
                        this.name = nameAndRemainer[0];
                        this.address = addressAndPhoneNumber[0];
                        this.phoneNumber = addressAndPhoneNumber[1];
                    }
            }
    }




    // The following setters will take a string of the related policy
    // and update the policy holder's insurance details to match.
    public void setHomePolicy( String inHomePolicy )
    {
        homeInsurance = new HomePolicy( inHomePolicy );
    }

    public void setCarPolicy( String inCarPolicy )
    {
        carInsurance = new CarPolicy( inCarPolicy );
    }

    public void setTravelPolicy( String inTravelPolicy )
    {
        travelInsurance = new TravelPolicy( inTravelPolicy );
    }




    // NAME:    promptForInsurances
    // PURPOSE: Prompt the user to enter in details for each type of
    //          insurance.
    // INPUTS:  Details for each type of insurance policy.
    // OUTPUTS: Prompts for details for each type of insurance policy.
    // IMPORTS: None.
    // EXPORTS: None.

    public void promptForInsurances()
    {
        System.out.println( "Home Insurance" );
        homeInsurance = HomePolicy.promptForInsurance();

        System.out.println( "Car Insurance" );
        carInsurance = CarPolicy.promptForInsurance();

        System.out.println( "Travel Insurance" );
        travelInsurance = TravelPolicy.promptForInsurance();
    }




    // NAME:    fileString
    // PURPOSE: Return a string that represents the policy holder
    //          details to save to the file.
    // IMPORTS: None.
    // EXPORTS: Single string that is not newline terminated that
    //          represents the whole policy holder, including insurance
    //          policies in the format expected for saving to the file.
    // REMARKS: None.

    public String fileString()
    {
        return name + "," + address + "(" + phoneNumber + ")" + "\n" +
            homeInsurance.fileString() + "\n" +
            carInsurance.fileString() + "\n" +
            travelInsurance.fileString() + "\n";
    }




    // NAME:    toString
    // PURPOSE: Returns a string that is in human readable format,
    //          showing all the details of the policy holder, along with
    //          their policies.
    // IMPORTS: None.
    // EXPORTS: String of policy holder in human readable format.
    // REMARKS: I am a fish, I am a fish, I am a fish, I am a fish.

    public String toString()
    {
        return "Name: " + name + "\n" +
            "Address: " + address + "\n" +
            "Phone number: " + phoneNumber + "\n\n" +
            "Home Policy\n" +
            homeInsurance + "\n\n" +
            "Car Policy\n" +
            carInsurance + "\n\n" +
            "Travel Policy\n" +
            travelInsurance + "\n\n";
    }




    // NAME:    equals
    // PURPOSE: To compare two HomePolicy instances for equality.
    // IMPORTS: inHomePolicy - HomePolicy we want to compare to.
    // EXPORT:  boolean, true if they are equal.

    @Override public boolean equals( Object inObj )
    {
        boolean result = true;

        // Handle the case of being handed null or completely different
        // objects.
        if ( !(inObj instanceof PolicyHolder ) )
            {
                result = false;
            }
        else
            {
                PolicyHolder comparisionObject = (PolicyHolder) inObj;

                // As per assignment spec, a policy holder is defined by
                // the name and address.
                result = comparisionObject.name.equals( name ) &&
                    comparisionObject.address.equals( address );
            }

        return result;
    }
}
