package io.github.simonnozaki.longtimenosee.adapter;

import io.github.simonnozaki.longtimenosee.domain.note.usecase.UseCaseOutput;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * Runnerで得た、ドメインロジックの結果を標準出力する外向きのアダプタークラス
 */
@Component
public class ConsolePrinter {
    /**
     * 表形式に整形して出力する
     *
     * <pre>
     * | #|Content                |Created at                 |Updated at                 |
     * | 1|created at format test |2025-04-20T16:54:50.648110 |2025-04-20T16:54:50.648110 |
     * </pre>
     */
    public void printForTable(UseCaseOutput output) {
        printForTable(List.of(output));
    }

    /**
     * 表形式に整形して出力する
     *
     * <pre>
     * | #|Content                |Created at                 |Updated at                 |
     * | 1|created at format test |2025-04-20T16:54:50.648110 |2025-04-20T16:54:50.648110 |
     * </pre>
     */
    public void printForTable(List<UseCaseOutput> outputs) {
        var tableText = formatForTable(outputs);
        System.out.println(tableText);
    }

    private record TextLengths(Integer id, Integer content, Integer createdAt, Integer updatedAt) {}

    private String formatForTable(List<UseCaseOutput> outputs) {
        var idColumnMaxLength = getMaxLength(outputs, (n) -> n.getId().toString().length());
        var contentColumnMaxLength = getMaxLength(outputs, (n) -> n.getContent().length());
        var createdAtColumnLength = getMaxLength(outputs, (n) -> n.getCreatedAt().length());
        var updatedAtColumnLength = getMaxLength(outputs, (n) -> n.getUpdatedAt().length());
        var textLengths = new TextLengths(idColumnMaxLength, contentColumnMaxLength, createdAtColumnLength, updatedAtColumnLength);
        List<String> rows = toTableRows(outputs, textLengths);

        return String.join("\n", rows);
    }

    /**
     * 列ごとの最大長を返す
     * TODO マルチバイト文字を等幅フォントできれいにパディングする
     */
    private Integer getMaxLength(List<UseCaseOutput> outputs, Function<UseCaseOutput, Integer> transformer) {
        var maxLength = outputs.stream()
                .map(transformer)
                .max(Comparator.naturalOrder())
                .orElse(2);
        // 少しだけ余白をとって見やすくする
        return maxLength + 1;
    }

    /**
     * 表の行を文字列として返す
     */
    private List<String> toTableRows(List<UseCaseOutput> outputs, TextLengths lengths) {
        var header = getHeaderRow(lengths);
        List<String> rows = new ArrayList<String>() {
            {
                add(header);
            }
        };
        for (UseCaseOutput output: outputs) {
            var idText =  pad(lengths.id, output.getId().toString(), Alignment.RIGHT);
            var contentText = pad(lengths.content, output.getContent(), Alignment.LEFT);
            var contentCreatedAt = pad(lengths.createdAt, output.getCreatedAt(), Alignment.LEFT);
            var contentUpdatedAt = pad(lengths.updatedAt, output.getUpdatedAt(), Alignment.LEFT);
            rows.add("|%s|%s|%s|%s|".formatted(idText, contentText, contentCreatedAt, contentUpdatedAt));
        }
        return rows;
    }

    private String getHeaderRow(TextLengths lengths) {
        var headerId = pad(lengths.id, "#", Alignment.RIGHT);
        var headerContent = pad(lengths.content, "Content", Alignment.LEFT);
        var headerCreatedAt = pad(lengths.createdAt, "Created at", Alignment.LEFT);
        var headerUpdatedAt = pad(lengths.updatedAt, "Updated at", Alignment.LEFT);
        return  "|%s|%s|%s|%s|".formatted(headerId, headerContent, headerCreatedAt, headerUpdatedAt);
    }

    private enum Alignment {
        LEFT,
        RIGHT
    }

    /**
     * 列最大幅との差をとってパディング
     */
    private String pad(Integer headerLength, String value, Alignment alignment) {
        var paddings = (" ".repeat(headerLength - value.length()));
        return switch (alignment) {
            case LEFT -> value + paddings;
            case RIGHT -> paddings + value;
        };
    }
}
