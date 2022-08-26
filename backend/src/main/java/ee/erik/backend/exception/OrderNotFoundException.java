package ee.erik.backend.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Order with id: " + id + " not found");
    }
}
