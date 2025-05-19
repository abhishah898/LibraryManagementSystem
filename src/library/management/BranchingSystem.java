package library.management;

import library.exception.BookAlreadyExistException;
import library.exception.BookNotFoundException;
import library.exception.BranchAlreadyExistException;
import library.exception.BranchNotFoundException;
import library.models.Book;
import library.models.Branch;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class BranchingSystem implements BranchingService{
    private static final Logger logger = Logger.getLogger(BranchingSystem.class.getName());
    private final Map<String, Branch> branches;

    public BranchingSystem() {
        this.branches = new HashMap<>();
    }

    @Override
    public void addBranch(Branch branch) throws BranchAlreadyExistException {
        if (branches.containsKey(branch.getBranchId())) {
            logger.warning("Branch already exists with ID: " + branch.getBranchId());
            throw new BranchAlreadyExistException("Branch with ID " + branch.getBranchId() + " already exists.");
        }
        branches.put(branch.getBranchId(), branch);
        logger.info("Branch added: " + branch);
    }

    @Override
    public void transferBooks(String sourceBranchID, String destBranchID, String ISBN) throws BookNotFoundException {
        Branch sourceBranch = branches.get(sourceBranchID);
        Branch destBranch = branches.get(destBranchID);

        if (sourceBranch == null || destBranch == null) {
            logger.warning("Source or Destination branch not found.");
            throw new BranchNotFoundException("Source or Destination branch not found.");
        }

        Book book = sourceBranch.getBookByISBN(ISBN);
        if (book == null) {
            logger.warning("Book not found in source branch: " + ISBN);
            throw new BookNotFoundException("Book with ISBN " + ISBN + " not found in source branch.");
        }

        if (destBranch.getBooks().containsKey(ISBN)) {
            logger.warning("Book already exists in destination branch: " + ISBN);
            throw new BookAlreadyExistException("Book with ISBN " + ISBN + " already exists in destination branch.");
        }

        sourceBranch.removeBookByISBN(ISBN);
        destBranch.addBook(book);
        logger.info("Book transferred: " + ISBN + " from " + sourceBranch.getBranchName() + " to " + destBranch.getBranchName());
    }

    @Override
    public Branch getBranchById(String branchID) {
        if (!branches.containsKey(branchID)) {
            logger.warning("Branch already exists with ID: " + branchID);
            throw new BranchAlreadyExistException("Branch with ID " + branchID + " already exists.");
        }
        return branches.get(branchID);
    }

    @Override
    public boolean isBranchAvailable(String branchId) {
        return branches.containsKey(branchId);
    }

}
