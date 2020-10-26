/**
 * this class wraps a string so the closed hash set could know if string was deleted or was never
 * placed in bucket.
 */

public class StringSpecialWrapper {

    // ------------------------Data members ------------------------ :
    /**
     * the string value the object holds.
     */
    private String stringToWrap;

    // ------------------------ Constructors ------------------------ :

    /**
     * a constructor with a string parameter
     * @param stringValue string param
     */
    StringSpecialWrapper(String stringValue){
        this.stringToWrap = stringValue;
    }
    // ------------------------ Methods ------------------------ :

    /**
     * set string wrapper holds
     * @param value string to hold
     */
    void setStringToWrap(String value){

        this.stringToWrap = value;
    }

    /**
     * get string wrapper holds
     * @return string wrapper holds
     */
    String getStringToWrap(){
        return this.stringToWrap;
    }


}
