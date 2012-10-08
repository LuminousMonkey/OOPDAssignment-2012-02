// FILE:      PolicyFile.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   For handling reading and writing to the policy
//            file. Anything to do with reading and writing to the file,
//            should be here.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  TextFile class provided for assignment.
// Last Mod:  8th October 2012

public class PolicyFile
{
    // These are constants that relate to the modes available in the
    // TextFile class. Please note that these depend on that class and
    // may change if that class changes.
    private static final String READ_MODE = "r";
    private static final String WRITE_MODE = "w";
    private static final String APPEND_MODE = "a";

    // Name of the policy file that we want to read/write/append to.
    private String filename = "";

    // Assume that we're creating a new file when we write, this should
    // be updated to APPEND_MODE if the file already exists.
    private String writeMode = WRITE_MODE;

     // Default constructor
    //
    // PURPOSE: Takes a file name, and tests if the file exists, if it
    //          does, then it makes sure we're in append mode.
    // IMPORT:  inFilename - A string that represents the filename of the
    //          file.
    // EXPORT:  PolicyFile class, set into append mode if the file
    //          exists, otherwise any write to the file will create it.
    // REMARKS: There is no need for a "blank" default constructor, file
    //          operations always need a filename.

    public PolicyFile( String inFilename )
    {
        filename = inFilename;
        if ( testForFile( inFilename ) )
            {
                // If the file exists, then if we ever write to it,
                // we're appending.
                writeMode = APPEND_MODE;
            }
    }




    // NAME: fileExists
    // PURPOSE: Returns true if the file this instance represents
    //          exists.
    // IMPORT:  None.
    // EXPORT:  Boolean, true if the file exists, false otherwise.
    // REMARKS: The file exist test is just based off the write mode, it
    //          doesn't check to see if the file actually exists each time.
    //          So if something happens between the time we actually
    //          checked, and the time that this is called, it won't be
    //          actually true.

    public boolean fileExists()
    {
        return ( !writeMode.equals( WRITE_MODE ) );
    }




    // NAME: findHolder
    // PURPOSE: Search the file for the given PolicyHolder and return
    //          the compete PolicyHolder with details from the file.
    // IMPORT:  PolicyHolder we are to search for.
    // EXPORT:  PolicyHolder that was found, including any policy details.
    // Assertions
    //     Pre: File is available for reading.
    // REMARKS: Will give a false positive if the file contains a blank
    //          PolicyHolder.

    public PolicyHolder findHolder( PolicyHolder inHolder )
    {
        // To check if a policy holder exists in a file, we open it, and
        // loop through each entry, comparing with the policy holder
        // we've been given, a match means that the policy holder exists
        // in the file.
        TextFile fileOfPolicies = new TextFile( filename, READ_MODE );
        PolicyHolder currentFilePolicyHolder = new PolicyHolder();

        boolean matchFound = false;
        if ( fileOfPolicies.openFile() )
            {
                // We can read the file, lets start searching.
                while ( !fileOfPolicies.endOfFile() && !matchFound )
                    {
                        // Read in the line as a policy holder.

                        // It's expected if the policy holder class is
                        // passed invalid data that it will just create
                        // a blank policy holder.

                        // A blank policy holder shouldn't match the
                        // given holder we're searching for, unless the
                        // user added in blank data. So that will give a
                        // false positive.
                        currentFilePolicyHolder =
                            new PolicyHolder( readLine( fileOfPolicies) );

                        matchFound = inHolder.equals( currentFilePolicyHolder );
                    }
            }

        // If a match was found, then the next three lines should be the
        // policy holder's home, car, and travel policies in that order.
        // Pass each line into the policy holder. As far as we're
        // concerned, policy holder does it all.
        if ( matchFound )
            {
                currentFilePolicyHolder.setHomePolicy( readLine( fileOfPolicies) );
                currentFilePolicyHolder.setCarPolicy( readLine( fileOfPolicies) );
                currentFilePolicyHolder.setTravelPolicy( readLine( fileOfPolicies) );
            }
        else
            {
                // We didn't find a match, so make sure we will return a
                // null result.
                currentFilePolicyHolder = null;
            }

        // TextFile takes care of file closing for us, no need to check
        // if the file opened.
        fileOfPolicies.closeFile();

        return currentFilePolicyHolder;
    }

    // NAME: writeHolder

    // PURPOSE: Given a PolicyHolder, writes the holder to the file,
    //          either appending or creating the file as necessary.
    // OUTPUT:  Writes the policy holder out to file if successful.
    // IMPORT:  inHolder - PolicyHolder to write.
    // EXPORT:  None.
    // REMARKS: Assumes that the save is always going to work.
    //          Doesn't check if polcy holder already exists in the
    //          file, that is expected to be done before this method is
    //          called.

    public void writeHolder( PolicyHolder inHolder )
    {
        // We don't actually keep the file handle open all
        // the time, we only open it for when we need it.

        TextFile fileToWriteTo = new TextFile( filename, writeMode );

        if ( fileToWriteTo.openFile() )
            {
                // We have managed to open the file in a writeable mode.
                // Check that the policy holder we've got isn't already
                // in the file.
                fileToWriteTo.printIt( inHolder.fileString() );

                // We do no error checking to keep the assignment simple
                // assume it saved, and make sure we switch the file to
                // append mode now.
                writeMode = APPEND_MODE;
            }

        // We don't need to worry about checking if the file was open or
        // not, as that is taken care of in the TextFile class.
        fileToWriteTo.closeFile();
    }





    // NAME: toString
    // PURPOSE: Very simple method, just shows filename and the
    //          fileExists value.
    // IMPORT: None.
    // EXPORT: String that represents the date.
    // REMARKS: None.

    public String toString()
    {
        return "Filename: " + filename + " File Exists: " +
            fileExists();
    }




    // NAME:    readLine
    // PURPOSE: Read a single line from the text file including the
    //          terminating newline.
    // INPUT:   Read line from file.
    // IMPORT:  File to read from.
    // EXPORT:  A string of the line that was read, does not include the
    //          terminating newline character.
    //          String will be empty if there's nothing more to read.
    // Assertions
    //     Pre: openFile() method has been called and was successful.
    // REMARKS: All our read operations are done through this, doesn't
    //          handle any read errors.
    private String readLine( TextFile fileOfPolicies )
    {
        String result = "";
        boolean newLineFound = false;

        char currentCharacter = ' ';

        // Loop until we find a newline character, or we hit the end of
        // the file.
        while ( !fileOfPolicies.endOfFile() && !newLineFound )
            {
                currentCharacter = fileOfPolicies.readChar();
                if ( currentCharacter == '\n' )
                    {
                        // We've found our newline.
                        newLineFound = true;
                    }
                else
                    {
                        result += currentCharacter;
                    }
            }

        return result;
    }




    // NAME:    testForFile
    // PURPOSE: Just tests if file with the given name can be read.
    // IMPORT:  filename - String, name of file we're looking for.
    // EXPORT:  Boolean, returns true if the file was readable.
    // REMARKS: Doesn't do a comprehensive test, writing might not work,
    //          etc.

    private static boolean testForFile( String filename )
    {
        TextFile fileTest = new TextFile( filename, READ_MODE );

        boolean result = false;
        if ( fileTest.openFile() )
            {
                result = true;
            }

        // TextFile class is nice, and will make sure we can't close a
        // file we haven't opened already.
        fileTest.closeFile();

        return result;
    }
}
