package ir.hamyiar.coronavirustracker.net;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class CoronaConnection {

    public String downloadCsvCoronaInformation(String coronaNetworkUrl) {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = HttpRequest.newBuilder(URI.create(coronaNetworkUrl)).build();
        HttpResponse<String> coronaInfos = null;
        try {
            coronaInfos = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        if (coronaInfos == null)
            return "";
        return coronaInfos.body();
    }
}
