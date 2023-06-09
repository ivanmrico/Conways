package view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import mod.Conway;
import mod.FileManager;
import mod.Maps;
import mod.Version;
import java.io.*;

/*
public class WindowController;
This class controls the main GUI for the application. The ConwayGUI.FXML file defines the fxID for every GUI
component as well as the reference (fxID) for all GUI components that are required by this class to
function and access is exported from the FXML to the package through declaration in the module-info.java.
 */
public class WindowController {

    // The Array of labels that show images of living and dead cells. Represents the map.
    private Label[][] _lbls;

    // The Conway's map raw data. 0 represents a dead cell and 1 represents a living cell.
    private int[][] _map;

    // Used to count the current cycle in terms of how many cycles are set to run.
    private int _cycleCount;

    // Used to keep track of the total number of cycles run on the current map.
    private int _cycleTotal;

    /*
    The default images used to represent living and dead cells.
     */
    private Image _dead = new Image(this.getClass().getResourceAsStream("Dead.png"));
    private Image _cell = new Image(this.getClass().getResourceAsStream("Cell.png"));
    private ImageView _cell2 = new ImageView(_cell);
    private ImageView _dead2 = new ImageView(_dead);

    /*
    Timeline used to control the update of the Conway's map.
     */
    private Timeline _tl;

    // A toggle.
    private boolean _on = false;

    /*
    Important Variables for resizing screen as the size of the Conway Map changes. Also
    Contains the the divider width used for locking the divider of the split pane and the
    maximum map rows and cols supported by the GUI.
     */
    private static final int PIXEL_SIZE = 10;
    private static final int HORIZONTAL_LABEL_DIFFERENCE = 4;
    private static final double DIVIDER_MAX_WIDTH = 0.86;
    private static final int MAX_ROWS = 53;
    // Deprecated: This int was used to store the maximum possible cols in a Conway's Map.
    private static final int MAX_COLS = 61;

    /*
    The FXML GUI components that will be referenced in the code. Note that not all the GUI
    components are used in the code and thus do not require a reference.
     */
    @FXML
    private SplitPane mainwin;
    @FXML
    private Pane Window;
    @FXML
    private Button start;
    @FXML
    private Button repop;
    @FXML
    private Button fill;
    @FXML
    private Button clear;
    @FXML
    private Button save;
    @FXML
    private Button load;
    @FXML
    private Button about;
    @FXML
    private Button tutorial;

    @FXML
    private Label cycles;
    @FXML
    public TextField speedTxt;
    @FXML
    private TextField rowTxt;
    @FXML
    private TextField colTxt;
    @FXML
    private TextField cycText;

    private void intro(){
        _map = Maps.INTRO_MAP.getMap();
        for(int r = 0; r < _lbls.length; r++){
            for(int c = 0; c < _lbls[0].length; c++){
                if(_map[r][c] == 1){
                    _lbls[r][c].setGraphic(new ImageView(_cell));
                }
                else{
                    _lbls[r][c].setGraphic(new ImageView(_dead));
                }
            }
        }
    }
    /*
    private void rePopulate();
    This method is used to repopulate the map to the specified size with random status for each cell when the
    Randomize button is clicked. Also resets the cycle counter and total and updates the GUI with the
    new map.
     */
    @FXML
    private void rePopulate(){
        _cycleCount = 0;
        _cycleTotal = 0;
        cycles.setText("" + _cycleCount);
        int row = getRowSize();
        int col = getColSize();
        int spacingH = HORIZONTAL_LABEL_DIFFERENCE * PIXEL_SIZE + ((MAX_ROWS - col) * PIXEL_SIZE)/2;
        int spacingV = ((MAX_ROWS - row) * PIXEL_SIZE) / 2;
        int var = 4;
        _lbls = new Label[row][col];
        _map = new int[row][col];
        Window.getChildren().clear();
        for(int r = 0; r < _lbls.length; r++){
            for(int c = 0; c < _lbls[0].length; c++){
                if(Math.random() < 0.5){
                    _map[r][c] = 1;
                    _lbls[r][c] = new Label();
                    _lbls[r][c].relocate(c*PIXEL_SIZE + spacingH,r*PIXEL_SIZE + spacingV);
                    _lbls[r][c].setGraphic(new ImageView(_cell));
                    Window.getChildren().add(_lbls[r][c]);
                }
                else{
                    _map[r][c] = 0;
                    _lbls[r][c] = new Label();
                    _lbls[r][c].setGraphic(new ImageView(_dead));
                    _lbls[r][c].relocate(c*PIXEL_SIZE+ spacingH,r*PIXEL_SIZE + spacingV);
                    Window.getChildren().add(_lbls[r][c]);
                }
            }
        }
    }

    /*
    private void loadMap(int[][] map);
    Method used to load a map from a file. Updates the GUI based on the map supplied to the parameter.
    Used in conjunction with the load method.
     */
    private void loadMap(int[][] map){
        _cycleCount = 0;
        _cycleTotal = 0;
        cycles.setText("" + _cycleCount);
        int rows = map.length;
        int cols = map[0].length;
        int spacingH = HORIZONTAL_LABEL_DIFFERENCE * PIXEL_SIZE + ((MAX_ROWS - cols) * PIXEL_SIZE)/2;
        int spacingV = ((MAX_ROWS - rows) * PIXEL_SIZE) / 2;
        _map = map;
        _lbls = new Label[rows][cols];
        Window.getChildren().clear();
        for(int r = 0; r < _lbls.length; r++){
            for(int c = 0; c < _lbls[0].length; c++){
                if(_map[r][c] == 1){
                    _map[r][c] = 1;
                    _lbls[r][c] = new Label();
                    _lbls[r][c].relocate(c*PIXEL_SIZE + spacingH,r*PIXEL_SIZE + spacingV);
                    _lbls[r][c].setGraphic(new ImageView(_cell));
                    Window.getChildren().add(_lbls[r][c]);
                }
                else{
                    _map[r][c] = 0;
                    _lbls[r][c] = new Label();
                    _lbls[r][c].setGraphic(new ImageView(_dead));
                    _lbls[r][c].relocate(c*PIXEL_SIZE+ spacingH,r*PIXEL_SIZE + spacingV);
                    Window.getChildren().add(_lbls[r][c]);
                }
            }
        }
    }

    /*
    private void save() throws IOException;
    Method used to save the current map as a .cnw file when the save button is clicked. The method
    calls upon the FileManager class to handle the dialog prompt to save and allows the user to choose
    the file location and name.
     */
    @FXML
    private void save() throws IOException {
        FileManager.saveMap(_map, mainwin);
    }

    /*
    private void load() throws IOException;
    Method used to load a map from a file when the "Load" button is clicked. The Method uses the
    FileManager class to get the Name of the file and the data stored in the file and then updates
    the GUI to represent the chosen map.
     */
    @FXML
    private void load() throws IOException {
        mainwin.setDisable(true);
        String fileName = FileManager.getFileName(mainwin);
        try {
            _map = FileManager.getFileData(fileName);
        } catch (IOException e) {
            mainwin.setDisable(false);
            throw new RuntimeException(e);
        }
        mainwin.setDisable(false);
        loadMap(_map);
    }

    /*
    private void clear();
    Method used to kill all the cells in the current map. Useful for creating specific Conway's
    patterns (for example: a glider).
     */
    @FXML
    private void clear(){

        for(int row = 0; row < _map.length; row++){
            for(int col = 0;  col < _map[0].length; col++){
                _map[row][col] = 0;
                _lbls[row][col].setGraphic(new ImageView(_dead));
            }
        }

    }

    /*
    private void fill();
    Method used to bring to life all of the cells in the current map when the "Fill" button is clicked.
    Useful creating unique designs and maps.
     */
    @FXML
    private void fill(){
        for(int row = 0; row < _map.length; row++){
            for(int col = 0;  col < _map[0].length; col++){
                _map[row][col] = 1;
                _lbls[row][col].setGraphic(new ImageView(_cell));
            }
        }
    }

    /*
     public void start(ActionEvent event);
     When the start button is pressed changes the button to the stop button and then starts
     a timeline that will update the GUI and Conway's map according to Conway's Rules. The speed
     will be updated based on the speed set in the speed text field in the GUI. When Start is pressed
     it will be changed to the stop button and disable the randomize, fill and clear. Stopping stops
     the timeline from updating the map, updates the total cycles and stops the current cycle counter and
     changes the button back to the start button.
     */
    public void start(ActionEvent event){

        if(_lbls == null) return;
        if(start.getText().equals("Start")) {
            _tl = new Timeline(new KeyFrame(Duration.millis(getSpeed()), e -> setImage()));
            _tl.setCycleCount(getMaxCycles());
            buttonsDisabler(true);
            textsDisabler(true);
            start.setText("Stop");
            _tl.stop();
            event.consume();
            _tl.play();
            start.setText("Stop");
        }
        else{
            _tl.stop();
            _cycleTotal += _cycleCount;
            _cycleCount = 0;
            start.setText("Start");
            buttonsDisabler(false);
            textsDisabler(false);
        }
    }

    /*
    private void about();
    Sends out an alert to inform the User About the program and the current version whenever the "..." button
    is pressed.
     */
    @FXML
    private void about(){
        mainwin.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, Version.ABOUT.getText(), ButtonType.OK);
        alert.setTitle(Version.HEADER.getText());
        alert.setHeaderText(null);
        alert.showAndWait();
        mainwin.setDisable(false);
    }

    /*
    private void tutorial();
    Sends an alert to the user to explain how the program works.
     */
    @FXML
    private void tutorial(){
        mainwin.setDisable(true);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cycles -> Current cycle. Each cycle cell status will change (alive or dead) based on Conway's rules. \n\n Speed -> Set the speed of each cycle. Speed is determined when Start is pressed. \n\n Start -> Updates cells' state and cycles run as fast as the designated speed and according to the rules of Conway's Game of Life and changes into the Stop button\n\n Stop -> Stops the Conway's map from updating and turns into the Start button. \n\n Rows, Cols -> Set the number of rows/cols in the map. Rows/cols set when Randomize is pressed. Max Rows are 53 and Cols are 61. \n\n Randomize -> Repopulates the map with random cell status with a size of rows/cols. Defaults are 53 and 61 respectively. \n\n Fill -> Convert all cells status to alive. \n\n Clear -> Convert all cells status to dead. \n\n Run Cycles -> Set a stopping point to a specified amount of cycles. The default is -1 for infiinite cycles \n\n Save -> Save your map as a .cnw file. \n\n Load -> Load a map from a . cnw file. \n\n ? -> Tutorial. \n\n ... -> About the program and Version number. ", ButtonType.OK);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.showAndWait();
        mainwin.setDisable(false);
    }

    /*
    private int getSpeed();
    Gets the speed listed in the speed text field. Used whenever the start button is pressed.
     */
    private int getSpeed(){
        int val = 100;
        try{
            val = Integer.parseInt(speedTxt.getText());
            if(val < 35 || val > 1000000){
                val = 100;
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return val;
    }

    /*
    private int getRowSize()
    Used to get user defined row size from the row text field. Max size is 53 rows and the minimum is 2.
    Called whenever the map is randomized.
     */
    private int getRowSize(){
        int size = 53;
        try{
            size = Integer.parseInt(rowTxt.getText());
            if(size < 2 || size > 53){
                size = 53;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return size;
    }

    /*
    private int getColSize();
    Used to get user defined column size from the column text field. Max size is 61 columns and the minimum is 2.
    Called whenever the map is randomized.
     */
    private int getColSize(){
        int size = 61;
        try{
            size = Integer.parseInt(colTxt.getText());
            if(size < 2 || size > 61){
                size = 61;
            }
        }
        catch(Exception e){
//            e.printStackTrace();
        }
        return size;
    }

    /*
    private int getMaxCycles();
    Gets the amount of cycles to run before automatically stopping. The minimum is -1 for infinity and
    the maximum is Integer.MAX_VALUE.
     */
    private int getMaxCycles(){
        int max = -1;
        try{
            max = Integer.parseInt(cycText.getText());
            if(max < 1 || max > Integer.MAX_VALUE){
                max = -1;
            }
        }
        catch(Exception e){
//            e.printStackTrace();
        }
        return max;
    }

    /*
    private void initialize();
    Whenever the program is started, creates the Version History text file in the current directory,
    locks the divider on the split pane so the panes can't be resized and randomizes a conway map
    with the default specifications and sets a mouse click event to change the status of a cell
    whenever the cell is clicked based on the location of the click.
     */
    @FXML
    private void initialize() throws IOException {
       if(!FileManager.verifyVersionText()){
            createVersionText(Version.VERSION_HISTORY.getText());
            System.out.println("Version history created.");
        }
        speedTxt.setText("100");
        cycText.setText("-1");
        rowTxt.setText("53");
        colTxt.setText("61");
        lockDivider();
        loadMyFirstConway();
        Window.setOnMouseClicked(event -> setImage(event.getX(), event.getY()));
    }

    /*
    private void loadMyFirstConway() throws IOException
    Method to determine whether to create the MyFirstConway.cnw file if it is not found or to
    load the MyFirstConway.cnw if it is found.
     */
    private void loadMyFirstConway() throws IOException {
        if(!FileManager.verifyFirstConwayMap()){
            rePopulate();
            intro();
            FileManager.createFirstConwayMap(_map);
        }
        else{
            String fileName = "MyFirstConway.cnw";
            try {
                _map = FileManager.getFileData(fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            loadMap(_map);
        }
    }

    /*
    private void setImage();
    Method to change the images based on the _map. Used to update the GUI to reflect living and
    dead cells after each cycle according to Conway's rules.
    */
    private void setImage(){
        _map = Conway.updateMap(_map);
        cycles.setText("" + (++_cycleCount + _cycleTotal));
        for(int r = 0; r < _lbls.length; r++){
            for(int c = 0; c < _lbls[0].length; c++){
                if(_map[r][c] == 1){
                    _lbls[r][c].setGraphic(new ImageView(_cell));
                }
                else{
                    _lbls[r][c].setGraphic(new ImageView(_dead));
                }
            }
        }
        if(getMaxCycles() != -1 && getMaxCycles() == _cycleCount){
            _cycleTotal += _cycleCount;
            _cycleCount = 0;
            start.setText("Start");
            repop.setDisable(false);
        }
    }

    /*
    private void SetImage(double x, double y);
    Overloaded method. Called whenever a cell is clicked. Uses the location of the click to find the
    matching cell and change it's status to the opposite status.
     */
    private void setImage(double x, double y){
        int rowSize = _lbls.length;
        int colSize = _lbls[0].length;
        int spacingH = HORIZONTAL_LABEL_DIFFERENCE * PIXEL_SIZE + ((MAX_ROWS - colSize) * PIXEL_SIZE)/2;
        int spacingV = ((MAX_ROWS - rowSize) * PIXEL_SIZE) / 2;
        int row = ((int) y - spacingV) / PIXEL_SIZE;
        int col = ((int) x - spacingH) / PIXEL_SIZE;
        if(row > rowSize || col > colSize || row < 0 || col < 0) { return; }
        if(_map[row][col] == 0){
            _map[row][col] = 1;
            _lbls[row][col].setGraphic(new ImageView(_cell));
        }
        else if(_map[row][col] == 1){
            _map[row][col] = 0;
            _lbls[row][col].setGraphic(new ImageView(_dead));
        }

    }

    /*
    private void lockDivider();
    Method used to lock the divider on the split pane whenever the program is initialized. Prevents
    user from resizing GUI.
     */
    private void lockDivider(){
        mainwin.getDividers().get(0).positionProperty().addListener((observable,oldValue,newValue) -> {
            if(mainwin.getDividers().get(0).getPosition() > DIVIDER_MAX_WIDTH || mainwin.getDividers().get(0).getPosition() < DIVIDER_MAX_WIDTH)
                mainwin.setDividerPosition(0, DIVIDER_MAX_WIDTH);});
    }

    /*
    private void createVersionText(String version);
    Method used to create the Version History text file that lists history of all the changes and
    fixes given the text of the file as a String. The file can be found in the current directory.
    The method is called when the application starts via the initialize method.
     */
    private void createVersionText(String version){
        try {
            String filePath = "./version.txt"; // The "./" specifies the current directory
            File file = new File(filePath);
            FileWriter writer = new FileWriter(file);
            writer.write(version);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*
    private void buttonsEnabler(boolean on);
    This method toggles enables/disables all buttons that should not function when a map is running
    (disables after the user has clicked on the Start button and enables when they click Stop).
     */
    private void buttonsDisabler(boolean off){
        repop.setDisable(off);
        fill.setDisable(off);
        clear.setDisable(off);
        save.setDisable(off);
        load.setDisable(off);
        about.setDisable(off);
        tutorial.setDisable(off);
    }

    /*
    private void buttonsEnabler(boolean on);
    This method toggles enables/disables all text fields that should not function when a map is running
    (disables after the user has clicked on the Start button and enables when they click Stop).
    */
    private void textsDisabler(boolean off){
        speedTxt.setDisable(off);
        rowTxt.setDisable(off);
        colTxt.setDisable(off);
        cycText.setDisable(off);

    }

}
