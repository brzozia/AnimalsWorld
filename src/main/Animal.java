package main;

import java.util.Arrays;

public class Animal {
    private Vector2D position;
    private Orientation orientation;
    private double energy; //determines days of animals' living
                            // double, because grassEnergy might be split between animals with the same amount of their own energy
    private int[] genotype;



    public Vector2D getPosition(){
        return this.position;
    }

    public Animal(Vector2D position, Orientation orientation, double energy, int[] genotype) {
        this.position = position;
        this.orientation = orientation;
        this.energy = energy;
        this.genotype = genotype;
    }

    public void rotate(Animal rat){
        int idRandRotate = (int)(Math.random()*31); //chooses index from 0 to 31 (from genotype)
        int numRandRotate = genotype[idRandRotate]; //chooses number of rotations from genotype
        rat.changeOrientation(numRandRotate); // rotates animal
    }

    public void changeOrientation(int rotate){ //changes orientation of animal when it knows how many times do this
        while(rotate>0){
        orientation.next();
        rotate--;
        }
    }

    public void eatingGrass(Grass grass){
        this.energy+=grass.getEnergy();
    }

    public Animal reproduction(Animal rat, Vector2D placeToBorn){ //new means that it is for a new born animals' child

        Orientation newOrientation = rat.orientation;
        newOrientation.getRandom(); //new super-random orientation

        double newEnergy=((1/4)*rat.energy)+((1/4)*this.energy);

        int[] newGenotype = crossingOverGens(rat);

        return new Animal(placeToBorn,newOrientation,newEnergy,newGenotype);

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

    private int[] makeDirectionArrayCorrect (int newGenotype[]){ //check number of gens
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
                    randGen = (int) Math.random() * 31; //finds gen to change from genotype;
                }
                while(directionsInNewGenotype[randGen]<2); //if founded gen is the only gen of this type find another one

                //we have a gen to change, so (k is a missing gen)
                newGenotype[randGen]=k;
                directionsInNewGenotype[k]++; //number of k-direction is bigger
                directionsInNewGenotype[newGenotype[randGen]]--; //number of changed direction is smaller

            }
        }
            return newGenotype;
    }






}
