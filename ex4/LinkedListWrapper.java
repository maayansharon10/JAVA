import java.util.LinkedList;

/**
 * wrapper for linked list
 */
public class LinkedListWrapper {


    // data members
    /**
     * linked list of Strings
     */
    private LinkedList<String> linkedList;

    // ------------------------ Constructors ------------------------ :

    /**
     * constructor create an object of linked list of strings
     */
    public LinkedListWrapper(){

        this.linkedList = new LinkedList<String>();


    }


    // ------------------------ Methods ------------------------ :

    /**
     * return the linked list this object holds
     * @return linked list
     */
    public LinkedList<String> getLinkedList(){
        return this.linkedList;
    }


    /**
     * adding a value to linked list
     * @param newValue string
     * @return true upon success, false otherwise
     */
    public boolean add (String newValue){
        return this.linkedList.add(newValue);
    }


    /**
     * check if value in linked list
     * @param value string wishing to check
     * @return true is value exist in linked list, false otherwise
     */
    public boolean contains(String value){
        return this.linkedList.contains(value);
    }


    /**
     * remove (delete) value from linked list
     * @param deleteValue value to delete
     * @return true if was sucessfully removed. false otherwise
     */
    public boolean delete (String deleteValue){
        return this.linkedList.remove(deleteValue);
    }

    /**
     * return the size, number of elements, in linked list
     * @return this linked list number of elements
     */
    public int size(){
        return this.linkedList.size();
    }

}
