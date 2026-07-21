package model;


public class ExchangeRate {

    private divisa from;
    private divisa to;
    private double rate;

    public ExchangeRate(divisa from, divisa to, double rate) {
        this.from = from;
        this.to = to;
        this.rate = rate;
    }

    public divisa getFrom() {
        return from;
    }

    public divisa getTo() {
        return to;
    }

    public double getRate() {
        return rate;
    }

}