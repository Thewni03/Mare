package backend.exception;

public class AdminNotFoundException extends RuntimeException {
    public AdminNotFoundException(Long id)  {
        super("could not find id " + id);
    }

    public AdminNotFoundException(String message){
        super(message);
    }
}


