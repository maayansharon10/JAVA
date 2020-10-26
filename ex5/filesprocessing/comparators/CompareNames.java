package filesprocessing.comparators;

import java.io.File;
import java.util.Comparator;

public class CompareNames implements Comparator<File> {
    /**
     * compare files according to their absolute path in alphabetical order
     * @param file1 File object
     * @param file2 File object
     * @return -1 if file2 is greater file1, 0 if they're equal, 1 if 2 is greater than 1
     */
    @Override
    public int compare(File file1, File file2) {
        return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
    }

}