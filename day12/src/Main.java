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

    int numberOfPathsPart1 = depthFirstSearch("start", new ArrayList<String>());
    int numberOfPathsPart2 = depthFirstSearch("start", new HashMap<String, Integer>(), false);

    IO.println(numberOfPathsPart1);
    IO.println(numberOfPathsPart2);

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

private int depthFirstSearch(String current, Map<String, Integer> visitSmallMap, boolean usedDoubleVisit) {
  // if we reached the destination, we found one valid path.
  if (current.equals("end")) {
    return 1;
  }

  // copy state for this "branch"
  Map<String, Integer> visitSmallMapCopy = new HashMap<>(visitSmallMap);

  // if the current cave is a small cave,
  // record that we have visited it in this path.
  if (current.equals(current.toLowerCase())) {
    visitSmallMapCopy.put(current, visitSmallMapCopy.getOrDefault(current, 0) + 1);
  }

  int total = 0;

  for (String neighbor : graph.get(current)) {

    if (neighbor.equals("start")) continue;

    boolean isSmall = neighbor.equals(neighbor.toLowerCase());
    int visits = visitSmallMapCopy.getOrDefault(neighbor, 0);

    if (!isSmall) {
      // big cave → always OK
      total += depthFirstSearch(neighbor, visitSmallMapCopy, usedDoubleVisit);
    }
    else if (visits == 0) {
      // small cave first time visit
      total += depthFirstSearch(neighbor, visitSmallMapCopy, usedDoubleVisit);
    }
    else if (visits == 1 && !usedDoubleVisit) {
      // small cave second time visit — use double visit
      total += depthFirstSearch(neighbor, visitSmallMapCopy, true);
    }
    // else not allowed to enter the cave
  }

  return total;
}
