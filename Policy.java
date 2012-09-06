// FILE:      Policy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Base policy interface, defines the minimum methods that need
//            to be defined for insurance policy classes.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  6th September 2012

public interface Policy
{
    // Be sure we can set the date on polcies.
    void setDate( int year, int month, int day );

    // Returns the value of the insurance premium.
    double premium();

    // Returns the policy as a single string.
    String toString();
}
