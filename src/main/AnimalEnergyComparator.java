package main;

import java.util.Comparator;

public class AnimalEnergyComparator implements Comparator {

    public int compare(Object one, Object other) {
        if (Double.compare(((Animal) one).getEnergy(), ((Animal) other).getEnergy()) == 0) {
            return Integer.compare(((Animal) one).getId(), ((Animal) other).getId());
        } else
            return Double.compare(((Animal) one).getEnergy(), ((Animal) other).getEnergy());
    }

}
