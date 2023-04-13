package mod;

import javafx.scene.control.SplitPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;

/*
public class FileManager
This class handles saving and loading of .cnw files saved by the user. It reads the format of
.cnw files and converts the data to be used in the WindowController class. The format for the .cnw
files is found in the writeFileData and getFileData methods.
 */
public class FileManager {
    /*
    public static String getFileName();
    This method uses the Java FileChooser class to prompt the user to select a file to load and to return
    the name of that file to be used in conjunction with the getFileData method.
     */
    public static String getFileName(SplitPane win){
        String fileName = "FAILED";
        // Create a FileChooser instance
        FileChooser fileChooser = new FileChooser();

        // Set the initial directory and file extension filter
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Conway files (*.cnw)", "*.cnw");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the file chooser dialog and get the selected file
        File selectedFile = fileChooser.showOpenDialog(win.getScene().getWindow());

        if (selectedFile != null) {
            try {
                fileName = selectedFile.getParent() + "/" + selectedFile.getName();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /*
    public static int[][] getFileData(String fileName) throws IOException;
    This method uses a BufferedReader to load a .cnw file and gather the data from it. From that data
    it generates a int[][] in the 0-1 format required to be used to generate a Conway map and returns it.
     */
    public static int[][] getFileData(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line;
        int rowCount = 0;
        int colCount = 0;
        ArrayList<String> lst = new ArrayList<>();
        String build = "";
        while ((line = reader.readLine()) != null) {
            for(int i = 0; i < line.length(); i++){
                char c = line.charAt(i);
                if(c == '#' || c == '$' || c == ' '){

                }
                else{
                    build += c;
                }

            }
            lst.add(new String(build));
            build = "";
        }
        reader.close();

        rowCount = lst.size();
        colCount = lst.get(0).length();
        int[][] result = new int[rowCount][colCount];
        int ind = 0;
        int subInd = 0;

        for(int row = 0; row < rowCount; row++){
            for(int col = 0; col < colCount; col++) {
                if(lst.get(ind).substring(subInd, ++subInd).equals("1")) {
                    result[row][col] = 1;
                }
                else{
                    result[row][col] = 0;
                }
            }
            subInd = 0;
            ind++;
        }
        reader.close();
        return result;
    }

    /*
    public static boolean saveMap(int[][] map);
    This method is used to write the current map to a file. It uses the FileChooser class to allow the user
    to choose the directory and name of the .cnw file that will be saved. It returns true or false depending
    on whether or not the file was saved.
     */
    public static boolean saveMap(int[][] map, SplitPane win) {
        win.setDisable(true);
        FileChooser fileChooser = new FileChooser();
        File initialDirectory = new File(System.getProperty("user.dir"));
        fileChooser.setInitialDirectory(initialDirectory);
        String defaultFileName = "Untitled.cnw";
        fileChooser.setInitialFileName(defaultFileName);
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Conway files (*.cnw)", "*.cnw");
        fileChooser.getExtensionFilters().add(txtFilter);
        File file = fileChooser.showSaveDialog(win.getScene().getWindow());
        try{
            if (file != null) {
                // Get the file extension from the selected file name
                String fileExtension = "";
                int i = file.getName().lastIndexOf('.');
                if (i > 0) {
                    fileExtension = file.getName().substring(i + 1);
                }

                // Modify the file name to include the desired extension
                String fileName = "./" + file.getName();
                if (!fileExtension.equals("cnw")) {
                    fileName += ".cnw";
                }
                File newFile = new File(file.getParentFile(), fileName);

                FileWriter writer = new FileWriter(newFile);
                writer.write(writeFileData(map)); // Write your data here
                writer.close();
                win.setDisable(false);
                return true;
            }
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
            win.setDisable(false);
        }
        win.setDisable(false);
        return false;
    }

    /*
    private static String writeFileData(int[][] map);
    This method is used to convert a map in the int[][] format to a String format that is readable by
    the getFileData method.
     */
    private static String writeFileData(int[][] map){
        String ret = "$";
        for(int[] row : map){
            ret += "#";
            for(int val : row){
                if(val == 1){
                    ret += "1 ";
                }
                else{
                    ret += "0 ";
                }
            }
            if(row == map[map.length - 1]){
                ret += "#";
            }
            else {
                ret += "#\n";
            }

        }
        ret += "$";
        return ret;
    }

    /*
    public static boolean verifyVersionText() throws IOException();
    This method is used to verify the version history text file to see if it exists, is incompatible with the
    version of the program. If it exits the file will not be generated. If it is incompatible
    the file will be overridden. If it does not exist it will be generated.
     */
    public static boolean verifyVersionText() throws IOException {
        File file = new File("version.txt");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        }
        catch(Exception e){
            System.out.println("Version history not found.");
            return false;
        }

        String line;
        String build = "";
        while ((line = reader.readLine()) != null) {
            for(int i = 0; i < line.length(); i++){
                char c = line.charAt(i);
                if(c == '*'){
                    build += c;
                    for(int j = 1; j < line.length(); j++){
                        char c2 = line.charAt(j);
                        build += c2;
                    }
                    if(build.equals(Version.CURRENT_VERSION.getText())){
                        System.out.println("Version history up to date.");
                        reader.close();
                        return true;
                    }
                    else{
                        System.out.println("Version history incompatible");
                        reader.close();
                        return false;
                    }
                }

            }
        }
        return false;
    }
    /*
    public static verifyFirstConwayMap() throws IOP Exception;
    This method is used to verify whether the first conway map has been created or not.
     */
    public static boolean verifyFirstConwayMap() throws IOException {
        File file = new File("MyFirstConway.cnw");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            System.out.println("MyFirstConway.cnw located.");
        }
        catch(Exception e){
            System.out.println("MyFirstConway.cnw not found.");
            return false;
        }
        reader.close();
        return true;
    }

    /*
    public static void createFirstConwayMap(int[][] map);
    This method is used to create the first conway map. It will use the randomly generated Conway map
    that was created on startup.
     */
    public static void createFirstConwayMap(int[][] map){
        File file = new File("MyFirstConway.cnw");
        try{
                FileWriter writer = new FileWriter(file);
                writer.write(writeFileData(map)); // Write your data here
                writer.close();
                System.out.println("MyFirstConway.cnw created.");
        } catch (Exception e) {
            // Handle the exception here
            e.printStackTrace();
        }
    }
}
