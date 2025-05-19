package library.management;

import library.exception.BookNotFoundException;
import library.exception.PatronNotFoundException;
import library.models.Transaction;

import java.util.List;

public interface LendingService extends PatronService {
    void borrowBook(String ISBN, String patronId) throws BookNotFoundException, PatronNotFoundException;
    void returnBook(String ISBN, String patronId) throws BookNotFoundException, PatronNotFoundException;
    List<Transaction> getAllTransactions();
}
