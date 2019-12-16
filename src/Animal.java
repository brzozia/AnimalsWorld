import java.util.List;

public class Animal {
    private Vector2D position;
    private Orientation orientation;
    private int energy;
    private Integer[] gens;

    public Animal(Vector2D position, Orientation orientation, int energy, Integer[] gens) {
        this.position = position;
        this.orientation = orientation;
        this.energy = energy;
        this.gens = gens;
    }

    //rozmnazanie


    public void move(){
        int idRandRotate = (int)(Math.random()*31);
        int numRandRotate = gens[idRandRotate];

    }

    public void changeOrientation(int rotate){
        while(rotate>0){
        orientation.next();
        rotate--;
        }
    }



}
