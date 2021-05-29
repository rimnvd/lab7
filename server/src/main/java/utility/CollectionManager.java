package utility;

import data.Color;
import data.Dragon;
import data.DragonCharacter;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Vector;

/**
 * This class is responsible for the working with the collection.
 */
public class CollectionManager {
    private LocalDate date;
    private Long maxId;
    private Vector<Dragon> vector = new Vector<>(0);
    private final FileManager fileManager;

    public CollectionManager(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    /**
     * Loads the collection from the file.
     */
    public void loadCollection() {
        vector = fileManager.readCollection();
        date = LocalDate.now();
        if (fileManager.getMaxId() != null) {
            maxId = fileManager.getMaxId();
        } else maxId = 0L;
    }

    /**
     * Determines type of this collection.
     *
     * @return type of this collection as a String
     */
    public String collectionType() {
        return vector.getClass().getName();
    }

    /**
     * Returns the collection.
     *
     * @return the collection
     */
    public Vector<Dragon> getCollection() {
        return vector;
    }

    /**
     * Removes all of the elements from this collection.
     */
    public void clear() {
        vector.clear();
        maxId = 0L;
    }

    /**
     * Returns the number of the components in this collection.
     *
     * @return the number of the components in this collection
     */
    public int collectionSize() {
        return vector.size();
    }

    /**
     * Returns the creation date of this collection.
     *
     * @return the creation date of this collection
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Appends the specified element to the end of this collection.
     */
    public void addToCollection(Dragon dragon) {
        if (maxId != Long.MAX_VALUE) {
            maxId++;
            dragon.setId(maxId);
            dragon.setCreationDate(LocalDate.now());
            vector.add(dragon);
        } else System.out.println("Невозможно добавить элемент в коллекцию");
    }

    /**
     * Removes the element from this collection with the specified id value.
     * If the collection does not contain the specified element, it is unchanged.
     *
     * @param id id value
     */
    public boolean removeById(Long id) {
        boolean checkRemoving = false;
        for (Dragon dragon : vector) {
            if (dragon.getId().equals(id)) {
                vector.remove(dragon);
                checkRemoving = true;
                System.out.println("Элемент успешно удален из коллекции");
            }
        }
        if (!checkRemoving) {
            System.out.println("В коллекции нет элемента с таким значением id");
        }
        return checkRemoving;
    }

    /**
     * Returns the number of the components in this collection with specified character value.
     *
     * @param character character value
     * @return the number of the components in this collection with specified character value
     */
    public int countCharacter(String character) {
        int count = 0;
        for (Dragon dragon : vector) {
            if (dragon.getCharacter().equals(DragonCharacter.valueOf(character.toUpperCase()))) {
                count++;
            }
        }
        return count;
    }

    /**
     * Removes from this collection all of the elements that are lower than the specified element.
     *
     * @param d the element to be compared.
     */
    public void removeLower(Dragon d) {
        int i = 0;
        while (vector.get(0).compareTo(d) < 0 && i < vector.size()) {
            vector.removeElementAt(0);
            i++;
        }
    }

    /**
     * Tests if this collection has no components.
     *
     * @return true if this vector has no components; false otherwise
     */
    public boolean isEmpty() {
        return vector.isEmpty();
    }

    /**
     * Returns the maximum component of this collection.
     *
     * @return the maximum component of this collection
     */
    public Dragon maxElement() {
        Dragon maxElement = vector.get(0);
        for (Dragon dragon : vector) {
            if (dragon.compareTo(maxElement) > 0) {
                maxElement = dragon;
            }
        }
        return maxElement;
    }

    /**
     * Sorts the collection in descending order
     */
    public void reverseSort(Vector<Dragon> v) {
        Collections.sort(v);
        Collections.reverse(v);
    }

    /**
     * Updates the element of this collection with the specified if value.
     *
     * @param idValue id value
     */
    public boolean updateById(Long idValue, Dragon dragon) {
        boolean checkUpdating = true;
        for (int i = 0; i < vector.size() && checkUpdating; i++) {
            if (vector.get(i).getId().equals(idValue)) {
                dragon.setId(idValue);
                vector.set(i, dragon);
                checkUpdating = false;
            }
        }
        return checkUpdating;
    }

    /**
     * Removes the last element from this collection.
     */
    public void removeLast() {
        vector.removeElementAt(vector.size() - 1);
    }

    /**
     * Removes the element with the specified color value from this collection.
     *
     * @param color color value
     */
    public boolean removeByColor(Color color) {
        boolean flag = true;
        for (int i = 0; i < vector.size() && flag; i++) {
            if (vector.get(i).getColor().equals(color)) {
                vector.removeElementAt(i);
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Saves this collection to the file.
     */
    public void saveToFile() {
        fileManager.writeCollection(vector);
    }


}