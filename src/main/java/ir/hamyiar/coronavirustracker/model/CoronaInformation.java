package ir.hamyiar.coronavirustracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoronaInformation {
    private String state;
    private String country;
    private List<Integer> infos;
}
