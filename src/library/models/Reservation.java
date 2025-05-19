package library.models;

import java.time.LocalDate;

public class Reservation {
    private final String reservationId;
    private final Patron patron;
    private final Book book;
    private final LocalDate reservationDate;

    public Reservation(String reservationId, Patron patron, Book book, LocalDate reservationDate) {
        this.reservationId = reservationId;
        this.patron = patron;
        this.book = book;
        this.reservationDate = reservationDate;
    }

    public String getReservationId() {
        return reservationId;
    }

    public Patron getPatron() {
        return patron;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId='" + reservationId + '\'' +
                ", patron=" + patron +
                ", book=" + book +
                ", reservationDate=" + reservationDate +
                '}';
    }
}
