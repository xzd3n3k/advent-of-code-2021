public class Board {
  int size;
  int[][] numbers;
  boolean[][] flashed;

  int[] rowsCount;
  int[] colsCount;

  int flashes = 0;

  public Board(int[][] numbers) {
    this.numbers = numbers;
    this.size = numbers.length;
    this.flashed = new boolean[size][size];
    this.rowsCount = new int[size];
    this.colsCount = new int[size];
  }

  public Board(Board other) {
    this.size = other.size;
    this.numbers = new int[size][size];
    for (int i = 0; i < size; i++) {
      System.arraycopy(other.numbers[i], 0, this.numbers[i], 0, size);
    }
    this.flashed = new boolean[size][size];
    this.rowsCount = new int[size];
    this.colsCount = new int[size];
  }

  @Override
  public String toString() {
    return matrixToString(numbers);
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

  public void flash(int row, int column) {
    // if already flashed, skip that one
    if (isFlashed(row, column)) {
      return;
    }

    // else reset energy, set flashed to true, increment flash counter, call side effect on neighbors
    numbers[row][column] = 0;
    flashed[row][column] = true;
    flashes++;
    flashNeighbors(row, column);
  }

  public void unmarkAll() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        flashed[i][j] = false;
      }
    }
  }

  public boolean isFlashed(int row, int column) {
    return flashed[row][column];
  }

  public boolean allZero() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        if (numbers[i][j] != 0) {
          return false;
        }
      }
    }
    return true;
  }

  public void step() {
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        // if already flashed, skip that one
        if (flashed[i][j]) {
          continue;
        }
        // if is about to flash, flash
        if ((numbers[i][j] + 1) > 9) {
          flash(i, j);
          continue;
        }
        // else increment
        numbers[i][j]++;
      }
    }

    // reset flash sign
    unmarkAll();
  }

  private void flashNeighbors(int row, int col) {
    for (int deltaRow = -1; deltaRow <= 1; deltaRow++) {
      for (int deltaCol = -1; deltaCol <= 1; deltaCol++) {

        // skip source destination
        if (deltaRow == 0 && deltaCol == 0) {
          continue;
        }

        int newRow = row + deltaRow;
        int newCol = col + deltaCol;

        if (newRow >= 0 && newRow < size &&
            newCol >= 0 && newCol < size) {
          // if already flashed, skip that one
          if (flashed[newRow][newCol]) {
            continue;
          }
          // if is about to flash, flash
          if ((numbers[newRow][newCol] + 1) > 9) {
            flash(newRow, newCol);
            continue;
          }
          // else increment
          numbers[newRow][newCol]++;
        }
      }
    }
  }
}