package io.github.simonnozaki.longtimenosee;

import io.github.simonnozaki.longtimenosee.adapter.ArgumentParser;
import io.github.simonnozaki.longtimenosee.adapter.ConsolePrinter;
import io.github.simonnozaki.longtimenosee.application.AppOption;
import io.github.simonnozaki.longtimenosee.application.InvalidArgumentOptionException;
import io.github.simonnozaki.longtimenosee.domain.note.UseCase;
import io.github.simonnozaki.longtimenosee.domain.note.usecase.CreateUseCaseInput;
import io.github.simonnozaki.longtimenosee.domain.note.usecase.UpdateUseCaseInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * アプリケーションのユースケースに相当するクラス
 * オプション引数に応じてモードおよび引数を処理する
 */
@Component
@Slf4j
public class LongTimeNoSeeRunner implements ApplicationRunner {
    @Autowired
    private UseCase useCase;
    @Autowired
    private ConsolePrinter printer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        var option = ArgumentParser.parse(args);
        switch (option.mode()) {
            case LIST -> printAll();
            case CREATE -> createNote(option);
            case FIND -> printById(option);
            case DELETE -> deleteById(option);
            case UPDATE -> updateById(option);
            default -> log.info("Nothing processed");
        }
    }

    private void printAll() {
        var notes = useCase.listAll();
        printer.printForTable(notes);
    }

    private void printById(AppOption option) {
        var id = option.getIdOrThrow();
        var note = useCase.findById(id);
        printer.printForTable(note);
    }

    /**
     * 新規メモを作成する
     */
    private void createNote(AppOption option) {
        if (!option.optionArgs().containsKey("content")) {
            throw new InvalidArgumentOptionException("Content required when running with 'create' mode");
        }
        var content = Optional
                .ofNullable(option.optionArgs().get("content").getFirst())
                .orElse("");
        var input = new CreateUseCaseInput(content);
        var output = useCase.create(input);
        printer.printForTable(output);
    }

    /**
     * ID指定でメモを削除する
     */
    private void deleteById(AppOption option) {
        var id = option.getIdOrThrow();
        var output = useCase.deleteById(id);
        printer.printForTable(output);
    }

    /**
     * ID指定でメモを編集する
     */
    private void updateById(AppOption option) {
        var id = option.getIdOrThrow();
        var content = option.optionArgs().get("content").stream()
                .findFirst()
                .orElseThrow(() -> new InvalidArgumentOptionException("Content required when updating a note"));
        var input = new UpdateUseCaseInput(id, content);
        var output = useCase.updateById(input);
        printer.printForTable(output);
    }
}
