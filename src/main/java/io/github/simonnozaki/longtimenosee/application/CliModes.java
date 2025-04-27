package io.github.simonnozaki.longtimenosee.application;

import io.vavr.control.Try;

import java.util.Optional;

/**
 * CLIの起動モード定義型
 */
public enum CliModes {
    LIST,
    FIND,
    CREATE,
    DELETE,
    UPDATE,
    NONE;

    /**
     * 引数文字列が定義されていない列挙型に相当する場合に初期値を返すラップ関数
     */
    public static CliModes valueOrDefault(String rawMode) {
        return Try
                .of(() -> CliModes.valueOf(rawMode))
                .getOrElse(NONE);
    }
}
