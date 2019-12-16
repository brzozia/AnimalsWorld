public class Vector2D {
    public final int x;
    public final int y;

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public boolean precedes(Vector2D other){
        return ((this.x <= other.x) && (this.y <= other.y));

    }
    public boolean follows(Vector2D other){
        if((this.x >= other.x) && (this.y >= other.y))
            return true;
        else
            return false;
    }


    public boolean equals(Object other){
        if(other == this)
            return true;
        if (!(other instanceof Vector2D))
            return false;
        Vector2D that = (Vector2D) other;
        if (that.x==this.x &&  that.y==this.y)
            return true;
        else
            return false;
    }
}
