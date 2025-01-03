package pt.psoft.g1.psoftg1.views;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ReaderLendingsAvgPerMonthView {
    private Integer year;
    private Integer month;
    private List<ReaderAverageView> durationAverages;

}
