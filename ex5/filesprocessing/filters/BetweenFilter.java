package filesprocessing.filters;

import java.io.File;
/**
 * class which represent a filter which determines if their size is greater than a given upper bound
 * threshold ans smaller than a given smaller bound
 * implements Filter
 */
public class BetweenFilter  implements Filter{


    // ------------------- data members -------------------
    /**
     * one kilo byte
     */
    static private final int KILO_BYTE =1024;

    /**
     * lower threshold size
     */
    private double lowerBoundSize;
    /**
     * upper threshold size
     */
    private double upperBoundSize;


    // ------------------- constructors -------------------
    /**
     * a constactor for between filter
     * @param lowerBound some given lower threshold which will be converted to size.
     * @param upperBound some given lower threshold which will be converted to size.
     */
    public BetweenFilter(double lowerBound, double upperBound){
        this.lowerBoundSize = lowerBound * KILO_BYTE;
        this.upperBoundSize = upperBound * KILO_BYTE;
    }


    // ------------------- methods -------------------
    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file){
        // return true if file is between upper and lower bounds.
        return ((file.length() >= getLowerBoundSize()) && (file.length() <= getUpperBoundSize()) );
    }

    /**
     * return lower bound size (double)
     * @return lower bound size (double)
     */
    private double getLowerBoundSize(){
        return this.lowerBoundSize;
    }

    /**
     * return upper bound size (double)
     * @return upper bound size (double)
     */
    private double getUpperBoundSize(){
        return this.upperBoundSize;
    }

}
