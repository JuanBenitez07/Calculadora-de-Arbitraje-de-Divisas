package controlador;

import model.*;
import java.util.*;

public class controller {

    private Grafo graph;
    private Calculadora calculator;

    public controller(Grafo graph) {
        this.graph = graph;
        this.calculator = new Calculadora();
    }

    public List<String> getCurrencyCodes() {
        return new ArrayList<>(graph.getCurrencyIndex().keySet());
    }

    public Results executeArbitrage(double capital, String c1, String c2, String c3) {

        Map<String, Integer> idx = graph.getCurrencyIndex();

        if (!idx.containsKey(c1) || !idx.containsKey(c2) || !idx.containsKey(c3)) {
            return new Results(false, null);
        }

        List<Integer> cycle = new ArrayList<>();
        cycle.add(idx.get(c1));
        cycle.add(idx.get(c2));
        cycle.add(idx.get(c3));
        cycle.add(idx.get(c1));

        for (int i = 0; i < cycle.size() - 1; i++) {
            if (!graph.hasRate(cycle.get(i), cycle.get(i + 1))) {
                return new Results(false, null);
            }
        }

        Results calculado = calculator.calculate(graph, new Results(true, cycle), capital);

        boolean rentable = calculado.getFinalCapital() > calculado.getInitialCapital();

        Results resultado = new Results(rentable, cycle);
        resultado.setInitialCapital(calculado.getInitialCapital());
        resultado.setFinalCapital(calculado.getFinalCapital());
        resultado.setProfit(calculado.getProfit());
        resultado.setPercentage(calculado.getPercentage());

        // Solo mostramos la ruta como "arbitraje" si de verdad hay ganancia
        resultado.setRoute(rentable ? buildRoute(cycle) : null);

        return resultado;
    }

    private String buildRoute(List<Integer> cycle) {

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < cycle.size() - 1; i++) {

            int current = cycle.get(i);
            int next = cycle.get(i + 1);

            String from = graph.getCurrency(current).getCode();
            String to = graph.getCurrency(next).getCode();
            double rate = graph.getRate(current, next);

            sb.append(String.format("%-4s → %-4s   (1 %s = %.6f %s)%n", from, to, from, rate, to));
        }

        return sb.toString();
    }
}