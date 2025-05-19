package library.management;

import library.exception.BookNotFoundException;
import library.exception.PatronNotFoundException;
import library.models.Book;
import library.models.Patron;
import library.models.Transaction;

import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;

public class LendingSystem implements LendingService {

    private static final Logger logger = Logger.getLogger(LendingSystem.class.getName());

    private final InventoryService inventoryService;
    private final Map<String, Patron> patronsById;
    private final List<Transaction> transactions;

    public LendingSystem(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.patronsById = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    @Override
    public void addPatron(Patron patron) {
        patronsById.put(patron.getPatronId(), patron);
        logger.info("Added patron: " + patron);
    }

    @Override
    public void updatePatron(Patron patron) throws PatronNotFoundException {
        if (!patronsById.containsKey(patron.getPatronId())) {
            logger.warning("Attempted to update non-existing patron: " + patron.getPatronId());
            throw new PatronNotFoundException("Patron with ID " + patron.getPatronId() + " not found.");
        }
        patronsById.put(patron.getPatronId(), patron);
        logger.info("Updated patron: " + patron);
    }

    public Patron getPatronById(String patronId) throws PatronNotFoundException {
        Patron patron = patronsById.get(patronId);
        if (patron == null) {
            throw new PatronNotFoundException("Patron with ID " + patronId + " not found.");
        }
        return patron;
    }

    @Override
    public void borrowBook(String ISBN, String patronId) throws BookNotFoundException, PatronNotFoundException {
        Book book = inventoryService.getBookByISBN(ISBN);
        if (!book.isAvailable()) {
            logger.warning("Book not available for borrowing: " + ISBN);
            throw new BookNotFoundException("Book with ISBN " + ISBN + " is not available.");
        }
        Patron patron = getPatronById(patronId);
        book.setAvailable(false);
        patron.getBorrowedBooks().add(book);
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), book, patron, LocalDate.now());
        transactions.add(transaction);
        logger.info("Book borrowed: " + book + " by patron: " + patron.getName());
    }

    @Override
    public void returnBook(String ISBN, String patronId) throws BookNotFoundException, PatronNotFoundException {
        Patron patron = getPatronById(patronId);
        Optional<Book> borrowedBookOpt = patron.getBorrowedBooks().stream()
                .filter(book -> book.getISBN().equals(ISBN))
                .findFirst();

        if (borrowedBookOpt.isEmpty()) {
            logger.warning("Patron " + patronId + " does not have book with ISBN " + ISBN);
            throw new BookNotFoundException("Patron " + patronId + " did not borrow book with ISBN " + ISBN);
        }

        Book book = borrowedBookOpt.get();
        book.setAvailable(true);
        patron.getBorrowedBooks().remove(book);

        // Update the corresponding transaction return date
        transactions.stream()
                .filter(tx -> tx.getBook().getISBN().equals(ISBN) && tx.getPatron().getPatronId().equals(patronId) && tx.getReturnDate() == null)
                .findFirst()
                .ifPresent(tx -> tx.setReturnDate(LocalDate.now()));

        logger.info("Book returned: " + book + " by patron: " + patron.getName());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return Collections.unmodifiableList(transactions);
    }
}
