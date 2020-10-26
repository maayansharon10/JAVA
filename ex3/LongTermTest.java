//maayan
import static org.junit.Assert.*;
import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;
import java.util.HashMap;
import java.util.Map;


/**
 * This file should be able to test any possible implementation a LongTermStorage class
 * (to be provided in a Locker.java file) which implements the functionalities
 * described in the Locker actions section of this exercise.
 */

public class LongTermTest {


    private Map<String, Integer> testMap;

    private LongTermStorage longTerm = new LongTermStorage();
    // items;
    private Item item0 = ItemFactory.createSingleItem("baseball bat");
    private Item item1 = ItemFactory.createSingleItem("helmet, size 1");
    private Item item2 = ItemFactory.createSingleItem("helmet, size 3");
    private Item item3 = ItemFactory.createSingleItem("spores engine");
    private Item item4 = ItemFactory.createSingleItem("football");

    private final int LONG_TERM_CAPACITY =1000;
    private final int METHOD_SUCCESS = 0 ;
    private final int METHOD_FAIL = -1 ;
    private final int NEGATIVE_INT = -1;



    /**
     * reset long term object before each test.
     */
    @Before
    public void resetLongTerm() {
        longTerm = new LongTermStorage();
    }


    /**
     * test for the constructor .
     */
    @Test
    public void testConstructor() {
        assertNotNull("object is null- constructor failed to create object", longTerm);
        assertEquals("constructor test", LONG_TERM_CAPACITY, longTerm.getCapacity());
    }


    /**
     * test Add item - in an undirect way - not by return value,
     * check that method change other data members.
     */
    @Test
    public void testAddItemUndirected() {
        // check add item actually changed the state of long term :
        // before addition, long term is actually empty
        assertEquals("before addition, long term is actually empty", this.LONG_TERM_CAPACITY,
                longTerm.getAvailableCapacity());
        longTerm.addItem(item0, 1);
        //after adding an item from a single type capacity changes
        assertEquals("after addition, long term is not empty",
                this.LONG_TERM_CAPACITY - item0.getVolume(), longTerm.getAvailableCapacity());
        //after adding an item from a single type capacity changes
        longTerm.addItem(item1, 3);
        assertEquals("after addition, long term is not empty",
                this.LONG_TERM_CAPACITY - item0.getVolume() - item1.getVolume() * 3, longTerm.getAvailableCapacity());
    }


    /**
     * test add item when method suppose to succeed.
     */
    @Test
    public void testAddItemSuccess() {
        // adding single item to long term
        assertEquals("adding item to longterm", this.METHOD_SUCCESS,longTerm.addItem(item0, 1));
        // adding a few items to long term
        assertEquals("adding few items to longterm", this.METHOD_SUCCESS, longTerm.addItem(item0, 10));
        // adding another item from different type to same long term
        assertEquals("adding another item from different type to longterm failed",
                this.METHOD_SUCCESS, longTerm.addItem(item1, 0));
    }


    /**
     * test add item when adding to a full storage - one type of item.
     */
    @Test
    public void testAddItemFullStorage1() {
        // adding items from same type to long term so it takes 100% capacity
        assertEquals("adding item to longterm", this.METHOD_SUCCESS, longTerm.addItem(item3, 100));
    }


    /**
     * test add item when adding to a full storage - different types of items.
     */
    @Test
    public void testAddItemFullStorage2() {
        // adding items from different type to long term so it takes 100% capacity
        longTerm.addItem(item3, 50);
        assertEquals("adding item to longterm", this.METHOD_SUCCESS, longTerm.addItem(item2, 100));
        //(should be exactly 100% capacity)
    }


    /**
     * test add item when adding zero items.
     */
    @Test
    public void testAddItemZero() {
        // adding 0*items to storage when storage is empty
        assertEquals("adding 0*items to storage when storage is empty",
                this.METHOD_SUCCESS, longTerm.addItem(item3, 0));
        // adding 0*items to storage when storage is not empty
        longTerm.addItem(item3, 1);
        assertEquals("adding 0*items to storage when storage is not empty",
                this.METHOD_SUCCESS, longTerm.addItem(item3, 0));
        longTerm.addItem(item3, 99);
        // adding 0*items to storage when storage is full
        assertEquals("adding 0*items to storage when storage is not empty",
                this.METHOD_SUCCESS, longTerm.addItem(item3, 0));
    }


    /**
     * test add item when adding one item which is more than locker's capacity.
     */
    @Test
    public void testAddItemFailOneItem() {
        // at once trying to add more than locker's capacity
        assertEquals("cannot add to longterm items with volume>longterm's capacity",
                this.METHOD_FAIL, longTerm.addItem(item3, 101));
    }


    /**
     * test AddItem Fail when a few items are added exceed 100% of capacity.
     */
    @Test
    public void testAddItemFailFewItems() {
        // something in longTerm, trying to add to it less than locker's capacity but over all exceeded.
        longTerm.addItem(item2, 100);
        assertEquals("trying to surpass longTerm's capacity",
                this.METHOD_FAIL, longTerm.addItem(item3, 60));

    }


    /**
     * test AddItem fail when adding a negative n.
     */
    @Test
    public void testAddItemNegativeFail() {
        // adding a negative number will faill
        assertEquals("adding a negative number should faill",this.METHOD_FAIL,
                longTerm.addItem(item0, this.NEGATIVE_INT));
    }


    /**
     * test resetting an empty map - stay the same
     */
    @Test
    public void testResetInventoryInitial() {
        // resetting an empty map - stay the same
        testMap = new HashMap<String, Integer>();
        longTerm.resetInventory();
        assertEquals(" resetting an empty map - stay the same", testMap, longTerm.getInventory());
    }


    /**
     * test ResetInventory after addItem, reset will make map empty
     */
    @Test
    public void testResetInventoryBasic() {
        testMap = new HashMap<String, Integer>();

        // after addItem, reset will make map empty
        longTerm.addItem(item0, 2);
        longTerm.addItem(item3, 4);
        longTerm.resetInventory();

        assertEquals("after reset inventory, map should be empty", testMap, longTerm.getInventory());
    }


    /**
     * test ResetInventory Result - check after addItem, reset will make map empty
     */
    @Test
    public void testResetInventoryResult() {

        // after addItem, reset will make map empty
        longTerm.addItem(item0, 2);
        longTerm.addItem(item3, 4);
        Map<String, Integer> beforeResetMap = longTerm.getInventory();
        longTerm.resetInventory();

        assertFalse("reset inventory,map before(when locker has items in it)," +
                        " and after reset are not the same",
                beforeResetMap == longTerm.getInventory());

        // reset update available capacity to 1000
        assertEquals("reset update available capacity to 1000", this.LONG_TERM_CAPACITY,
                longTerm.getAvailableCapacity());
    }


    /**
     * test GetItem - adding 0* item to empty locker- counter return same result
     */
    @Test
    public void testGetItemCountZero1() {
        // adding 0* item to empty locker- counter return same result
        longTerm.addItem(item0, 0);
        assertEquals("adding 0*item to empty locker ", 0,
                longTerm.getItemCount(item0.getType()));

        // adding 0* item to a not empty locker- counter return same result
        longTerm.addItem(item0, 3);
        longTerm.addItem(item0, 0);
        assertEquals("adding 0*item to a not empty locker ", 3,
                longTerm.getItemCount(item0.getType()));
    }


    /**
     * test get item count adding 0* item to a full locker- counter return same result
     */
    @Test
    public void testGetItemCountZero2() {
        // adding 0* item to a full locker- counter return same result
        longTerm.addItem(item3, 100);
        longTerm.addItem(item3, 0);
        assertEquals("adding 0*item to a full locker ", 100,
                longTerm.getItemCount(item3.getType()));
    }


    /**
     *  test get item count
     * item counter can recognize different items, if its' one item or mulitule of this type
     */
    @Test
    public void testGetItemCountBasic() {
        // item counter can recognize different items, if its' one item or mulitule of this type
        longTerm.addItem(item0, 1);
        longTerm.addItem(item2, 4);
        longTerm.addItem(item3, 10);

        assertEquals("item counter filed count one item", 1,
                longTerm.getItemCount(item0.getType()));
        assertEquals("item counter filed count one item", 4,
                longTerm.getItemCount(item2.getType()));
        assertEquals("item counter filed count one item", 10,
                longTerm.getItemCount(item3.getType()));
    }


    /**
     *  test get item count
     *  add items when capacity is full, couldn't add- counter stay the same
     */
    @Test
    public void testGetItemCountFullCap(){
        // add items when capacity is full, couldn't add- counter stay the same
        longTerm.addItem(item3,100); // storage is full
        longTerm.addItem(item3,1); // adding another item should fail
        assertEquals("item count doesn't change when lts is full",100,
                longTerm.getItemCount(item3.getType()));
    }


    /**
     * test GetItemCount
     * adding a negative n - counter stay the same
     */
    @Test
    public void testGetItemCountNegativeFail() {
        //  also adding a negative n - counter stay the same
        longTerm.addItem(item3,10); // storage is not empty
        longTerm.addItem(item3,this.NEGATIVE_INT); // adding negative amount of item should fail
        assertEquals("item count doesn't change when lts is full",10,
                longTerm.getItemCount(item3.getType()));
    }


    /**
     * test GetInventory - Initial
     * getting empty long term  - map stays empty
     */
    @Test
    public void testGetInventoryInitial() {
        // getting empty long term  - map stays empty
        testMap = new HashMap<String, Integer>();
        assertEquals("resetting empty long term  - map stays empty", testMap, longTerm.getInventory());
        //after adding 0* item of some type, map stays the same
        longTerm.addItem(item0, 0);
        assertEquals("after adding 0* item of some type, map stays the same",
                testMap, longTerm.getInventory());
    }


    /**
     * test get inventory basic action
     */
    @Test
    public void testGetInventoryBasic() {
        longTerm.addItem(item2, 4);
        longTerm.addItem(item3, 10);

        testMap = new HashMap<String, Integer>();
        testMap.put(item2.getType(), 4);
        testMap.put(item3.getType(), 10);

        assertEquals("getinventory represet current map after adding", testMap,
                longTerm.getInventory());
    }


    /**
     * test get inventory
     * after adding 0* items get inventory returns a map same as before
     */
    @Test
    public void testGetInventoryZero() {
        //after adding 0* items get inventory returns a map same as before
        longTerm.addItem(item2, 4);
        Map<String, Integer> testMap = longTerm.getInventory();
        longTerm.addItem(item3, 0);

        assertEquals("after adding 0*item, get inventory return the same map", testMap,
                longTerm.getInventory());

    }


    /**
     * test GetCapacity - Initial after creating
     */
    @Test
    public void testGetCapacityInitial() {
        //after creating
        assertEquals("capacity after creation", this.LONG_TERM_CAPACITY, longTerm.getCapacity());
    }


    /**
     * test GetCapacity -after adding an item
     */
    @Test
    public void testGetCapacityBasic() {
        //after adding an item
        longTerm.addItem(item0, 1);
        assertEquals("capacity aadding an item stay the same", this.LONG_TERM_CAPACITY,
                longTerm.getCapacity());
    }


    /**
     * test GetCapacity - after reset
     */
    @Test
    public void testGetCapacityAfterReset() {
        // after reset
        longTerm.resetInventory();
        assertEquals("capacity aadding an item stay the same", this.LONG_TERM_CAPACITY,
                longTerm.getCapacity());
    }


    /**
     * test GetAvailableCapacity - Initial available capacity is the same as capacity
     */
    @Test
    public void testGetAvailableCapacityInitial() {
        //initial available capacity is the same as capacity
        assertEquals("initial available capacity = capacity", longTerm.getCapacity(),
                longTerm.getAvailableCapacity());
    }


    /**
     * test GetAvailableCapacity - Zero
     * adding 0*item after initiating  or when long term with several items
     */
    @Test
    public void testGetAvailableCapacityZero() {
        // adding 0*item after initiating  doesn't effect available capacity
        longTerm.addItem(item3, 0);
        assertEquals("adding zero after initiating, available capacity = capacity",
                longTerm.getCapacity(), longTerm.getAvailableCapacity());

        //adding zero items to longTerm with several items doesn't effect available capacity
        longTerm.addItem(item3, 3);
        longTerm.addItem(item3, 0);
        assertEquals("adding zero after initiating, available capacity doesn't change",
                longTerm.getCapacity() - item3.getVolume() * 3, longTerm.getAvailableCapacity());
    }


    /**
     * test - GetAvailableCapacity - basic test
     */
    @Test
    public void testGetAvailableCapacityBasic1() {
        //one type of item, change capacity
        longTerm.addItem(item3, 3);

        assertEquals("available capacity change - adding one type of item",
                longTerm.getCapacity() - item3.getVolume() * 3, longTerm.getAvailableCapacity());

        // few types of items
        longTerm.addItem(item2, 10);
        assertEquals("available capacity change - adding a few types of items",
                longTerm.getCapacity() - item3.getVolume() * 3 - item2.getVolume() * 10,
                longTerm.getAvailableCapacity());
    }


    /**
     * test - GetAvailableCapacity - can be 0 when long term is full
     */
    @Test
    public void testGetAvailableCapacityFull() {
        // available capacity can be zero - when long term is full
        longTerm.addItem(item3, 100);
        assertEquals("when longTerm is full, available capacity is Zero", this.METHOD_SUCCESS,
                longTerm.getAvailableCapacity());
    }


    /**
     * test GetAvailableCapacity - when addition was failed
     */
    @Test
    public void testGetAvailableCapacityFailAddition() {
        // when addition was failed
        longTerm.addItem(item1, 3);
        int before = longTerm.getAvailableCapacity();
        longTerm.addItem(item3, 100); // this addition failed
        assertEquals("when item failed", before, longTerm.getAvailableCapacity());
    }


    /**
     * test - GetAvailableCapacity - adding a negative n doesn't change available capacity
     */
    @Test
    public void testGetAvailableCapacityAddingNegative() {
        //adding a negative n doesn't change available capacity
        longTerm.addItem(item1, 3);
        int before = longTerm.getAvailableCapacity();
        longTerm.addItem(item3, this.NEGATIVE_INT); // this addition failed
        assertEquals("when item failed", before, longTerm.getAvailableCapacity());

    }
}
