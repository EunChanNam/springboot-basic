package kr.co.programmers.springbootbasic.exception;

public class NoValidDiscountPrice extends RuntimeException {
    public NoValidDiscountPrice(String message) {
        super(message);
    }
}
