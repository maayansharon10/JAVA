package filesprocessing.exceptions;

/**
 * this class represent a type 12 exception (Invalid usage, I/O problems, A bad sub-section name or
 * Bad format of the Commands File).
 */
public class ExceptionType2 extends Exception{

    private static final long serialVersionUID = 1L;

    // ------------------- constructors -------------------
    public ExceptionType2(String errorMessage){
        super(errorMessage);

    }
}
