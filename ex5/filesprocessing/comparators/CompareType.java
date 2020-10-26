package filesprocessing.comparators;

import java.io.File;
import java.util.Comparator;

/**
 * compare files by file type, going from ’a’ to ’z’. if type is the same - by absolute path
 */
public class CompareType implements Comparator<File> {
    /**
     * compare files t files by file type, going from ’a’ to ’z’
     * @param file1 File object
     * @param file2 File object
     * @return -1 if file2 is greater file1, 0 if they're equal, 1 if 2 is greater than 1
     */
    @Override
    public int compare(File file1, File file2) {
        String file1Type = getFileExtension(file1);
        String file2Type = getFileExtension(file2);
        int result = file1Type.compareTo(file2Type);
        if (result == 0){
            CompareNames comparator = new CompareNames();
            return comparator.compare(file1,file2);
        }
        return file1Type.compareTo(file2Type);
    }


    /**
     * returns the file extension (type).
     * @param file file wishing ot find it's extension
     * @return the file extension (type).
     */
    private static String getFileExtension( File file) {
        String name = file.getName();
        int extensionIndex = name.lastIndexOf("." );
        if (extensionIndex == -1 ||extensionIndex ==0) { // if there's not extenssion or if file is hidden or
            return "";
        }
        return name.substring(extensionIndex +1);
    }

}
