// FILE:      PolicyManger.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This is the class that holds the main logic for the OOPD
//            assignment.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  13th September 2012

import io.ConsoleInput;
import TextFile;

class PolicyManager
{
    // These are constants that relate to the modes available in the
    // TextFile class. Please note that these depend on that class and
    // may change if that class changes.
    private static final String READ_MODE = "r";
    private static final String WRITE_MODE = "w";
    private static final String APPEND_MODE = "a";

    public static void main( String[] args )
    {
        // Prompt for filename
        String policyFilename = promptForFilename();

        boolean policyFileReadable = false;
        boolean createPolicyFile = false;

        // Test that file exists
        if ( fileExists ( policyFilename ) )
            {
                System.out.println( "Found " + policyFilename );
                policyFileReadable = true;
            }
        else
            {
                createPolicyFile = promptYes( "Unable to read " + policyFilename +
                                              " create file instead?" );
            }
        // File doesn't exist, inform the user that the file will be
        // created if they add a new policy. View should not be
        // available for empty files.

        // Generate the menu
        displayMenu();
        // If the user wants to create a new, then run add user method.

        // If the user wants to view a policy (if the file isn't empty)
        // Then view policy holder method.
    }

    private static String promptForFilename()
    {
        // Output prompt to user.
        return ConsoleInput.readLine(
            "Please enter the filename of the policy file" );
    }

    // Tests that a file exists already, returns true if the file is
    // available for reading (writing may not work), otherwise false.
    private static boolean fileExists( String filename )
    {
        TextFile fileTest = new TextFile( filename, READ_MODE );

        result = false;
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
