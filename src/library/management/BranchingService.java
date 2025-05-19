package library.management;

import library.exception.BookNotFoundException;
import library.exception.BranchAlreadyExistException;
import library.models.Branch;

public interface BranchingService {
    void addBranch(Branch branch) throws BranchAlreadyExistException;
    void transferBooks(String sourceBranchID, String destBranchID, String ISBN) throws BookNotFoundException;
    Branch getBranchById(String branchID);
    boolean isBranchAvailable(String branchId);
}
