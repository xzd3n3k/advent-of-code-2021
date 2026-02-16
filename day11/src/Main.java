static final String FILE_PATH = "src/input.txt";

void main() {
  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    String line;
    List<List<Integer>> boardNumbers = new ArrayList<>();
    Board board;

    while ((line = br.readLine()) != null) {
      List<Integer> row = Arrays.stream(line.trim().split(""))
          .map(Integer::parseInt)
          .collect(Collectors.toList());
      boardNumbers.add(row);
    }

    board = new Board(to2DArray(boardNumbers));
    boardNumbers.clear();

    for (int x = 0; x < 100; x++) {
      board.step();
    }

    IO.println(board.flashes);

  } catch (IOException e) {
    IO.println("Error reading file.");
  }
}

private static int[][] to2DArray(List<List<Integer>> list) {
  int rows = list.size();
  int cols = list.getFirst().size();
  int[][] array = new int[rows][cols];

  for (int i = 0; i < rows; i++) {
    for (int j = 0; j < cols; j++) {
      array[i][j] = list.get(i).get(j);
    }
  }
  return array;
}
