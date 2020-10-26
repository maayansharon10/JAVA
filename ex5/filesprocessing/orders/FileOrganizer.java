package filesprocessing.orders;

import filesprocessing.sortingfile.FileMergeSorter;

import java.io.File;
import java.util.Comparator;

/**
 * file organizer subject, implements order and receives a comparator in constructor to organize file
 * array according to it.
 */
public class FileOrganizer implements Order{



    // ------------------- data members -------------------
    /**
     * given comparator of files
     */
    private Comparator<File> comparator ;

    // ------------------- constructors -------------------

    /**
     * constructor of File Organizer
     * @param compare file comarator
     */
    public FileOrganizer(Comparator<File> compare){
        this.comparator = compare;

    }

    // ------------------- methods -------------------

    /**
     * sort file array in absolute name (using File.getAbsolutePath() ), going from ’a’ to ’z’
     * @param fileArray file array to sort
     */
    @Override
    public void orderFiles(File[] fileArray){
        FileMergeSorter.sort(fileArray,this.comparator);


    }


    /**
     * get comparator
     * @return comapator
     */
    public Comparator<File> getComparator() {
        return comparator;
    }
}

