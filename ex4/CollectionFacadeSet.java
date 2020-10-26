

/**
 * Wraps an underlying Collection and serves to both simplify its API and give it a common type with
 * the implemented SimpleHashSets.
 * implements SimpleSet
 */
public class CollectionFacadeSet implements SimpleSet {

    // ------------------------Data members ------------------------ :
    /**
     * a collection ehich can hold items of type String
     */
    protected java.util.Collection<java.lang.String> collection;

    /**
     * Describes the number of elements currently in the set
     */
    private int size;

    // ------------------------ Constructors ------------------------ :
    /**
     * Creates a new facade wrapping the specified collection
     * @param collection1 type of collection
     */
    CollectionFacadeSet(java.util.Collection<java.lang.String> collection1){
        collection = collection1;
        this.size = collection.size();
    }

    // ------------------------ Methods ------------------------ :

    // over ride simple set:
    /**
     * Add a specified element to the set if it's not already in it.
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    @Override
    public boolean add(String newValue) {

        if (this.collection.contains(newValue)) {
            return false;  // if newValue already in collection, it cannot be added
        }
        //if newValue is not in collection:
        this.collection.add(newValue); // add new value
        updateSizeAddition(); // update collection size
        return true;
    }


    /**
     * Look for a specified value in the set.
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    @Override
    public boolean contains(String searchVal) {

        return (this.collection.contains(searchVal));
    }


    /**
     * Remove the input element from the set.
     * @param toDelete Value to delete
     * @return True iff toDelete is found and deleted
     */
    @Override
    public boolean delete(String toDelete) {
        if (this.collection.contains(toDelete)) { // if object was found
            this.collection.remove(toDelete); //delete object
            updateSizeDeletion(); // update collection size
            return true;
        }return false;
    }

    /**
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.size;
    }


    // other methods :

    /**
     * update size of set after adding an item (+1)
     */
    private void updateSizeAddition(){
        this.size += 1;
    }

    /**
     * update size of set after adding an item (-1)
     */
    private void updateSizeDeletion(){
        this.size -= 1;
    }
}
