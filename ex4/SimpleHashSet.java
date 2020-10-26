/**
 * An abstract class implementing SimpleSet.
 * A superclass for implementations of hash-sets implementing the SimpleSet interface.
 */
public abstract class SimpleHashSet implements SimpleSet {

    // ------------------------Data members ------------------------ :
    // CONSTANTS
    /**
     * Describes the higher load factor of a newly created hash set - default value
     */
    protected static float DEFAULT_HIGHER_CAPACITY = 0.75f;

    /**
     * Describes the lower load factor of a newly created hash set - default value
     */
    protected static float DEFAULT_LOWER_CAPACITY = 0.25f;

    /**
     * Describes the capacity of a newly created hash set - default value
     */
    protected static int INITIAL_CAPACITY = 16;

    /**
     * initial size value
     */
    private int INITIAL_SIZE = 0;

    /**
     * when resizing multiply or divide by this int
     */
    protected int BUCKET_POWER_CHANGE_VALUE = 2;

    // OTHER DATA MEMBERS:

    /**
     * Describes the higher load factor of a newly created hash set
     */
    private float upperLoadFactor;

    /**
     * Describes the lower load factor of a newly created hash set
     */
    private float lowerLoadFactor;

    /**
     *Describes the current capacity (number of cells) of the table.
     */
    protected int capacityNow;

    /**
     * Describes the number of elements currently in the set
     */
    protected int size;

    // ------------------------ Constructors ------------------------ :
    /**
     * Constructs a new hash set with the default capacities given in DEFAULT_LOWER_CAPACITY and
     * DEFAULT_HIGHER_CAPACITY
     */
    SimpleHashSet(){
        this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
        this.capacityNow = INITIAL_CAPACITY;
        this.size = this.INITIAL_SIZE;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY
     * @param upperLoadFactor float
     * @param lowerLoadFactor float
     */
    SimpleHashSet(float upperLoadFactor, float lowerLoadFactor){
        this.upperLoadFactor = upperLoadFactor;
        this.lowerLoadFactor = lowerLoadFactor;
        this.capacityNow = INITIAL_CAPACITY;
        this.size = this.INITIAL_SIZE;
    }


    // ------------------------ Methods ------------------------ :
    // API methods:
    /**
     *
     * @return int
     */
    abstract int capacity();

    /**
     * Clamps hashing indices to fit within the current table capacity
     * (see the exercise description for details)
     * @param index string.hashcode
     */
    protected int clamp(int index){

        return (index & (capacity() - 1));
    }

    protected float getLowerLoadFactor(){
        return this.lowerLoadFactor;
    }


    protected float getUpperLoadFactor(){
        return this.upperLoadFactor;
    }


    // override simple set methods:
    /**
     * The number of elements currently in the set
     * @return The number of elements currently in the set
     */
    @Override
    public int size() {
        return this.size;
    }


    // additional methods:

    /**
     * return true if surpass the upper load factor so need to make table larger
     * @return true if surepass capacity, false otherwise
     */
    protected boolean doesSurpassCapacity(){
        return ( ( ( (size()+1) /(float)capacity() ) ) ) > this.getUpperLoadFactor();

    }

    /**
     * return  true if result smaller than lower load factor so need to make table shorter
     * @return true id smaller than capacity, false otherwise
     */
    protected boolean isSmallerThanCapacity(){
        return ( ( (size()/(float)capacity()) ) < this.getLowerLoadFactor() ) && (capacity() > 0) ;
    }


    /**
     *  multiplying the current capacity with the param value
     * @param value int
     */
    protected void enlargeCapacityTimes(int value){
        this.capacityNow *= value;
    }


    /**
     * diving the current capacity in the param value, as long possible (as long result is a positive int)
     * @param value positive int
     */
    protected void shrinkCapacityTimes(int value){
        // capacity can only be positive int, so can never divide capacity if table consists one cell.
        if (capacity()<this.BUCKET_POWER_CHANGE_VALUE) {
            return;
        }this.capacityNow /= value;
    }


    /**
     * add one to size
     */
    protected void addOneToSize(){
        this.size += 1;
    }


    /**
     * remove one from size
     */
    protected void removeOneFromSize(){
        this.size -= 1;
    }

}
