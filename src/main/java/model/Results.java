package model;
import java.util.List;

public class Results {


   private boolean arbitrageFound;
    private List<Integer> cycle;

    private double initialCapital;
    private double finalCapital;
    private double profit;
    private double percentage;
    private String route;

    public Results(boolean arbitrageFound, List<Integer> cycle) {
        this.arbitrageFound = arbitrageFound;
        this.cycle = cycle;
    }

    public boolean isArbitrageFound() {
        return arbitrageFound;
    }

    public List<Integer> getCycle() {
        return cycle;
    }

    public double getInitialCapital() {
        return initialCapital;
    }

    public void setInitialCapital(double initialCapital) {
        this.initialCapital = initialCapital;
    }

    public double getFinalCapital() {
        return finalCapital;
    }

    public void setFinalCapital(double finalCapital) {
        this.finalCapital = finalCapital;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

}
