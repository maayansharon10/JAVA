package filesprocessing.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * compare files by file size, and if equal - by absolute path
 */
public class CompareSize implements Comparator<File> {
    /**
     * compare files according to their absolute path in alphabetical order.
     * @param file1 File object
     * @param file2 File object
     * @return  negative int if file2 is greater file1,
     *          0 if they're equal,
     *          positive int if file2 is greater than file1.
     */
    @Override
    public int compare(File file1, File file2) {
        int result = Long.compare(file1.length(), file2.length());
        if (result ==0){ // if both of the sizes are equal - sort according to absolute path
            CompareNames compareNames = new CompareNames();
            return compareNames.compare(file1, file2);
        }
        return result;
    }
}
