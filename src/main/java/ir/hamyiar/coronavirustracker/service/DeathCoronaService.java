package ir.hamyiar.coronavirustracker.service;

import ir.hamyiar.coronavirustracker.helper.CsvParser;
import ir.hamyiar.coronavirustracker.model.CoronaInformation;
import ir.hamyiar.coronavirustracker.model.GenericCoronaInformation;
import ir.hamyiar.coronavirustracker.net.CoronaConnection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeathCoronaService {

    @Value("${death}")
    private String url;


    private static List<CoronaInformation> allInformation = new ArrayList<>();
    private static Map<String, GenericCoronaInformation> allCountryInformation = new HashMap<>();

    public List<CoronaInformation> getAllInformation() {
        return allInformation;
    }

    public Map<String, GenericCoronaInformation> getAllCountryInformation() {
        return allCountryInformation;
    }

    @PostConstruct
    @Scheduled(cron = "0 0 0/12 * * *")
    private void processCoronaInformation() {
        String csvData = new CoronaConnection().downloadCsvCoronaInformation(url);
        allInformation = new CsvParser(csvData).parse();
        updateGenericData(allInformation);
    }

    private void updateGenericData(List<CoronaInformation> allInformation) {
        for (CoronaInformation coronaInformation : allInformation) {
            GenericCoronaInformation genericCoronaInformation = allCountryInformation.get(coronaInformation.getCountry());
            Integer lastDayPop = coronaInformation.getInfos().get(coronaInformation.getInfos().size() - 1);
            Integer lastTwoDayPop = coronaInformation.getInfos().get(coronaInformation.getInfos().size() - 2);

            if (genericCoronaInformation == null) {
                GenericCoronaInformation newGenericCoronaInformation = new GenericCoronaInformation(
                        coronaInformation.getCountry(),
                        lastDayPop,
                        (lastDayPop - lastTwoDayPop)
                );
                allCountryInformation.put(newGenericCoronaInformation.getCountry(), newGenericCoronaInformation);
            } else {
                genericCoronaInformation.setTotalPopulation(genericCoronaInformation.getTotalPopulation() + lastDayPop);

                genericCoronaInformation.setLastDayPopulation(
                        genericCoronaInformation.getTotalPopulation() + (lastDayPop - lastTwoDayPop)
                );
            }
        }
    }

}
