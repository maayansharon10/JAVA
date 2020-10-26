package filesprocessing.filters;

import java.io.File;

/**
 * class which receives a filter and filter according to the opposite of this filter.
 */
public class NotFilterDecorator implements Filter {

    /**
     * given filter
     */
    private Filter filter;

    // ------------------- constructors -------------------

    /**
     * constructor for NotFilterDecorator
     * @param filter given filter
     */
    public NotFilterDecorator(Filter filter){
        this.filter = filter;
    }


    /**
     * return the opposite result of the given filter
     * @param file the given file
     * @return the opposite result of the given filter
     */
    @Override
    public boolean doesPassFilter(File file) {
        return (! this.filter.doesPassFilter(file));
    }
}
