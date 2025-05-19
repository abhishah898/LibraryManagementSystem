package library.app;

import library.exception.BookNotFoundException;
import library.exception.BranchNotFoundException;
import library.management.*;
import library.models.Book;
import library.models.Branch;
import library.models.Patron;
import library.models.Transaction;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

public class LibraryApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = Logger.getLogger(LendingSystem.class.getName());

    public static void main(String[] args) {
        // Define all services
        InventoryService inventorySystem = new InventorySystem();
        LendingService lendingSystem = new LendingSystem(inventorySystem);
        BranchingService branchSystem = new BranchingSystem();

        try {
            while (true) {
                System.out.println("\n--- Library Management System ---");
                System.out.println("1. Add Book in Inventory");
                System.out.println("2. Remove Book from Inventory");
                System.out.println("3. Update Book in Inventory");
                System.out.println("4. Search Book in Inventory");
                System.out.println("5. Add Patron");
                System.out.println("6. Update Patron");
                System.out.println("7. Borrow Book");
                System.out.println("8. Return Book");
                System.out.println("9. View All Books");
                System.out.println("10. View All Transactions");
                System.out.println("11. Add Branch");
                System.out.println("12. Add Book in Branch");
                System.out.println("13. Transfer Book Between Branches");
                System.out.println("14. View Branch Details");
                System.out.println("0. Exit");
                System.out.print("Enter choice: ");

                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> addBook(inventorySystem);
                    case 2 -> removeBook(inventorySystem);
                    case 3 -> updateBook(inventorySystem);
                    case 4 -> searchBook(inventorySystem);
                    case 5 -> addPatron(lendingSystem);
                    case 6 -> updatePatron(lendingSystem);
                    case 7 -> borrowBook(lendingSystem);
                    case 8 -> returnBook(lendingSystem);
                    case 9 -> viewAllBooks(inventorySystem);
                    case 10 -> viewAllTransactions(lendingSystem);
                    case 11 -> addBranch(branchSystem);
                    case 12 -> addBookInBranch(inventorySystem, branchSystem);
                    case 13 -> transferBookBetweenBranches(branchSystem);
                    case 14 -> viewBranchDetails(branchSystem);
                    case 0 -> {
                        System.out.println("Exiting... Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addBook(InventoryService inventory) {
        System.out.print("Enter Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.print("Enter Publication Year: ");
        int year = Integer.parseInt(scanner.nextLine());

        Book book = new Book(title, author, isbn, year);
        inventory.addBook(book);
        System.out.println("Book added successfully.");
    }

    private static void addPatron(LendingService lendingSystem) {
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact Info: ");
        String contactInfo = scanner.nextLine();

        Patron patron = new Patron(patronId, name, contactInfo);
        lendingSystem.addPatron(patron);
        System.out.println("Patron added successfully.");
    }

    private static void updatePatron(LendingService lendingSystem) {
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Enter Updated Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Updated Contact Info: ");
        String contactInfo = scanner.nextLine();

        Patron patron = new Patron(patronId, name, contactInfo);
        lendingSystem.updatePatron(patron);
        System.out.println("Patron updated successfully.");
    }

    private static void borrowBook(LendingService lendingSystem) {
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        try {
            lendingSystem.borrowBook(patronId, isbn);
            System.out.println("Book borrowed successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void returnBook(LendingService lendingSystem) {
        System.out.print("Enter Patron ID: ");
        String patronId = scanner.nextLine();
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();

        try {
            lendingSystem.returnBook(patronId, isbn);
            System.out.println("Book returned successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void removeBook(InventoryService inventory) {
        System.out.print("Enter ISBN of the book to remove: ");
        String isbn = scanner.nextLine();
        try {
            inventory.removeBook(isbn);
            System.out.println("Book removed successfully.");
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateBook(InventoryService inventory) {
        System.out.print("Enter ISBN of the book to update: ");
        String isbn = scanner.nextLine();
        try {
            Book book = inventory.getBookByISBN(isbn);
            System.out.print("Enter new Title: ");
            book.setTitle(scanner.nextLine());
            System.out.print("Enter new Author: ");
            book.setAuthor(scanner.nextLine());
            System.out.print("Enter new Publication Year: ");
            book.setPublicationYear(Integer.parseInt(scanner.nextLine()));
            inventory.updateBook(book);
            System.out.println("Book updated successfully.");
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void searchBook(InventoryService inventory) {
        System.out.print("Enter ISBN to search: ");
        String isbn = scanner.nextLine();
        try {
            Book book = inventory.getBookByISBN(isbn);
            System.out.println(book);
        } catch (BookNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewAllBooks(InventoryService inventory) {
        List<Book> books = inventory.getAllBooks();
        if (books.isEmpty()) {
            System.out.println("No books available.");
        } else {
            books.forEach(System.out::println);
        }
    }

    private static void viewAllTransactions(LendingService lendingSystem) {
        List<Transaction> transactions = lendingSystem.getAllTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            transactions.forEach(System.out::println);
        }
    }

    private static void addBranch(BranchingService branchService) {
        System.out.print("Enter Branch ID: ");
        String branchId = scanner.nextLine();
        System.out.print("Enter Branch Name: ");
        String branchName = scanner.nextLine();

        try {
            Branch branch = new Branch(branchId, branchName);
            branchService.addBranch(branch);
            System.out.println("Branch added successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void transferBookBetweenBranches(BranchingService branchService) {
        System.out.print("Enter Source Branch ID: ");
        String sourceBranchId = scanner.nextLine();
        System.out.print("Enter Destination Branch ID: ");
        String destinationBranchID = scanner.nextLine();
        System.out.print("Enter ISBN of the Book to Transfer: ");
        String isbn = scanner.nextLine();

        try {
            branchService.transferBooks(sourceBranchId, destinationBranchID, isbn);
            System.out.println("Book transferred successfully.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void viewBranchDetails(BranchingService branchService) {
        System.out.print("Enter Branch ID: ");
        String branchId = scanner.nextLine();

        Branch branch = branchService.getBranchById(branchId);
        if (branch != null) {
            System.out.println(branch);
        } else {
            System.out.println("Branch not found.");
        }
    }
    private static void addBookInBranch(InventoryService inventoryService, BranchingService branchService)  {
        System.out.print("Enter ISBN: ");
        String isbn = scanner.nextLine();
        System.out.println("Enter BranchID: ");
        String branchId = scanner.nextLine();

        try {
            if (!inventoryService.isBookAvailable(isbn)) {
                logger.warning("Book with ISBN: " + isbn + " not exist in inventory.");
                throw new BookNotFoundException("Book with ISBN: " + isbn + " not exist in inventory.");
            }
            if (!branchService.isBranchAvailable(branchId)) {
                logger.warning("Branch with branch ID: " + branchId + " not exist.");
                throw new BranchNotFoundException("Branch with branch ID: " + branchId + " not exist.");
            }
            Book book = inventoryService.getBookByISBN(isbn);
            Branch branch = branchService.getBranchById(branchId);
            branch.addBook(book);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
