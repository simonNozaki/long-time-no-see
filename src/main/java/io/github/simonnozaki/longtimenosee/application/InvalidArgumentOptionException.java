package io.github.simonnozaki.longtimenosee.application;

/**
 * 予期しないオプション引数が入ってきたときにスローされる実行時例外
 */
public class InvalidArgumentOptionException extends RuntimeException {
    public InvalidArgumentOptionException(String message) {
        super(message);
    }
}
