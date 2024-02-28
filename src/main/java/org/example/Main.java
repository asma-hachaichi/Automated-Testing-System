package org.example;
import java.util.Scanner;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void main(String[] args) {
        Currency c = Currency.USD;
        Wallet wallet1 = new Wallet("Ref1", c);
        ExchangeRate E = new ExchangeRate();
        Scanner sc=new Scanner(System.in);

        //System.out.println(wallet1.getWalletRef()+" Created successfully!\nInitial amount:");
        //System.out.println(wallet1.getAmount(Currency.TND, E));

        //System.out.println("Enter your amount :");
        //Double amount =sc.nextDouble();
        wallet1.addAmount(1, c,E);

        System.out.println("New total amount :"+wallet1.getAmount(c,E));

        System.out.println("Enter amount to add in EUR:");
        //amount =sc.nextDouble();
        //wallet1.addAmount(amount, Currency.EUR,E);

        System.out.println("New total amount :"+wallet1.getAmount(Currency.USD,E));
        System.out.println("New total amount :"+wallet1.getAmount(Currency.EUR,E));
        System.out.println("New total amount :"+wallet1.getAmount(Currency.TND,E));

    }
}