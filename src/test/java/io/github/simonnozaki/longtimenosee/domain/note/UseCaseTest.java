package io.github.simonnozaki.longtimenosee.domain.note;

import io.github.simonnozaki.longtimenosee.application.NotFoundRuntimeException;
import io.github.simonnozaki.longtimenosee.domain.note.usecase.UseCaseOutput;
import io.github.simonnozaki.longtimenosee.domain.note.usecase.UpdateUseCaseInput;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@SpringBootTest
public class UseCaseTest {
    @MockitoBean
    private Repository repository;
    @Autowired
    private UseCase useCase;

    @Nested
    @DisplayName("#listAll")
    class ListAllUseCase {
        @Test
        void shouldEmptyListWhenNoEntities() {
            Mockito.when(repository.findAll()).thenReturn(List.of());
            var notes = useCase.listAll();
            assertEquals(0, notes.size());
        }

        @Test
        void shouldListSomeEntities() {
            Mockito.when(repository.findAll()).thenReturn(List.of(
                    new Entity(1L, "first note", "2025-04-25T13:00:00.000", "2025-04-25T13:00:00.000"),
                    new Entity(2L, "second note", "2025-04-25T13:00:00.000", "2025-04-25T13:00:00.000")
            ));
            var notes = useCase.listAll();

            var note1 = Stream.ofAll(notes.stream()).find(output -> output.getId() == 1L);
            var note2 = Stream.ofAll(notes.stream()).find(output -> output.getId() == 2L);
            Function<Option<UseCaseOutput>, Consumer<String>> createNullAndContentChecker = (note) -> (content) -> {
                assertNotNull(note.getOrNull());
                assertThat(note.getOrElseThrow(RuntimeException::new), hasProperty("content", is(content)));
            };
            createNullAndContentChecker.apply(note1).accept("first note");
            createNullAndContentChecker.apply(note2).accept("second note");
        }
    }

    @Nested
    @DisplayName("#findById")
    class FindByIdUseCase {
        @Test
        void shouldThrowRuntimeException() {
            Mockito.when(repository.findById(Mockito.any())).thenReturn(Optional.empty());
            assertThrows(
                    NotFoundRuntimeException.class,
                    () -> useCase.findById(1L)
            );
        }

        @Test
        void shouldGetTargetById() {
            var mockResult = Optional.of(
                    new Entity(3L, "Long time no see", "2025-04-25T13:00:00.000", "2025-04-25T13:00:00.000")
            );
            Mockito.when(repository.findById(3L)).thenReturn(mockResult);

            var target = useCase.findById(3L);
            assertEquals("Long time no see", target.getContent());
        }
    }

    @Nested
    @DisplayName("#create")
    class CreateUseCase {
        @Test
        void shouldSave() {
            var entity = Entity.builder().content("new note").build();
            assertDoesNotThrow(() -> repository.save(entity));
        }
    }

    @Nested
    @DisplayName("#updateById")
    class UpdateById {
        @Test
        void targetShouldExist() {
            assertThrowsExactly(NotFoundRuntimeException.class, () -> {
                var req = new UpdateUseCaseInput(1L, "updated note");
                useCase.updateById(req);
            });
        }

        @Test
        void shouldUpdate() {
            var mockResult = Optional.of(
                    new Entity(1L, "Long time no see", "2025-04-25T13:00:00.000", "2025-04-25T13:00:00.000")
            );
            Mockito.when(repository.findById(1L)).thenReturn(mockResult);
            assertAll(
                    () -> useCase.updateById(new UpdateUseCaseInput(1L, "updated note")),
                    () -> {
                        var target = repository.findById(1L);
                        assertTrue(target.isPresent());
                        assertEquals("updated note", target.get().getContent());
                    }
            );
        }
    }

    @Nested
    @DisplayName("#deleteById")
    class DeleteById {
        @Test
        void targetShouldExist() {
            assertThrowsExactly(
                    NotFoundRuntimeException.class,
                    () -> useCase.deleteById(1L)
            );
        }

        @Test
        void shouldDeleteById() {
            var mockResult = Optional.of(
                    new Entity(1L, "Long time no see", "2025-04-25T13:00:00.000", "2025-04-25T13:00:00.000")
            );
            Mockito.when(repository.findById(1L)).thenReturn(mockResult);
            var result = useCase.deleteById(1L);

            Mockito.verify(repository).deleteById(1L);
            assertEquals("Long time no see", result.getContent());
        }
    }
}
