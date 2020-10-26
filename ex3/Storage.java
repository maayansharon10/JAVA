import oop.ex3.spaceship.Item;

import java.util.HashMap;
import java.util.Map;

import static oop.ex3.spaceship.ItemFactory.createAllLegalItems;

public abstract class Storage {

    //magic numbers :

    private Item[] validItems = createAllLegalItems();
    protected Map<String, Integer> storageMap;
    private int capacity;
    protected int takenCapacity;

    // ---------------Constructor ---------------
    public Storage(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity is a non negative int");
        }
        this.capacity = capacity;
        this.storageMap = new HashMap<String, Integer>();
        this.takenCapacity = 0;
    }

// ---------------AddItem  related functions---------------

    /**
     * adding an item in Storage
     *
     * @param item - the item of type Item we wish to add
     * @param n    - the quantity of item in int
     * @return int - indicates whether action was succesful of not
     */
    public abstract int addItem(Item item, int n);


    /**
     * this function checks the preecondition for adding to a storage.
     * return 0 if n=0,
     * return -1 if n is negative, item is not valid, locker has not enough space for n*items
     *
     * @param item of type item we wish to add
     * @param n    the amount we wish to add it
     * @return -1 if n is negative, or item is invalid, addition failed or if locker doesn't have enough
     * space 0 if n = 0 and item is valid
     * 1 otherwise
     */
    protected int addItemCheckPreConditions(Item item, int n) {

        if ((n < 0) || (!itemIsValid(item))) {
            // if n is negative, or item is invalid, addition failed
            System.out.println("Error: Your request cannot be completed at this time." +
            " Problem: no room for "+n+" items of type "+item.getType());
            return -1;
        }
        if (n == 0) { // adding 0*items is a success but doesn't change locker status
            return 0;
        }
        if (!canAddAmountToStorage(item.getVolume() * n, getAvailableCapacity())) {
            // if locker doesn't have enough space
            System.out.println("Error: Your request cannot be completed at this time." +
            " Problem: no room for "+n+" items of type "+item.getType());
            return -1;
        }
        return 1;
    }


    /**
     * return true if item is valid, false otherwise
     *
     * @param item of type item
     * @return true if item is valid, false otherwise
     */
    private boolean itemIsValid(Item item) {
        for (int i = 0; i < this.validItems.length; i++) {
            if (validItems[i].getType().equals(item.getType())) {
                return true;
            }
        }
        return false;
    }


    /**
     * return true if amount can add to locker, false otherwise
     *
     * @param amount int
     * @return true if amount can be added to storage, false otherwise
     */
    private boolean canAddAmountToStorage(int amount, int availableCapacity) {

        return (amount <= availableCapacity);
    }


    /**
     * assuming there's enough place for n*items in storage.
     * update inventory and return -1.
     *
     * @param item item we wish to add
     * @param n    the amount of item we wish to add
     */
    protected void addAmountToStorage(Item item, int n) {
        if (n == 0) {
            return;
        }
        if (isItemInInventory(item)) { // if item already exist in locker
            int oldAmount = this.storageMap.get(item.getType());
            this.storageMap.put(item.getType(), oldAmount + n);

        }//if item doesn't exist in locker
        this.storageMap.put(item.getType(), n);
        setTakenCapacity(item.getVolume() * n);
    }


    protected boolean isItemInInventory(Item item) {
        return this.storageMap.containsKey(item.getType());
    }
// ---------------getItemCount related functions---------------


    /**
     * Returns the number of Items of type 'type' the locker contains.
     *
     * @param type - a string
     * @return int the number of Items of type 'type' the locker contains.
     */
    public int getItemCount(String type) {

        // check object of type 'type' exist in this locker
        if (this.storageMap.get(type) == null) { // if item doesn't exists in locker
            return 0;
        } else {
            return (this.storageMap.get(type));
        }
    }

    // ---------------getInventory related functions---------------

    public Map<String, Integer> getInventory() {

        return new HashMap<String, Integer>(this.storageMap);

    }


    // ---------------getCapacity related functions---------------

    /**
     * Returns the locker’s total capacity.
     *
     * @return int the locker’s total capacity.
     */
    public int getCapacity() {
        return this.capacity;
    }
    // ---------------getAvailableCapacity related functions---------------

    /**
     * Returns the locker’s available capacity,
     * i.e. how many storage units are unoccupied by Items
     *
     * @return int - the locker’s available capacity
     */
    public int getAvailableCapacity() {
        return this.getCapacity() - this.getTakenCapacity();
    }

    /**
     * change taken capacity. if param is a positive int - add to taken capacity
     * if negative int - remove from taken capacity
     *
     * @param addition int
     */
    protected void setTakenCapacity(int addition) {
        this.takenCapacity += addition;
    }

    /**
     * return this.takenCapacity
     *
     * @return return this.takenCapacity
     */

    private int getTakenCapacity() {
        return this.takenCapacity;
    }


}
