

import java.util.List;

public class salaries {
    public static void main(String[] args){
        List<String> employees = List.of(
                "Alice:60000",
                "Bob:45000",
                "Charlie:75000",
                "David:60000",
                "Eva:90000"
        );
        List<Integer> annaulSalaries=employees.stream()
                .map(e->Integer.parseInt(e.split(":")[1]))
                .filter(salary->salary>50000)
                .map(salary->salary*12)
                .distinct()
                .sorted((a,b)->b-a)
                .skip(1)
                .limit(2)
                .toList();
        System.out.println("Annual salaries after processing:"+annaulSalaries);
        System.out.println("Count: "+annaulSalaries.size());

    }

}