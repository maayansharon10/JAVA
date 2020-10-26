import oop.ex3.spaceship.*;

/**
 * a class represent a locker with a certain capacity.
 *
 * A locker can contain different types of items. Each of these items have a unique identifying type, such
 * as -baseball bat- or -helmet, size 3-. All items of the same type take up the same amount of storage
 * units in the locker. Storage units are positive integers.
 * Each locker has a capacity, which is the total amount of storage units it can hold. The capacity of a
 * locker is a non-negative integer1
 * , and cannot be changed once it has been set. Each item can only be added
 * in full - half an item, or any other percentage of an item, cannot be stored in a locker.
 * If items of a specific type take up more than 50% of the storage
 * units of a specific locker, some of them are automatically moved to the long-term storage.
 */
public class Locker extends Storage {

    // DATA MEMBERS
    protected static LongTermStorage longTermStorage = new LongTermStorage();//static member
    // possible clashing items
    private Item football = ItemFactory.createSingleItem("football");
    private Item baseball = ItemFactory.createSingleItem("baseball bat");

    // magic numbers :
    private String MSG_ADD_ITEM_GO_TO_LTS = "Warning: Action successful, but has caused items to be moved " +
            "to storage";




    // ---------------Constructor ---------------

    /**
     * . This constructor initializes a Locker object with the given capacity
     *
     * @param capacity int
     */
    public Locker(int capacity) {
        super(capacity);
    }


    // ---------------get/set data members related functions---------------

    /**
     * return the long term storage data member
     *
     * @return return the long term storage data member
     */
    protected LongTermStorage getLongTermStorage() {
        return longTermStorage;
    }

    // ---------------AddItem  related functions---------------

    /**
     * This method adds n Items of the given type to the locker.
     * <p>
     * If the addition is successful and doesn’t cause Items to be moved to long-term storage, this
     * method should return 0.
     * <p>
     * If n Items cannot be added to the locker at this time, no Items should be added,
     * the method should return -1, and the following message should be printed to System.out.println:
     * Error: Your request cannot be completed at this time. Problem: no room for n items of type type .
     * <p>
     * If this action causes n∗Items to be moved to long-term storage and it can accommodate all n∗Items,
     * the method should return 1, and the following message should be printed to System.out.println:
     * Warning: Action successful, but has caused items to be moved to storage.
     * <p>
     * If this action requires Items to be moved to long-term storage,
     * but it doesn’t have room to accommodate all n∗Items, then no Items should be added,
     * the method should return -1, and the following message should be printed
     * to System.out.println: Error: Your request cannot be completed at this time. Problem: no room
     * for n∗Items of type type .
     *
     * @param item - an object of type item.
     * @param n    - int, amount of items we wish to add.
     * @return 0, 1, -1 as discribed above.
     */
    public int addItem(Item item, int n) {

        if (this.lockerPreConditions(item) == -2) { //check if football or baseball clashing
            return -2;
        }
        int preConditions = this.addItemCheckPreConditions(item, n);
        // if n is negative,or item is invalid, addition failed or if locker doesn't have enough space
        //return 0 if n==0 adding 0*items is a success but doesn't change locker status:
        if (preConditions == -1 || preConditions == 0) {
            return preConditions;
        }
        // if n>0, item is valid, locker has enough space for n*items :
        if (shouldSomeMoveToLongTerm(item, n)) {  //check if some of item type should move to longTerm
            // try to move to longTerm
            int numberOfItemsToKeep = itemsToKeepInLocker(item, n);
            int numberOfItemsToSend = n + this.getItemCount(item.getType()) - numberOfItemsToKeep;
            if (getLongTermStorage().addItem(item, numberOfItemsToSend) == 0) {
                // if addition to longTerm was successful
                this.addAmountToStorage(item, numberOfItemsToKeep); // add items to keep to locker
                System.out.println("Warning: Action successful, but has caused items to be moved to storage");
                return 1;
            } // add item to long term failed, msg was printed to screen(through longterm.add item)
            return -1;
        } //should not move to long term and passed all pre conditions - add as is to locker
        this.addAmountToStorage(item, n); // add items to keep to locker
        return 0;
    }


    /**
     * return -2 if there's an attempt to add a football when a baseball is in locker and vice-versa.
     * return 0 if there's no clash
     *
     * @param item - of type item we wish to add
     * @return -2 if there's an attempt to add a football when a baseball is in locker and vice-versa.
     * or 0 if there's no clash
     */
    private int lockerPreConditions(Item item) {
        if (item.getType().equals(this.football.getType())) {
            // check if there's at least one baseball in locker
            if (0 < this.getItemCount(baseball.getType())) {
                System.out.println("Error: Your request cannot be" +
                        "completed at this time. Problem: the locker cannot contain items of type "
                                + item.getType( )+ " as it contains a contradicting item");
                return -2;
            }
        }
        if (item.getType().equals(this.baseball.getType())) {
            // check if there's at least one baseball in locker
            if ((0 < this.getItemCount(football.getType()))) {
                System.out.println("Error: Your request cannot be" +
                        "completed at this time. Problem: the locker cannot contain items of type "
                        + item.getType( )+ " as it contains a contradicting item");
                return -2;
            }
        }
        return 0; // it is not a football or a baseball, so there's no clash
    }


    /**
     * return true if some of the items should move to long storage, false otherwise
     *
     * @param item we wish to add
     * @param n    the amount of this item we wish to add
     * @return true if some of the items should move to long storage, false otherwise
     */
    private boolean shouldSomeMoveToLongTerm(Item item, int n) {
        int currentAmountOfType;

        if (!isItemInInventory(item)) {
            currentAmountOfType = 0;
        } else {
            currentAmountOfType = this.storageMap.get(item.getType()) * item.getVolume();
            //current amount in locker
        }
        int addedAmount = item.getVolume() * n;
        // calc the current amount of item  +the amount we wish to add.
        // check if it is more than 50% of locker's capacity
        return (isSurpassThreshold(currentAmountOfType + addedAmount, 50));
    }


    /**
     * return if amount is more than the threshold precentage of locker's capacity
     *
     * @param amount              int
     * @param thresholdPrecentage - int [0 =0% , 100 =100%]
     * @return true if amount takes up more than the precentage of locker's capacity ,false otherwise
     */
    private boolean isSurpassThreshold(int amount, int thresholdPrecentage) {
        double precentage = (double) ((amount * 100) / this.getCapacity());
        return (precentage > thresholdPrecentage);
    }


    /**
     * return the number of items which should be kept in locker
     *
     * @param item of type item wishing to check
     * @param n    - number of times wishing to add this item
     * @return int - number of items which should be kept in locker
     */
    private int itemsToKeepInLocker(Item item, int n) {
        int itemsCalc = (int) ((0.2 * this.getCapacity()) / item.getVolume());
        return (int) Math.floor(itemsCalc);
    }


    // ---------------removeItem  related functions---------------

    /**
     * This method removes n Items of the type type from the locker.
     * If the action is successful, this method should return 0.
     * In case there are less than n Items of this type in the locker,
     * no Items should be removed,
     * the method should return -1,
     * and the following message should be printed to System.out.println:
     * Error: Your request cannot be completed at this time.
     * Problem: the locker does not contain n items of type type .
     * In case n is negative, no Items should be removed, the method should return -1,
     * and the following message should be printed to System.out.println:
     * Error: Your request cannot be completed at this time.
     * Problem: cannot remove a negative number of items of type type .
     *
     * @param item - an object of type item
     * @param n    - int
     * @return int
     */
    public int removeItem(Item item, int n) {
        //check if n=0: return 0
        if (n == 0) { //
            return 0;
        }
        //check if n<0: print negative n msg and return -1
        if (n < 0) { // removing 0*items is a success but doesn't change locker status
            System.out.println("Error: Your request cannot be completed at this " +
                    "time. Problem: cannot remove a negative number of items of type " + item.getType());
            return -1;
        }
        // if locker doesn't have enough items of this type to remove
        if (!canRemoveItemFromStorage(item, n)) {
            System.out.println("Error: Your request cannot be completed at this time. " +
                    "Problem: the locker does not contain " + n +" items of type "+ item.getType());
            return -1;
        }
        //item is valid, n>0,  locker has enough of this type to remove
        removeAmountfromlocker(item, n);

        return 0;  // action was successful
    }


    /**
     * assuming at least n items exist in locker .
     * update inventory to screen and return -1.
     *
     * @param item item we wish to add
     * @param n    the amount of item we wish to add
     */
    private void removeAmountfromlocker(Item item, int n) {
        int oldAmount = this.storageMap.get(item.getType());
        this.storageMap.put(item.getType(), oldAmount - n);
        this.setTakenCapacity(-(item.getVolume() * n));
    }


    /**
     * return true if amount of type can be remove from locker, false otherwise
     *
     * @param item item to remove
     * @param n    number to items we wish to remove
     * @return true if amount of typf can be remove from locker, false otherwise
     */
    private boolean canRemoveItemFromStorage(Item item, int n) {
        if (isItemInInventory(item)) {
            return (this.storageMap.get(item.getType()) >= n);
        }
        return false;
    }


    // ---------------getInventory  related functions---------------
    // exists in super


    // ---------------getCapacity  related functions---------------
    // in super


    // ---------------getAvailableCapacity  related functions---------------
    // exists in super

    // ---------------getItemCount related functions---------------

}


