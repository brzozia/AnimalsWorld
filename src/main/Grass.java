package main;

public class Grass {
    private Vector2D position;
    private int grassEnergy; //quantity of energy, which animal would get eating grass

    public Grass(Vector2D position, int grassEnergy) {
        this.position = position;
        this.grassEnergy=grassEnergy;
    }

    public int getEnergy(){
        return this.grassEnergy;
    }

    public String toString(){
        return "  *   ";
    }

}
