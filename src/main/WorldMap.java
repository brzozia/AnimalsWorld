package main;

import java.util.*;



public class WorldMap implements IPositionChangeObserver {
    int width;
    int height;
    int maxNoOfWorldGrass;
    Vector2D worldLeftDown=new Vector2D(0,0);
    Vector2D worldRightUp;
    private Jungle jungle;               //contains dimensions of jungle
    private Comparator compare =Comparator.comparingDouble(Animal::getEnergy);
    private MultiMap<Vector2D, Animal> neverLandMap = new MultiMap<Vector2D, Animal>(this.compare);    //makes a MultiMap where keys are type Vector2D, and values are Animals and gives a comparator, which compares Animals (values) using Animals' energy
                                                                                                                                                    // neverLand in the name of my world, because I don't want to name my world 'myWorld' or 'wholeWorld' or anything else
    protected List<Animal> animals = new LinkedList<>();
    Map<Vector2D, Grass> grassMap = new HashMap<>();


    public WorldMap (int width, int height, double jungleRatio){         //leftDown = (0,0) rightUp=(width, height)
        this.width=width;
        this.height=height;
        this.jungle=new Jungle(width, height, jungleRatio);
        this.maxNoOfWorldGrass =width*height - jungle.getMaxNoOfJungleGrass();
        this.worldRightUp=new Vector2D(width-1,height-1);
    }

    @Override
    public String toString() {
        MapVisualizer mapVis = new MapVisualizer(this);
        return mapVis.draw(worldLeftDown, worldRightUp);
    }

//      life is taking place in World

//    public void Life( int daysOfSimulation,int  noOfAnimals,int  startEnergy,int  moveEnergy) {
//
//        placeRandomAnimals(noOfAnimals, startEnergy);
//
//        for (int i = 0; i < daysOfSimulation; i++) {
//            System.out.println("today is day: " + i);
//            daySimulation(moveEnergy, startEnergy);
//            System.out.println(animals.toString());
//            System.out.println(grassMap.toString());
//
//        }
//    }




    public void daySimulation(int moveEnergy, int startEnergy){
        //------------------killing animals---------------

        List<Animal> toKill =new LinkedList<>(); //list with animals to kill
        for(Animal rat: animals ){              // removing dead animals, but only from neverLand
            if(rat.getEnergy()<=0) {

                neverLandMap.delete(rat.getPosition(), rat);
                toKill.add(rat);
            }
        }

        for(Animal soonDead: toKill){           //kills animals from the list
            animals.remove(soonDead);

        }
        toKill.clear();

        //-----------------running and rotating-------------
       // MultiMap<Vector2D, Animal > addMap = new MultiMap<>(this.compare);
        for(Animal rat: animals ){
                System.out.println("przed zmianą: "+rat.getPosition());
                rat.rotate();                                   //changes orientation, moves and decreases energy
                move(rat);

            rat.decreaseEnergy(moveEnergy);
        }


        for(Animal rat: animals ){
            neverLandMap.add(rat.getPosition(), rat);
        }

        //----------------eating grass-------------------
        for(Animal rat: animals ){
            if(objectAt(rat.getPosition()) instanceof Grass) {  //if animal stands at grass it can eat it
                if(jungle.inJungle(rat.getPosition()))
                    jungle.setMaxNoOfJungleGrass(1);
                else
                    maxNoOfWorldGrass++;
                divideAndEat(rat);

            }
        }


        //-------------making new animals----------------
        for(Map.Entry<Vector2D, TreeSet<Animal>> entry: neverLandMap.entrySet() ){             //iterating through MultiMap
            TreeSet<Animal> rats=entry.getValue();
System.out.println("sth"+rats);
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
                    newBorn.addObserver(this);
                }
            }
        }

            //-------------------adding grass--------------------
            if(jungle.getMaxNoOfJungleGrass() >0){
            addJungleGrass();               //add grass to jungle
            jungle.setMaxNoOfJungleGrass(-1);
            }

        if (maxNoOfWorldGrass >0){
            addWorldGrass();               //add grass not to jungle
            maxNoOfWorldGrass--;
        }

    }

    public void move(Animal rat){
        Vector2D newPosition =rat.getPosition().add(rat.getOrientation().toUniVector());
        System.out.println("po zmianie: "+newPosition);

        wrapMap(newPosition);
        System.out.println("po zmianie: "+newPosition);

        rat.positionChanged(rat.getPosition(),newPosition);
        rat.setPosition(newPosition);

    }

    public void wrapMap(Vector2D position){
            if (position.x < this.worldLeftDown.x) {
                position.x = this.worldRightUp.x ;
            }
            if (position.y < this.worldLeftDown.y) {
                position.y = this.worldRightUp.y ;
                return;                         // checked if position is smaller than worldLeftDown (but not precisely smaller) and it cannot be at the same time bigger than
                                                // worldRightUp so we dont have to check further conditions
            }


            if (position.x > this.worldRightUp.x) {
                position.x = this.worldLeftDown.x ;
            }
            if (position.y > this.worldRightUp.y) {
                position.y = this.worldLeftDown.y;
            }

    }


    public Vector2D findPlaceToBorn(Vector2D position){
        Vector2D place = position;
        Orientation orient = Orientation.NORTH;

        int i;
        place.add(orient.toUniVector());
        for(i=0;i<7 || isOccupied(place);i++){
            place.substract(orient.toUniVector());
            orient=orient.next();
            place.add(orient.toUniVector());
            i++;
        }
        if(i>=8){
            place.substract(orient.toUniVector());
            orient.getRandom();
            place.add(orient.toUniVector());
        }

        return place;
    }

    public void divideAndEat(Animal rat){

        Vector2D animalPosition = rat.getPosition();

                                                        //if animal stands on the grass
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
            else if(neverLandMap.size(animalPosition)== 1){                             //he can also be not the strongest one but then the strongest animals will eat grass when it will be their turn
                rat.eatingGrass(grassMap.get(animalPosition).getEnergy());
                grassMap.remove(animalPosition);
            }


    }


    public void placeRandomAnimals(int numberOfAnimals, int startEnergy){   //to place Adam and Eva
        int i=0;
        while(i<numberOfAnimals){
            Animal randomAnimal = randomAnimal(startEnergy);

            if(!isOccupied(randomAnimal.getPosition())){        //at first at map can by only animals

                animals.add(randomAnimal);
                neverLandMap.add(randomAnimal.getPosition(), randomAnimal);
                randomAnimal.addObserver(this);
                i++;
                System.out.println(randomAnimal.toStringAttributes());
            }

        }
    }

    public Animal randomAnimal(int startEnergy){ //to make Adam and Eva
        int[] genotype=new int[32];
        for(int i=0;i<32;i++)
            genotype[i]=(int)(Math.random()*8);

        return new Animal(new Vector2D((int)(Math.random()*(this.width-1)),(int)(Math.random()*(this.height-1))),Orientation.EAST.getRandom(),startEnergy, genotype);
    }

    public void addJungleGrass(){
        Vector2D randPos;
        do {
             randPos = new Vector2D((int) (jungle.leftDown.x + Math.random() * (jungle.rightUp.x - jungle.leftDown.x+1)), (int) (jungle.leftDown.y + Math.random() * (jungle.rightUp.y - jungle.leftDown.y+1)));
        }while(!(jungle.inJungle(randPos) && !isOccupied(randPos)));

        grassMap.put(randPos, new Grass(randPos));
    }
    public void addWorldGrass(){
        Vector2D randPos;
        do {
            randPos = new Vector2D((int) (Math.random() * width), (int) (Math.random() * height));
        }while(jungle.inJungle(randPos) || objectAt(randPos)instanceof Grass);

        grassMap.put(randPos, new Grass(randPos));
    }

    public boolean isOccupied(Vector2D vec){    //checks if there is a grass or an animal on vec-position
        if(grassMap.containsKey(vec)) {
            return false;
        }
        else return neverLandMap.containsKey(vec);
    }

    public Object objectAt(Vector2D pos){
        if(grassMap.containsKey(pos))
            return grassMap.get(pos);
        else
            return neverLandMap.get(pos); //returns null or animal
    }



    @Override
    public void positionChanged(Animal cow,Vector2D oldPosition, Vector2D newPosition) {
        neverLandMap.add(newPosition, cow);
        neverLandMap.delete(oldPosition, cow);
    }
}
