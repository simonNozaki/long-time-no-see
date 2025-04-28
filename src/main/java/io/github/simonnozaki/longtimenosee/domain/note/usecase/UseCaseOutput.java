package io.github.simonnozaki.longtimenosee.domain.note.usecase;

import jakarta.annotation.Nonnull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * ユースケース出力オブジェクト
 * 責務の分離を徹底するべく、ドメインの色を薄めたもの。
 * もし、参照と更新で必要なアウトプットが変わるのであればこれを分解する。
 */
@Data
@AllArgsConstructor
@Builder
public class UseCaseOutput {
    @Nonnull
    private Long id;
    private String content;
    private String createdAt;
    private String updatedAt;
}
