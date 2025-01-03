package pt.psoft.g1.psoftg1.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import pt.psoft.g1.psoftg1.model.reader.ReaderDetails;

@Data
@Schema(description = "Reader with lending count")
public class ReaderAverageDto {
    @NotNull
    private ReaderDetails readerView;

    private Long lendingCount;
}
