package filesprocessing.filters;

import java.io.File;

/**
 * class which represent a filter which determines if file has a certain prefix,
 * (excluding path).
 * implements Filter.
 */
public class PrefixFilter implements Filter {


    // ------------------- data members -------------------
    /**
     * value of prefix file as string
     */
    String prefixValue;

    // ------------------- constructors -------------------

    /**
     * constructor for contains Filter
     * @param somePrefix a string represent the value to search
     */
    public PrefixFilter(String somePrefix) {
        this.prefixValue = somePrefix;
    }
    // ------------------- methods -------------------

    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file) {
        return file.getName().startsWith(getPrefixValue());
    }

    /**
     * return prefix value
     * @return return prefix value
     */
    private String getPrefixValue(){
        return this.prefixValue;
    }
}