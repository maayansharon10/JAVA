/**
 * This class represents a patron, which has a first name, last name, ComicTendency, DramaticTendency,
 * EducationalTendency and enjoymentThreshold
 * */
public class Patron {

    /**
     * The first name of this patron.
     */
    final String firstName;

    /**
     * The name of this patron.
     */
    final String lastName;

    /**
     * The weight the patron assigns to the comic aspects of books
     */
    int patronComicTendency;

    /**
     * The weight the patron assigns to the dramatic aspects of books.
     */
    int patronDramaticTendency;

    /**
     * The weight the patron assigns to the educational aspects of books
     */
    int patronEducationalTendency;

    /**
     * The minimal literary value a book must have for this patron to enjoy it.
     */
    int enjoymentThreshold;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new book with the given characteristic.
     *
     * @param patronFirstName          The first name of the patron.
     * @param patronLastName           The name of this patron.
     * @param comicTendency            The weight the patron assigns to the comic aspects of books.
     * @param dramaticTendency         The weight the patron assigns to the dramatic aspects of books
     * @param educationalTendency      The weight the patron assigns to the educational aspects of books.
     * @param patronEnjoymentThreshold The minimal literary value a book must have for this patron to enjoy it.
     */
    Patron(String patronFirstName, String patronLastName, int comicTendency, int dramaticTendency,
           int educationalTendency, int patronEnjoymentThreshold) {

        firstName = patronFirstName;
        lastName = patronLastName;
        patronComicTendency = comicTendency;
        patronDramaticTendency = dramaticTendency;
        patronEducationalTendency = educationalTendency;
        enjoymentThreshold = patronEnjoymentThreshold;
        // ??? need to add an array?

    }

    /*----=  Instance Methods  =-----*/

    /**
     * Returns a string representation of the patron,
     * which is a sequence of its first and last name, separated by a single white space.
     * For example, if the patron's first name is "Ricky" and his last name is "Bobby",
     * this method will return the String "Ricky Bobby"
     *
     * @return the String representation of this patron.
     */
    String stringRepresentation() {
        return firstName + " " + lastName;
    }

    /**
     * @param book - The book to asses
     * @return the literary value this patron assigns to the given book.
     */
    int getBookScore(Book book) {
        // all values of the book
        int bookComicValue = book.getComicValue();
        int bookDramaticValue = book.getdramaticValue();
        int bookEducationalValue = book.getEducationalValue();
        // the formula for a book score of this patron:
        int bookScore = bookComicValue * patronComicTendency + bookDramaticValue * patronDramaticTendency +
                bookEducationalValue * patronEducationalTendency;
        return bookScore;
    }

    /**
     * @param book - The book to asses
     * @return true of this patron will enjoy the given book, false otherwise.
     */
    boolean willEnjoyBook(Book book){
        int bookScore = getBookScore(book);

        if (bookScore >= enjoymentThreshold){
            return true;
        } else
            return false;
    }

    /**
     * get patron full name
     * @return patron full name
     */
    String getPatronName(){
        return firstName + " " + lastName;
    }

    /**
     * get patron's comic tendency
     * @return patron's comic tendency
     */
    int getPatronComicTendency() {
        return patronComicTendency;
    }


    /**
     * get patron's dramatic tendency
     * @return patron's dramatic tendency
     */
    int getPatronDramaticTendency(){
        return patronDramaticTendency;
    }


    /**
     * get patron's educational tendency
     * @return patron's educational tendency
     */
    int getPatronEducationalTendency(){
        return patronEducationalTendency;
    }


    /**
     * get patron's Enjoyment Threshold
     * @return patron's Enjoyment Threshold
     */
    int getEnjoymentThreshold(){
        return enjoymentThreshold;
    }
}
