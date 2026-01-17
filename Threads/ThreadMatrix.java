class RowSumThread extends Thread {
    int row;
    int[][] matrix;
    int[] result;

    RowSumThread(int row, int[][] matrix, int[] result) {
        this.row = row;
        this.matrix = matrix;
        this.result = result;
    }

    public void run() {
        int sum = 0;
        for (int j = 0; j < 6; j++) {
            sum += matrix[row][j];
        }
        result[row] = sum;
    }
}

public class ThreadMatrix {
    public static void main(String[] args) throws InterruptedException {
        int[][] matrix = {
            {1, 2, 3, 4, 5, 6},
            {7, 8, 9, 1, 2, 3},
            {4, 5, 6, 7, 8, 9},
            {9, 8, 7, 6, 5, 4},
            {1, 3, 5, 7, 9, 2},
            {2, 4, 6, 8, 1, 3}
        };

        int[] result = new int[6];
        RowSumThread[] threads = new RowSumThread[6];

        for (int i = 0; i < 6; i++) {
            threads[i] = new RowSumThread(i, matrix, result);
            threads[i].start();
        }
        for (int i = 0; i < 6; i++) {
            threads[i].join();
        }
        System.out.println("Row sums:");
        for (int i = 0; i < 6; i++) {
            System.out.println("Row " + (i + 1) + ": " + result[i]);
        }
    }
}
