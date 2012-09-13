// FILE:      PolicyHolder.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:  Handles all the details for the policy holder.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  13th September 2012

public class PolicyHolder
{
    // Variables
    public String name = "";
    public String address = "";
    public String phoneNumber = "";

    // Default constructor
    public PolicyHolder( String nameToUse, String addressToUse,
                         String phoneNumberToUse )
    {
        name = nameToUse;
        address = addressToUse;
        phoneNumber = phoneNumberToUse;
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

    public String toString()
    {
        return name + "," + address + "(" + phoneNumber + ")";
    }
}
