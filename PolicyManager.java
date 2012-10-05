// FILE:      PolicyManger.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This is the class that holds the main logic for the OOPD
//            assignment.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  None.
// Last Mod:  5th October 2012

import io.ConsoleInput;

class PolicyManager
{
    public static void main( String[] args )
    {
        // Prompt for filename
        String policyFilename = promptForFilename();

        // If the policy file can't be read, then we will try to create
        // it, and give an error if they try to read it.
        boolean createPolicyFile = fileExists( policyFilename );

        // File doesn't exist, inform the user that the file will be
        // created if they add a new policy. View should not be
        // available for empty files.

        // Generate the menu
        //
        // The menu will show slightly different text if adding a new
        // policy holder will create the file instead of appending to an
        // existing file.
        displayMenu( createPolicyFile );

        char selectedOption = menuSelection();

        processMenuOption( menuSelection() );
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
    private static void displayMenu( boolean willCreateFile )
    {
        System.out.println( "OOPD Assignment Menu" );

        displayAddNewPolicyOption( willCreateFile );
        displayViewPolicyOption( willCreateFile );

        System.out.println( "(Q)uit" );
    }

    // Our add new policy holder menu option varies if the policy file
    // the user specified exists or not.
    private static void displayAddNewPolicyOption( boolean willCreateFile )
    {
        // We just append a string to the menu option to let the user
        // know what will be happening.
        String newOrExistingFileMessage = " to existing file.";

        if ( willCreateFile )
            {
                newOrExistingFileMessage = " and create file.";
            }
        System.out.println( "(A)dd a new policy holder" );
    }

    private static void displayViewPolicyOption( boolean willCreateFile )
    {
        // Can't view a policy holder if the file doesn't exist.
        if ( !willCreateFile )
            {
                System.out.println( "(V)iew a policyholder's policies" );
            }
        else
            {
                System.out.println( "No policy file available for reading." );
            }
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

    // This is what does the actual work in what we do next.  The
    // character that's passed in is expected to be either 'a' or 'v'
    // 'q' should be caught before we get to this proceedure.
    // Anything else will pass through like yesterday's sour milk.
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
            }
    }

    // Prompt for the details of a new policy holder.  There is no
    // checked done here, as we can't verify anything, and doing so is
    // making mountains out of molehills.
    private static void promptNewPolicyHolder()
    {
        System.out.println( "Adding new policy holder, please enter: " );
        String name = ConsoleInput.readLine( "Name" );
        String address = ConsoleInput.readLine( "Address" );
        String phoneNumber = ConsoleInput.readLine( "Phone number" );

        // If we're appending, then we check that the policy holder
        // doesn't already exist.

        // Otherwise, we just create and add the policy holder.
    }

    // The user wants to view a policy, we just need the name and the
    // address.
    private static void viewPolicyHolder()
    {
        System.out.println( "Viewing eh? Please enter: " );
        String name = ConsoleInput.readLine( "Name" );
        String address = ConsoleInput.readLine( "Address" );

        // Search for the policy holder, if we find the policy holder,
        // then show the holder to the user.
        PolicyFile insurances = PolicyFile( "test.txt" );
    }
}
