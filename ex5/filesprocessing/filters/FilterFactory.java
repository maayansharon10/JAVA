package filesprocessing.filters;

import filesprocessing.exceptions.ExceptionType1;

/**
 * Factory class which creates filters
 */
public class FilterFactory {

    // ------------------- data members -------------------
    /**
     * yes string
     */
    private static final String YES = "YES";

    /**
     * no string
     */
    private static final String NO = "NO";

    /**
     * no string
     */
    private static final String NOT = "NOT";

    /**
     * names of filters:
     */
    private static final String GREATER_THAN ="greater_than";
    private static final String BETWEEN ="between";
    private static final String SMALLER_THAN ="smaller_than";
    private static final String FILE ="file";
    private static final String CONTAINS ="contains";
    private static final String PREFIX ="prefix";
    private static final String SUFFIX="suffix";
    private static final String WRITABLE ="writable";
    private static final String EXECUTABLE ="executable";
    private static final String ALL ="all";
    private static final String HIDDEN ="hidden";


    // ------------------- constructors -------------------


    // ------------------- methods ------------------------

    /**
     * check if number is negative
     * @param number double
     * @throws ExceptionType1 thrown if number is negative
     */
    private static void checkNonNegative(double number) throws ExceptionType1 {
        if (number<0){
            throw new ExceptionType1("negative number");
        }
    }

    /**
     * check if value is either yes or no
     * @param value given value to check
     * @return true if it is yes or no
     * @throws ExceptionType1 thrown if value is not yes or no
     */
    private static boolean isYESorNO(String value) throws ExceptionType1 {
        if  (value.equals(YES) || value.equals(NO)) {
            return true;
        } else {
            throw new ExceptionType1("not yes or no");
        }
    }

    /**
     * true is string is equal to NOT, false otherwise
     * @param string given string
     * @return true is string is equal to NOT, false otherwise
     */
    private static boolean isStringNOT(String string){
        return string.equals(NOT);
    }


    /**
     * check if there's a valid number of arguments .
     * @param expected expected number of args.
     * @param actual actual number of args.
     * @param not boolean - if last arg is NOT.
     * @throws ExceptionType1 thrown if number of args is not valid.
     */
    private static void  checkArguments(int expected, int actual, boolean not) throws ExceptionType1{
        boolean result;
        if (!not) { //if there's no not
            result =(expected == actual);
        }
        else { // if there's not argument should be expected + 1
            result = (expected+1 ==actual);
        }
        if (!result){
            throw new ExceptionType1("Bad format, no suitable filter");
        }
    }


    /**
     * create single ilter
     * @param rawFilter
     * @return
     * @throws ExceptionType1
     */
    public static Filter createSingleFilter(String[] rawFilter) throws ExceptionType1 {

        Filter filter;
        boolean not = isStringNOT(rawFilter[rawFilter.length-1]);
        int actual = rawFilter.length;
        try{
            switch (rawFilter[0]) {
                case (GREATER_THAN):  // greater than filter :
                    checkArguments(2, actual, not);
                    double threshold = Double.parseDouble(rawFilter[1]);
                    checkNonNegative(threshold) ;
                    filter = (new GreaterThanFilter(threshold));
                    break;

                case (BETWEEN):      // between filter :
                    checkArguments(3, actual, not);
                    double lowerThreshold = Double.parseDouble(rawFilter[1]);
                    double upperThreshold = Double.parseDouble(rawFilter[2]);
                    checkNonNegative(lowerThreshold);
                    checkNonNegative(upperThreshold);
                    if (lowerThreshold > upperThreshold) {
                        throw new ExceptionType1("number is negative");
                    }filter = new BetweenFilter(lowerThreshold, upperThreshold);
                    break;

                case (SMALLER_THAN):  //smaller than
                    checkArguments(2, actual, not);
                    double lowerBound = Double.parseDouble(rawFilter[1]);
                    checkNonNegative(lowerBound);
                    filter = new SmallerThanFilter(lowerBound);
                    break;

                case (FILE): // file filter
                    checkArguments(2, actual, not);
                    //if 2nd instruction is string:
                    filter = new FileFilter(rawFilter[1]);
                    break;

                case (CONTAINS):// contains filter
                    checkArguments(2, actual, not);
                    filter = new ContainsFilter(rawFilter[1]);
                    break;

                case (PREFIX):// prefix filter
                    checkArguments(2, actual, not);
                    filter = new PrefixFilter(rawFilter[1]);
                    break;

                case (SUFFIX): // suffix filter
                    checkArguments(2, actual, not);
                    filter = new SuffixFilter(rawFilter[1]);
                    break;

                case (WRITABLE):  // writable filter
                    checkArguments(2, actual, not);
                    if (!isYESorNO(rawFilter[1])) {
                        throw new ExceptionType1("Not yes ot No");
                    }filter = new WritableFilter(rawFilter[1]);
                    break;

                case (EXECUTABLE):// excetuable filter)
                    checkArguments(2, actual, not);
                    if (!isYESorNO(rawFilter[1])) {
                        throw new ExceptionType1("Not yes ot No");
                    } filter = new ExecutableFilter(rawFilter[1]);
                    break;

                case (HIDDEN):// hidden filter
                    checkArguments(2, actual, not);
                    if (!isYESorNO(rawFilter[1])) {
                        throw new ExceptionType1("Not yes ot No");
                    }filter = new HiddenFilter(rawFilter[1]);
                    break;

                case (ALL):// all filter
                    checkArguments(1, actual, not);
                    filter = new AllFilter();
                    break;

                default:
                    throw new ExceptionType1("bad format filter");
            }

            if (not){
                return new NotFilterDecorator(filter);
            }
            return filter;

        }catch (NumberFormatException e){
            throw new ExceptionType1("bad format, number not found");
        }

    }


    /**
     * return default filter
     * @return default filter
     */
    public static Filter createDefaultFilter(){
        return new AllFilter();
    }

}
