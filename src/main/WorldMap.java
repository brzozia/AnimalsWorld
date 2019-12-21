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
            daySimulation(moveEnergy, startEnergy);
        }

    }




    public void daySimulation(int moveEnergy, int startEnergy){
        for(Animal rat: animals ){              // removing dead animals
            if(rat.getEnergy()<=0)
                neverLandMap.delete(rat.getPosition(),rat);
        }

        for(Animal rat: animals ){
            rat.rotate();                       //changes orientation, moves and decreases energy
            rat.move();
            rat.decreaseEnergy(moveEnergy);

        }

        for(Animal rat: animals ){             //eating grass
            divideAndEat(rat);
        }

                                                                                                //reproduction
        for(Map.Entry<Vector2D, TreeSet<Animal>> entry: neverLandMap.entrySet() ){             //iterating through MultiMap
            TreeSet<Animal> rats=entry.getValue();

            Animal rat1;
            Animal rat2;
            if(rats.size()>1) {                                                     //finding the strongest pair sitting in one field
                Iterator<Animal> itr = rats.descendingIterator();
                rat1=itr.next(); //rats.last()
                rat2=itr.next();

                if(rat2.checkReproductionEnergy(startEnergy)) {                     //it is enough to check second rat, because they are sorted in Tree
                    Vector2D placeToBorn=findPlaceToBorn(rat2.getPosition());

                    Animal newBorn = rat1.reproduction(rat2,placeToBorn );
                    animals.add(newBorn);
                    neverLandMap.add(placeToBorn, newBorn);

                }
            }
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


    public Vector2D findPlaceToBorn(Vector2D position){
        Vector2D place = position;
        Orientation orient = Orientation.NORTH;

        int i=0;
        place.add(orient.toUniVector());
        while(isOccupied(place) || i>7){
            place.substract(orient.toUniVector());
            orient=orient.next();
            place.add(orient.toUniVector());
            i++;
        }
        if(i==8){
            place.substract(orient.toUniVector());
            orient.getRandom();
            place.add(orient.toUniVector());
        }

        return place;
    }

    public void divideAndEat(Animal rat){
        Vector2D animalPosition = rat.getPosition();

        if(objectAt(animalPosition) instanceof Grass) {       //if animal stands on the grass

            if (neverLandMap.size(animalPosition)!= 1 &&  neverLandMap.last(animalPosition).equals(rat) ) {     //if rat is the animal with the highest energy
                SortedSet<Animal> equalRat=neverLandMap.tailSet(animalPosition,rat);    //set with animals with the same energy
                int noOfAnimalsOnGrass=equalRat.size();                                 // no of animals with the same energy - is it working? I dont know

                if(noOfAnimalsOnGrass==1) {                                             //if he is the strongest one
                    rat.eatingGrass(grassMap.get(animalPosition).getEnergy());
                }
                else{                                                                       // if he has equal strong friends
                    double dividedEnergy= grassMap.get(animalPosition).getEnergy()/noOfAnimalsOnGrass;
                    Iterator<Animal> itr= equalRat.iterator();
                    while(itr.hasNext()) {
                        itr.next().eatingGrass(dividedEnergy);
                    }
                }
                grassMap.remove(animalPosition);
            }
            else if(neverLandMap.size(animalPosition)== 1){
                rat.eatingGrass(grassMap.get(animalPosition).getEnergy());
                grassMap.remove(animalPosition);
            }

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
