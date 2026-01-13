public class FifaStats {
    static class InvalidScoreException extends RuntimeException {
        public InvalidScoreException(String message) {
            super(message);
        }
    }

    public static int calculateGoalDifference(int scored, int conceded) {
        System.out.println("Calculating goal difference...");
        if (scored < 0 || conceded < 0) {
            throw new InvalidScoreException("Score cannot be negative");
        }
        return scored - conceded;
    }
    public static void main(String[] args) {
        try {
            int diff = calculateGoalDifference(3, -1);
            System.out.println("Goal Difference: " + diff);
        } catch (InvalidScoreException e) {
            System.out.println("Invalid score error: " + e.getMessage());
        } 
        catch (Exception e) {
            System.out.println("Some error occurred: " + e.getMessage());
        } 
        finally {
            System.out.println("Match stats processed");
        }
    }
}
