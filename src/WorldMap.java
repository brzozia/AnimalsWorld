

public class WorldMap {
    int width;
    int height;

    public WorldMap (int width, int height){
        this.width=width;
        this.height=height;
    }

    public void addGrass(Vector2D leftDown, Vector2D rightUp){
        Vector2D randPos;
        do {
             randPos = new Vector2D((int) Math.random() * rightUp.x - leftDown.x, (int) (Math.random() * rightUp.y - leftDown.y));
        }while(randPos.precedes(rightUp) && randPos.follows(leftDown));
    }
}
