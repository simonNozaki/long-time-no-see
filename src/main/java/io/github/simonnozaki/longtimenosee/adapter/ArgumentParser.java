package io.github.simonnozaki.longtimenosee.adapter;

import io.github.simonnozaki.longtimenosee.application.AppOption;
import io.github.simonnozaki.longtimenosee.application.CliModes;
import org.springframework.boot.ApplicationArguments;

import java.util.stream.Collectors;

/**
 * フレームワーク機能からアプリケーション引数を解析する機能を提供する
 */
public class ArgumentParser {
    public static AppOption parse(ApplicationArguments args) {
        // オプション引数ではない引数の中から先頭一つを引く
        var rawMode = args.getNonOptionArgs().stream()
                .findFirst()
                .orElse("")
                .toUpperCase();
        var mode = CliModes.valueOrDefault(rawMode);
        var optionArgs = args.getOptionNames().stream().collect(Collectors.toMap((name) -> name, args::getOptionValues));
        return new AppOption(mode, optionArgs);
    }
}
