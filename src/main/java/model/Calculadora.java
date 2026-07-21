package model;

import java.util.List;

public class Calculadora {


    public Results calculate(Grafo graph,Results result,double capital) {

        if (!result.isArbitrageFound()) {
            return result;
        }

        List<Integer> cycle = result.getCycle();

        double amount = capital;

        for (int i = 0; i < cycle.size() - 1; i++) {

            double rate = graph.getRate(
                    cycle.get(i),
                    cycle.get(i + 1));

            amount *= rate;
        }

        result.setInitialCapital(capital);
        result.setFinalCapital(amount);
        result.setProfit(amount - capital);
        result.setPercentage(((amount - capital) / capital) * 100);

        return result;
    }
}

