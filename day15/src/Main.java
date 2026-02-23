static final String FILE_PATH = "src/input.txt";
static final boolean PART1 = false;

void main() {

  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    String line;
    List<String> lines = new ArrayList<>();

    while ((line = br.readLine()) != null) {
      lines.add(line.trim());
    }

    // transform string into grid with numbers
    int baseRows = lines.size();
    int baseColumns = lines.getFirst().length();
    int[][] baseRisk = new int[baseRows][baseColumns];

    for (int row = 0; row < baseRows; row++) {
      for (int column = 0; column < baseColumns; column++) {
        // substract '0' (48 ASCII/UNICODE) so it results into int
        baseRisk[row][column] = lines.get(row).charAt(column) - '0';
      }
    }

    // expand to 5×5 tiled grid
    int[][] risk = PART1 ? baseRisk : expandGrid(baseRisk, 5);
    int rows = risk.length;
    int columns = risk[0].length;

    // init distance
    int[][] distance = new int[rows][columns];
    for (int[] row : distance) Arrays.fill(row, Integer.MAX_VALUE);
    distance[0][0] = 0; // 0 risk on start

    // priority queue for Dijkstra
    // chooses the least risk field
    PriorityQueue<int[]> priorityQ = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
    priorityQ.add(new int[]{0, 0, 0}); // {row, col, totalRisk}

    // directions
    int[] deltaRow = {-1, 1, 0, 0};
    int[] deltaColumn = {0, 0, -1, 1};

    // main Dijkstra loop
    while (!priorityQ.isEmpty()) {
      int[] curr = priorityQ.poll(); // field with least risk
      int row = curr[0];
      int column = curr[1];
      int dist = curr[2];

      // better path has already been found
      if (dist > distance[row][column]) {
        continue;
      }

      // search through all 4 adjacent
      for (int i = 0; i < 4; i++) {
        int newRow = row + deltaRow[i];
        int newColumn = column + deltaColumn[i];

        if (newRow >= 0 && newRow < rows && newColumn >= 0 && newColumn < columns) {
          int newDist = dist + risk[newRow][newColumn];
          if (newDist < distance[newRow][newColumn]) {
            distance[newRow][newColumn] = newDist;
            priorityQ.add(new int[]{newRow, newColumn, newDist});
          }
        }

      }
    }

    System.out.println("Lowest total risk = " + distance[rows - 1][columns - 1]);
  } catch (IOException e) {
    IO.println("Error reading file.");
  }
}

private static int[][] expandGrid(int[][] base, int tiles) {
  int h = base.length;
  int w = base[0].length;

  int[][] result = new int[h * tiles][w * tiles];

  for (int ty = 0; ty < tiles; ty++) {
    for (int tx = 0; tx < tiles; tx++) {

      int increment = tx + ty;

      for (int y = 0; y < h; y++) {
        for (int x = 0; x < w; x++) {

          int val = base[y][x] + increment;

          // wrap 1..9
          if (val > 9) {
            val = (val - 1) % 9 + 1;
          }

          result[ty * h + y][tx * w + x] = val;
        }
      }
    }
  }

  return result;
}
