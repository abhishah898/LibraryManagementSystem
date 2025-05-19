package library.models;
import java.time.LocalDate;

public class Transaction {
    private final String transactionId;
    private final Book book;
    private final Patron patron;
    private final LocalDate borrowDate;
    private LocalDate returnDate;

    public Transaction(String transactionId, Book book, Patron patron, LocalDate borrowDate) {
        this.transactionId = transactionId;
        this.book = book;
        this.patron = patron;
        this.borrowDate = borrowDate;
        this.returnDate = null; // Null until its returned
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Book getBook() {
        return book;
    }

    public Patron getPatron() {
        return patron;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId='" + transactionId + '\'' +
                ", book=" + book +
                ", patron=" + patron +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
