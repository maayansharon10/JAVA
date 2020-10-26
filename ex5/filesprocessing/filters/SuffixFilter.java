package filesprocessing.filters;

import java.io.File;


/**
 * class which represent a filter which determines if file has a certain given suffix,
 * (excluding path).
 * implements Filter.
 */
public class SuffixFilter implements Filter{

    // ------------------- data members -------------------
    /**
     * value of given suffix
     */
    private String suffix;


    // ------------------- constructors -------------------

    /**
     * constructor for suffix filter
     * @param suffixValue  the given suffix value.
     */
    public SuffixFilter(String suffixValue){
        this.suffix = suffixValue;
    }

    // ------------------- methods -------------------
    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file){
        return file.getName().endsWith(getSuffix());
    }

    /**
     * return given suffix
     * @return return given suffix
     */
    private String getSuffix(){
        return this.suffix;
    }

}
