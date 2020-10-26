/**
 * A hash-set based on closed-hashing with quadratic probing. Extends SimpleHashSet
 */
public class ClosedHashSet  extends SimpleHashSet implements SimpleSet{

    // ------------------------Data members ------------------------ :
    /**
     * array of  StringSpecialWrapper
     */
    private StringSpecialWrapper[] bucket = new StringSpecialWrapper[INITIAL_CAPACITY];


    // ------------------------ Constructors ------------------------ :
    /**
     *  A default empty constructor.
     */
    public ClosedHashSet(){
        super();
    }

    /**
     * Constructs a new, empty table with the specified load factors, and the default initial capacity(16).
     * @param upperLoadFactor float
     * @param lowerLoadFactor float
     */
    public ClosedHashSet(float upperLoadFactor, float lowerLoadFactor){
        super(upperLoadFactor,lowerLoadFactor);
    }

    /**
     * Data constructor - builds the hash set by adding the elements one by one.
     * @param data list of strings
     */
    public ClosedHashSet(java.lang.String[] data){
        super();
        for(String dataMember: data){
            this.add(dataMember);
        }
    }

    // ------------------------ Methods ------------------------ :


    // Override SimpleHashSet
    /**
     * The current capacity (number of cells) of the table.
     * capacity in class SimpleHashSet
     * @return The current capacity (number of cells) of the table.
     */
    @Override
    public int capacity() {
        // return number of cells
        return this.capacityNow;
    }


    // override SimpleSet methods:
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
        if (addNewVal(newValue)) {
            // update size
            addOneToSize();
            return true;
        }
        //
        return false;
    }


    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {
        // checks if searchVal is in bucket. since there could be a case where there are 2 strings with same
        // hashCodes(), and the first was deleted this function:
        // 1) check if we got the searched val
        // 2) if it was deleted - (if item.getStringToWrap == null, but object itself isn't bull) -continue
        // 3) if object is null - it could not be reached so definitely bucket doesn't contains searchVal
        //
        for (int i = 0; i < this.bucket.length; i++) {
            // call clamp with relevant formula, given in ex'.
            int hash = getHash(searchVal);
            int currentIndex = clamp((indexForClamp(hash,i)));
            // if bucket in relevant place is null - return false
            if (this.bucket[currentIndex] == null) {
                return false;
            } if (this.bucket[currentIndex].getStringToWrap()== null) {
                continue;
            }if (this.bucket[currentIndex].getStringToWrap().equals(searchVal)) {
                return true;
            }
        } // if was not found
        return false;
    }


    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (!contains(toDelete)){
            return false;
        }
        int hash = getHash(toDelete);
        for (int i = 0; i < this.bucket.length; i++) {
            // call clamp with relevant formula, given in ex'.
            int currentIndex = clamp((indexForClamp(hash,i)));
            // if this place in bucket or value is non - it's not what we're looking for to delete- continue
            if ( (this.bucket[currentIndex] == null) ||
                    (this.bucket[currentIndex].getStringToWrap() == null) ) {
                continue;
            }// if it exists and equal to the string we want to delete - delete the string value
            if (this.bucket[currentIndex].getStringToWrap().equals(toDelete)) {
                // mark this object as deleted = his value is now null
                this.bucket[currentIndex].setStringToWrap(null);
                //update size -
                removeOneFromSize();
                // check if need to shrink bucket -
                if (isSmallerThanCapacity()) {// if removing item caused ratio to be under lowerLoadFactor
                    shortenBucket(); // now bucket is extended
                }
                return true;
            }
        } // if was not found, so cannot be deleted
        return false;
    }



    /**
     * function extends bucket and rehashes all items to new bucket.
     */
    private void extendBucket(){
        enlargeCapacityTimes(this.BUCKET_POWER_CHANGE_VALUE);
        // copy all items in bucket to temp bucket
        // create array in length  new capacity
        this.bucket = reHashing(this.bucket, capacity());
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



    /**
     * rehashing
     * @param fromBucket the initial bucket we wish to re hash
     * @param toCapacity the new capacity of the 'toBucket'
     * @return 'toBucket' the new hashtable after rehashing all values
     */
    private StringSpecialWrapper[] reHashing(StringSpecialWrapper[] fromBucket, int toCapacity){
        StringSpecialWrapper[] toBucket= new StringSpecialWrapper[toCapacity];
        for (StringSpecialWrapper item: fromBucket){
            // if item is null or deleted - there's no need to re-hash a non- existing object - continue
            if ((item == null) || (item.getStringToWrap() == null) ) {
                continue;
            } // if item and string exists - re hash it.
            int itemHash = item.getStringToWrap().hashCode();
            for (int i = 0; i < this.bucket.length; i++) {
                // call clamp with relevant formula, given in ex'
                    // if object is null or it's value is null
                    if (toBucket[clamp(indexForClamp(itemHash, i))] == null) {
                        //if the array in the returned index is empty - place new val there
                        toBucket[clamp(indexForClamp(itemHash, i))] = item;
                       break;
                    }
                }
            }// after adding all
        return toBucket;
    }

    // other methods :
    /**
     *  finds number according to iteration to put in clamp function
     * @param index string.hashcode
     * @param i - the iteration
     * @return value calculated according to given formula
     */

    private int indexForClamp(int index, int i){
        return (index + (i + i * i) / 2);
    }


    /**
     * after checking a string should add into bucket, add to bucket in an empty place
     * @param newValue string
     * @return true if value was added to bucket. notice it would always be true according to clamp
     * algorithm
     */
    private boolean addNewVal(String newValue) {
        // index is hashcode() of newValue
        int hash = getHash(newValue);
        for (int i = 0; i < bucket.length; i++) {
            // call clamp with relevant formula, given in ex'.
            // if the place of this index is null or the value of this object is null- add new val to bucket
            int afterClamp = clamp(indexForClamp(hash,i));
            if  (this.bucket[afterClamp] == null) {
                this.bucket[afterClamp] = new StringSpecialWrapper(newValue);
                return true;
            }
            if (this.bucket[afterClamp].getStringToWrap() == null) {
                //if the array in the returned index is empty - place new val there
                this.bucket[afterClamp].setStringToWrap(newValue);
                return true;
            }
        }return false; // according to the ex' this formula mandates a valid result after o(n)
    }


    /**
     * return the hashcode for a given String
     * @param value the given string
     * @return return the hashcode for a given String
     */
    private int getHash(String value) {
        return value.hashCode();
    }




}
