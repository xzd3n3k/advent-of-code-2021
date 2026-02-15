static final String FILE_PATH = "src/input.txt";
static enum Position {
    FIRST,
    LAST
}

void main() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
        String line = br.readLine();

        int[] numbersToBeDrawn;
        numbersToBeDrawn = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();

        List<List<Integer>> boardNumbers = new ArrayList<>();

        List<Board> boards = new ArrayList<Board>();

        while ((line = br.readLine()) != null) {
            if (line.isBlank()) {

                if (boardNumbers.isEmpty()) {
                    continue;
                }

                boards.add(new Board(to2DArray(boardNumbers)));
                boardNumbers.clear();
            } else {
                List<Integer> row = Arrays.stream(line.trim().split("\\s+"))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());
                boardNumbers.add(row);
            }
        }

        boards.add(new Board(to2DArray(boardNumbers)));
        boardNumbers.clear();

        System.out.println(solveBoards(boards, numbersToBeDrawn, Position.FIRST));
        System.out.println(solveBoards(boards, numbersToBeDrawn, Position.LAST));

    } catch (IOException e) {
        System.out.println("Error reading file.");
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

private static int solveBoards(List<Board> boards, int[] numbersToBeDrawn, Position position) {
    int solvedCount = 0;

    List<Board> boardsCopy = boards.stream()
            .map(Board::new)
            .toList();

    outer:
    for (int i = 0; i < numbersToBeDrawn.length; i++) {
        int number = numbersToBeDrawn[i];

        for (int j = 0; j < boardsCopy.size(); j++) {
            Board board = boardsCopy.get(j);

            if (board.markNumber(number) && !board.won) {
                solvedCount++;
                board.won = true;
                if (position == Position.LAST && solvedCount == boardsCopy.size()) {
                    return board.unmarkedValuesSum() * number;
                } else if (position == Position.FIRST) {
                    return board.unmarkedValuesSum() * number;
                }
            }
        }
    }

    return -1;
}
