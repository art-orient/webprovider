package by.art.webprovider.exception;

public class ProviderDatabaseException extends Exception {

    public ProviderDatabaseException() {
    }

    public ProviderDatabaseException(String message) {
        super(message);
    }

    public ProviderDatabaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProviderDatabaseException(Throwable cause) {
        super(cause);
    }
}
