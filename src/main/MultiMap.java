package main;

import java.util.*;

public class MultiMap<K, A> {
    private Comparator<A> Comparator;
    protected Map<K, TreeSet<A>> map = new HashMap<>();

    public MultiMap(Comparator c) {
        this.Comparator = c;
    }

    public void add(K key, A val){

        if(map.containsKey(key))
            map.put(key, new TreeSet<A>(Comparator));

        map.get(key).add(val); //get returns TreeSet, into which animal is added ///czy get position zroci puste drzewo czy nie?
    }

    public void delete(K key, A val){
        map.get(key).remove(val);  //removes value from TreeSet

        if(map.get(key).isEmpty())  //if that was last value in TreeSet removes key leading to that TreeSet
            map.remove(key);
    }

    public boolean containsKey(K key){
        return map.containsKey(key);
    }

    public Object get(K key){
        return map.get(key);
    }
    public int size(K key){
        int size=0;
        for(A val: map.get(key)){
            size++;
        }
        return size;
    }
    public Object last(K key){
        return map.get(key).last();
    }

    public Iterator<A> descendingIterator(K key){
        return map.get(key).descendingIterator();
    }

    public SortedSet<A>  tailSet(K key, A val){
        return map.get(key).tailSet(val);
    }

    public Set<Map.Entry<K, TreeSet<A>>> entrySet() {
        return map.entrySet();
    }


}


