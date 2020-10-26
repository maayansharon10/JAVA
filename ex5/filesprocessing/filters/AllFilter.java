package filesprocessing.filters;

import java.io.File;

/**
 * class which represent a filter which determines  all files are matched
 * implements Filter
 */
public class AllFilter implements Filter {

    // ------------------- data members -------------------


    // ------------------- constructors -------------------


    // ------------------- methods -------------------

    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file){
        // all files are matched
        return true;
    }

}
