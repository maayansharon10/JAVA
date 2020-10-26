package filesprocessing.filters;

import java.io.File;

/**
 * class which represent a filter which determines if file is hidden or not.
 * implements Filter
 */
public class HiddenFilter implements Filter{

    // ------------------- data members -------------------

    /**
     * whether file should be hidden or not
     */
    private boolean hidden;

    // ------------------- constructors -------------------
    /**
     * constructor for writable filter
     * @param value "YES" if want to check if has hidden permission, "NO" otherwise.
     */
    public HiddenFilter(String value){
        this.hidden = value.equals("YES");
    }


    // ------------------- methods -------------------

    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file){
        return file.isHidden() == getHidden();
    }

    /**
     * return a bool represent wanted hidden value
     * @return a bool represent wanted hidden value
     */
    private boolean getHidden(){
        return this.hidden;
    }

}
