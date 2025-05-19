package library.management;

import library.exception.BookAlreadyExistException;
import library.exception.BookNotFoundException;
import library.models.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InventorySystem implements InventoryService {

    private static final Logger logger = Logger.getLogger(InventorySystem.class.getName());
    private final Map<String, Book> books;

    public InventorySystem() {
        this.books = new HashMap<>();
    }

    @Override
    public void addBook(Book book) throws BookAlreadyExistException {
        if (books.containsKey(book.getISBN())) {
            logger.warning("Book with ISBN:" + book.getISBN() + " is already present.");
            throw new BookAlreadyExistException("Book with ISBN: " + book.getISBN() + " already exist.");
        }
        books.put(book.getISBN(), book);
        logger.info("Added book: " + book);
    }

    @Override
    public void removeBook(String ISBN) throws BookNotFoundException {
        if (!books.containsKey(ISBN)) {
            logger.warning("Attempted to remove non-existing book ISBN: " + ISBN);
            throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
        }
        books.remove(ISBN);
        logger.info("Removed book with ISBN: " + ISBN);
    }

    @Override
    public void updateBook(Book updatedBook) throws BookNotFoundException {
        String ISBN = updatedBook.getISBN();
        if (!books.containsKey(ISBN)) {
            logger.warning("Attempted to update non-existing book ISBN: " + ISBN);
            throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
        }
        books.put(ISBN, updatedBook);
        logger.info("Updated book: " + updatedBook);
    }

    @Override
    public Book getBookByISBN(String ISBN) throws BookNotFoundException {
        Book book = books.get(ISBN);
        if (book == null) {
            logger.warning("Book not found for ISBN: " + ISBN);
            throw new BookNotFoundException("Book with ISBN " + ISBN + " not found.");
        }
        return book;
    }

    @Override
    public List<Book> searchByTitle(String title) {
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchByAuthor(String author) {
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public boolean isBookAvailable(String isbn) {
        return books.containsKey(isbn);
    }
}
