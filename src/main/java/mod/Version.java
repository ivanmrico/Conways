package mod;

/*
public enum Version;
The Version enum is an enum that stores the entire Version History of the application from start. As
new versions are added the HEADER, CURRENT_VERSION and VERSION_HISTORY need to be updated.
 */
public enum Version {

    /*
    These enum constants are used to inform the user of the Version History. When a new Version is completed
    The HEADER, ABOUT, CURRENT_VERSION and VERSION_HISTORY need to append the new addition.
     */

    // The HEADER is used to head the file with basic information. Version number needs to be updated.
    CURRENT_VERSION("*Version 1.0.15*"),
    HEADER("Conway's Game Of Life\nby Ivan M. Rico\n" + CURRENT_VERSION.getText() + "\n\n"),

    // The SIGNATURE is used in the about section of the application. It does not need to be modified.
    SIGNATURE("Dedicated to my children, family and friends. Thanks for your love and support.\n\nSee the version.txt file in this directory for more information about Version History.\n\n"),

    // New version information is added here.
    VERSION_1_0_15("Version 1.0.15\n" + "4.13.23\n" + "Fixed an issue where the main GUI could be moved on top of save and load dialogs.\n\n"),
    VERSION_1_0_14("Version 1.0.14\n" + "4.11.23\n" + "Fixed an issue where extra memory would be allocated to a Conway's map within the application.\n" + "Added some behind the scene version tracking for GitHub.\n\n"),
    VERSION_1_0_13("Version 1.0.13\n" + "4.6.23\n" + "Added a feature that saves your first randomized map when the application loads as \"MyFirstConway.cnw\".\n" + "This feature will not overwrite MyFirstConway.cnw if it already exists.\n" + "This feature will load MyFirstConway.cnw if it already exists.\n\n"),
    VERSION_1_0_12("Version 1.0.12\n" + "4.5.23\n" + "Fixed an issue where exiting the save/load dialog without selecting a file would cause the program to become unusable.\n" + "Slightly improved code performance.\n" + "Fixed an issue where some features in the tutorial were overlooked.\n" + "Fixed a grammar issue in the tutorial.\n\n"),
    VERSION_1_0_11("Version 1.0.11\n" + "4.4.23\n" + "Added a feature in the code that made it easier to update the version history text file.\n" + "Fixed an issue where version history text file would generate even if it already correctly exists.\n" + "Fixed an issue that improperly generated version history text file.\n\n"),
    VERSION_1_0_10("Version 1.0.10\n" + "4.3.23\n" + "Fixed an issue that allowed buttons to be pressed while a map was running.\n" + "Fixed an issue that allowed text to be edited while a map was running.\n\n"),
    VERSION_1_0_9("Version 1.0.9\n" + "4.2.23\n" + "Fixed an issue where maps could run and be altered while a dialog was opened.\n" + "Fixed an issue where past versions were displaying the incorrect dates.\n" + "Known Issues: The save and load dialogs are not always on top of the main GUI.\n\n"),
    VERSION_1_0_8("Version 1.0.8 \n" + "3.30.23\n" + "Version history now includes dates\n" +  "Fixed some minor formatting issues in the version history.\n" + "Fixed a minor issue with the about dialog box displaying the title incorrectly.\n\n"),
    VERSION_1_0_7("Version 1.0.7\n" + "3.23.23\n" + "Fixed an issue where maps were not loading correctly.\n\n"),
    VERSION_1_0_6("Version 1.0.6\n" + "3.22.23\n" +  "Improved performance on startup.\n" + "Fixed an issue with maps not displaying correctly.\n" + "Fixed an issue where incorrect cells were toggled when clicked if the map had a different number of rows versus cols.\n\n"),
    VERSION_1_0_5("Version 1.0.5\n" + "3.20.23\n" +  "Cleaned code and added comments in preparation for first GitHub upload.\n\n"),
    VERSION_1_0_4("Version 1.0.4\n" + "3.19.23\n" +  "Fixed an issue where save and load dialogs would default to the home directory rather than the current directory.\n\n"),
    VERSION_1_0_3("Version 1.0.3 \n" + "3.16.23\n" + "Cleaned GUI and added Tutorial and About.\n\n"),
    VERSION_1_0_2("Version 1.0.2 \n" + "3.12.23\n" + "Changed Size Control so rows and cols could be set to different amounts. \nKnown Issues: Wrong cells toggle when clicking in maps that have mismatched rows/cols\n\n"),
    VERSION_1_0_1("Version 1.0.1 \n" + "3.1.23\n" + "Reworked Save/Load to use Java's FileChooser \nKnown Issues: FileChooser is set to a different environment from the rest of the program.\n\n"),
    VERSION_1_0_0("Version 1.0.0 \n" + "4.1.23\n" + "Featuring speed control, size control, cycle control, saving/loading, clickable cell status and more.\n\n"),


    // CURRENT_VERSION is used for the VERSION_HISTORY and uses the HEADER and the most recent version.
    CURRENT_VERSION_INFO(HEADER.getText() +  VERSION_1_0_15.getText()),

    // ABOUT is used in the ABOUT section of the application. It uses the HEADER, SIGNATURE and most recent version.
    ABOUT(CURRENT_VERSION_INFO.getText() + SIGNATURE.getText()),

    // VERSION_HISTORY is used to generate the version.txt file when the application loads.
    VERSION_HISTORY(CURRENT_VERSION_INFO.getText()
            + VERSION_1_0_14 + VERSION_1_0_13.getText() + VERSION_1_0_12.getText()
            + VERSION_1_0_11.getText() + VERSION_1_0_10.getText() + VERSION_1_0_9.getText() + VERSION_1_0_8.getText()
            + VERSION_1_0_7.getText() + VERSION_1_0_6.getText() + VERSION_1_0_5.getText() + VERSION_1_0_4.getText()
            + VERSION_1_0_3.getText() + VERSION_1_0_2.getText() + VERSION_1_0_1.getText() + VERSION_1_0_0.getText());

    /*
    private final String VERSION;
    The string that is used by the different constants. Version text will never change.
    */
    private final String VERSION;

    /*
    public String getText();
    Getter method to gain access to the text stored in the Version constants.
     */
    public String getText() { return VERSION; }
    /*
    Version(String version);
    Constructor for the Version enum that stores the Version info.
     */
    Version(String version){
        VERSION = version;
    }

}
