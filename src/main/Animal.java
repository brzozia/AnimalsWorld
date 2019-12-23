package main;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class Animal {
    private Vector2D position;
    private Orientation orientation;
    private double energy; //determines days of animals' living
                            // double, because grassEnergy might be split between animals with the same amount of their own energy
    private int[] genotype;
    private final int id;
    protected List<IPositionChangeObserver> observers = new LinkedList<>();


    public Animal(Vector2D position, Orientation orientation, double energy, int[] genotype, int id) {
        this.position = position;
        this.orientation = orientation;
        this.energy = energy;
        this.genotype = genotype;
        this.id=id;
        id++;
    }


    public String toStringAttributes(){
        return "id:"+id+"pozycja:"+this.getPosition()+" orient: "+this.getOrientation()+" energia:"+this.energy+" genotype:"+ Arrays.toString(this.genotype);
    }

    public String toString(){
        return "A";
    }

    public Vector2D getPosition(){
        return this.position;
    }

    public void setPosition(Vector2D newPosition) { this.position=newPosition;}

    public double getEnergy() {
        return this.energy;
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void decreaseEnergy(int moveEnergy){
        this.energy-=moveEnergy;

    }
    public void rotate(){
        int idRandRotate = (int)(Math.random()*31); //chooses index from 0 to 31 (from genotype)
        int numRandRotate = genotype[idRandRotate]; //chooses number of rotations from genotype
        this.changeOrientation(numRandRotate); // rotates animal
    }

    public void changeOrientation(int rotate){ //changes orientation of animal when it knows how many times do this
        while(rotate>0){
        orientation=orientation.next();
        rotate--;
        }
    }

    public void eatingGrass(double energy){
        this.energy+=energy;
    }

    public boolean checkReproductionEnergy(int startEnergy){
        if(this.energy<(startEnergy/2))
            return false;
        else
            return true;
    }

    public Animal reproduction(Animal rat, Vector2D placeToBorn, int newId) { //new means that it is for a new born animals' child

        Orientation newOrientation = rat.orientation;
        newOrientation.getRandom(); //new super-random orientation

        double newEnergy=(rat.energy/4)+(this.energy/4);
        this.energy-=this.energy/4;
        rat.energy-=rat.energy/4;

        int[] newGenotype = crossingOverGens(rat);

        return new Animal(placeToBorn,newOrientation,newEnergy,newGenotype, newId );

    }


    private int[] crossingOverGens(Animal rat){
        int div1=(int)(Math.random()*29);
        int div2=(int)(Math.random()*31);
        div1=Math.min(div1,div2);

        int[] newGenotype = new int[32];
        for(int i=0;i<32;i++){
            if(i<=div1)
                newGenotype[i]=rat.genotype[i];
            else if(i<div2)
                newGenotype[i]=this.genotype[i];
            else
                newGenotype[i]=rat.genotype[i];
        }

        newGenotype=makeDirectionArrayCorrect(newGenotype);
        Arrays.sort(newGenotype);

        return newGenotype;

    }

    private int[] makeDirectionArrayCorrect (int[] newGenotype){ //check number of gens
        int[] directionsInNewGenotype=new int[8];
        for(int i=0;i<8;i++){
            directionsInNewGenotype[i]=0;
        }

        for(int j=0;j<32;j++){
            directionsInNewGenotype[newGenotype[j]]++;  //counts how many gens are in each direction
        }

            for(int k=0;k<8;k++){
                if(directionsInNewGenotype[k]==0){      //checks whether there are missing directions (in directionsInNewGenotype we can check how many directions are missed)
                int randGen;
                do {
                    randGen = (int) (Math.random() * 31); //finds gen to change from genotype;
                }
                while(directionsInNewGenotype[(newGenotype [randGen])]<2); //if founded gen is the only gen of this type find another one

                //we have a gen to change, so (k is a missing gen)
                newGenotype[randGen]=k;
                directionsInNewGenotype[k]++; //number of k-direction is bigger
                directionsInNewGenotype[newGenotype[randGen]]--; //number of changed direction is smaller

            }
        }
            return newGenotype;
    }


    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

   public void removeObserver(IPositionChangeObserver observer){
        observers.remove(observer);
    }

    public void positionChanged(Vector2D oldPosition, Vector2D newPosition){
        for(IPositionChangeObserver s: observers){
            s.positionChanged(this, oldPosition, newPosition);
        }
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object o) {
        try {
            assert o instanceof Animal;
        } catch (IllegalArgumentException ex) {
            System.out.println(ex + " not an Animal");
        }

        return (this.getId()) == (((Animal) o).getId());

    }

}
