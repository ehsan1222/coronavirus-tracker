package ir.hamyiar.coronavirustracker.helper;

import ir.hamyiar.coronavirustracker.model.CoronaInformation;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class CsvParser {

    private String csvString;

    public CsvParser(String csvString) {
        this.csvString = csvString;
    }

    public List<CoronaInformation> parse() {
        List<CoronaInformation> coronaInformations = new ArrayList<>();
        CSVParser csvParser = getCsvParser();
        for (CSVRecord csvRecord : csvParser) {
            String state = csvRecord.get("Province/State");
            String country = csvRecord.get("Country/Region");
            List<Integer> infos = new ArrayList<>();
            for (int i = 4; i < csvRecord.size(); i++) {
                Integer aDayInfo = Integer.parseInt(csvRecord.get(i));
                infos.add(aDayInfo);
            }
            CoronaInformation coronaInformation = new CoronaInformation(
                    state,
                    country,
                    infos
            );
            coronaInformations.add(coronaInformation);
        }
        return coronaInformations;
    }

    private CSVParser getCsvParser() {
        StringReader stringReader = new StringReader(csvString);
        CSVParser csvParser = null;
        try {
            csvParser = new CSVParser(stringReader, CSVFormat.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                stringReader.close();
                csvParser.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvParser;
    }

}
