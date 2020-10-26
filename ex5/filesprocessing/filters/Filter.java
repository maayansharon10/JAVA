package filesprocessing.filters;

import java.io.File;
import java.util.ArrayList;

/**
 * inteface of filter, meaning an object which can filter files.
 */
public interface Filter {


    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    boolean doesPassFilter(File file);



}