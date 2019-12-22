package main;

public enum Orientation {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    public Orientation next(){
        switch(this) {
            case NORTH:
                return NORTHEAST;
            case NORTHEAST:
                return EAST;
            case EAST:
                return SOUTHEAST;
            case SOUTHEAST:
                return SOUTH;
            case SOUTH:
                return SOUTHWEST;
            case SOUTHWEST:
                return WEST;
            case WEST:
                return NORTHWEST;
            case NORTHWEST:
                return NORTH;
        }
        return null;
    }

    public Vector2D toUniVector(){
        switch(this) {
            case NORTH:
                return new Vector2D(0,1);
            case NORTHEAST:
                return new Vector2D(1,1);
            case EAST:
                return new Vector2D(1,0);
            case SOUTHEAST:
                return new Vector2D(1,-1);
            case SOUTH:
                return new Vector2D(0,-1);
            case SOUTHWEST:
                return new Vector2D(-1,-1);
            case WEST:
                return new Vector2D(-1,0);
            case NORTHWEST:
                return new Vector2D(-1,1);

            default:
                return new Vector2D(0,0);

        }

    }

    public Orientation getRandom(){
        int rotate=(int)(Math.random()*8);
        Orientation orient=Orientation.SOUTHWEST;

        for(int i=0;i<rotate;i++)
            orient=orient.next();

        return orient;

    }


}
