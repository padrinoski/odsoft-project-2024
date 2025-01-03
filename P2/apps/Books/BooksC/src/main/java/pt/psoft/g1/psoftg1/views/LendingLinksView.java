package pt.psoft.g1.psoftg1.views;

import lombok.Data;
import lombok.Setter;

import java.util.Map;

@Data
@Setter
public class LendingLinksView {
    private Map<String, String> self;
    private Map<String, String> book;
    private Map<String, String> reader;

}
