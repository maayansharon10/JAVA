// maayan


import static org.junit.Assert.*;

import oop.ex3.spaceship.Item;
import oop.ex3.spaceship.ItemFactory;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;


/**
 * This file should be able to test any possible implementation a Locker class
 * (to be provided in a Locker.java file) which implements the functionalities
 * described in the Locker actions section of this exercise.
 */
public class LockerTest {
    // Lockers

    private Locker locker1;
    private Locker locker2;
    private Locker locker3;

    private Item item0 = ItemFactory.createSingleItem("baseball bat");
    private Item item1 = ItemFactory.createSingleItem("helmet, size 1");
    private Item item2 = ItemFactory.createSingleItem("helmet, size 3");
    private Item item3 = ItemFactory.createSingleItem("spores engine");
    private Item item4 = ItemFactory.createSingleItem("football");

    private final int LARGE_CAPACITY =1000000;
    private final int METHOD_SUCCESS = 0 ;
    private final int METHOD_FAIL = -1 ;



    @Before
    public void resetLockers() {
        locker1 = new Locker(10);
        locker2 = new Locker(100);
        locker3 = new Locker(50);
    }


    /**
     * Reset the long term storage before each test
     */
    @Before
    public void resetLongTerm() {
        Locker.longTermStorage.resetInventory();
    }


    // ---------------Constructor ---------------

    /**
     * test for locker constructor and get capacity
     */
    @Test
    public void testLockerConstructor() {
        // check constructor create an object.
        assertNotNull("object is null- constructor failed to create object", locker1);
        assertNotNull("object is null- constructor failed to create object", locker2);
        assertNotNull("object is null- constructor failed to create object", locker3);
        // check positive int -
        assertEquals("capacity can be 50", 50, locker3.getCapacity());
        assertEquals("capacity can be 10", 10, locker1.getCapacity());
        assertEquals("capacity can be 100", 100, locker2.getCapacity());


    }


    @Test
    public void testConstructorExtremeCapacity() {

        // check capacity zero -
        locker1 = new Locker(0);
        assertEquals("capacity can be 0", this.METHOD_SUCCESS, locker1.getCapacity());
        // check a large int -
        locker2 = new Locker(this.LARGE_CAPACITY);
        assertEquals("capacity can be a large int", this.LARGE_CAPACITY, locker2.getCapacity());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testLockerConstructorException() {
        // check negative int
        locker1 = new Locker(-1);
    }

    // ---------------AddItem  related functions---------------

    /**
     * test AddItem - Contradicting Items no.1
     * check a football cannot be added when baseball in locker
     */
    @Test
    public void testAddItemContradictingItems1() {
        locker2.addItem(item0, 1);
        assertEquals("a football cannot be added when baseball in locker", -2,
                locker2.addItem(item4, 1));
    }

    /**
     * test AddItem - Contradicting Items no.2
     * check a baseball cannot be added when football in locker
     */
    @Test
    public void testAddItemContradictingItems2() {
        locker2.addItem(item4, 1);
        assertEquals("a football cannot be added when baseball in locker", -2,
                locker2.addItem(item0, 1));
    }


    /**
     * test AddItem - Contradicting Items no.3
     * check 0 baseball items cannot be added when football in locker
     */
    @Test
    public void testAddItemContradictingItems3() {
        locker2.addItem(item4, 1);
        assertEquals("a football cannot be added when baseball in locker", -2,
                locker2.addItem(item0, 0));
    }


    /**
     * test AddItem - Contradicting Items no.1
     * check 0 football items cannot be added when baseball in locker
     */
    @Test
    public void testAddItemContradictingItems4() {
        locker2.addItem(item0, 1);
        assertEquals("a football cannot be added when baseball in locker", -2,
                locker2.addItem(item4, 0));
    }


    @Test
    public void testAddItemBasic() {
        //check item was actually added to locker
        int capacityBefore = locker2.getAvailableCapacity();
        locker2.addItem(item0, 1);
        // item is in locker
        assertEquals("adding item to locker wasn't succesful", 1,
                (locker2.getItemCount(item0.getType())));
        // locker doesn't have same available capacity as it had before adding
        assertNotEquals("available capacity did't change during adding", capacityBefore,
                locker2.getAvailableCapacity());
        // locker capacity changed to what it should
        assertEquals("capacity should update after adding an item",
                (locker2.getCapacity() - item0.getVolume()), locker2.getAvailableCapacity());

    }


    @Test
    public void testAddItemResultZeroBasic() {

        //1) add one item - less than 50 of locker's capacity
        assertEquals("one item, 10% of capacity", this.METHOD_SUCCESS,
                locker1.addItem(item0, 1));

        //2) more than 50% but not from the same type of item
        assertEquals("adding 33% of first type of item", this.METHOD_SUCCESS,
                locker2.addItem(item1, 11)); // 33%
        assertEquals("adding 20% of 2nd type of item", this.METHOD_SUCCESS,
                locker2.addItem(item3, 2)); //20 %
    }


    @Test
    public void testAddItemResultZeroMultipule1() {
        //1) adding a few times a few items of same type, to same locker, less than 50@
        assertEquals("1st adding few of items of same type", this.METHOD_SUCCESS,
                locker2.addItem(item0, 10));
        assertEquals("2nd adding few of items of same type", this.METHOD_SUCCESS,
                locker2.addItem(item0, 15));
    }


    @Test
    public void testAddItemResultZeroMultipule2() {
        //2) adding different items to locker, one of each, less than 50% capacity (20/50)
        assertEquals("adding first type of item", this.METHOD_SUCCESS, locker3.addItem(item0, 1));
        assertEquals("adding 2nd type of item", this.METHOD_SUCCESS, locker3.addItem(item1, 1));
        assertEquals("adding 3rd type of item", this.METHOD_SUCCESS, locker3.addItem(item2, 1));
        assertEquals("adding 4th type of item", this.METHOD_SUCCESS, locker3.addItem(item3, 1));

    }


    @Test
    public void testAddItemResultZeroMultipule3() {
        //3) adding different items to locker, few of each, less than 50% capacity (48/50)
        assertEquals("adding 2 of 2nd type of item", this.METHOD_SUCCESS, locker2.addItem(item1, 2));
        assertEquals("adding 2 of 3rd type of item", this.METHOD_SUCCESS, locker2.addItem(item2, 2));
        assertEquals("adding 2 of 4th type of item", this.METHOD_SUCCESS, locker2.addItem(item3, 2));
        assertEquals("adding 2 of 5th type of item", this.METHOD_SUCCESS, locker2.addItem(item4, 3));

    }


    @Test
    public void testAddItemResultOne() {
        //1) add one item - 100% locker's capacity - one item go to storage
        assertEquals("add one item,100%, goes directly to storage", 1,
                locker1.addItem(item3, 1));

        //2) add one item to a locker which already has the same type of item,
        // together exactly 50% of locker's capacity, 8 unit goes to storage
        locker2.addItem(item0, 24);
        assertEquals("same item is added, exceed 50%", 1, locker2.addItem(item0, 2));

        // 3) adding a few items of same type at once more than 50%,
        // need to round down. 9*3 = 27, need to leave 10 in locker, move 17 to long term
        assertEquals("same item is added, exceed 50%", 1, locker3.addItem(item1, 9));

    }


    @Test
    public void testAddZeroItem() {
        //1) adding 0 * item to empty locker
        assertEquals("add 0 items to  locker", this.METHOD_SUCCESS, locker1.addItem(item0, 0));
        //2) adding 0 to a full locker
        locker2.addItem(item0, 20); // 40%
        locker2.addItem(item2, 8); //40 %
        locker2.addItem(item3, 2); //20%
        assertEquals("adding 0 to a full locker", this.METHOD_SUCCESS, locker2.addItem(item0, 0));
    }


    @Test
    public void testAddItemResultNegativeOneBasic() {

        //1) add a few items of same type with total volume(15) > locker capacity (10)
        assertEquals("add few of same item w/ total volume > locker capacity", this.METHOD_FAIL,
                locker1.addItem(item2, 3));

        //1) add one item whose volume is more than locker's capacity
        locker1 = new Locker(3);
        assertEquals("add one item whose volume> locker's capacity", this.METHOD_FAIL,
                locker1.addItem(item3, 1));

    }


    @Test
    public void testAddItemResultNegativeOneCapZero() {
        locker1 = new Locker(0);
        // add one item to locker with capacity 0
        assertEquals("add one item to locker with capacity 0", this.METHOD_FAIL,
                locker1.addItem(item0, 1));
    }


    @Test
    public void addItemNegativeOneDueToLTS() {
        // item volume > 50% of locker storage, wishes to move 1100 (to leave item in 20% of storage,
        // but fails, since storage has only capacity of 1000).
        locker1 = new Locker(2000);
        assertEquals("item volume > 50% of locker cap., and leftover> LTS", this.METHOD_FAIL,
                locker1.addItem(item3, 1500));
    }

    // ---------------removeItem  related functions---------------
    @Test
    public void testRemoveItemFail() {
        // trying to remove from empty locker
        assertEquals("cannot remove from empty locker", this.METHOD_FAIL,
                locker2.removeItem(item0, 3));
        // negative int (actual - adding through remove)
        locker2.addItem(item0, 3);
        assertEquals("cannot remove a negative int", this.METHOD_FAIL,
                locker2.removeItem(item0, -1));
        // trying to take out an item which doesn't exist in locker
        assertEquals("cannot remove from empty locker", this.METHOD_FAIL,
                locker2.removeItem(item1, 1));
        // cannot remove more than there are
        assertEquals("cannot remove from empty locker", this.METHOD_FAIL,
                locker2.removeItem(item1, 4));
    }


    @Test
    public void testRemoveItemBasic() {
        //remove 0 items from empty locker
        assertEquals("remove 0 items from empty locker",
                this.METHOD_SUCCESS, locker2.removeItem(item0, 0));

        //check item was actually removed from locker
        locker2.addItem(item0, 1);
        locker2.removeItem(item0, 1);
        // there are no items of this type in locker
        assertEquals("after removing there're still items of this type in locker", 0,
                locker2.getItemCount(item0.getType()));
        // locker has same available capacity as it had before add and remove
        assertEquals("same available capacity as it had before add and remove",
                locker2.getCapacity(), locker2.getAvailableCapacity());
    }


    @Test
    public void testGetItemCountZero() {
        // if no item is in locker, item is counted as 0
        assertEquals("item that does not exist is counted as 0", 0,
                locker2.getItemCount(item0.getType()));

        // add and remove 0 items, see it counter stay the same
        locker2.addItem(item0, 0);
        assertEquals("item that does not exist is counted as 0", 0,
                locker2.getItemCount(item0.getType()));
        locker2.removeItem(item0, 0);
        assertEquals("item that does not exist is counted as 0", 0,
                locker2.getItemCount(item0.getType()));
    }


    @Test
    public void testGetItemCountAdd() {

        //add a few different items, see counter can count them correctly
        locker2.addItem(item3, 1);
        assertEquals("item counter after adding ", 1,
                locker2.getItemCount(item3.getType()));
        locker2.addItem(item1, 2);
        assertEquals("item counter count after adding", 2,
                locker2.getItemCount(item1.getType()));
        locker2.addItem(item2, 3);
        assertEquals("item counter count after adding", 3,
                locker2.getItemCount(item2.getType()));
    }


    @Test
    public void testGetItemCountRemove() {
        //remove only part of same item and see counter is precise
        locker2.addItem(item2, 3);
        locker2.removeItem(item2, 1);
        assertEquals("item counter after removing part if item", 2,
                locker2.getItemCount(item2.getType()));


    }

    @Test
    public void testGetItemCountNegativeFail() {
        // if add item retun -1 fails - get item count will stay same
        locker2.addItem(item0, 1);
        locker2.addItem(item0, -1);//this action should fail
        assertEquals("when adding a negative, item count shouldn't change", 1,
                locker2.getItemCount(item0.getType()));
    }


    @Test
    public void testGetItemCountMoveLTS() {
        // add footbool , more than 50%, get item count is updated
        locker2.addItem(item4, 20);// 60 units will go to long term storage, so 50 will stay.
        assertEquals("get item count should update when some of item move to longTerm",
                5, locker2.getItemCount(item4.getType()));
    }

    // ---------------getInventory  related functions---------------

    @Test
    public void testGetInventoryInitial() {
        //when nothing was added to locker, map is empty
        Map<String, Integer> testMap = new HashMap<String, Integer>();
        assertEquals("map should be empty, when locker is empty", testMap,
                locker2.getInventory());
    }


    @Test
    public void testGetInventoryAdd() {

        //check if map is is updated when items are added to locker
        locker2.addItem(item0, 1);
        locker2.addItem(item1, 4);
        Map<String, Integer> testMap = new HashMap<String, Integer>();
        testMap.put(item0.getType(), 1);
        testMap.put(item1.getType(), 4);
        assertEquals("get inventory fail, map isn't correct after adding",
                testMap, locker2.getInventory());
    }


    @Test
    public void testGetInventoryRemove() {
        //check if map is is updated when a few items are removed from locker
        locker2.addItem(item0, 1);
        locker2.addItem(item1, 4);
        // remove some of the items -
        locker2.removeItem(item0, 1);
        //assertTrue("bla", 0 ==locker2.storageMap.get(item0.getType()));
        locker2.removeItem(item1, 3);
        Map<String, Integer> testMap = new HashMap<String, Integer>();
        testMap.put(item0.getType(), 0);
        testMap.put(item1.getType(), 1);
        assertEquals("get inventory fail, map isn't correct after removing", testMap,
                locker2.storageMap);
    }


    @Test
    public void testGetInventoryProgressive() {
        locker1.addItem(item0, 4); // Excided 50%, automaticly move 3*item to storage so only 20%
        // (1 of item0) would stay in locker
        Map<String, Integer> testMap = new HashMap<String, Integer>();
        testMap.put(item0.getType(), 1);
        assertEquals("get inventory failed, map isn't correct", testMap, locker1.getInventory());
    }


    // ---------------getCapacity  related functions---------------
    @Test
    public void testGetCapacityBasic() {

        // according to locker we initialize before each test, check if get capacity
        assertEquals("get capacity fail", 10, locker1.getCapacity());
        assertEquals("get capacity fail", 100, locker2.getCapacity());
        assertEquals("get capacity fail", 50, locker3.getCapacity());
    }


    @Test
    public void testGetCapacityZero() {
        locker1 = new Locker(0);
        assertEquals("capacity of 0", 0, locker1.getCapacity());
        assertEquals("initial AvailableCapacity failed",
                locker1.getCapacity(), locker1.getAvailableCapacity());
    }


    // ---------------getAvailableCapacity  related functions---------------
    @Test
    public void testGetAvailableCapacityInitial() {

        // in case capacity is positive
        assertEquals("initial AvailableCapacity failed",
                locker2.getCapacity(), locker2.getAvailableCapacity());
        assertEquals("initial AvailableCapacity failed",
                locker3.getCapacity(), locker3.getAvailableCapacity());

    }


    @Test
    public void testGetAvailableCapacityAdd() {
        // check when adding one of an item
        locker1.addItem(item1, 1);
        assertEquals("getAvailableCapacity failed after adding 1 item",
                (locker1.getCapacity() - item1.getVolume()), locker1.getAvailableCapacity());

        // check when adding a few of the same item
        locker2.addItem(item2, 3);
        assertEquals("getAvailableCapacity failed after a few of the same item",
                (locker2.getCapacity() - item2.getVolume() * 3), locker2.getAvailableCapacity());

        // check after adding a few items from different types
        locker2.addItem(item3, 2);
        assertEquals("getAvailableCapacity failed after adding  a few items from different types",
                (locker2.getCapacity() - item2.getVolume() * 3 - item3.getVolume() * 2),
                locker2.getAvailableCapacity());
    }


    @Test
    public void testGetAvailableCapacityRemove() {
        locker2.addItem(item3, 4);
        locker2.removeItem(item3, 3);
        assertEquals("bla", 1, locker2.getItemCount(item3.getType()));
        //check after removing not the whole amount of an item
        assertEquals("getAvailableCapacity failed-remove some of the same item",
                (locker2.getCapacity() - item3.getVolume()), locker2.getAvailableCapacity());
    }


    @Test
    public void testGetAvailableCapacityMoveToLTS() {
        // check moving things to storage available capacity change to correct number (20%)
        locker1.addItem(item0, 4); // Excided 50%, automaticly move 3*item to storage so only 20%
        // (1 of item0) would stay in locker
        assertEquals("get available capacity failed after some object moved to long term",
                locker1.getCapacity() - item0.getVolume(), locker1.getAvailableCapacity());
    }


}
