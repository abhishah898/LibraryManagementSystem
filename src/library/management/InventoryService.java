package library.management;
import library.exception.BookAlreadyExistException;
import library.exception.BookNotFoundException;
import library.models.Book;
import java.util.List;

public interface InventoryService {
    void addBook(Book book) throws BookAlreadyExistException;
    void removeBook(String ISBN) throws BookNotFoundException;
    void updateBook(Book book) throws BookNotFoundException;
    boolean isBookAvailable(String isbn);
    Book getBookByISBN(String ISBN) throws BookNotFoundException;
    List<Book> searchByTitle(String title);
    List<Book> searchByAuthor(String author);
    List<Book> getAllBooks();

}
