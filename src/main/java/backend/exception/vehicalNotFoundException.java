package backend.exception;

public class vehicalNotFoundException extends RuntimeException {
    public vehicalNotFoundException(Long id) {
        super("Vehicle with ID " + id + " not found");
    }
}
