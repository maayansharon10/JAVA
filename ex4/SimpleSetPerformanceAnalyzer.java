
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * this class compare the performances of the following data structure:
 * 1. OpenHashSet
 * 2. ClosedHashSet
 * 3. Java’s TreeSet
 * 4. Java’s LinkedList
 * 5. Java’s HashSe
 */
public class SimpleSetPerformanceAnalyzer {

    // ------------------------Data members ------------------------ :

    /**
     * an array of simple set to hold all sort of collections
     */
    private SimpleSet[] collectionArray = new SimpleSet[5];
    /**
     * open hash set object
     */
    private OpenHashSet openHash = new OpenHashSet();
    /**
     * close hash set object
     */
    private ClosedHashSet closedHash = new ClosedHashSet();
    /**
     * java tree set wrapped as collection facade set object
     */
    private CollectionFacadeSet treeSet = new CollectionFacadeSet(new TreeSet<java.lang.String>());
    /**
     * java linked list set wrapped as collection facade set object
     */
    private CollectionFacadeSet linkedList = new CollectionFacadeSet(new LinkedList<String>());
    /**
     * java hashSet set wrapped as collection facade set object
     */
    private CollectionFacadeSet hashSet = new CollectionFacadeSet(new HashSet<String>());
    /**
     * data 1 as list of strings
     */
    private String[] data1 = Ex4Utils.file2array("C:\\Users\\Maayan\\gittest\\ex4-maayan.sharon\\src\\data1.txt");
    /**
     * data 2 as list of strings
     */
    private String[] data2 = Ex4Utils.file2array("C:\\Users\\Maayan\\gittest\\ex4-maayan.sharon\\src\\data2.txt");


    // ------------------------ Constructors ------------------------ :

    /**
     * default constructor for class
     */
    public SimpleSetPerformanceAnalyzer() {

        // create an array with an instance of each set -
        this.collectionArray[0] = openHash;
        this.collectionArray[1] = closedHash;
        this.collectionArray[2] = treeSet;
        this.collectionArray[3] = linkedList;
        this.collectionArray[4] = hashSet;

    }


    // ------------------------ Methods ------------------------ :

    //  general initialization methods :

    /**
     * reset all items in collection Array
     */
    private void reset(){
        this.collectionArray[0] = new OpenHashSet();
        this.collectionArray[1] = new ClosedHashSet();
        this.collectionArray[2] = new CollectionFacadeSet(new TreeSet<java.lang.String>());
        this.collectionArray[3] = new CollectionFacadeSet(new LinkedList<String>());
        this.collectionArray[4] = new CollectionFacadeSet(new HashSet<String>());
    }

    /**
     * fill all collectionArray item with requested data (as param)
     * @param data - requested data
     */
    private void fillWithData(String[] data){
        this.collectionArray[0] = new OpenHashSet(data);
        this.collectionArray[1] = new ClosedHashSet(data);
        this.collectionArray[2] = new CollectionFacadeSet(new TreeSet<java.lang.String>());
        addAllData(this.collectionArray[2], data);
        this.collectionArray[3] = new CollectionFacadeSet(new LinkedList<String>());
        addAllData(this.collectionArray[3], data);
        this.collectionArray[4] = new CollectionFacadeSet(new HashSet<String>());
        addAllData(this.collectionArray[4], data);
    }

    /**
     * add all items in data to collection
     * @param collection of type simple set
     * @param data String[]
     */
    private void addAllData(SimpleSet collection, String[] data){
        for( String item: data){
            collection.add(item);
        }
    }

    // measuring add methods :
    // - helpers functions
    /**
     * measure a performance of adding an item to list and return the result
     *
     * @param data list of strings
     * @param list a simpleSet object
     */
    private long addPerformances(String[] data, SimpleSet list) {
        long timeBefore = System.nanoTime();
        for (String item : data) {
            list.add(item);
        }
        long nanoDifference = System.nanoTime() - timeBefore;
        return convertToMS(nanoDifference);
    }

    /**
     * takes a time measured in nanoseconds as input, divide it by 1,000,000 to translate it to milliseconds
     *
     * @param time time measured in nanoseconds
     * @return time in  milliseconds.
     */
    private long convertToMS(long time) {
        return time / 1000000;
    }

    // - tests with printing functions

    /**
     * test no. 1 :
     * These values correspond to the time it takes (in ms) to insert data1
     * to all data structures
     */
    private void addDataSet1_PerformancesManager() {
        // reset all collections
        reset();

        // print msg regarding test
        System.out.println("#These values correspond to the time it takes (in ms) to insert data1 " +
                "to all data structures");

        // Adding all the words in data1.txt, one by one, to each of the data structures

        System.out.println("OpenHashSet_AddData1 = " + addPerformances(data1, collectionArray[0]));
        System.out.println("ClosedHashSet_AddData1 = " + addPerformances(data1, collectionArray[1]));
        System.out.println("TreeSet_AddData1 = " + addPerformances(data1, collectionArray[2]));
        System.out.println("LinkedList_AddData1 = " + addPerformances(data1, collectionArray[3]));
        System.out.println("HashSet_AddData1 = " + addPerformances(data1, collectionArray[4]));

    }


    /**
     * test no. 2
     * These values correspond to the time it takes (in ms) to insert data2 to all data structures
     */
    private void AddData2_PerformancesManager() {
        // reset all colections
        reset();

        //print msg regarding test
        System.out.println("#These values correspond to the time it takes (in ms) to insert data2 " +
                "to all data structures");

        // Adding all the words in data2.txt, one by one, to each of the data structures
        System.out.println("OpenHashSet_AddData2 = " + addPerformances(data2, collectionArray[0]));
        System.out.println("ClosedHashSet_AddData2 = " + addPerformances(data2, collectionArray[1]));
        System.out.println("TreeSet_AddData2 = " + addPerformances(data2, collectionArray[2]));
        System.out.println("LinkedList_AddData2 = " + addPerformances(data2, collectionArray[3]));
        System.out.println("HashSet_AddData2 = " + addPerformances(data2, collectionArray[4]));


    }

    // measuring contains methods :
    // - helpers functions

    /**
     * long which describes the time in nano seconds which took the function to run with warm up
     * @param list simple set
     * @param searchVal string
     * @return long which describes the time in nano seconds which took the function to run with warm up
     */
    private long containPerformancesWithWarmUp(SimpleSet list, String searchVal) {
        // first - warm up - run the function 7000 times
        int i = 0;
        while (i < 70000) {
            list.contains(searchVal);
            i++;
        }
        // start measuring time
        long timeBefore = System.nanoTime();
        int j = 0;
        while (j < 70000) {
            list.contains(searchVal);
            j++;
        }
        // stop measuring
        long nanoAllDifference = (System.nanoTime() - timeBefore);
        // check how long (avr.) one iteration took
        // return result in nano seconds
        return nanoAllDifference / (long) 70000;
    }


    /**
     * long which describes the time in nano seconds which took the function to run with no warm up
     * @param list simple set
     * @param searchVal string
     * @return long which describes the time in nano seconds which took the function to run with warm up
     */
    private long containPerformancesNoWarmUp(SimpleSet list, String searchVal) {
        // start measuring time
        long timeBefore = System.nanoTime();
        list.contains(searchVal);

        // stop measuring and return the difference in nano seconds
        return (System.nanoTime() - timeBefore);
    }



    // tests with printing functions

    /**
     * test no. 3
     * These values correspond to the time it takes (in ns) to check if \"hi\" is contained in
     * the data structures initialized with data1"
     */
    private void containsHi1() {

        // reset all collections
        reset();

        //fill with data1
        fillWithData(data1);

        //print msg regarding test
        String toPrint = "#These values correspond to the time it takes (in ns) to check " +
                "if \"hi\" is contained in\n" +
                "#the data structures initialized with data1";
        System.out.println(toPrint);

        System.out.println("OpenHashSet_Contains_hi1 = " +
                containPerformancesWithWarmUp(collectionArray[0], "hi"));
        System.out.println("ClosedHashSet_Contains_hi1 = " +
                containPerformancesWithWarmUp(collectionArray[1], "hi"));
        System.out.println("TreeSet_Contains_hi1 = " +
                containPerformancesWithWarmUp(collectionArray[2], "hi"));
        // linked list check without warm up
        System.out.println("LinkedList_Contains_hi1 = " +
                containPerformancesNoWarmUp(collectionArray[3], "hi"));
        System.out.println("HashSet_Contains_hi1 = " +
                containPerformancesWithWarmUp(collectionArray[4], "hi"));
    }


    /**
     * test no. 4
     * These values correspond to the time it takes (in ns) to check  if \"-13170890158\" is contained
     * in the data structures initialized with data1
     */
    private void containsNegative() {

        // reset all collections
        reset();

        //fill with data1
        fillWithData(data1);

        //print msg regarding test
        String msg = "#These values correspond to the time it takes (in ns) to check " +
                "if \"-13170890158\" is contained in\n" +
                "#the data structures initialized with data1";
        System.out.println(msg);

        System.out.println("OOpenHashSet_Contains_negative = " +
                containPerformancesWithWarmUp(collectionArray[0], "-13170890158"));

        System.out.println("ClosedHashSet_Contains_negative = " +
                containPerformancesWithWarmUp(collectionArray[1], "-13170890158"));

        System.out.println("TreeSet_Contains_negative = " +
                containPerformancesWithWarmUp(collectionArray[2], "-13170890158"));

        // linked list check without warm up
        System.out.println("LinkedList_Contains_negative = " +
                containPerformancesNoWarmUp(collectionArray[3], "-13170890158"));

        System.out.println("HashSet_Contains_negative = " +
                containPerformancesWithWarmUp(collectionArray[4], "-13170890158"));

    }

    /**
     * test no. 5
     * hese values correspond to the time it takes (in ns) to check if \"23\" is contained in
     * the data structures initialized with data2
     */
    private void contains23() {

        // reset all collections
        reset();

        //fill with data1
        fillWithData(data2);

        //print msg regarding test
        String msg = "#These values correspond to the time it takes (in ns) to check if \"23\" is contained in\n" +
                "#the data structures initialized with data2";
        System.out.println(msg);

        System.out.println("OpenHashSet_Contains_23 = " +
                containPerformancesWithWarmUp(collectionArray[0], "23"));

        System.out.println("ClosedHashSet_Contains_23 = " +
                containPerformancesWithWarmUp(collectionArray[1], "23"));

        System.out.println("TreeSet_Contains_23 = " +
                containPerformancesWithWarmUp(collectionArray[2], "23"));

        // linked list check without warm up
        System.out.println("LinkedList_Contains_23 = " +
                containPerformancesNoWarmUp(collectionArray[3], "23"));

        System.out.println("HashSet_Contains_23 = " +
                containPerformancesWithWarmUp(collectionArray[4], "23"));


    }

    /**
     * test no. 6
     * These values correspond to the time it takes (in ns) to check if \"hi\" is contained in
     * the data structures initialized with data2
     */
    private void containsHi2() {

        // reset all collections
        reset();

        //fill with data1
        fillWithData(data2);

        //print msg regarding test
        String toPrint = "#These values correspond to the time it takes (in ns) to check if \"hi\" is " +
                "contained in\n the data structures initialized with data2";
        System.out.println(toPrint);

        System.out.println("OpenHashSet_Contains_hi2 = " +
                containPerformancesWithWarmUp(collectionArray[0], "hi"));

        System.out.println("ClosedHashSet_Contains_hi2 = " +
                containPerformancesWithWarmUp(collectionArray[1], "hi"));

        System.out.println("TreeSet_Contains_hi2 = " +
                containPerformancesWithWarmUp(collectionArray[2], "hi"));

        // linked list check without warm up
        System.out.println("LinkedList_Contains_hi2 = " +
                containPerformancesNoWarmUp(collectionArray[3], "hi"));

        System.out.println("HashSet_Contains_hi2 = " +
                containPerformancesWithWarmUp(collectionArray[4], "hi"));

    }


    // methods related to main method:

    /**
     * return which test should run
     * recive input from user and return it as a list of strings
     * @return which test should run
     */
    private static String[] whichTestShouldRun() {
        // receive an input from user - to chose which tests to run
        Scanner scan = new Scanner(System.in);
        System.out.println("choose number of test to run input can include ints between 1- 6 and if " +
                "includes a few test ' ' should separate each test number. \n " +
                "example : '2 5' for tests 2 and 5");
        String input = scan.nextLine();
        return input.split(" ");
    }


    /**
     * main function. reciving an input from user, and acoordindly running the requested tests.
     * @param args
     */
    public static void main(String[] args) {

        SimpleSetPerformanceAnalyzer analyzer = new SimpleSetPerformanceAnalyzer();

        // receive an input from user - to chose which tests to run
        String[] testsToRun = whichTestShouldRun();

        for (String item : testsToRun) {
            if (item.equals("1")) {
                /* test 1 - add data 1 */
                analyzer.addDataSet1_PerformancesManager();
            } else if (item.equals("2")) {
                /* test 2 - add data 2 */
                analyzer.AddData2_PerformancesManager();
            } else if (item.equals("3")) {
                /* test 3 - contains "hi" with data 1 */
                analyzer.containsHi1();
            } else if (item.equals("4")) {
                /* test 4 - contains "-13170890158" with data 1 */
                analyzer.containsNegative();
            } else if (item.equals("5")) {
                /* test 5 - contains 23 with data 2 */
                analyzer.contains23();
            } else if (item.equals("6")) {
                /* test 6 - contains "hi" with data 2 */
                analyzer.containsHi2();
            } else {
                System.out.println("test requested is invalid");
            }
        }

    }
}


