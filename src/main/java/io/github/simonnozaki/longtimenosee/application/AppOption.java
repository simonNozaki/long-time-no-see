package io.github.simonnozaki.longtimenosee.application;

import io.vavr.collection.Stream;
import io.vavr.control.Try;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 解析したCLI引数を格納するレコードクラス
 * CLIのモードとオプション引数を持つ
 */
public record AppOption(CliModes mode, Map<String, List<String>> optionArgs) {
    /**
     * オプション引数から安全にIDの値を探す
     */
    public Long getIdOrThrow() {
        var rawId = Stream.ofAll(this.optionArgs.entrySet())
                .find((entry) -> Objects.equals(entry.getKey(), "id"))
                .getOrElseThrow(() -> new InvalidArgumentOptionException("Id required"))
                .getValue()
                .getFirst();

        return Try
                .of(() -> Long.parseLong(rawId))
                .getOrElseThrow(() -> new InvalidArgumentOptionException("Id should be a number"));
    }
}
