package main;

public class Vector2D {
    public final int x;
    public final int y;

    public Vector2D(int x, int y){
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "x:"+this.x+" , y:"+this.y;
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

    public Vector2D add(Vector2D other){
        return new Vector2D(this.x+other.x, this.y+other.y);
    }

    public Vector2D substract(Vector2D other){
        return new Vector2D(this.x-other.x, this.y-other.y);
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

    @Override
    public int hashCode() {
        int hash = 13;
        hash += this.x * 31;
        hash += this.y * 17;
        return hash;
    }
}
