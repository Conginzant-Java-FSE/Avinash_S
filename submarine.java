package oops;
import java.util.Scanner;

public class submarine{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Missile Type: ");
        String type = sc.nextLine();

        System.out.print("Missile ID: ");
        String id = sc.nextLine();

        System.out.print("Threat Level: ");
        int threat = Integer.parseInt(sc.nextLine());

        System.out.print("Clearance: ");
        boolean clearance = Boolean.parseBoolean(sc.nextLine());
        Missile missile;
        if (type.equalsIgnoreCase("NuclearMissile")) {
            missile = new NuclearMissile(id, 10000);
        } else if (type.equalsIgnoreCase("TorpedoMissile")) {
            missile = new TorpedoMissile(id, 5000);
        } else {
            missile = new DefenseInterceptor(id, 3000);
        }

        boolean result = missile.launch(threat, clearance);
        System.out.println("\nMissile ID: " + missile.missileId);

        if (result) {
            System.out.println("Launch Status: APPROVED");
            if (missile instanceof DefenseInterceptor) {
                System.out.println("Interceptor launched successfully");
            } else {
                System.out.println("Missile launched successfully");
            }
        } else {
            System.out.println("Launch Status: DENIED");
            if (missile instanceof NuclearMissile) {
                System.out.println("Reason: Threat level insufficient for nuclear launch");
            } else {
                System.out.println("Reason: Launch conditions not satisfied");
            }
        }
        sc.close();
    }
}
abstract class Missile {
    String missileId;
    int range;
    public Missile(String missileId, int range) {
        this.missileId = missileId;
        this.range = range;
    }
    abstract boolean launch(int threatLevel, boolean hasClearance);
}
class NuclearMissile extends Missile {
    public NuclearMissile(String missileId, int range) {
        super(missileId, range);
    }
    @Override
    boolean launch(int threatLevel, boolean hasClearance) {
        if (threatLevel >= 9 && hasClearance == true) {
            return true;
        }
        return false;
    }
}
class TorpedoMissile extends Missile {

    public TorpedoMissile(String missileId, int range) {
        super(missileId, range);
    }
    @Override
    boolean launch(int threatLevel, boolean hasClearance) {
        if (threatLevel >= 5) {
            return true;
        }
        return false;
    }
}
class DefenseInterceptor extends Missile {
    public DefenseInterceptor(String missileId, int range) {
        super(missileId, range);
    }
    @Override
    boolean launch(int threatLevel, boolean hasClearance) {
        if (threatLevel >= 3) {
            return true;
        }
        return false;
    }
}
