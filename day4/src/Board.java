public class Board {
    int size;
    int[][] numbers;
    boolean[][] marked;

    int[] rowsCount;
    int[] colsCount;

    boolean won;

    public Board(int[][] numbers) {
        this.numbers = numbers;
        this.size = numbers.length;
        this.marked = new boolean[size][size];
        this.rowsCount = new int[size];
        this.colsCount = new int[size];
        this.won = false;
    }

//    public Board(Board other) {
//        this(other.numbers);
//    }

    public Board(Board other) {
        this.size = other.size;
        this.numbers = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(other.numbers[i], 0, this.numbers[i], 0, size);
        }
        this.marked = new boolean[size][size];
        this.rowsCount = new int[size];
        this.colsCount = new int[size];
    }

    @Override
    public String toString() {
        return matrixToString(numbers);
    }

    public boolean markNumber(int number) {
        if (won) {
            return true;
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (numbers[i][j] == number && !marked[i][j]) {
                    marked[i][j] = true;
                    rowsCount[i]++;
                    colsCount[j]++;

                    return rowsCount[i] == size || colsCount[j] == size;
                }
            }
        }
        return false;
    }

    public int unmarkedValuesSum() {
        int sum = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!marked[i][j]) {
                    sum += numbers[i][j];
                }
            }
        }

        return sum;
    }

    public static String matrixToString(int[][] numbers) {
        StringBuilder sb = new StringBuilder();
        for (int[] row : numbers) {
            for (int num : row) {
                sb.append(String.format("%3d ", num)); // width 3 for alignment
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
