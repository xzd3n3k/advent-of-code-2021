static final String FILE_PATH = "src/input.txt";

void main() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

        String line;
        List<List<Integer>> fieldMap = new ArrayList<List<Integer>>();

        while ((line = br.readLine()) != null) {
            fieldMap.add(Arrays.stream(line.split("")).map(Integer::parseInt).toList());
        }

        int riskLevelsSum = 0;

        for (int i = 0; i < fieldMap.size(); i++) {
            for (int j = 0; j < fieldMap.get(i).size(); j++) {
                int currentNumber = fieldMap.get(i).get(j);

                if (isLowerThanNeighbors(currentNumber, i, j, fieldMap)) {
                    riskLevelsSum += currentNumber+1;
                }
            }
        }

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
