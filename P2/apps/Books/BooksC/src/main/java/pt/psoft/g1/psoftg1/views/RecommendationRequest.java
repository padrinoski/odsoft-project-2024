package pt.psoft.g1.psoftg1.views;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A DTO for creating a Recommendation request")
public class RecommendationRequest {

    @NonNull
    public String readerNumber;
}
