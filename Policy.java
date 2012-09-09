// FILE:      Policy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   Base policy interface, defines the minimum methods that need
//            to be defined for insurance policy classes.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  9th September 2012

public interface Policy
{
    // All policies must have a date, hence the policy interface must
    // have a way of having a date set.
    void setDate( String dateToParse );

    // All insurance policies must have a premium that must be
    // calculated, the premium calculation may depend on details in the
    // poilcy, so if this method is called before policy information is
    // set, it must return a null value.
    double premium();

    // Returns the policy as a single line, as expected in the file
    // format.
    String toString();
}