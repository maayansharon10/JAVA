package filesprocessing.filters;

import java.io.File;

/**
 * class which represent a filter which determines if  file have execution permission or not
 * (for the current user)
 * implement Filter
 */
public class ExecutableFilter implements Filter {

    // ------------------- data members -------------------

    /**
     * true if filter should return files which could be executed, false other wise
     */
    private boolean executableValue;

    // ------------------- constructors -------------------

    /**
     * constructor for Executue Filter
     * @param executable  "YES" if want to check if has executable permission, "NO" otherwise.
     *
     */
    public ExecutableFilter(String executable){
        // if value is "YES" convert to true, otherwise (meaning value = "NO") convert to false
        this.executableValue = executable.equals("YES");
    }

    // ------------------- methods -------------------

    /**
     * this function return true if file should pass filter, false otherwise.
     * @param file the given file
     * @return true if file passes filter, false otherwise
     */
    @Override
    public boolean doesPassFilter(File file){
        return file.canExecute() == getExecutableValue();
    }


    /**
     * return executableValue
     * @return executableValue
     */
    private boolean getExecutableValue(){
        return this.executableValue;
    }
}
