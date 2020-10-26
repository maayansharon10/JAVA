package filesprocessing.filters;

import java.io.File;


/**
 * class which represent a filter which determines if file have writing permission or not
 * (for the current user) ,
 * implements Filter.
 */
public class WritableFilter implements Filter {

    // ------------------- data members -------------------
    /**
     * given boolean acording to we wish to filter
     */
    private boolean writableValue;


    // ------------------- constructors -------------------

    /**
     * constructor for writable filter
     * @param value "YES" if want to check if has wrting permission, "NO" otherwise.
     */
    public WritableFilter(String value){

        // if value is "YES" convert to true, otherwise (meaning value = "NO") convert to false
        this.writableValue = (value.equals("YES"));

    }

    // ------------------- methods -------------------

    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file){
        return file.canWrite() == getWritableValue();
    }

    /**
     * return writable value
     * @return return writable value
     */
    private boolean getWritableValue(){
        return writableValue;
    }
}
