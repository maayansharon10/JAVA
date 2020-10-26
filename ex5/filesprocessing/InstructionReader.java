package filesprocessing;

import filesprocessing.exceptions.ExceptionType1;
import filesprocessing.exceptions.ExceptionType2;
import filesprocessing.filters.Filter;
import filesprocessing.filters.FilterFactory;
import filesprocessing.orders.Order;
import filesprocessing.orders.OrderFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * this class reads instructions from text file and create valid object of type section out them,
 * if possible.
 */
public class InstructionReader {


    // ------------------- data members -------------------
    /**
     * name of command file, recieved from command line in which there're instructions.
     */
    private String commandFile;

    /**
     * filter string.
     */
    private final String FILTER = "FILTER";
    /**
     * ORDER string.
     */
    private final String ORDER = "ORDER";

    /**
     * ORDER string.
     */
    private final String NO_FILTER_FIRST_LINE = "there's no Filter in first line";

    /**
     * sign which according to it string will be split.
     */
    private final String SIGN_FOR_SPLIT ="#";

    // ------------------- constructors -------------------

    /**
     * constructor to InstructionReader.
     * @param commandFile string of file in which there're the commands for creating sections.
     */
    public InstructionReader(String commandFile) {

        this.commandFile = commandFile;
    }

    // ------------------- methods ------------------------

    /**
     * create an array list of valid sections out of text file from the constructor.
     * @return an array list of valid sections
     * @throws ExceptionType2 thrown if there's a problem with the text file
     */
    public ArrayList<Section> makeSections() throws ExceptionType2 {
        ArrayList<String> instructionsToArray = readInstructionsIntoArray();
        return  arrayIntoSection(instructionsToArray);
    }

    /**
     * convert file into array list of strings where each item is a line (same order).
     * @return array list of strings where each item is a line (same order).
     * @throws ExceptionType2 indicates a problem in text file or IO exception.
     */
    private ArrayList<String> readInstructionsIntoArray() throws ExceptionType2 {
        try {

            //create reader
            BufferedReader reader = new BufferedReader(new FileReader(this.commandFile));

            // create new array - each item is a line in command file
            ArrayList<String> textAsArray = new ArrayList<String>();

            // line counter
            String currentLine;

            //read entire file,put in array and close.
            while ((currentLine = reader.readLine()) != null) {
                textAsArray.add(currentLine);
            }

            reader.close(); //close file

            return textAsArray;

        } catch (IOException e) { //
            throw new ExceptionType2("command file not found");
        }
    }


    /**
     * return a valid array of sections.
     * @param textAsArray array of strings. each one is one line in text file
     * @return array list of valid sections.
     * @throws ExceptionType2 if there's a problem with text format.
     */
    private ArrayList<Section> arrayIntoSection(ArrayList<String> textAsArray) throws ExceptionType2 {

        ArrayList<Section> sectionArray = new ArrayList<Section>();

        // if the text file was empty
        if (textAsArray.size() == 0) {
            return sectionArray;
        }

        int lastLine = 0;
        while (lastLine < textAsArray.size()) {

            Section currentSection = new Section();
            // ---------------read filter--------------- :

            //read first line and check it's ok

            checkFirstLineInSection(textAsArray, lastLine, "FILTER", "filter does not exist");


            lastLine++;  // continue to next line

            // read second line :
            //filter factory - or exception if not correct and then
            // update warning if needed
            checkSecondLineInSection(textAsArray, lastLine, currentSection);
            lastLine++;  // continue to next line

            // ---------------read order--------------- :

            // read 3rd line:
            checkThirdLineInSection(textAsArray, lastLine);
            lastLine++; //continue to next line

            // read 4th line:
            lastLine = checkForthLineInSection(textAsArray, lastLine, currentSection);

            // --------------- add current section to array --------------- :

            // add current section - with valid filter and order to section array
            sectionArray.add(currentSection);
    }
        return sectionArray;
    }

    /**
     * read first line of section and check it's in right format
     * @param textAsArray array of text
     * @param lastLine number of line to check
     * @throws ExceptionType2 given line was
     */
    private void checkFirstLineInSection(ArrayList<String> textAsArray, int lastLine, String filter,
                                         String s) throws ExceptionType2 {
        if (!textAsArray.get(lastLine).equals(filter)) {
            throw new ExceptionType2(s);
        }
    }




    /**
     * read second line :
     * filter factory - or exception if not correct and then
     * update warning if needed
     * @param textAsArray string array
     * @param lastLine index of line
     * @param currentSection current section
     * @throws ExceptionType2 problem with file format
     */
    private void checkSecondLineInSection(ArrayList<String> textAsArray, int lastLine, Section currentSection) throws ExceptionType2 {
        try {
            //read next line and if exists convert into string[] with instructions.
            //if doesn't exists - throw IndexOutOfBoundException which will be caught in main function
            // (indicates wrong format of file:
            String[] filterInstructions = stringToInstruction(textAsArray.get(lastLine));

            // create filter. if bad format exception. would be thrown
            Filter f = FilterFactory.createSingleFilter(filterInstructions);

            //if filter is fine, set this filter in current section.
            currentSection.setFilter(f);
        }
        catch (IndexOutOfBoundsException e) {
            // next line doesn't exist  - indicate of bad format. will be catch in main.
            throw new ExceptionType2("Filter Sub-Section missing");
        }
        catch (ExceptionType1 e) {
            // filer is in bad format, so create a default filter in section
            currentSection.setFilter(FilterFactory.createDefaultFilter());
            currentSection.setFilterWarning(lastLine +1);
        }
    }



    /**
     * check third line in section is valid
     * @param textAsArray given text in array
     * @param lastLine given line index
     * @throws ExceptionType2 thrown if order doen't exists
     */
    private void checkThirdLineInSection(ArrayList<String> textAsArray, int lastLine) throws ExceptionType2 {
        try {
            checkFirstLineInSection(textAsArray, lastLine, ORDER, "ORDER sub-section missing");
        } catch (IndexOutOfBoundsException e) {
            throw new ExceptionType2( "ORDER sub-section missing");
        }
    }




    /**
     * check if forth line is valid and if so update section's order.
     * @param textAsArray array of strings
     * @param lastLine index of line in text as array
     * @param currentSection of type section
     * @return last line index updated
     */
    private int checkForthLineInSection(ArrayList<String> textAsArray, int lastLine, Section currentSection) {
        try {
            // if exists and equal to filter - make default order and continue to next section
            if (textAsArray.get(lastLine).equals(FILTER)) { //
                currentSection.setOrder(OrderFactory.createDefaultOrder());

            } else { // if order exist and it is not filter - it should be a valid order
                String[] orderInstructions = stringToInstruction(textAsArray.get(lastLine));
                Order order = OrderFactory.CreateSingleOrder(orderInstructions);
                currentSection.setOrder(order);
                lastLine++;  // continue to next line
            }
        } catch (IndexOutOfBoundsException e) { // if file ended
            currentSection.setOrder(OrderFactory.createDefaultOrder());

        } catch (ExceptionType1 e) {// if order exist but is in bad format
            currentSection.setOrder(OrderFactory.createDefaultOrder());
            currentSection.setOrderWarning(lastLine+1);
            lastLine++;
        }
        return lastLine;
    }





    /**
     * receive a string and return an array splitted according to regex
     * @param rawFilter string to split
     * @return receive a string and return an array splitted according to regex
     */
    private String[] stringToInstruction(String rawFilter) {
        return rawFilter.split(SIGN_FOR_SPLIT);
    }
}
