public class AccountExistedException extends RuntimeException {

    public AccountExistedException(String message) {
        super(message);
    }

    public AccountExistedException() { super(); }

}
