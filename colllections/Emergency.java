import java.util.*;

class Emergency {
    public static void main(String[] args) {
        Queue<String> normalList = new LinkedList<>();
        ArrayList<String> emergencyList = new ArrayList<>();
        String[][] patients = {
            {"Arjun", "5"},
            {"Mia", "9"},
            {"Leo", "7"},
            {"Sara", "10"}
        };

        for (int i = 0; i < patients.length; i++) {
            String name = patients[i][0];
            int priority = Integer.parseInt(patients[i][1]);

            if (priority > 8) {
                emergencyList.add(name);
            } else {
                normalList.add(name);
            }
        }
        for (int i = 0; i < emergencyList.size(); i++) {
            System.out.println("Emergency case : " + emergencyList.get(i));
        }

        int size = normalList.size();
        for (int i = 0; i < size; i++) {
            System.out.println("Treating : " + normalList.poll());
        }
    }
}
