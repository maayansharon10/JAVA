package filesprocessing.filters;

import java.io.File;


/**
 * class which represent a filter which determines if value is contained in the file name
 * (excluding path)
 * implements Filter
 */
public class ContainsFilter implements Filter {
    // ------------------- data members -------------------
    /**
     * string we wish file name would contain
     */
    private String containsValue;

    // ------------------- constructors -------------------

    /**
     * constructor for contains Filter
     * @param someString a string represent the value to search
     */
    public ContainsFilter(String someString){
        this.containsValue = someString;
    }

    // ------------------- methods -------------------

    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file){
        // return true if file's fame contains the given value.
        return file.getName().contains(getContainsValue());
    }

    /**
     * return a string which represent containsValue
     * @return containsValue (string)
     */
    private String getContainsValue(){
        return this.containsValue;
    }
}
