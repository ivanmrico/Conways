package mod;

/*
public class Conway;
This class holds all the rules of Conway's Game of life and can apply them to a private int[][] _map;
 */
public class Conway {

    // Conway's maps are stored as int[][].
    private int[][] _map;

    // Deprecated. Was used to count the cycle number.
    private int cycle = 0;

    // Deprecated. Was used to gain access to the _map.
    public int[][] getMap() { return _map; }

    // Deprecated. Was used to change maps.
    public void setMap(int[][] map) { _map = map; }

    /*
    public Conway(int[][] map);
    Constructor that creates a Conway object given a map of int[][].
     */
    public Conway(int[][] map){
        _map = map;
    }

    /*
    public int[][] updateMap(){
    Updates the map stored in the Conway class based on Conway's rules. Returns the updated map.
     */
    public int[][] updateMap(){
        cycle++;
        int[][] newMap = new int[_map.length][_map[0].length];
        for(int r = 0; r < _map.length; r++){
            for(int c = 0; c < _map[0].length; c++){
                int cnt = countNeighbors(r, c);
                if(_map[r][c] == 1 && (cnt < 2  || cnt > 3)){
                    newMap[r][c] = 0;
                }
                else if(_map[r][c] == 1 && cnt == 2 || cnt == 3){
                    newMap[r][c] = 1;
                }
                else if(_map[r][c] == 0 && cnt == 3){
                    newMap[r][c] = 1;
                }
            }
        }
        _map = newMap;
        return _map;
    }

    /*
    private int countNeighbors(int row, int col);
    Given a cell at a specified row and col this method counts the living neighbors.
     */
    private int countNeighbors(int row, int col){
        int cnt = 0;
        for(int r = row - 1; r <= row + 1; r++){
            for(int c = col - 1; c <= col + 1; c++){
                if(!((r == row) && (c == col)) && inBounds(r,c) && _map[r][c] >= 1){
                    cnt++;
                }
            }
        }
        return cnt;
    }

    /*
    private boolean inBounds(int row, int col);
    Given the row and col of a cell this method will return true if the cell exists in the map; false otherwise.
     */
    private boolean inBounds(int row, int col) {
        if( row < 0 || col < 0 || row >= _map.length || col >= _map[0].length){
            return false;
        }
        return true;
    }
}
