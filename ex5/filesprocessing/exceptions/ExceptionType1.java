package filesprocessing.exceptions;

/**
 * this class represent a type 1 exception (filter or orders in bad format, but still valid)
 */
public class ExceptionType1 extends Exception {

    private static final long serialVersionUID = 1L;

    // ------------------- constructors -------------------
    public ExceptionType1(String msg){
        super(msg);
    }
}
