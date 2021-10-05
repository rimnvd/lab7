package utility;

import data.Color;
import data.Dragon;


import java.time.LocalDate;
import java.util.Collections;
import java.util.Vector;

/**
 * This class is responsible for the working with the collection.
 */
public class CollectionManager {
    //private final FileManager fileManager;
    private LocalDate date = LocalDate.now();
    private Long maxId;
    private final Vector<Dragon> vector = new Vector<>(0);


    /*public void loadCollection() {
        vector = fileManager.readCollection();
        date = LocalDate.now();
        maxId = (fileManager.getMaxId() != null) ? fileManager.getMaxId() : 0L;
    }*/

    /**
     * Determines type of this collection.
     *
     * @return type of this collection as a String
     */
    public synchronized String collectionType() {
        synchronized (vector) {
            return vector.getClass().getName();
        }
    }

    /**
     * Returns the collection.
     *
     * @return the collection
     */
    public synchronized Vector<Dragon> getCollection() {
        synchronized (vector) {
            return vector;
        }
    }

    /**
     * Removes all of the elements from this collection.
     */
    public synchronized void clear(String owner) {
        synchronized (vector) {
            vector.stream().filter(dragon -> dragon.getOwner().equals(owner)).forEach(vector::remove);
        }
    }

    /**
     * Returns the number of the components in this collection.
     *
     * @return the number of the components in this collection
     */
    public synchronized int collectionSize() {
        synchronized (vector) {
            return vector.size();
        }
    }

    /**
     * Returns the creation date of this collection.
     *
     * @return the creation date of this collection
     */
    public synchronized LocalDate getDate() {
        synchronized (vector) {
            return date;
        }
    }

    /**
     * Appends the specified element to the end of this collection.
     */
    public synchronized void addToCollection(Dragon dragon, Long id) {
        synchronized (vector) {
            dragon.setId(id);
        }
    }


    /**
     * Removes the element from this collection with the specified id value.
     * If the collection does not contain the specified element, it is unchanged.
     *
     * @param id id value
     */
    public synchronized boolean removeById(Long id, String owner) {
        synchronized (vector) {
            return vector.removeIf(dragon -> dragon.getId().equals(id) && dragon.getOwner().equals(owner));
        }
    }

    /**
     * Removes from this collection all of the elements that are lower than the specified element.
     *
     * @param d the element to be compared.
     */
    public synchronized void removeLower(Dragon d) {
        synchronized (vector) {
            vector.removeIf(dragon -> dragon.compareTo(d) < 0);
        }
    }

    /**
     * Tests if this collection has no components.
     *
     * @return true if this vector has no components; false otherwise
     */
    public synchronized boolean isEmpty() {
        synchronized (vector) {
            return vector.isEmpty();
        }
    }

    /**
     * Returns the maximum component of this collection.
     *
     * @return the maximum component of this collection
     */
    public synchronized Dragon maxElement() {
        synchronized (vector) {
            return Collections.max(vector);
        }
    }

    /**
     * Updates the element of this collection with the specified if value.
     *
     * @param idValue id value
     */
    public synchronized void updateById(Long idValue, Dragon dragon) {
        synchronized (vector) {
            boolean flag = true;
            for (int i = 0; i < vector.size() && flag; i++) {
                if (vector.get(i).getId().equals(idValue)) {
                    dragon.setId(idValue);
                    vector.set(i, dragon);
                    flag = false;
                }
            }
        }

    }

    /**
     * Removes the last element from this collection.
     */
    public synchronized void removeLast(String owner) {
        synchronized (vector) {
            if (vector.get(vector.size() - 1).getOwner().equals(owner))
            vector.removeElementAt(vector.size() - 1);
        }
    }

    /**
     * Removes the element with the specified color value from this collection.
     *
     * @param color color value
     */
    public synchronized boolean removeByColor(Color color, String owner) {
        synchronized (vector) {
            boolean flag = false;
            for (int i = 0; i < vector.size() && !flag; i++) {
                if (vector.get(i).getColor().equals(color) && vector.get(i).getOwner().equals(owner)) {
                    vector.removeElementAt(i);
                    flag = true;
                }
            }
            return flag;
        }
    }



}