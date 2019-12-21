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

    public Orientation getRandom(){
        int rotate=(int)Math.random()*8;

        for(int i=0;i<rotate;i++)
            this.next();

        return this;

    }


}