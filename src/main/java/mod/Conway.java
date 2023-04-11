package mod;

/*
public class Conway;
Revised: This class no longer functions as a place to hold Conway's maps. Instead, this class will modify a
given int[][] map according to the rules of Conway's Game of Life.
Deprecated: This class holds all the rules of Conway's Game of life and can apply them to a private int[][] _map;
 */
public class Conway {

    /*
    public static int[][] updateMap(int[][] map);
    Revised: Given an int[][] map updates that Map according to Conway's rules.
    Deprecated: Updates the map stored in the Conway class based on Conway's rules. Returns the updated map.
     */
    public static int[][] updateMap(int[][] map){
        int[][] newMap = new int[map.length][map[0].length];
        for(int r = 0; r < map.length; r++){
            for(int c = 0; c < map[0].length; c++){
                int cnt = countNeighbors(r, c, map);
                if(map[r][c] == 1 && (cnt < 2  || cnt > 3)){
                    newMap[r][c] = 0;
                }
                else if(map[r][c] == 1 && cnt == 2 || cnt == 3){
                    newMap[r][c] = 1;
                }
                else if(map[r][c] == 0 && cnt == 3){
                    newMap[r][c] = 1;
                }
            }
        }
        map = newMap;
        return map;
    }

    /*
    private static int countNeighbors(int row, int col);
    Given a cell at a specified row and col this method counts the living neighbors.
     */
    private static int countNeighbors(int row, int col, int[][] map){
        int cnt = 0;
        for(int r = row - 1; r <= row + 1; r++){
            for(int c = col - 1; c <= col + 1; c++){
                if(!((r == row) && (c == col)) && inBounds(r,c,map) && map[r][c] >= 1){
                    cnt++;
                }
            }
        }
        return cnt;
    }

    /*
    private static boolean inBounds(int row, int col);
    Given the row and col of a cell this method will return true if the cell exists in the map; false otherwise.
     */
    private static boolean inBounds(int row, int col, int[][] map) {
        if( row < 0 || col < 0 || row >= map.length || col >= map[0].length){
            return false;
        }
        return true;
    }

    /*
    private int[][] _map;
    Deprecated. Was used to store a Conway's map. Conway's maps are stored as int[][].
    */
    private int[][] _map;

    /*
    private int cycle = 0;
    Deprecated. Was used to count the cycle number.
     */
    private int cycle = 0;

    /*
    public int[][] getMap();
    Deprecated. Was used to gain access to the _map.
     */
    public int[][] getMap() { return _map; }

    /*
    public void setMap(int[][] map);
    Deprecated. Was used to change maps.
     */
    public void setMap(int[][] map) { _map = map; }

    /*
    public Conway(int[][] map);
    Deprecated. Constructor that creates a Conway object given a map of int[][].
     */
    public Conway(int[][] map){
        _map = map;
    }
}
