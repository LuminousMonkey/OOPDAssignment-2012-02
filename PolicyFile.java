// FILE:      PolicyFile.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   For handling reading and writing to the policy
//            file. Anything to do with reading and writing to the file, should be
//            here.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  16th September 2012

public class PolicyFile
{
    // These are constants that relate to the modes available in the
    // TextFile class. Please note that these depend on that class and
    // may change if that class changes.
    private static final String READ_MODE = "r";
    private static final String WRITE_MODE = "w";
    private static final String APPEND_MODE = "a";

    // Our error codes incase we have some problems with reading/writing
    // to policy files.
    public enum FileError
    {
        NO_ERROR,               // What it says on the packet, we
                                // haven't had any errors.

        // Since the TextFile class doesn't explicitly allow us to test
        // for the existance of a file (files may exist, but we can't
        // read or write to them) there is little real difference
        // between EXISTS and READ errors. However, they remain here for
        // completeness and for the possibility of update in the future.
        EXISTS,
        READ,
        WRITE,                  // Write error, if we get this error,
                                // it's possible that we can read the
                                // file, or the file exists but we just
                                // can't write to it.

        POLICY_EXISTS           // Policy holder already exists in the
                                // file, duplicates are not allowed.

    }

    public enum WriteMode
    {
        UNKNOWN,                // File only for reading.
        WRITE_NEW,              // Create a new file.
        APPEND                  // Append any new records.
    }

    // Name of the policy file that we want to read/write/append to.
    private String filename = "";
    private FileError fileError = FileError.NO_ERROR;
    private WriteMode writeMode = WriteMode.UNKNOWN;

    private TextFile fileOfPolicies = null;

    // Constructor
    //
    // Our class for handling file access to our policy files, always
    // needs a filename, will check if the file exists, and set the
    // result in fileError.
    //
    // If a write attempt is tried on a file that doesn't appear to
    // exist, then it will try to create a file with the filename.
    public PolicyFile( String inFilename )
    {
        filename = inFilename;
        if ( !fileExists( inFilename ) )
            {
                // The filename we've been given is no good for reading,
                // this isn't necessarily a dealbreaker, as we may still
                // be able to write to the file.
                //
                // The file may exist, but we just can't read it, with
                // the TextFile class, there's no real way of knowing.
                fileError = FileError.READ;
                writeMode = WriteMode.WRITE_NEW;
            }
        else
            {
                // If the file exists, then if we ever write to it,
                // we're appending.
                writeMode = WriteMode.APPEND;
            }
    }

    // Tests that a file exists already, returns true if the file is
    // available for reading (writing may not work), otherwise false.
    //
    // This is an explicit test, give a filename, get a result back,
    // does not keep any instances around.
    public static boolean fileExists( String filename )
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

    // Opens the file for reading, expecting to use readLine to try and
    // read the file.
    public boolean readFile()
    {
        fileOfPolicies = new TextFile( filename, READ_MODE );
        return fileOfPolicies.openFile();
    }

    // Read line
    //
    // As TextFile doesn't offer a read line method, so we need our own.
    // This method will just go from the current file position, and
    // return a string up to, but not including a newline character. The
    // newline character will be skipped and the file position should be
    // starting at the character after the newline (if any).
    //
    // It assumes that the file is already opened, it will just return
    // an empty string if there's nothing left, or end of file, etc.
    public String readLine()
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

    // Given a policy holder, this will search for a policy holder that
    // equals the policy holder passed in, if there's a match then it
    // will return the policy holder that was found in the file.

    // This method already assumes that the file has already been tested
    // if it exists, or can be read, any failure should result in false
    // being returned.
    public PolicyHolder findHolder( PolicyHolder inHolder )
    {
        // To check if a policy holder exists in a file, we open it, and
        // loop through each entry, comparing with the policy holder
        // we've been given, a match means that the policy holder exists
        // in the file.
        fileOfPolicies = new TextFile( filename, READ_MODE );
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
                        currentFilePolicyHolder = new PolicyHolder( readLine() );

                        matchFound = inHolder.equals( currentFilePolicyHolder );
                    }
            }

        // If a match was found, then the next three lines should be the
        // policy holder's home, car, and travel policies in that order.
        // Pass each line into the policy holder. As far as we're
        // concerned, policy holder does it all.
        if ( matchFound )
            {
                currentFilePolicyHolder.setHomePolicy( readLine() );
                currentFilePolicyHolder.setCarPolicy( readLine() );
                currentFilePolicyHolder.setTravelPolicy( readLine() );
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

        // Be sure to clear off our file handle.
        fileOfPolicies = null;

        return currentFilePolicyHolder;
    }

    // Find a policy holder just via name and address, which as per
    // assignment spec is the only thing that matters.
    public PolicyHolder findHolder( String inName, String inAddress )
    {
        PolicyHolder searchFor = new PolicyHolder( inName, inAddress, "" );
        return findHolder( searchFor );
    }

    // Given a single policy holder, write that policy holder to the
    // given file, will check that the policy holder doesn't already
    // exist in the file already.
    public boolean writeHolderToFile( PolicyHolder inHolder )
    {
        // First, we check that the policy holder we have isn't already
        // in the file, if they are, then we need to return false and
        // set the error field so the correct message can be returned to
        // the user.

        // We don't actually keep the file handle open all
        // the time, we only open it for when we need it.
        TextFile fileToWriteTo = new TextFile( filename, WRITE_MODE );



        boolean result = false;
        if ( fileToWriteTo.openFile() )
            {
                // We have managed to open the file in a writeable mode.
                // Check that the policy holder we've got isn't already
                // in the file.
                fileToWriteTo.printIt( policyHolderEntry( inHolder ) );
            }

        // We don't need to worry about checking if the file was open or
        // not, as that is taken care of in the TextFile class.
        fileToWriteTo.closeFile();

        return result;
    }

    // Policy holder entry This returns the string of how the given
    // entry will be saved into the file, we put it here because the
    // file format is part of how policy files are managed.
    public static String policyHolderEntry( PolicyHolder inPolicyHolder )
    {
        return inPolicyHolder.toString() + "\n" +
            inPolicyHolder.getHomeInsurance() + "\n" +
            inPolicyHolder.getCarInsurance() + "\n" +
            inPolicyHolder.getTravelInsurance() + "\n";
    }

    // Return a human readable error string.
    private String errorMessage()
    {
        String result = "Undefined Error";
        switch( fileError )
            {
            case NO_ERROR:
                result = "No error";
                break;
            case READ:
                result = "Read error";
                break;
            case WRITE:
                result = "Write error";
                break;
            }

        return result;
    }

    // Our toString function.
    public String toString()
    {
        return "Filename: " + filename + " Error: " + errorMessage();
    }
}
