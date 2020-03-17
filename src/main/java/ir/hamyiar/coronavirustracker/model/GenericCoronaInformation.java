package ir.hamyiar.coronavirustracker.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GenericCoronaInformation {
    private String country;
    private Integer totalPopulation;
    private Integer lastDayPopulation;
}
