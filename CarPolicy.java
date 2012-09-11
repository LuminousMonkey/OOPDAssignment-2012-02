// FILE:      CarPolicy.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This class handles the insurance policies that take care of
//            cars.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  11th September 2012

public class CarPolicy implements {
    // Constants Value that represents an invalid year for date of
    // manufacture.
    private static final int INVALID_YEAR = -1;

    private PolicyDate date = null;
    private String carMake = "";
    private String carModel = "";
    private int manufactureYear = INVALID_YEAR;

    // Constructor
    public CarPolicy( int date, String make, String model, int year )
    {
        // Assume the fields are correct for now.
        this.setDate( date );
        this.setMake( make );
        this.setModel( model );
        this.setYear( year );
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

    public void setMake( String make )
    {
        carMake = make;
    }

    public void setModel( String model )
    {
        carModel = model;
    }

    // Returns a string that represents the line that should be written
    // out to the file, single line as per the assignment spec.
    public String toString()
    {
        String[] stringFields = { date.toString(),
                                  carMake,
                                  carModel };

        if ( date.isNullDate() )
            {
                stringFields = new String[1];
                stringFields[0] = date.toString();
            }

        return Utility.interposeFields( stringFields ) + "\n";
    }
}
