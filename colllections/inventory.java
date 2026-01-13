import java.util.*;

class inventory {
    public static void main(String[] args) {
        Map<String, Integer> currentStock = new HashMap<>();
        currentStock.put("MacBook", 5);
        currentStock.put("iPhone", 10);
        currentStock.put("AirPods", 25);

        Map<String, Integer> incomingStock = new HashMap<>();
        incomingStock.put("iPhone", 5);
        incomingStock.put("AirPods", 5);
        incomingStock.put("VisionPro", 2);

        for (String item : incomingStock.keySet()) {

            if (currentStock.containsKey(item)) {
                int oldQty = currentStock.get(item);
                int newQty = oldQty + incomingStock.get(item);
                currentStock.put(item, newQty);
            } else {
                currentStock.put(item, incomingStock.get(item));
            }
        }

        TreeMap<String, Integer> sorted = new TreeMap<>(currentStock);
        System.out.println("Updated Stock:");
        int total = 0;

        for (String item : sorted.keySet()) {
            System.out.println(item + " : " + sorted.get(item));
            total = total + sorted.get(item);
        }
        System.out.println("\nTotal Units in Store: " + total);
    }
}
