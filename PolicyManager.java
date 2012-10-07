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
    // Our constants for menu choices.
    private static final char VIEW_HOLDER_KEY = 'v';
    private static final char ADD_HOLDER_KEY = 'a';
    private static final char QUIT_KEY = 'q';

    // The user gets a different prompt for data entry if they're
    // entering a new policy holder, or if they're searching for an
    // existing one.
    private enum AddOrSearch { ADDING_HOLDER, SEARCHING_FOR_HOLDER };

    public static void main( String[] args )
    {
        // Prompt for filename
        String policyFilename = promptForFilename();

        PolicyFile insurances = new PolicyFile( policyFilename );

        // Generate the menu
        //
        // The menu will show slightly different text if adding a new
        // policy holder will create the file instead of appending to an
        // existing file.
        displayMenu( insurances.fileExists() );

        char menuSelected = menuSelection();

        while ( menuSelected != QUIT_KEY )
            {
                processMenuOption( menuSelected, insurances );
                displayMenu( insurances.fileExists() );
                menuSelected = menuSelection();
            }

        System.exit( 0 );
    }

    // promptForFilename
    // What is says on the packet, prompts the user for the filename of
    // the policy file they want to open. Does no error checking, of any
    // kind.
    private static String promptForFilename()
    {
        // Output prompt to user.
        return ConsoleInput.readLine(
            "Please enter the filename of the policy file" );
    }

    // displayMenu
    //
    // Outputs the main menu to the user (add, view, quit). Takes a
    // boolean that indicates if the file the user specified already
    // exists, just results in the text for the menu options being a
    // little different.
    private static void displayMenu( boolean fileExists )
    {
        System.out.println( "OOPD Assignment Menu" );

        displayAddNewPolicyOption( fileExists );
        displayViewPolicyOption( fileExists );

        System.out.println( QUIT_KEY + " - Quit" );
    }

    // Our add new policy holder menu option varies if the policy file
    // the user specified exists or not.
    private static void displayAddNewPolicyOption( boolean fileExists )
    {
        // We just append a string to the menu option to let the user
        // know what will be happening.
        String newOrExistingFileMessage = " and create file.";

        if ( fileExists )
            {
                newOrExistingFileMessage = " to existing file.";
            }
        System.out.println( ADD_HOLDER_KEY + " - Add a new policy holder" +
                            newOrExistingFileMessage );
    }

    // displayViewPolicyOption
    //
    // Outputs the menu line option for viewing a policy holder. If the
    // file doesn't exists, then it doesn't make sense to have this
    // option available, so it takes a boolean that indicates if the
    // file exists, and changes the output accordingly.
    private static void displayViewPolicyOption( boolean fileExists )
    {
        // Can't view a policy holder if the file doesn't exist.
        if ( fileExists )
            {
                System.out.println( VIEW_HOLDER_KEY +
                                    " - View a policyholder's policies" );
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
        char optionSelected = ' ';

        do
            {
                // Get a single character from the user, make sure it's
                // lowercase.
                optionSelected = Character.toLowerCase(
                                   ConsoleInput.readChar(
                                       "Please select a menu option" ) );
                // Calling readChar does not get rid of the newline
                // character the user would have to have pressed.

                // We're not interested in anymore characters after the
                // first, clear the input.
                ConsoleInput.clearLine();

            } while ( optionSelected != VIEW_HOLDER_KEY &&
                      optionSelected != ADD_HOLDER_KEY &&
                      optionSelected != QUIT_KEY );

        return optionSelected;
    }

    // This is what does the actual work in what we do next.  The
    // character that's passed in is expected to be either 'a' or 'v'
    // 'q' should be caught before we get to this procedure.
    // Anything else will pass through like yesterday's sour milk.
    private static void processMenuOption( char optionSelected, PolicyFile insurances )
    {
        switch ( optionSelected )
            {
            case ADD_HOLDER_KEY:
                addNewPolicyHolder( insurances );
                break;
            case VIEW_HOLDER_KEY:
               viewPolicyHolder( insurances );
                break;
            }
    }

    // addNewPolicyHolder

    // Goes through the throws of adding a new policy holder.  Prompting
    // for the policy holder details, getting the insurance details
    // entered, etc.

    // At the end, we either have a file created, or appended to with
    // the given details, if the policy holder already existed in the
    // file, then they won't be added.
    private static void addNewPolicyHolder( PolicyFile insurances )
    {
        PolicyHolder holderToAdd = promptForPolicyHolder( AddOrSearch.ADDING_HOLDER );
        PolicyHolder holderFound = insurances.findHolder( holderToAdd );

        if ( holderFound == null )
            {
                // Holder wasn't found, we can add them. Prompt for
                // insurance details.
                holderToAdd.promptForInsurances();

                // We should now have all the details we need. Write the
                // policy holder to the file.
                insurances.writeHolderToFile( holderToAdd );
                
                System.out.println( "Policy holder added to file." );
            }
        else {
            System.out.println( "Policy holder already exists in file." );
        }
    }

    // The user wants to view a policy, we just need the name and the
    // address.
    private static void viewPolicyHolder( PolicyFile insurances )
    {
        PolicyHolder holderToView = promptForPolicyHolder( AddOrSearch.SEARCHING_FOR_HOLDER );

        PolicyHolder holderFound = insurances.findHolder( holderToView );

        if ( holderFound != null )
            {
                // We found them!
                // Show them to the user.
                System.out.println( holderFound );
            }
        else
            {
                System.out.println( "Sorry, couldn't find that policy holder." );
            }
    }

    // promptForPolicyHolder

    // Prompts for the policy holder details, depending if they're
    // adding, or searching for a policy holder. Searching for a policy
    // holder means we don't need to know the phone number.
    private static PolicyHolder promptForPolicyHolder( AddOrSearch addingOrSearching )
    {
        String promptHeading = "Adding new policy holder";

        if ( addingOrSearching == AddOrSearch.SEARCHING_FOR_HOLDER )
            {
                promptHeading = "Searching for policy holder";
            }

        System.out.println( promptHeading );

        String name = ConsoleInput.readLine( "Name" );
        String address = ConsoleInput.readLine( "Address" );

        PolicyHolder policyHolderFromUser = null;

        // We only ask for the phone number if we're adding a new policy holder, it's not needed for searching policy holders.
        if ( addingOrSearching == AddOrSearch.ADDING_HOLDER )
            {
                String phoneNumber = ConsoleInput.readLine( "Phone Number" );
                policyHolderFromUser = new PolicyHolder( name, address, phoneNumber );
            }
        else
            {
                policyHolderFromUser = new PolicyHolder( name, address );
            }

        return policyHolderFromUser;
    }
}
