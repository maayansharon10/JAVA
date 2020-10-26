
/**
 * This class represents a library, which hold a collection of books. Book can be registered to a library
 * (but not unregistered from it).
 * Patrons can register at the library to be able to check out books,
 * if a copy of the requested book is available.
 * patron can also get a recommendation, for the book in the library he will enjoy the most, if such exist.
 */
class Library {
    /*----=  data members  =-----*/

    /**
     * The maximal number of books this library can hold.
     */
    int libraryMaxBookCapacity;

    /**
     * Current number of books the library owns
     */
    int currentNumberBooks = 0;

    /**
     * //The maximal number of books this library allows a single patron to borrow at the same time.
     */
    int libraryMaxBorrowedBooks;

    /**
     * The maximal number of registered patrons this library can handle.
     */
    int libraryMaxPatronCapacity;

    /**
     * Array represents a shelf which holds all the books in the library
     */
    Book[] shelf; // like a shelf holds all books in library

    /**
     * counter - how many books in library (on shelf) at this moment
     */

    int shelfCounter = 0;
    /**
     * array with all patron registereg to library
     */
    Patron[] patronArray;

    /**
     * counter - how many patron registered to library at this moment
     */
    int patronCounter = 0;

    /**
     * how many books each patron borrowed from library/
     * a histogram, place of the array is patronId, and value repesents how many books she's
     * currently borrowing.
     * (example - patronId = 0 holds 3 books from this library so borrowPatronStatusArray[0] = 3 .)
     */
    int[] borrowPatronStatusArray;

    /*----=  Constructors  =-----*/

    /**
     * Creates a new book with the given characteristic.
     *
     * @param maxBookCapacity   The maximal number of books this library can hold.
     * @param maxBorrowedBooks  The maximal number of books this library allows a
     *                          single patron to borrow at the same time.
     * @param maxPatronCapacity The maximal number of registered patrons this library can handle.
     */
    Library(int maxBookCapacity, int maxBorrowedBooks, int maxPatronCapacity) {
        libraryMaxBookCapacity = maxBookCapacity;
        libraryMaxBorrowedBooks = maxBorrowedBooks;
        libraryMaxPatronCapacity = maxPatronCapacity;
        shelf = new Book[libraryMaxBookCapacity];
        patronArray = new Patron[libraryMaxPatronCapacity];
        borrowPatronStatusArray = new int[libraryMaxPatronCapacity];
    }

    /*===================  Instance Methods  ==================*/

    /*------- Book Methods ----------*/

    /**
     * add book to library (to shelf), update number of books in library,
     * set his id number, same as his spot on shelf (end of array)
     *
     * @param book of type Book
     */
    void addBookToShelf(Book book) {
        shelf[shelfCounter] = book;
        book.setBookId(shelfCounter);
        currentNumberBooks += 1;
    }

    /**
     * check if book is currently in library (shelf).
     * @param book - object we would like to check
     * @return int represent place of book on shelf (book id) , -1 if is not registered
     */
    int isBookRegisteredAlready(Book book) {
        // go over all books in library so far and check if the book exist in library
        for (int i = 0; i < shelfCounter; i++) {
            if (shelf[i] == book) {
                return i;
            }
        }return -1; // went through all existing books, and book is not there
    }

    /**
     * Adds the given book to this library, if there is place available, and it isn't already in the library.
     * @param book - The book to add to this library
     * @return a non-negative id number for the book if there was a spot and the book was successfully
     * added, or if the book was already in the library; a negative number otherwise.
     * the number is the book's place in the array
     */
    int addBookToLibrary(Book book) {
        if (shelfCounter < libraryMaxBookCapacity) { // if the library can hold another book

            if ((isBookRegisteredAlready(book)) >= 0) { //if book is registered already
                return (isBookRegisteredAlready(book));
            } else { // if book is not registered yet and doesn't exist yet in library add it to shelf
                addBookToShelf(book); //adding book to library
                int bookId = book.getBookId();
                shelfCounter += 1;
                return bookId; //return bookId number
            }
        } else if (shelfCounter == libraryMaxBookCapacity) { //library is full - check if the book exists
            int bookId2 = (isBookRegisteredAlready(book));
            return bookId2; // non- negative if exists , negative otherwise
        } return -1; // book cannot be added to library
    }

    /**
     * Returns true if the given number is an id of some book in the library, false otherwise.
     * @param bookId - The id to check.
     * @return true if the given number is an id of some book in the library, false otherwise.
     */
    boolean isBookIdValid(int bookId) {
        if (bookId > libraryMaxBookCapacity) { // if book id is not valid
            return false;
            // if valid - continue checking
        }if (shelf[bookId] != null) {// if there's a book at this place on shelf
            return true;
        } else {// there's no book at this id
            return false;
        }
    }

    /**
     * Returns the non-negative id number of the given book if it is owned by this library, -1 otherwise.
     * @param book - The book for which to find the id number.
     * @return a non-negative id number of the given book if it is owned by this library, -1 otherwise.
     */
    int getBookId(Book book) {
        return book.getBookId();
    }

    /**
     * Returns true if the book with the given id is available, false otherwise.
     * @param bookId -  The id number of the book to check.
     * @return true if the book with the given id is available, false otherwise.
     */
    boolean isBookAvailable(int bookId) {
        if (shelf[bookId] == null) { //if the book doesn't exist
            return false;
        }if (shelf[bookId].getCurrentBorrowerId() >= 0) { //some patron holds this book so not available
            return false;
        } else { // no patron holds the book (
            return true;
        }
    }


    /*------- Patron Methods ----------*/

    /**
     * Registers the given Patron to this library, if there is a spot available.
     *
     * @param patron -The patron to register to this library.
     * @return a non-negative id number for the patron if there was a spot and the patron was successfully
     * registered, a negative number otherwise.
     */

    int registerPatronToLibrary(Patron patron) {
        if (patronCounter <= libraryMaxPatronCapacity) { // if the library didn't exceed it's ma patrons
            // - check if patron in library
            int patronNumber = isPatronRegisteredAlready(patron);
            if (patronNumber >= 0) { //if patron is registered already
                return patronNumber;
            } else {
                if (patronCounter == libraryMaxPatronCapacity) {
                    // if patron is not registered yet - but capacity is full .
                    return -1;
                } else { // if patron is not registered yet - and capacity is  not full - register
                    registerPatronHelper(patron);
                    int newPatronId = getPatronId(patron);
                    return newPatronId;
                }
            }
        } return -1;// else library cannot add another patron so patron was not registered.

    }

    /**
     * registerPatronHelper - add a patron to patronArray acourding to it's id Number, then update counter
     * @param patron - the patron we wish to register
     */

    void registerPatronHelper(Patron patron) {
        patronArray[patronCounter] = patron;
        patronCounter += 1;
    }

    /**
     * isPatronRegisteredAlready - checks if patron already exists in library.
     * @param patron - the patron we wish to check
     * @return non negative number representing patron's id if registered, negative number if not registered.
     */
    int isPatronRegisteredAlready(Patron patron) {
        // go over all patrons registered in library so far and check if this patron is among them.
        for (int i = 0; i < patronCounter; i++) {
            if (patronArray[i] == patron) {
                return i; // the patron registered in library, return it's number
            }
        }return -1; // went through all existing books, and book is not there
    }

    /**
     * Returns true if the given number is an id of a patron in the library, false otherwise.
     * @param patronId - The id to check.
     * @return true if the given number is an id of a patron in the library, false otherwise.
     */
    boolean isPatronIdValid(int patronId) {
        if (patronId > libraryMaxPatronCapacity) { // if patron's id is not valid
            return false;
        } else {  // patron's id is valid - continue checking
            if (patronArray[patronId] != null) { // patron exists in library
                return true;
            } else { // id number is valid, but there's no such patron in library
                return false;
            }
        }
    }

    /*------- Library's activity-related Methods ----------*/

    /**
     * helper function, cheking both book and patron are valid
     * @param bookId  - int
     * @param patronId - int
     * @return true if both valid, false other wise
     */
    boolean bothBookAndPatronValid(int bookId, int patronId) {
        // check book id is valid
        if (!isBookIdValid(bookId)) { // if book is not valid
            return false;
        }if (!isPatronIdValid(patronId)){  //if patron is not valid
            return false;
        }else
            {return true;
            }
    }

    /**
     * Marks the book with the given id number as borrowed by the patron with the given patron id,
     * if this book is available,
     * the given patron isn't already borrowing the maximal number of books allowed,
     * and if the patron will enjoy this book.
     * @param bookId - The id number of the book to borrow.
     * @param patronId - The id number of the patron that will borrow the book.
     * @return -true if the book was borrowed successfully, false otherwise.
     */
    boolean borrowBook(int bookId, int patronId) {

        if (!bothBookAndPatronValid(bookId, patronId)) { // if at least one of them isn't valid
            // - it cannot be borrowed
            return false;
        }if ((isBookRegisteredAlready(shelf[bookId]) < 0) ||
                (isPatronRegisteredAlready(patronArray[patronId]) < 0)) {
            // if bookId is not valid or patronId is not valid - book cannot be borrowed
            return false;

        } else { //both bookId and patronId are valid
            //if patron is allowed to borrow this book and book is available
            if (patronIsAllowedToBorrowBook(patronId) && isBookAvailable(bookId)) {

                // if patron will enjoy book
                if (patronArray[patronId].willEnjoyBook(shelf[bookId])) {
                    markBookAsBorrowedByPatron(patronId, bookId); // mark book as borrowed
                    return true; // book was borrowed successfully.
                }

            } else { // patron is not allowed to borrow this book or book is not available.
                return false; // book was not successfully borrowed.
            }
        }return false;
    }

    /**
     * helper function for borrowBook
     * assuming patron is registered to library and book exist in library.
     * check if patron is allowed to borrow this book.
     * @param patronId - patron we want to check
     * @return true if is allowed to borrow book, false other wise
     */
    boolean patronIsAllowedToBorrowBook(int patronId){
        if (borrowPatronStatusArray[patronId] < libraryMaxBorrowedBooks){
            // patron is allowed to borrow another book since she didn't reach her capacity.
            return true;
        }return false; // patron already holds the max amount of books she's allowed
    }


    /**
     * helper funcion for borrowBook.
     * assuming patron is registered to library and book exsist in library.
     * update borrowPatronStatusArray and update book's currentBorrowerId.
     * @param patronId - patron wishing to borrow
     * @param bookId - id of the book patron wish to borrow
     */
    void markBookAsBorrowedByPatron(int patronId, int bookId){
        // update borrowPatronStatusArray
        borrowPatronStatusArray[patronId] +=1; // add +1 to sign that patron borrowed another book
        // update book's currentBorrowerId
        shelf[bookId].setBorrowerId(patronId);
    }

    /**
     *Return the given book.
     * @param bookId -  The id number of the book to return.
     */
    void returnBook(int bookId){
        // check which patron wishing to return the book
        int patronId = shelf[bookId].getCurrentBorrowerId();
        // mark book as returned
        markBookAsReturnedByPatron(patronId, bookId);
    }

    /**
     *  helper function to returnBook. update Patron's Status and initialize
     *  book's borrower's status.
     * @param patronId The id number of the patron who wish to return the given book.
     * @param bookId The id number of the book to return.
     */
    void markBookAsReturnedByPatron(int patronId, int bookId) {
        // update borrowPatronStatusArray
        borrowPatronStatusArray[patronId] -=1; // subtract -1 to sign that patron returned a book
        // update book's currentBorrowerId to none
        shelf[bookId].setBorrowerId(-1);
    }

    /**
     *Suggest the patron with the given id the book he will enjoy the most, out of all
     * available books he will enjoy, if any such exist.
     * @param patronId -The id number of the patron to suggest the book to.
     * @return - The available book the patron with the given will enjoy the most.
     * Null if no book is available.
     */
    Book suggestBookToPatron(int patronId){
        Patron patron = patronArray[patronId];
        // go through all books in library and check which one patron would like the best
        Book currentBook = shelf[0];
        int bestBookScore = 0 ;
        Book bestBook = null;

        for (int i =0; i<shelfCounter; i++) {

            // check if patron will enjoy book
            if (patron.willEnjoyBook(currentBook)) { //if patron will enjoy book
                int currentBookScore = patron.getBookScore(currentBook);

                // check if patron will enjoy current book more than best book
                if (currentBookScore > bestBookScore) {
                    // update best book
                    bestBook = shelf[i];
                    bestBookScore = patron.getBookScore(currentBook);
                }
            }
        }return bestBook;
    }

    /**
     * Returns the non-negative id number of the given patron if she's registered to this library,
     * -1 otherwise.
     * @param patron - The patron for which to find the id number.
     * @return a non-negative id number of the given patron if she is registered to this library,
     * -1 otherwise.
     */
    int getPatronId(Patron patron) {
        for (int i = 0; i < patronCounter; i++) {
            if (patronArray[i] == patron) {
                return i;
            }
        } return -1;
    }
}

