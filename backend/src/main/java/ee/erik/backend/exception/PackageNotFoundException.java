package ee.erik.backend.exception;

public class PackageNotFoundException extends RuntimeException {
    
    public PackageNotFoundException(Long id) {
        super("Package with id: " + id + " not found");
    }
}
