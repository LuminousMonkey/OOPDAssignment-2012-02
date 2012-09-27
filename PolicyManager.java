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

class PolicyManager
{
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
        displayMenu( );
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

    // Prints out the menu to the user.
    private static void displayMenu()
    {
        System.out.println( "OOPD Assignment Menu" );
        System.out.println( "(A)dd a new policy holder" );
        System.out.println( "(V)iew a policyholder's policies" );
        System.out.println( "(Q)uit" );
    }

    // Menu prompt
    // Keeps looping until it gets a value option.
    private static char menuSelection()
    {
        char optionSelected = '';

        do
            {
                optionSelected = ConsoleInput.readChar( "Please select a menu option" );
            } while ( optionSelect != 'a' && optionSelect != 'v' &&
                      optionSelect != 'q' );

        return optionSelected;
    }

    // This is what does the actual work in what we do next.
    private static void processMenuOption( char optionSelected )
    {
        switch ( optionSelected )
            {
            case 'a':
                addNewPolicyHolder();
                break;
            case 'v':
                viewPolicyHolder();
                break;
            case 'q':
                System.out.println( "Thanks! Cheerio!" );
                System.exit( 0 );
                break;
            }
    }
}
