package library.management;

import library.models.Patron;

public interface PatronService {
    void addPatron(Patron patron);
    void updatePatron(Patron patron);
}
