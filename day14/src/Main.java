static final String FILE_PATH = "src/input.txt";

Map<String, String> pairInsertionRules = new HashMap<String, String>();
Map<String, Long> lettersCount = new HashMap<String, Long>();
Map<String, Long> pairCounts = new HashMap<String, Long>();

void main() {

  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    String line;
    List<String> polymerTemplateChars = Arrays.stream(br.readLine().split("")).collect(Collectors.toCollection(ArrayList::new));
    br.readLine(); // skip empty line

    // load pair insertion rules
    while ((line = br.readLine()) != null) {
      String[] instructions = line.split(" -> ");

      pairInsertionRules.put(instructions[0], instructions[1]);
    }

    // create init pairs
    for (int i = 0; i < polymerTemplateChars.size() - 1; i++) {
      String pair = polymerTemplateChars.get(i) + polymerTemplateChars.get(i + 1);
      pairCounts.merge(pair, 1L, Long::sum);
    }

    for (int x = 0; x < 40; x++) {
      polymerize();
    }

    IO.println(findDiffBetweenMostLeastCommonElements());
  } catch (IOException e) {
    IO.println("Error reading file.");
  }
}

private void polymerize() {
  Map<String, Long> newPairCounts = new HashMap<>();

  for (Map.Entry<String, Long> entry : pairCounts.entrySet()) {
    String pair = entry.getKey();
    long count = entry.getValue();

    String insert = pairInsertionRules.get(pair);

    if (insert != null) {
      lettersCount.merge(insert, count, Long::sum);

      // make new pairs
      String left = "" + pair.charAt(0) + insert;
      String right = "" + insert + pair.charAt(1);

      newPairCounts.merge(left, count, Long::sum);
      newPairCounts.merge(right, count, Long::sum);
    }
  }

  pairCounts.clear();
  pairCounts.putAll(newPairCounts);
}

private long findDiffBetweenMostLeastCommonElements() {
  long max = 1;
  long min = 1;
  int i = 0;

  for (Map.Entry<String, Long> entry : lettersCount.entrySet()) {
    String key = entry.getKey();
    long value = entry.getValue();

    if (value > max) {
      max = value;
    }

    if (i == 0) {
      min = value;
    }

    if (value < min) {
      min = value;
    }

    i++;
  }

  return max - min;
}
