package backend.exception;

public class HrNotFoundException extends RuntimeException {
    public HrNotFoundException(Long id)  {
        super("could not find id " + id);
    }

    public HrNotFoundException(String message){
        super(message);
    }
}


