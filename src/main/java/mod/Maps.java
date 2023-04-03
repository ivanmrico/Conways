package mod;

/*
public enum Maps;
This enum is deprecated. Originally, it housed specific Conwayu's maps for testing purposes.
 */

public enum Maps {
    /*
    The Map constants used for testing purposes.
     */
    MAP1( new int[][] {
            {0,0,1,0,0,0,0,0,0,0,0},
            {1,0,1,0,0,0,0,0,0,0,0},
            {0,1,1,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0,0,0}
            }
    )
    ;

    // _map represents the Conway's map.
    private int[][] _map;

    // Getter method for _map.
    public int[][] getMap() { return _map; }

    // Simple constructor that takes in a map and stores it.
    private Maps(int[][] map){
        _map = map;
    }
}
