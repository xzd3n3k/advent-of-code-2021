static final String FILE_PATH = "src/input.txt";
Map<String, List<String>> graph = new HashMap<>();

void main() {

  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    String line;

    while ((line = br.readLine()) != null) {
      String[] parts = line.split("-");
      String from = parts[0];
      String to = parts[1];

      graph.computeIfAbsent(from, k -> new ArrayList<>()).add(to);
      graph.computeIfAbsent(to, k -> new ArrayList<>()).add(from);
    }

    int numberOfPaths = depthFirstSearch("start", new ArrayList<String>());

    IO.println(numberOfPaths);

  } catch (IOException e) {
    IO.println("Error reading file.");
  }
}

private int depthFirstSearch(String current, List<String> visitedSmall) {
  // if we reached the destination, we found one valid path.
  if (current.equals("end")) {
    return 1;
  }

  // each recursive branch must have its own copy of visited caves.
  // DFS explores multiple paths that diverge from the same point,
  // and each path has its own "history" of visited small caves.
  //
  // if we reused the same list, one branch could affect another,
  // causing valid paths to be incorrectly blocked.
  List<String> visitedSmallCopy = new ArrayList<>(visitedSmall);

  // if the current cave is a small cave,
  // record that we have visited it in this path.
  if (current.equals(current.toLowerCase())) {
    visitedSmallCopy.add(current);
  }

  int total = 0;
  List<String> currentNeighbors = graph.get(current);

  // explore each possible next step from the current cave.
  for (String currentNeighbor : currentNeighbors) {
    if (!visitedSmallCopy.contains(currentNeighbor)) {
      // recursively explore paths starting from this neighbor.
      // each recursive call represents continuing one possible path.
      total += depthFirstSearch(currentNeighbor, visitedSmallCopy);
    }
  }

  return total;
}
