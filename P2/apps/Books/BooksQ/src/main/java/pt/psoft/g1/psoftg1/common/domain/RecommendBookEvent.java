package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendBookEvent implements EventMsg {
    private String userId; // user who recommended the book
    private String isbn;
    private String title;
    private List<String> authorIds;
    private String recommendationMessage;
    private boolean positiveRecommendation;
}