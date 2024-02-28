package org.example;

public class Wallet {
    private String walletRef;
    private double amount;
    private Currency currency;

    public Wallet(String ref,  Currency c){
        this.walletRef=ref;
        this.amount = 0;
        this.currency=c;
    }
    public String getWalletRef() {
        return walletRef;
    }
    public void addAmount(double amount, Currency currency, ExchangeRate exchangeRateMap) {
        if(currency.equals(this.currency)){
            this.amount+=amount;
        }else{
            this.amount+=exchangeRateMap.getExchangeRate(currency.getDisplayName(),this.currency.getDisplayName())*amount;
        }
    }
    public double getAmount(Currency targetCurrency, ExchangeRate exchangeRateMap) {
        if(targetCurrency.equals(this.currency)){
            return this.amount;
        }else{
            Double exchange = exchangeRateMap.getExchangeRate(this.currency.getDisplayName(), targetCurrency.getDisplayName());
            return exchange*this.amount;
        }
    }
}


