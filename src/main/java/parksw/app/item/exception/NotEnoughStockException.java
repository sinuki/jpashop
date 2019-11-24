package parksw.app.item.exception;

/**
 * NotEnoughStockException
 * author: sinuki
 * createdAt: 2019/11/24
 **/
public class NotEnoughStockException extends RuntimeException {

    public NotEnoughStockException(String message) {
        super(message);
    }

    public NotEnoughStockException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughStockException(Throwable cause) {
        super(cause);
    }
}
