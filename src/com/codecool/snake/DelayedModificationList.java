package com.codecool.snake;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A special container which maintains a list of objects. If any modification (addition, removal)
 * is done, it won't happen until the doPendingModifications() method is called
 */
public class DelayedModificationList<T> {
    private List<T> objects = new LinkedList<>();
    private List<T> newObjects = new LinkedList<>();// Holds game objects crated in this frame.
    private List<T> oldObjects = new LinkedList<>();// Holds game objects that will be destroyed this frame.


    public void add(T obj) {
        newObjects.add(obj);
    }

    public void addAll(List<T> objs) {
        for(T obj : objs) {
            add(obj);
        }
    }

    public void remove(T obj) {
        oldObjects.add(obj);
    }

    public List<T> getList() {
        return Collections.unmodifiableList(objects);
    }

    public boolean isEmpty() {
        if(!newObjects.isEmpty() || !objects.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * All the modifications (Add, Remove) is pending until you call this method
     */
    public void doPendingModifications() {
        objects.addAll(newObjects);
        newObjects.clear();

        objects.removeAll(oldObjects);
        oldObjects.clear();
    }

    public T getLast() {
        if(!newObjects.isEmpty()) return newObjects.get(newObjects.size()-1);
        if(!objects.isEmpty()) return objects.get(objects.size()-1);
        return null;
    }

    public void clear() {
        objects.clear();
        newObjects.clear();
        oldObjects.clear();
    }
}
