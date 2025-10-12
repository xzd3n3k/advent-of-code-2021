static final String FILE_PATH = "src/input.txt";

void main() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

        String line;
        List<List<Integer>> fieldMap = new ArrayList<List<Integer>>();
        List<Integer> basins = new ArrayList<Integer>();

        while ((line = br.readLine()) != null) {
            fieldMap.add(Arrays.stream(line.split("")).map(Integer::parseInt).toList());
        }

        int riskLevelsSum = 0;

        for (int i = 0; i < fieldMap.size(); i++) {
            for (int j = 0; j < fieldMap.get(i).size(); j++) {
                int currentNumber = fieldMap.get(i).get(j);

                if (isLowerThanNeighbors(currentNumber, i, j, fieldMap)) {
                    Set<String> visited = new HashSet<>();
                    visited.add(i + "," + j);
                    basins.add(findOneHigher(currentNumber, i, j, fieldMap, visited)+1);
                    riskLevelsSum += currentNumber+1;
                }
            }
        }

        basins.sort(Comparator.reverseOrder());

        System.out.println(basins);

        int basinsTogether = 1;

        for (int i = 0; i < 3; i++) {
            basinsTogether *= basins.get(i);
        }

        System.out.println(basinsTogether);
        System.out.println(riskLevelsSum);

    } catch (IOException e) {
        System.out.println("Error reading file.");
    }
}

private static boolean isLowerThanNeighbors(int number, int row, int col, List<List<Integer>> fieldMap) {
    int rows = fieldMap.size();
    int cols = fieldMap.get(row).size();

    // Up
    if (row > 0 && fieldMap.get(row - 1).get(col) <= number) {
        return false;
    }

    // Down
    if (row < rows - 1 && fieldMap.get(row + 1).get(col) <= number) {
        return false;
    }

    // Left
    if (col > 0 && fieldMap.get(row).get(col - 1) <= number) {
        return false;
    }

    // Right
    if (col < cols - 1 && fieldMap.get(row).get(col + 1) <= number) {
        return false;
    }

    // If no neighbor is less or equal, then this is a low point
    return true;
}

private static int findOneHigher(int number, int row, int col,
                                 List<List<Integer>> fieldMap, Set<String> visited) {
    int rows = fieldMap.size();
    int cols = fieldMap.get(row).size();
    int count = 0;

    int[][] directions = {
            {-1, 0}, // up
            {1, 0},  // down
            {0, -1}, // left
            {0, 1}   // right
    };

    for (int[] dir : directions) {
        int newRow = row + dir[0];
        int newCol = col + dir[1];

        if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols)
            continue;

        int neighborValue = fieldMap.get(newRow).get(newCol);
        String coordKey = newRow + "," + newCol;

        if (!visited.contains(coordKey)
                && neighborValue != 9
                && neighborValue > number) {

            visited.add(coordKey);
            count++;
            count += findOneHigher(neighborValue, newRow, newCol, fieldMap, visited);
        }
    }

    return count;
}
