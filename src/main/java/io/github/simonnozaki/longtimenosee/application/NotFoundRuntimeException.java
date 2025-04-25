package io.github.simonnozaki.longtimenosee.application;

/**
 * 操作対象や検索結果がないときに発生する実行時例外
 */
public class NotFoundRuntimeException extends RuntimeException {
    public NotFoundRuntimeException(String message) {
        super(message);
    }
}
