package io.github.simonnozaki.longtimenosee.domain.note.usecase;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 参照ユースケース出力オブジェクト
 * 極度に責務の分離をしているが、フィールド数が少なければここまでしないで良い
 */
@Data
@AllArgsConstructor
@Builder
public class FindUseCaseOutput {
    @Nonnull
    private Long id;
    private String content;
    private String createdAt;
    private String updatedAt;
}
