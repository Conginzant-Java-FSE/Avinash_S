package stream;

import java.util.List;

public class Transaction{

    public static void main(String[] args) {

        List<String> transactions = List.of(
                "TXN1001:SAVINGS:12000",
                "TXN1002:CURRENT:15000",
                "TXN1003:SAVINGS:20000",
                "TXN1004:SAVINGS:12000",
                "TXN1005:SAVINGS:8000",
                "TXN1006:CURRENT:30000"
        );

        List<Double> processedAmounts = transactions.stream()
                .map(txn -> txn.split(":"))
                .filter(data -> data[1].equals("SAVINGS"))
                .map(data -> Double.parseDouble(data[2]))
                .filter(amount -> amount >= 10000)
                .map(amount -> amount + (amount * 0.18))
                .distinct()
                .sorted((a, b) -> Double.compare(b, a))
                .toList();

        long count = processedAmounts.stream()
                .filter(amount -> amount > 20000)
                .count();

        System.out.println("Processed Amounts: " + processedAmounts);
        System.out.println("Count of amounts > 20000: " + count);
    }
}
