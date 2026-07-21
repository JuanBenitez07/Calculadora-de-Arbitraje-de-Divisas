package model;

import java.util.*;

public class Grafo{

    private Map<String, Integer> currencyIndex;
    private Map<Integer, divisa> currencies;
    private List<Arista> edges;
    private Map<String, Double> rates;

    public Grafo() {

        currencyIndex = new HashMap<>();
        currencies = new HashMap<>();
        edges = new ArrayList<>();
        rates = new HashMap<>();

    }

    public void addCurrency(divisa currency) {

        if (!currencyIndex.containsKey(currency.getCode())) {

            int id = currencyIndex.size();

            currencyIndex.put(currency.getCode(), id);
            currencies.put(id, currency);

        }

    }

    public void addExchangeRate(ExchangeRate exchangeRate) {

        addCurrency(exchangeRate.getFrom());
        addCurrency(exchangeRate.getTo());

        int source = currencyIndex.get(exchangeRate.getFrom().getCode());
        int destination = currencyIndex.get(exchangeRate.getTo().getCode());

        edges.add(new Arista(
                source,
                destination,
                -Math.log(exchangeRate.getRate())
        ));

        rates.put(source + "-" + destination,
                exchangeRate.getRate());

    }

    public boolean hasRate(int from, int to) {
        return rates.containsKey(from + "-" + to);
    }

    public List<Arista> getEdges() {
        return edges;
    }

    public int size() {
        return currencies.size();
    }

    public divisa getCurrency(int id) {
        return currencies.get(id);
    }

    public Map<String, Integer> getCurrencyIndex() {
        return currencyIndex;
    }

    public double getRate(int from, int to) {
        return rates.get(from + "-" + to);
    }

}