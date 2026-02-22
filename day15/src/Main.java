static final String FILE_PATH = "src/input.txt";

void main() {

  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    String line;
    List<String> lines = new ArrayList<>();

    while ((line = br.readLine()) != null) {
      lines.add(line.trim());
    }

    // transform string into grid with numbers
    int rows = lines.size();
    int columns = lines.getFirst().length();
    int[][] risk = new int[rows][columns];

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        risk[row][column] = lines.get(row).charAt(column) - '0'; // substract '0' (48 ASCII/UNICODE) so it results into int
      }
    }

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
