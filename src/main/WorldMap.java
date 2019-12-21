package main;

import java.util.*;



public class WorldMap {
    int width;
    int height;
    int maxNoOfNoJungleGrass;
    protected Jungle jungle;                //contains dimensions of jungle
    protected MultiMap<Vector2D, Animal> neverLandMap = new MultiMap<Vector2D, Animal>(Comparator.comparingDouble((Animal x) -> x.getEnergy()));    //makes a MultiMap where keys are type Vector2D, and values are Animals and gives a comparator, which compares Animals (values) using Animals' energy
                                                                                                                                                    // neverLand in the name of my world, because I don't want to name my world 'myWorld' or 'wholeWorld' or anything else
    protected List<Animal> animals = new LinkedList<>();
    protected Map<Vector2D, Grass> grassMap = new HashMap<>();


    public WorldMap (int width, int height, double jungleRatio){         //leftDown = (0,0) rightUp=(width, height)
        this.width=width;
        this.height=height;
        this.jungle=new Jungle(width, height, jungleRatio);
        this.maxNoOfNoJungleGrass =width*height;
    }


    public void Life(int daysOfSimulation, int numberOfAnimals, int startEnergy, int moveEnergy){
        placeRandomAnimals(numberOfAnimals,startEnergy);

        for(int i=0;i<daysOfSimulation;i++){
            daySimulation(moveEnergy);
        }

    }

    public void daySimulation(int moveEnergy){
        for(Animal rat: animals ){              // removing dead animals
            if(rat.getEnergy()<=0)
                neverLandMap.delete(rat.getPosition(),rat);
        }

        for(Animal rat: animals ){
            rat.rotate();                       //changes orientation, moves and decreases energy
            rat.move();
            rat.decreaseEnergy(moveEnergy);

        }

        for(Animal rat: animals ){

            }






            if(jungle.maxNoOfJungleGrass >0){
            addJungleGrass();               //add grass to jungle
            jungle.maxNoOfJungleGrass--;
        }
        if (maxNoOfNoJungleGrass >0){
            addNeverLandGrass();               //add grass not to jungle
            maxNoOfNoJungleGrass--;
        }

    }

    public void divideAndEat(Animal rat){
        Vector2D animalPosition = rat.getPosition();

        if(objectAt(animalPosition) instanceof Grass) {       //if animal stands on the grass

            if (neverLandMap.size(animalPosition) == 1 || (neverLandMap.last(animalPosition)).equals(rat)  && ) {     //if its the only animal on this grass
                rat.eatingGrass(grassMap.get(animalPosition));
                grassMap.remove(animalPosition);
            } else
        }
    }

    public int noOfAnimalsOnGrass(Vector2D position) {
        Iterator itr = neverLandMap.descendingIterator(position);
        int number = 0;
        while (itr.hasNext() && itr.next().>=itr) {
        }
    }



    public void placeRandomAnimals(int numberOfAnimals, int startEnergy){   //to place Adam and Eva
        int i=0;
        while(i<numberOfAnimals){
            Animal randomAnimal = randomAnimal(startEnergy,this.width,this.height);
            if(!isOccupied(randomAnimal.getPosition())){
            animals.add(randomAnimal);
            neverLandMap.add(randomAnimal.getPosition(), randomAnimal);
            i++;
            }
            else
                i--;
        }
    }

    public Animal randomAnimal(int startEnergy, int width, int height){ //to make Adam and Eva
        int[] genotype=new int[32];
        for(int i=0;i<32;i++)
            genotype[i]=(int)(Math.random()*7);

        return new Animal(new Vector2D((int)(Math.random()*width),(int)(Math.random()*height)),Orientation.EAST.getRandom(),startEnergy, genotype);
    }

    public void addJungleGrass(){
        Vector2D randPos;
        do {
             randPos = new Vector2D((int) (Math.random() * (jungle.rightUp.x - jungle.leftDown.x)), (int) (Math.random() * (jungle.rightUp.y - jungle.leftDown.y)));
        }while(jungle.inJungle(randPos) && !isOccupied(randPos));

        grassMap.put(randPos, new Grass(randPos));
    }
    public void addNeverLandGrass(){
        Vector2D randPos;
        do {
            randPos = new Vector2D((int) (Math.random() * (width-1)), (int) (Math.random() * (height-1)));
        }while(!jungle.inJungle(randPos) && !isOccupied(randPos));

        grassMap.put(randPos, new Grass(randPos));
    }

    public boolean isOccupied(Vector2D vec){    //checks if there is a grass or an animal on vec-position
        if(grassMap.containsKey(vec))
            return false;
        else if(neverLandMap.containsKey(vec))
            return false;
        else
            return true;
    }

    public Object objectAt(Vector2D pos){
        if(grassMap.containsKey(pos))
            return grassMap.get(pos);
        else
            return neverLandMap.get(pos); //returns null or animal
    }
}
