import java.util.*;

public class feedback {
    public static void main(String[] args) {
        int[] ratings = {9, 10, 12, 8, -1, 7, 11, 10, 6};
        ArrayList<Integer> validRatings = new ArrayList<>();

        for (int i = 0; i < ratings.length; i++) {
            if (ratings[i] >= 1 && ratings[i] <= 10) {
                validRatings.add(ratings[i]);
            }
        }
        Collections.sort(validRatings);
        int total = 0;
        for (int num = 0; num < ratings.length; num++) {
            total = total + num;
        }
        double avg = (double) total / validRatings.size();

        System.out.println("Cleaned Ratings: " + validRatings);
        System.out.println("Highest Rating: " + Collections.max(validRatings));
        System.out.println("Lowest Rating: " + Collections.min(validRatings));
        System.out.printf("Average Rating: ", avg);
    }
}
