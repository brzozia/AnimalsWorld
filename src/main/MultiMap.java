package main;

import java.util.*;

public class MultiMap<V, A> {
    private Comparator<A> Comparator;
    protected Map<V, TreeSet<A>> map = new HashMap<>();

    public MultiMap(Comparator c) {
        this.Comparator = c;
    }

    public void add(V key, A val){

        if(map.containsKey(key))
            map.put(key, new TreeSet<A>(Comparator));

        map.get(key).add(val); //get returns TreeSet, into which animal is added ///czy get position zroci puste drzewo czy nie?
    }

    public void delete(V key, A val){
        map.get(key).remove(val);  //removes value from TreeSet

        if(map.get(key).isEmpty())  //if that was last value in TreeSet removes key leading to that TreeSet
            map.remove(key);
    }

    public boolean containsKey(V key){
        return map.containsKey(key);
    }

    public Object get(V key){
        return map.get(key);
    }
    public int size(V key){
        int size=0;
        for(A val: map.get(key)){
            size++;
        }
        return size;
    }
    public Object last(V key){
        return map.get(key).last();
    }

    public Iterator<A> descendingIterator(V key){
        return map.get(key).descendingIterator();
    }

//    public boolean hasNext(V key){
//        return map.get(key).hasN
//    }
}


