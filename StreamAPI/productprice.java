
import java.util.List;

public class productprice {

    public static void main(String[] args) {

        List<Integer> prices = List.of(
                500, 1200, 2500, 1200, 3000, 800, 2500
        );

        List<Integer> processedPrices = prices.stream()
                .filter(price -> price > 1000)
                .distinct()
                .sorted((a, b) -> b - a)
                .toList();

        long count = processedPrices.stream()
                .filter(price -> price > 2000)
                .count();

        System.out.println("Processed Prices: " + processedPrices);
        System.out.println("Count of prices > 2000: " + count);
    }
}
