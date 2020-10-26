import oop.ex3.spaceship.*;

/**
 * LongTermStorage item is a type of Storage.
 * it can contain different types of items. Each of these items have a unique identifying type,
 * All items of the same type take up the same amount of storage
 * units in the LongTermStorage. Storage units are positive integers.

 * it has a capacity of 1000 storage units (which cannot be changed.
 *
 */


public class LongTermStorage extends Storage {

    /**
     * This constructor initializes a Long-Term Storage object
     */
    public LongTermStorage() {
        super(1000);
    }

    /**
     * This method adds n Items of the given type to the longterm storage unit.
     * If the action is successful, this method should return 0.
     * If n Items cannot be added to the locker at this time, no Items should be added,
     * the method should return -1,
     * and the following message should be printed to System.out.println:
     * Error: Your request cannot be completed at this time. Problem: no room for n Items of type type
     *
     * @param item - object of type item
     * @param n    positive int
     * @return int 0, -1,
     */
    public int addItem(Item item, int n) {
        // if failed -
        // print message and return -1
        int preConditions = this.addItemCheckPreConditions(item, n);
        if (preConditions == -1) {
            //if n is negative, or item is invalid, addition failed or if locker doesn't have enough space
            return -1;
        }
        if (preConditions == 0) { // adding 0*items is a success but doesn't change locker status
            return 0;
        }
        // if n>0, item is valid, locker has enough space for n*items :
        this.addAmountToStorage(item, n);
        return 0;
    }


    /**
     * This method resets the long-term storage’s inventory (i.e. after
     * it is invoked the inventory does not contain any Item).
     */
    public void resetInventory() {
        this.storageMap.clear();
        this.takenCapacity = 0;
    }


}
