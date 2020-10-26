package filesprocessing.filters;

import java.io.File;

/**
 * class which represent a filter which determines if value is equals the file name
 * (excluding path)
 * implements Filter
 */
public class FileFilter implements Filter {

    // ------------------- data members -------------------

    /**
     * value of name file as string
     */
    private String fileNameToFilter;

    // ------------------- constructors -------------------

    /**
     * constructor for file filter.
     * @param fileName string according to we wishing to filter
     */
    public FileFilter(String fileName){
     this.fileNameToFilter = fileName;

    }


    // ------------------- methods -------------------

    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file) {
    return file.getName().equals(this.fileNameToFilter);
    }


}
