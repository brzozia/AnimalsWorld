package main;

public class Jungle {
    Vector2D leftDown;
    Vector2D rightUp;
    int maxNoOfJungleGrass;

    public Jungle (int width, int height, double jungleRatio) {
        int jungleWidth=(int)(width*jungleRatio);
        int jungleHeight=(int)(height*jungleRatio);
        Vector2D middle = new Vector2D((int)((width-1)/2), (int)((height-1)/2));
        this.leftDown=new Vector2D(middle.x - (int)(jungleWidth/2), middle.y - (int)(jungleHeight/2));
        this.rightUp=new Vector2D(middle.x + (int)(jungleWidth/2), middle.y + (int)(jungleHeight/2));
        this.maxNoOfJungleGrass=jungleHeight*jungleWidth;

    }

    public int getMaxNoOfJungleGrass(){
        return maxNoOfJungleGrass;
    }

    public void setMaxNoOfJungleGrass(int i){
        this.maxNoOfJungleGrass=this.maxNoOfJungleGrass+i;
    }

    public boolean inJungle(Vector2D pos){
        if(pos.precedes(rightUp) && pos.follows(leftDown))
            return true;
        else
            return false;
    }
}
