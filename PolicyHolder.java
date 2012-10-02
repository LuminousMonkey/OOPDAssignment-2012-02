// FILE:      PolicyHolder.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Handles all the details for the policy holder.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  16th September 2012

public class PolicyHolder
{
    // Constants
    // Seed value for the hashCode function needed by Java.
    private static final int HASH_SEED = 15;
    private static final int HASH_MULTIPLIER = 31;

    // Our fields for our policy holder is split differenty. The name
    // string is terminated by ",".
    // The phone number will start with "(".
    // These are regular expressions!
    private static final String nameTerminator = ",";
    private static final String phoneNumberRegEx = "\\(|\\)";

    // Variables
    private String name = "";
    private String address = "";
    private String phoneNumber = "";

    // Insurance policies for the policy holder.
    private HomePolicy homeInsurance = new HomePolicy();
    private CarPolicy carInsurance = new CarPolicy();
    private TravelPolicy travelInsurance = new TravelPolicy();

    // Default constructor
    public PolicyHolder()
    {

    }

    // Alternate constructor
    public PolicyHolder( String inName, String inAddress,
                         String inPhoneNumber )
    {
        name = inName;
        address = inAddress;
        phoneNumber = inPhoneNumber;
    }

    // Alternate constructor If we've given a single string, then we
    // expect it to be the same as the output string of our toString method.
    public PolicyHolder( String inHolder )
    {
        // We have our PolicyHolder string, the first "," represents the
        // name of the Policy Holder, with the rest of the string (which
        // may contain more "," characters that are unrelated to the
        // holder's name) being the address and phone number.

        // We then parse the remainer of text as address, and find the phone number.
        String nameAndRemainer[] = inHolder.split( nameTerminator, 2);

        if ( nameAndRemainer.length > 1 )
            {
                // First in the array will be our name, the second in the array
                // will be the address and phone number, so get the phone number,
                String addressAndPhoneNumber[] = nameAndRemainer[1].split( phoneNumberRegEx );

                if ( addressAndPhoneNumber.length > 1 )
                    {
                        name = nameAndRemainer[0];
                        address = addressAndPhoneNumber[0];
                        phoneNumber = addressAndPhoneNumber[1];
                    }
            }
    }

    // Accessors
    public String getName()
    {
        return name;
    }

    public String getAddress()
    {
        return address;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public HomePolicy getHomeInsurance()
    {
        return homeInsurance;
    }

    public CarPolicy getCarInsurance()
    {
        return carInsurance;
    }

    public TravelPolicy getTravelInsurance()
    {
        return travelInsurance;
    }

    // setPolicy
    public void setPolicy( HomePolicy inHomePolicy )
    {
        homeInsurance = new HomePolicy( inHomePolicy );
    }

    public String toString()
    {
        return name + "," + address + "(" + phoneNumber + ")";
    }

    // The equals method, this is one of the base methods that are
    // inherited from the Java "Object" itself, and has to follow
    // certain rules.
    @Override public boolean equals( Object inObj )
    {
        boolean result = true;

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

    // If you override the equals method, then you must also override
    // the hashCode method.
    //
    // Writing a good hash code is hard, need to know maths.
    @Override public int hashCode()
    {
        int result = HASH_SEED;

        // We don't care too much about generating a good hashing code
        // here.
        result = HASH_MULTIPLIER * result + name.hashCode();
        result = HASH_MULTIPLIER * result + address.hashCode();

        return result;
    }
}
