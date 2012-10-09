// FILE:      PolicyManger.java
// AUTHOR:    Mike Aldred
// UNIT:      ST151
// PURPOSE:   This is the class that holds the main logic for the OOPD
//            assignment.
// REFERENCE: None.
// COMMENTS:  None.
// REQUIRES:  ConsoleInput class.
// Last Mod:  9th October 2012

import io.ConsoleInput;

class PolicyManager
{
    // Constants
    // Menu choices.
    private static final char VIEW_HOLDER_KEY = 'v';
    private static final char ADD_HOLDER_KEY = 'a';
    private static final char QUIT_KEY = 'q';

    // The user gets a different prompt for data entry if they're
    // entering a new policy holder, or if they're searching for an
    // existing one.
    private enum AddOrSearch { ADDING_HOLDER, SEARCHING_FOR_HOLDER };

    // NAME:    main
    // PURPOSE: Where the magic happens.
    // IMPORT:  String array of args (No used)
    // EXPORT:  None.

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




    // NAME:    promptForFilename
    // PURPOSE: Just prompts and gets the filename of the policy file
    //          we're interested in.
    // INPUT:   String of the filename.
    // OUPUT:   Prompt for filename.
    // IMPORT:  None.
    // EXPORT:  String of the filename entered by the user.
    // REMARKS: No error check, because not much can be done.

    private static String promptForFilename()
    {
        // Output prompt to user.
        return ConsoleInput.readLine(
            "Please enter the filename of the policy file" );
    }




    // NAME:    displayMenu
    // PURPOSE: Outputs the menu of choices to the user. The options
    //          will change depending on if the file already exists. For
    //          example, if the file doesn't exist, then the view policy
    //          holder option is not available.
    // OUTPUT:  Menu options for user to select.
    // IMPORT:  fileExists - boolean if the policy file the user gave
    //          already exists.
    // REMARKS: None.

    private static void displayMenu( boolean fileExists )
    {
        System.out.println( "OOPD Assignment Menu" );

        displayAddNewPolicyOption( fileExists );
        displayViewPolicyOption( fileExists );

        System.out.println( QUIT_KEY + " - Quit" );
    }




    // NAME:    displayAddNewPolicyOption
    // PURPOSE: Outputs the needed menu option text for adding a new
    //          policy depending on if the file already exists.
    // OUTPUT:  Menu option for user to select.
    // IMPORT:  fileExists - boolean if the policy file the user gave
    //          already exists.
    // REMARKS: None.

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




    // NAME:    displayViewPolicyOption
    // PURPOSE: Outputs the needed menu option text for viewing a
    //          policy depending on if the file already exists.
    // OUTPUT:  Menu option for user to select.
    // IMPORT:  fileExists - boolean if the policy file the user gave
    //          already exists.

    // REMARKS: Will not show the option if the file doesn't exist,
    //          doesn't make sense for the user to view a policy if there
    //          is no file to view it from

    private static void displayViewPolicyOption( boolean fileExists )
    {
        String viewPolicyOptionMessage = "No policy file available " +
            "for reading.";

        // Can't view a policy holder if the file doesn't exist.
        if ( fileExists )
            {
                viewPolicyOptionMessage = VIEW_HOLDER_KEY +
                    " - View a policyholder's policies";
            }

        System.out.println( viewPolicyOptionMessage );
    }




    // NAME: menuSelection
    // PURPOSE: Gets a valid menu selection from the user, which means
    //          one of the option keys only. Will loop until it gets one.
    // INPUT:   Single character from user.
    // OUTPUT:  Prompt for menu selection.
    // IMPORT:  None.
    // EXPORT:  Single lowercase character for menu option.
    // Assertions
    //     Post: Returns a character that is only one of the option keys.
    // REMARKS: None.

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

                // We're not interested in anymore characters after the
                // first, clear the input.
                ConsoleInput.clearLine();

            } while ( optionSelected != VIEW_HOLDER_KEY &&
                      optionSelected != ADD_HOLDER_KEY &&
                      optionSelected != QUIT_KEY );

        return optionSelected;
    }




    // NAME:    processMenuOption
    // PURPOSE: Runs the submodule to do what the user wants, based on
    //          their choice.
    // IMPORT:  optionSelected - Single character, which represents a menu
    //                           option, excluding the quit option.
    //          ins urances     - PolicyFile to read and write to.
    // EXPORT:  None.
    // Assertions
    //     Pre: The character can not be the quit option, can only be
    //     either the add or view holder options
    // REMARKS: The quit option should have been caught before here.

    private static void processMenuOption( char optionSelected,
                                           PolicyFile insurances )
    {
        switch ( optionSelected )
            {
            case ADD_HOLDER_KEY:
                addNewPolicyHolder( insurances );
                break;
            case VIEW_HOLDER_KEY:
               viewPolicyHolder( insurances );
               break;
            default:
                // We should never, reach here.
                assert false;
            }
    }




    // NAME:    addNewPolicyHolder
    // PURPOSE: Prompts the user for the policy holder details, and if
    //          the holder doesn't already exist, prompts for insurance
    //          details.
    // INPUT:   Policy holder details, details for each policy.
    // OUPUT:   Prompts for details from user, writes to policy file.
    // IMPORT:  insurances - PolicyFile of the policies to read or write
    //          to.
    // EXPORT:  None.

    private static void addNewPolicyHolder( PolicyFile insurances )
    {
        PolicyHolder holderToAdd = promptForPolicyHolder( AddOrSearch.ADDING_HOLDER );
        PolicyHolder holderFound = insurances.findHolder( holderToAdd );

        String messageToUser = "Policy holder already exists in file.";

        if ( holderFound == null )
            {
                // Holder wasn't found, we can add them. Prompt for
                // insurance details.
                holderToAdd.promptForInsurances();

                // We should now have all the details we need. Write the
                // policy holder to the file.
                insurances.writeHolder( holderToAdd );

                messageToUser = "Policy holder added to file.";
            }

        System.out.println( messageToUser );
    }




    // NAME:    viewPolicyHolder
    // PURPOSE: Prompts the user for the policy holder details to search
    //          for. Only needs the name and address, if found displays the
    //          details of the policy holder and their policies to the user.
    // INPUT:   Policy holder details for searching.
    // OUTPUT:  name, address for the policy holder to search for.
    // IMPORT:  insurances - PolicyFile of the policies to read or write
    //          to.
    // EXPORT:  None.

    private static void viewPolicyHolder( PolicyFile insurances )
    {
        PolicyHolder holderToView =
            promptForPolicyHolder( AddOrSearch.SEARCHING_FOR_HOLDER );

        PolicyHolder holderFound = insurances.findHolder( holderToView );

        String resultToUser = "Sorry, couldn't find that policy holder.";

        if ( holderFound != null )
            {
                // We found them!
                // Show them to the user.
                resultToUser = holderFound.toString();
            }

        System.out.println( resultToUser );
    }




    // NAME:    promptForPolicyHolder
    // PURPOSE: Prompt the user for policy holder details, however if
    //          we're searching we only need the name and address, not
    //          the phone number.
    // INPUT:   name, address of policy holder.
    // OUTPUT:  Prompts for details of policy holder.
    // IMPORT:  addingOrSearching - Enum, if we're adding or searching
    //          for a policy holder.
    // EXPORT:  PolicyHolder details.
    // REMARKS: No error checking.

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

        // We only ask for the phone number if we're adding a new policy
        // holder, it's not needed for searching policy holders.
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
