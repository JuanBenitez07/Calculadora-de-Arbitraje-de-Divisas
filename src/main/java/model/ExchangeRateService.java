package model;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ExchangeRateService {

    private static final HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .build();

    @SuppressWarnings("unchecked")
    private static Map<String, Double> getDirectRates(String base, List<String> targets) throws Exception {

        String symbols = String.join(",", targets);
        String url = "https://api.frankfurter.dev/v1/latest?base=" + base + "&symbols=" + symbols;

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                "Error al consultar Frankfurter (HTTP " + response.statusCode() + "): " + response.body()
            );
        }

        Object parsed = SimpleJsonParser.parse(response.body());
        Map<String, Object> root = (Map<String, Object>) parsed;

        Object ratesObj = root.get("rates");
        if (!(ratesObj instanceof Map)) {
            throw new RuntimeException("La respuesta de Frankfurter no contiene el campo 'rates' esperado");
        }
        Map<String, Object> ratesNode = (Map<String, Object>) ratesObj;

        Map<String, Double> rates = new HashMap<>();
        for (String t : targets) {
            Object value = ratesNode.get(t);
            if (!(value instanceof Double)) {
                throw new RuntimeException("No se obtuvo tasa directa " + base + " -> " + t);
            }
            rates.put(t, (Double) value);
        }

        return rates;
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Double> getBtcDirectRates(List<String> fiatCodes) throws Exception {

        List<String> lower = new ArrayList<>();
        for (String c : fiatCodes) lower.add(c.toLowerCase());

        String url = "https://api.coingecko.com/api/v3/simple/price?ids=bitcoin&vs_currencies=" + String.join(",", lower);

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException(
                "Error al consultar CoinGecko (HTTP " + response.statusCode() + "): " + response.body()
            );
        }

        Object parsed = SimpleJsonParser.parse(response.body());
        Map<String, Object> root = (Map<String, Object>) parsed;

        Object bitcoinObj = root.get("bitcoin");
        if (!(bitcoinObj instanceof Map)) {
            throw new RuntimeException("La respuesta de CoinGecko no contiene el campo 'bitcoin' esperado");
        }
        Map<String, Object> bitcoinNode = (Map<String, Object>) bitcoinObj;

        Map<String, Double> btcInFiat = new HashMap<>();
        for (String fiat : fiatCodes) {
            Object value = bitcoinNode.get(fiat.toLowerCase());
            if (!(value instanceof Double)) {
                throw new RuntimeException("No se obtuvo precio directo de BTC en " + fiat);
            }
            btcInFiat.put(fiat, (Double) value);
        }

        return btcInFiat;
    }

    // loadGraph se mantiene exactamente igual, no depende del formato interno del JSON
    public static void loadGraph(Grafo graph, List<String> fiatCodes, boolean includeBtc) throws Exception {

        Map<String, divisa> divisaMap = new HashMap<>();
        for (String code : fiatCodes) {
            divisaMap.put(code, new divisa(code, code));
        }
        if (includeBtc) {
            divisaMap.put("BTC", new divisa("BTC", "BTC"));
        }

        for (String from : fiatCodes) {
            List<String> targets = new ArrayList<>();
            for (String to : fiatCodes) {
                if (!to.equals(from)) targets.add(to);
            }

            Map<String, Double> directRates = getDirectRates(from, targets);

            for (String to : targets) {
                graph.addExchangeRate(new ExchangeRate(
                        divisaMap.get(from), divisaMap.get(to), directRates.get(to)
                ));
            }
        }

        if (includeBtc) {
            Map<String, Double> btcInFiat = getBtcDirectRates(fiatCodes);

            for (String fiat : fiatCodes) {
                double btcToFiat = btcInFiat.get(fiat);
                double fiatToBtc = 1.0 / btcToFiat;

                graph.addExchangeRate(new ExchangeRate(divisaMap.get("BTC"), divisaMap.get(fiat), btcToFiat));
                graph.addExchangeRate(new ExchangeRate(divisaMap.get(fiat), divisaMap.get("BTC"), fiatToBtc));
            }
        }
    }
}