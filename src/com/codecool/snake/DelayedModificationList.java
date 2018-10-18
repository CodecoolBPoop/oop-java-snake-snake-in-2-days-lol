package com.codecool.snake;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * A special container which maintains a list of objects. If any modification (addition, removal)
 * is done, it won't happen until the doPendingModifications() method is called
 */
public class DelayedModificationList<T> {
    private List<T> gameObjects = new LinkedList<>();
    private List<T> newGameObjects = new LinkedList<>();// Holds game objects crated in this frame.
    private List<T> oldGameObjects = new LinkedList<>();// Holds game objects that will be destroyed this frame.


    public void add(T obj) {
        newGameObjects.add(obj);
    }

    public void addAll(List<T> objs) {
        for(T obj : objs) {
            add(obj);
        }
    }

    public void remove(T obj) {
        oldGameObjects.add(obj);
    }

    public List<T> getList() {
        return Collections.unmodifiableList(gameObjects);
    }

    public boolean isEmpty() {
        if(!newGameObjects.isEmpty() || !gameObjects.isEmpty()) {
            return false;
        }
        return true;
    }

    /**
     * All the modifications (Add, Remove) is pending until you call this method
     */
    public void doPendingModifications() {
        gameObjects.addAll(newGameObjects);
        newGameObjects.clear();

        gameObjects.removeAll(oldGameObjects);
        oldGameObjects.clear();
    }

    public T getLast() {
        if(!newGameObjects.isEmpty()) return newGameObjects.get(newGameObjects.size()-1);
        if(!gameObjects.isEmpty()) return gameObjects.get(gameObjects.size()-1);
        return null;
    }
}
