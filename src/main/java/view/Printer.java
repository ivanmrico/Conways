package view;

import mod.Conway;
import mod.Maps;

/*
public class Printer;
This class is deprecated. It was used to generate Conway's Maps before the GUI was added.
 */
public class Printer {

    // String used to represent a living cell for printing to the console.
    private static final String CELL = "X";

    // String used to represent a dead cell for printing to the console.
    private static final String DEAD_CELL = "O";

    /*
    public static void print(int[][] map, int cycle);
    Prints the map and cycle number to the console.
     */
    public static void print(int[][] map, int cycle){
        System.out.println("Cycle: " + cycle);
        for(int[] row : map){
            for(int val : row){
                if(val > 0) {
                    System.out.print(CELL + " ");
                }
                else{
                    System.out.print(DEAD_CELL + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
    public static void main(String[] args0);
    The main method before the JavaFX GUI was implemented. Prints Conway maps to console.

    public static void main(String[] args0){
        Conway obj = new Conway(Maps.MAP1.getMap());
        int cnt = 0;
        while(cnt < 100){
            cnt++;
            print(obj.getMap(), cnt);
            obj.updateMap();
        }
    }
     */
}
