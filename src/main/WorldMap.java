package main;

import java.util.*;

@GwtCompatible
public interface Multimap;

public class WorldMap {
    int width;
    int height;
    protected Jungle jungle;
    protected Map<Vector2D, TreeSet<Animal>> neverland= new TreeMultimap<>();    //neverand in the name of my world, because I don't want to name my world 'myWorld' or 'wholeWorld' or anything else
    protected List<Animal> animals = new LinkedList<>();


    public WorldMap (int width, int height, double jungleRatio){ //leftDown = (0,0) rightUp=(width, height)
        this.width=width;
        this.height=height;
        this.jungle=new Jungle(width, height, jungleRatio);
    }





    public void placeRandomAnimals(int numberOfAnimals, int startEnergy){ //to place Adam and Eva

        for(int i=0;i<numberOfAnimals; i++){
            Animal randomAnimal=randomAnimal(startEnergy,this.width,this.height);
            animals.add(randomAnimal);
            neverland.put((randomAnimal.getPosition(), randomAnimal));
        }
    }

    public Animal randomAnimal(int startEnergy, int width, int height){ //to make Adam and Eva
        int[] genotype=new int[32];
        for(int i=0;i<32;i++)
            genotype[i]=(int)Math.random()*7;


        return new Animal(new Vector2D((int)Math.random()*width,(int)Math.random()*height),Orientation.EAST.getRandom(),startEnergy, genotype);
    }

    public void addGrass(Vector2D leftDown, Vector2D rightUp){
        Vector2D randPos;
        do {
             randPos = new Vector2D((int) Math.random() * rightUp.x - leftDown.x, (int) (Math.random() * rightUp.y - leftDown.y));
        }while(randPos.precedes(rightUp) && randPos.follows(leftDown));
    }
}
