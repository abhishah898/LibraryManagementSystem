package library.models;

import library.exception.BookAlreadyExistException;
import library.exception.BookNotFoundException;

import java.util.Map;
import java.util.logging.Logger;

public class Branch {
    public static final Logger logger = Logger.getLogger(Branch.class.getName());

    private final String branchId;
    private String branchName;
    private Map<String, Book> books;

    public Branch(String branchId, String branchName) {
        this.branchId = branchId;
        this.branchName = branchName;
        this.books = null;
    }

    public String getBranchId() {
        return branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public Map<String, Book> getBooks() {
        return books;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void addBook(Book book) throws BookAlreadyExistException {
        if (books.containsKey(book.getISBN())) {
            logger.warning("Book with ISBN already exists: " + book.getISBN());
            throw new BookAlreadyExistException("Book with ISBN: " + book.getISBN() + " already present.");
        }
        books.put(book.getISBN(), book);
        logger.info(book.getTitle() + " added to the branch " + branchName + ": " );
    }

    public void removeBook(Book book) throws BookNotFoundException {
        if (!books.containsKey(book.getISBN())) {
            logger.warning("Book with ISBN: " + book.getISBN() + " not exist");
            throw new BookNotFoundException("Book with ISBN: " + book.getISBN() + " not exist");
        }
        books.remove(book.getISBN());
        logger.info("Book with ISBN: " + book.getISBN() + " removed successfully.");
    }

    public void removeBookByISBN(String isbn) throws BookNotFoundException {
        if (!books.containsKey(isbn)) {
            logger.warning("Book with ISBN: " + isbn + " not exist");
            throw new BookNotFoundException("Book with ISBN: " + isbn + " not exist");
        }
        books.remove(isbn);
        logger.info("Book with ISBN: " + isbn + " removed successfully.");
    }

    public Book getBookByISBN(String isbn) {
        if (!books.containsKey(isbn)){
            logger.warning("Book with ISBN: " + isbn + " not exist");
            throw new BookNotFoundException("Book with ISBN: " + isbn + " not exist");
        }
        return books.get(isbn);
    }

    @Override
    public String toString() {
        return "Branch{" +
                "branchId='" + branchId + '\'' +
                ", branchName='" + branchName + '\'' +
                ", books=" + books +
                '}';
    }
}
