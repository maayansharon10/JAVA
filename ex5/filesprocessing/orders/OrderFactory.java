package filesprocessing.orders;

import filesprocessing.exceptions.ExceptionType1;
import filesprocessing.comparators.CompareNames;
import filesprocessing.comparators.CompareSize;
import filesprocessing.comparators.CompareType;
import filesprocessing.comparators.ReverseComparatorDecorator;

/**
 * factory which creates object of type order which capable of sorting a file array according to some
 * comparator.
 */
public class OrderFactory {


    // ------------------- data members -------------------
    // megic numbers:
    private static final String REVERSE = "REVERSE";
    private static final String ABS = "abs";
    private static final String SIZE = "size";
    private static final String TYPE = "type";
    // ------------------- constructors -------------------

    // ------------------- methods ------------------------

    /**
     * return a single object of type order
     * @param rawOrder instructions for making an order (string splitted by '#')
     * @return a single object of type order
     * @throws ExceptionType1 thrown if the rawOrder is invalid in some sort
     */
    public static Order CreateSingleOrder(String[] rawOrder) throws ExceptionType1 {

        //abs order
        if (rawOrder[0].equals(ABS)){
            try{
                if (rawOrder[1].equals(REVERSE)){
                    return new FileOrganizer(new ReverseComparatorDecorator(new CompareNames()) );
                }
            }catch (IndexOutOfBoundsException e){
                return new FileOrganizer(new CompareNames());
            }
        }

        //size order
        else if (rawOrder[0].equals(SIZE)){
            try{
                if (rawOrder[1].equals(REVERSE)){
                    return new FileOrganizer(new ReverseComparatorDecorator(new CompareSize()) );
                }
            }catch (IndexOutOfBoundsException e){
                return new FileOrganizer(new CompareSize());
            }
        }

        //type order
        else if (rawOrder[0].equals(TYPE)){
            try{
                if (rawOrder[1].equals(REVERSE)){
                    return new FileOrganizer(new ReverseComparatorDecorator(new CompareType()));
                }
            }catch (IndexOutOfBoundsException e){
                    return new FileOrganizer(new CompareType());
            }
        }

        // else - order is in bad format
        throw new ExceptionType1("order in bad format / no such order");


    }

    public static Order createDefaultOrder() {
        return new FileOrganizer(new CompareNames());
    }
}
