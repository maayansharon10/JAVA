package filesprocessing;

import filesprocessing.exceptions.ExceptionType2;
import filesprocessing.filters.Filter;
import filesprocessing.orders.Order;

import java.io.File;
import java.util.ArrayList;

/**
 * main file which process a text file and a directory print the files accordng to filter and order given
 * in text file
 */
public class DirectoryProcessor {


    // ------------------- data members -------------------
    /**
     *     number of args from command line, as should be
     */
    private static final int NUMBER_OF_ARGS = 2;
    // ------------------- constructors -------------------


    // ------------------- methods ------------------------

    /**
     * main method. receives two args, one dircetory and one command file,
     * print files after they were filtered and ordered.
     * if there're problems of type 1 - print warning, then print files sorted
     * if there're type 2 problems (a structual problems mainly) - print only an error.
     * @param args from command line as string
     */
    public static void main(String[] args)  {

        //check arguments are valid
        try {
            if (args.length != NUMBER_OF_ARGS) {
                throw new ExceptionType2("one or more arguments from command line don't exists");
            }
        }catch (ExceptionType2 e){
            System.out.println(e.getMessage());
        }

        try {
            //directory - what should we sort
            String sourceDir = args[0];
            String commandFile = args[1];

            //create a list of files from directory
            ArrayList<File> filesFromDir = createFileListFromDir(sourceDir);

            // create an instruction reader object
            InstructionReader instructionReader = new InstructionReader(commandFile);

            //create an array with valid sections
            ArrayList<Section> sectionsArray = instructionReader.makeSections();

            for (Section currentSection : sectionsArray) {
                sectionProcessor(currentSection, filesFromDir);
            }
            // section is an object with 2 members - filter and an order
        }catch (ExceptionType2 e){
            System.err.println("ERROR: " + e.getMessage()+ "\n");
        }
    }

    /**
     * return an array list of file from given directory
     * @param directory string from command line
     * @return return an array list of file from given directory
     * @throws ExceptionType2 if directory is invalid or don't exists
     */
    private static ArrayList<File> createFileListFromDir(String directory) throws ExceptionType2 {
        try {
            // create array of all content from dir
            File[] allContentList = new File(directory).listFiles();

            // array with only files from dir
            ArrayList<File> onlyFiles = new ArrayList<File>();

            // create list of only the files in the directory (empty list is nothing in directory)
            return returnValidListFile(allContentList, onlyFiles);

        } catch (NullPointerException e) {
            throw new ExceptionType2("Directory does not exist");

        }
    }

    /**
     *  create list of only the files in the directory (or empty list if no file in directory)
     * @param allContentList all files in directory
     * @param onlyFiles empty array list
     * @return list of only the files in the directory (or empty list if no file in directory), without
     * sub directories.
     */
    private static ArrayList<File> returnValidListFile(File[] allContentList, ArrayList<File> onlyFiles) {
        // create list of only the files in the directory:
        if (allContentList != null) {

            for (File file : allContentList) {
                if (file.isFile()) {
                    onlyFiles.add(file);
                }
            } return onlyFiles;
        } else {
            return onlyFiles;
        }
    }

    /**
     * filter, sort and print relevant files for given section
     * by now assume files are valid
     * @param section section we'd like to handle
     * @param filesFromDir list of valid files which we want to sort/
     */
    private static void sectionProcessor(Section section, ArrayList<File> filesFromDir) {

        Filter filter = section.getFilter();
        Order order = section.getOrder();

        //filter
        ArrayList<File> filteredFiles = getFilteredFiles(filesFromDir, filter);
        // now filtered files contain only the filtered files

        // sort according to the right order :
        File[] filesArray = getFilesInOrder(order, filteredFiles);

        // print section:
        printSection(section, filesArray);

        // catch all exceptions for type 2
    }


    /**
     * get files in order according to section's order
     * @param order given order
     * @param filteredFiles the files array we wish to order
     * @return files in a specific order according to order.
     */
    private static File[] getFilesInOrder(Order order, ArrayList<File> filteredFiles) {
        File[] filesArray = filteredFiles.toArray(new File[0]);
        order.orderFiles(filesArray); // now files array is sorted according to the right order.
        return filesArray;
    }


    /**
     * filter array list of files according to a given filter
     * @param filesFromDir array to filter
     * @param filter given filter
     * @return array list of files filtered according to a given filter
     */
    private static ArrayList<File> getFilteredFiles(ArrayList<File> filesFromDir, Filter filter) {
        ArrayList<File> filteredFiles = new ArrayList<File>();
        for (File file : filesFromDir) {
            if (filter.doesPassFilter(file)) {
                filteredFiles.add(file); //if file passes filter - add to filtered files
            }
        }// now filtered files contain only the filtered files
        return filteredFiles;
    }

    /**
     * method deals with printing one section
     * @param section given section
     * @param filesArray files array after they were filtered and ordered by the section's data members
     */
    private static void printSection(Section section, File[] filesArray) {
        // check if there's a warning - and if so print it
        printSectionWarning(section);
        // print filtered files
        for (File file : filesArray){
            System.out.println(file.getName());
        }
    }

    /**
     * print section's warning
     * @param section given section
     */
    private static void printSectionWarning(Section section) {
        // if there's a warning in filter or in both filter and order - print only filter warning.
        // if there's a warning in order only - print order warning
        // if there's no warning at all - don't print.

        if (section.getShouldPrintFilterWarning()) { // if  there's a warning in filter
            System.err.println(section.getFilterWarningToPrint());
        }if(section.getShouldPrintOrderWarning()) { // there's a warning but not filter - must be order warning
            System.err.println(section.getOrderWarningToPrint());
        }
    }


}
