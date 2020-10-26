/**
 * a hash-set based on chaining. Extends SimpleHashSet. Note: the capacity of a chaining based hash-set
 * is simply the number of buckets (the length of the array of
 * lists)
 */
public class OpenHashSet extends SimpleHashSet implements SimpleSet {


    // ------------------------Data members ------------------------ :
    private LinkedListWrapper[] bucket;

    // ------------------------ Constructors ------------------------ :
    /**
     * A default constructor. Constructs a new, empty table with default initial capacity (16),
     * upper load factor (0.75) and lower load factor (0.25).
     */
    public OpenHashSet(){
        super();
        this.size = 0;
        this.bucket = new LinkedListWrapper[capacity()];
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity (16).
     * @param upperLoadFactor - The upper load factor of the hash table.
     * @param lowerLoadFactor - The lower load factor of the hash table.
     */
    public OpenHashSet(float upperLoadFactor,float lowerLoadFactor){
        super(upperLoadFactor, lowerLoadFactor);
        this.size = 0;
        this.bucket = new LinkedListWrapper[capacity()];
    }


    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * Duplicate values should be ignored. The new table has the default values of initial capacity (16),
     * upper load factor (0.75), and lower load factor (0.25).
     * @param data - Values to add to the set
     */
    public OpenHashSet(java.lang.String[] data){
        super();
        this.size = 0;
        this.bucket = new LinkedListWrapper[capacity()];
        for(String dataMember: data){
            add(dataMember);
        }
    }

    // ------------------------ Methods ------------------------ :

    // overrides simple set

    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        // go through all linked lists in bucket and check it search val is in there
        for (LinkedListWrapper item : this.bucket){
            if (item == null){
                continue;
            }
            if (item.contains(searchVal)){
                return true;
            }
        } // went through all linked lists and searchVal wasn't there - bucket doesn't contains it
        return false;
    }

    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        // if not in bucket -
        if (!contains(toDelete)) {
            return false;
        }// if in bucket -
        deleteValue(toDelete);
        removeOneFromSize(); //update size
        // check if need resizing :
        if (isSmallerThanCapacity()) {// if adding it would be under lowerLoadFactor
            // example - in default if there're more than 12 items
            shortenBucket(); // now bucket is extended
        } return true;
    }


    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {
        if (this.contains(newValue)) { //if value already in bucket - cannot be added
            return false;
        }// if not in bucket :
        if (doesSurpassCapacity()) {// if adding it would surpass upperLoadFactor
            // example - in default if there're more than 12 items
            extendBucket(); // now bucket is extended
        }// if not surpassing load factor or after extended bucket -
        // add new value to bucket according to valid index
        addNewVal(newValue);
        addOneToSize(); //update size
        return true;
    }


    // override simple hash set

    /**
     * The current capacity (number of cells) of the table.
     * capacity in class SimpleHashSet
     * @return The current capacity (number of cells) of the table.
     */
    @Override
    int capacity() {
        return this.capacityNow;
    }


    /**
     * The number of elements currently in the set
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.size;
    }


    // other methods:
    /**
     * function extends bucket and rehashes all items to new bucket.
     */
    private void extendBucket(){
        enlargeCapacityTimes(2);
        // copy all items in bucket to temp bucket
        // create array in length  new capacity
        this.bucket = reHashing(this.bucket, capacity());
    }

    /**
     * rehashing
     * @param fromBucket the initial bucket we wish to re hash
     * @param toCapacity the new capacity of the 'toBucket'
     * @return 'toBucket' , the new table after rehashing
     */
    private LinkedListWrapper[] reHashing(LinkedListWrapper[] fromBucket, int toCapacity) {
        LinkedListWrapper[] toBucket = new LinkedListWrapper[toCapacity];
        for (LinkedListWrapper subLinkedList : fromBucket) {
            // add all items in the linked lists to the new bucket (toBucket)
            if (subLinkedList == null) {
                continue;
            }
            for (String item : subLinkedList.getLinkedList()) {
                // call clamp with relevant formula, given in ex'.
                int currentIndex = clamp(item.hashCode());
                if (toBucket[currentIndex] == null) {
                    toBucket[currentIndex] = new LinkedListWrapper();
                }
                toBucket[clamp(item.hashCode())].add(item);
            }
        }       // after adding all
        return toBucket;
    }


    /**
     * adds new val to bucket after all requirements? are met.
     * @param newValue string to add
     */
    private void addNewVal(String newValue) {
        // index is hashcode() of newValue
        if (this.bucket[clamp(newValue.hashCode())] == null){
            this.bucket[clamp(newValue.hashCode())] = new LinkedListWrapper();
        }
        this.bucket[clamp(newValue.hashCode())].add(newValue);

    }

    /**
     *  deleting value to delete from bucket
     * @param toDelete string
     */
    private void deleteValue(String toDelete) {
        // index is hashcode() of newValue
        this.bucket[clamp(toDelete.hashCode())].delete(toDelete);
    }


    /**
     * shorten bucket and rehashes all items to new shorter bucket.
     */
    private void shortenBucket(){
        shrinkCapacityTimes(this.BUCKET_POWER_CHANGE_VALUE);
        // copy all items in bucket to temp bucket
        // create array in length  new capacity
        this.bucket = reHashing(this.bucket, capacity());
    }


}
