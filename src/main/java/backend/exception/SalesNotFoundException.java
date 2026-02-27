package backend.exception;

public class SalesNotFoundException extends RuntimeException {
    public SalesNotFoundException(Long id) {

        super("Vehicle with ID " + id + " not found");
    }
}


