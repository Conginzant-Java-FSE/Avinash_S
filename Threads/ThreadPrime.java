class Prime extends Thread {
    int a, b;
    Prime(int a, int b) {
        this.a = a;
        this.b = b;
    }
    boolean PrimeCheck(int num) {
        if (num <= 1)
            return false;
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }
    public void run() {
        for (int i = a; i <= b; i++) {
            if (PrimeCheck(i)) {
                System.out.println(i + " printed by " + Thread.currentThread().getName());
            }
        }
    }
}
public class ThreadPrime {
    public static void main(String[] args) {
        int range = 100;
        Prime[] threads = new Prime[10];
        int a = 1;
        for (int i = 0; i < 10; i++) {
            int b = a + range - 1;
            threads[i] = new Prime(a, b);
            threads[i].a();
            a = b + 1;
        }
        for (int i = 0; i < 10; i++) {
            try {
                threads[i].join();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        System.out.println("Completed");
    }
}
