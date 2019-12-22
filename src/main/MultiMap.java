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
            System.out.println("dodanie do istneijącego drzew"+map.get(key)+" "+map.get(key).last()+" "+key);
        }
        else {
            TreeSet<A> tree= new TreeSet<>(c);
            tree.add(val);
            map.put(key, tree);
            System.out.println("dodanie noewgo drzew"+map.get(key)+" "+key);

        }
    }

    public void delete(K key, A val) throws NullPointerException {
        //System.out.println(map.get(key)+"przed usunieciem mapa"+key);

        try{
            map.get(key).remove(val);
            System.out.println("deleted key:"+ key);

        }                                                       //removes value from TreeSet
        catch (NullPointerException ex){
            System.out.println(ex + "there is no such animal in that place");
        }
        //System.out.println(map.get(key)+"po usunieciu mapa"+key);

        if(map.get(key).isEmpty()) { //if that was last value in TreeSet removes key leading to that TreeSet
            map.remove(key);
            System.out.println(("removed whole tree"+key));
        }


    }

    public void putAll(Map<K,TreeSet<A>> addMap ){
        map.putAll(addMap);
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


