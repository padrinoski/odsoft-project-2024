package pt.psoft.g1.psoftg1.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReturnBookEvent implements EventMsg {
    private String isbn;
    private String title;
    private String userId;
    private String returnMessage;
}