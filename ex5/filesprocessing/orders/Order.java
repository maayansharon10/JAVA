package filesprocessing.orders;

import java.io.File;

/**
 * interface which represent a quality of an object which hold an array of files and can be sorted in some
 * order.
 */
public interface Order {

    /**
     * sort an array of files
     * @param fileArray the file to sort
     */
    void orderFiles(File[] fileArray);


}
