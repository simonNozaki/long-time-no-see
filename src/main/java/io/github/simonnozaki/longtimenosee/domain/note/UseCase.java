package io.github.simonnozaki.longtimenosee.domain.note;

import io.github.simonnozaki.longtimenosee.application.NotFoundRuntimeException;
import io.github.simonnozaki.longtimenosee.domain.note.usecase.CreateUseCaseInput;
import io.github.simonnozaki.longtimenosee.domain.note.usecase.UseCaseOutput;
import io.github.simonnozaki.longtimenosee.domain.note.usecase.UpdateUseCaseInput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * アプリケーションユースケースクラス
 */
@Component
@Slf4j
public class UseCase {
    @Autowired
    private Repository repository;

    public List<UseCaseOutput> listAll() {
        var notes = repository.findAll();
        final var message = "%d records found:".formatted(notes.size());
        log.info(message);

        return notes.stream().map(this::toOutput).toList();
    }

    public UseCaseOutput findById(Long id) throws NotFoundRuntimeException {
        var note = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundRuntimeException("Note id %d not found".formatted(id)));
        log.info("Record id: %d found".formatted(id));

        return toOutput(note);
    }

    public UseCaseOutput create(CreateUseCaseInput input) {
        var content = input.getContent();
        Entity note = Entity.builder().content(content).build();
        repository.save(note);
        log.info("Note saved");

        return toOutput(note);
    }

    public UseCaseOutput deleteById(Long id) throws NotFoundRuntimeException {
        var target = repository.findById(id);
        if (target.isEmpty()) {
            throw new NotFoundRuntimeException("Note id: %d not found".formatted(id));
        }
        repository.deleteById(id);
        final var message = "Note id: %d deleted".formatted(id);
        log.info(message);

        return toOutput(target.orElseThrow());
    }

    public UseCaseOutput updateById(UpdateUseCaseInput input) throws NotFoundRuntimeException {
        var id = input.getId();
        var target = repository
                .findById(id)
                .orElseThrow(() -> new NotFoundRuntimeException("Note id %d not found".formatted(id)));
        target.setContent(input.getContent());
        repository.save(target);
        final var message = "Note id: %d updated".formatted(id);
        log.info(message);

        return toOutput(target);
    }

    private UseCaseOutput toOutput(Entity note) {
        return UseCaseOutput.builder()
                .id(note.getId())
                .content(note.getContent())
                .createdAt(note.getCreatedAt())
                .updatedAt(note.getUpdatedAt())
                .build();
    }
}
