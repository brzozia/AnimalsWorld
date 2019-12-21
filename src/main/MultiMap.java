package main;

import java.util.*;

public class MultiMap<K, A> {
    private Comparator<A> c;
    protected Map<K, TreeSet<A>> map = new HashMap<>();

    public MultiMap(Comparator c) {
        this.c = c;
    }



    public void add(K key, A val){

        if(map.containsKey(key)) {
            map.get(key).add(val); //get returns TreeSet, into which animal is added
            System.out.println("add1: "+ map.get(key).add(val));
        }
        else {
            TreeSet<A> tree= new TreeSet<>(c);
            tree.add(val);
            map.put(key, tree);
            System.out.println("add2222: "+ map.get(key).add(val));

        }
    }

    public void delete(K key, A val) throws NullPointerException {
        try{
            map.get(key).remove(val);
        } //removes value from TreeSet
        catch (NullPointerException ex){
            System.out.println("dont cry" + ex);
        }

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
        return map.get(key).size();
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


