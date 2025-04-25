package io.github.simonnozaki.longtimenosee.domain.note.usecase;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 生成ユースケースの入力オブジェクト
 */
@AllArgsConstructor
@Data
public class CreateUseCaseInput {
    @Nonnull
    private String content;
}
