package backend.exception;

public class SalaryNotFoundException extends RuntimeException {
    public SalaryNotFoundException(Long id) {

        super("salary with ID " + id + " not found");
    }
}


