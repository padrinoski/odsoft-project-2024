package pt.psoft.g1.psoftg1.views;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Data
@EqualsAndHashCode(callSuper = true)
@Setter
public class ReaderQuoteView extends ReaderView{
    private String quote;
}
