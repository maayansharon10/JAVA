package filesprocessing.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * decorator class which reverse any file comparator given
 */
public class ReverseComparatorDecorator implements Comparator<File> {

    // ------------------- data members -------------------
    /**
     *     comparator we wish to wrap
     */
    Comparator<File> comp;

    // ------------------- constructors -------------------

    public ReverseComparatorDecorator(Comparator<File> comp){
        this.comp = comp;
    }


    /**
     * compare files t files by file type, going from ’a’ to ’z’
     * @param file1 File object
     * @param file2 File object
     * @return -1 if file2 is greater file1, 0 if they're equal, 1 if 2 is greater than 1
     */
    @Override
    public int compare(File file1, File file2) {
        return (-1)*comp.compare(file1,file2);
    }



}
