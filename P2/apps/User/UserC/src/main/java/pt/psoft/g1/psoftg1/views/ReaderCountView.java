package pt.psoft.g1.psoftg1.views;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReaderCountView {
    @NotNull
    private ReaderView readerView;

    private Long lendingCount;
}
