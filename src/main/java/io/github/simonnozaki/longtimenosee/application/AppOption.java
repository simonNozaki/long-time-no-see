package io.github.simonnozaki.longtimenosee.application;

import io.vavr.collection.Stream;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalLong;

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

        return safeParseLong(rawId).orElseThrow(() -> new InvalidArgumentOptionException("Id should be a number"));
    }

    /**
     * 文字列 -> Long型への変換をOptionに丸める
     */
    private OptionalLong safeParseLong(String str) {
        try {
            return OptionalLong.of(Long.parseLong(str));
        } catch (NumberFormatException e) {
            return OptionalLong.empty();
        }
    }
}
