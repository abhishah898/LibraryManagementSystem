# Library Management System

This project is a command-line based Library Management System implemented in Java, adhering to Object-Oriented Programming (OOP) principles and SOLID design principles. It enables efficient management of books, patrons, branches, and transactions within a library system.

## 📦 Project Structure

```
LibraryManagementSystem/
├── src/
│   └── library/
│       ├── app/
│       │   └── LibraryApp.java
│       ├── exception/
│       │   ├── BookAlreadyExistException.java
│       │   ├── BookNotFoundException.java
│       │   ├── BranchAlreadyExistException.java
│       │   ├── BranchNotFoundException.java
│       │   ├── PatronAlreadyExistException.java
│       │   └── PatronNotFoundException.java
│       ├── management/
│       │   ├── BranchingService.java
│       │   ├── BranchingSystem.java
│       │   ├── InventoryService.java
│       │   ├── InventorySystem.java
│       │   ├── LendingService.java
│       │   └── LendingSystem.java
│       └── models/
│           ├── Book.java
│           ├── Branch.java
│           ├── Patron.java
│           ├── Reservation.java
│           └── Transaction.java
```

## ✅ Features

* **Book Management:** Add, update, remove, and view books in the inventory.
* **Patron Management:** Add and update patron information.
* **Lending Process:** Borrow and return books, maintain transaction logs.
* **Branch Management:** Add branches and manage books within branches.
* **Exception Handling:** Custom exceptions for better error management.
* **Logging:** Uses Java's logging framework for error and transaction logs.

## 🚀 Getting Started

1. **Clone the repository:**

```bash
git clone https://github.com/abhishah898/LibraryManagementSystem
cd LibraryManagementSystem
```

2. **Compile the project:**

```bash
javac -d out/production/LibraryManagementSystem src/library/app/LibraryApp.java
```

3. **Run the application:**

```bash
java -cp out/production/LibraryManagementSystem library.app.LibraryApp
```

## 🛠️ How to Use

* Follow the on-screen menu to perform various operations such as adding books, borrowing books, viewing branches, etc.
* Enter the appropriate input based on the prompts.

## 📦 Class Diagram

![LibraryManagementSystem_ClassDiagram drawio](https://github.com/user-attachments/assets/81092506-be07-4dc6-bdb2-b43bcaab1507)
