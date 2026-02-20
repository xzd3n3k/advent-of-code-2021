static final String FILE_PATH = "src/input.txt";
record Coordinate(int row, int column) {}

List<Coordinate> coordinates = new ArrayList<Coordinate>();
List<Coordinate> coordinatesToRemove = new ArrayList<Coordinate>();
List<Coordinate> coordinatesToAdd = new ArrayList<Coordinate>();

void main() {

  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    String line;
    boolean commandSection = false;

    while ((line = br.readLine()) != null) {
      if (line.isBlank()) {
        commandSection = true;
        continue;
      }

      if (commandSection) {
        String[] commandParts = line.split(" ")[2].split("=");
        int foldValue = Integer.parseInt(commandParts[1]);
        
        switch (commandParts[0]) {
          case "y":
            coordinates.forEach(coordinate -> {

              if (coordinate.row > foldValue) {

                int newRow = 2 * foldValue - coordinate.row;
                Coordinate folded = new Coordinate(newRow, coordinate.column);

                coordinatesToRemove.add(coordinate);

                if (!coordinates.contains(folded)) {
                  coordinatesToAdd.add(folded);
                }
              }

            });

            applyListChanges();
            break;
          case "x":
            coordinates.forEach(coordinate -> {

              if (coordinate.column > foldValue) {

                int newCol = 2 * foldValue - coordinate.column;
                Coordinate folded = new Coordinate(coordinate.row, newCol);

                coordinatesToRemove.add(coordinate);

                if (!coordinates.contains(folded)) {
                  coordinatesToAdd.add(folded);
                }
              }

            });

            applyListChanges();
            break;
        }
      } else {
        String[] coordsParts = line.split(",");
        coordinates.add(new Coordinate(Integer.parseInt(coordsParts[1]), Integer.parseInt(coordsParts[0])));
      }
    }
    showMap();
  } catch (IOException e) {
    IO.println("Error reading file.");
  }
}

private void applyListChanges() {
  coordinatesToRemove.forEach(coordinates::remove);
  coordinates.addAll(coordinatesToAdd);
  coordinatesToAdd.clear();
  coordinatesToRemove.clear();
  IO.println(coordinates.size());
}

private void showMap() {
  if (coordinates == null || coordinates.isEmpty()) {
    IO.println("(empty map)");
    return;
  }

  int maxRow = 0;
  int maxCol = 0;

  for (Coordinate c : coordinates) {
    if (c.row() > maxRow) maxRow = c.row();
    if (c.column() > maxCol) maxCol = c.column();
  }

  // create grid filled with dots
  char[][] grid = new char[maxRow + 1][maxCol + 1];
  for (int r = 0; r <= maxRow; r++) {
    for (int c = 0; c <= maxCol; c++) {
      grid[r][c] = '.';
    }
  }

  // place hashtags
  for (Coordinate coord : coordinates) {
    grid[coord.row()][coord.column()] = '#';
  }

  // show grid
  for (int r = 0; r <= maxRow; r++) {
    for (int c = 0; c <= maxCol; c++) {
      System.out.print(grid[r][c]);
    }
    System.out.println();
  }
}
