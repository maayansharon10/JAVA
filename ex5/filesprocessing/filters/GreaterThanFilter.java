package filesprocessing.filters;

import java.io.File;

/**
 * class which represent a filter which determines if their size is greater than a given threshold
 * implements Filter
 */
public class GreaterThanFilter implements Filter{

    // ------------------- data members -------------------
    /**
     * one kilo byte
     */
    static private final int KILO_BYTE =1024;

    /**
     * threshold size
     */
    private double size;


    // ------------------- constructors -------------------
    /**
     * a constactor for Greater than
     * @param threshold some given threshold which will be converted to size.
     */
    public GreaterThanFilter(double threshold){
        this.size = threshold * KILO_BYTE;

    }


    // ------------------- methods -------------------

    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file){
        return file.length() > size;
    }
}



