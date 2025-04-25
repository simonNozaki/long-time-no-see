package io.github.simonnozaki.longtimenosee.domain.note.usecase;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 更新ユースケースの入力オブジェクト
 */
@Data
@AllArgsConstructor
public class UpdateUseCaseInput {
    @Nonnull
    private Long id;
    @Nonnull
    private String content;
}
