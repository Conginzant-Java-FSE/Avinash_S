import java.util.*;

public class hiring {
    public static void main(String[] args) {
        String[] applicants = {"John", "Aisha", "Ravi", "John", "Mina", "Ravi", "Aisha", "Tom"};
        Set<String> unique = new HashSet<>();
        for (int i = 0; i < applicants.length; i++) {
            unique.add(applicants[i]);
        }
        int duplicatesRemoved = applicants.length - unique.size();

        System.out.println("Unique Applicants: " + unique);
        System.out.println("Duplicates Removed: " + duplicatesRemoved);
    }
}
