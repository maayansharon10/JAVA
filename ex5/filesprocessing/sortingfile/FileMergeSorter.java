package filesprocessing.sortingfile;

import java.io.File;
import java.util.Comparator;

public class FileMergeSorter {

    // ------------------- data members -------------------


    // ------------------- constructors -------------------


    // ------------------- methods ------------------------
    /**
     * function which activates the merge sort
     * @param a File array
     * @param comp File comparator
     */
    public static void sort(File[] a, Comparator<File> comp){
        mergeSort(a, 0, a.length-1, comp);
    }

    /**
     * recursive merge sort method according to a file comparator
     * @param a file array
     * @param from int
     * @param to int
     * @param comp file comparator
     */
    private static void mergeSort(File[]a, int left, int right, Comparator<File> comp) {
        if (left < right) {

            //find middle point
            int middle = (left + right) / 2;

            //sort first half
            mergeSort(a, left, middle, comp);
            //sort second half
            mergeSort(a, middle + 1, right, comp);

            merge(a, left, middle, right, comp);
        }
    }
    /**
     * helper method to merge sort which merge 2 file arrays together
     * @param a
     * @param from
     * @param middle
     * @param to
     * @param comp
     */
    private static void merge(File[]a, int from, int middle, int to, Comparator<File> comp) {

        int n = to - from +1;
        File[] values = new File[n];
        int fromValue = from;
        int middleValue =middle+1;
        int index = 0;
        while (fromValue <= middle && middleValue<= to){
            if (comp.compare(a[fromValue], a[middleValue])<0){
                values[index] = a[fromValue];
                fromValue++;
            }else{
                values[index] = a[middleValue];
                middleValue++;
            }
            index++;
        }
        while (fromValue<=middle){
            values[index] = a[fromValue];
            fromValue++;
            index++;
        }
        while (middleValue<= to){
            values[index] = a[middleValue];
            middleValue++;
            index++;
        }

        for (index = 0; index<n; index++){
            a[from+index] = values[index];

        }
    }


}
