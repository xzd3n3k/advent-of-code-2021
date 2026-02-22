static final String FILE_PATH = "src/input.txt";

Map<String, String> pairInsertionRules = new HashMap<String, String>();
Map<String, Integer> lettersCount = new HashMap<String, Integer>();
List<String> polymerTemplateChars;

void main() {

  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
    String line;
    polymerTemplateChars = Arrays.stream(br.readLine().split("")).collect(Collectors.toCollection(ArrayList::new));
    br.readLine(); // skip empty line

    // load pair insertion rules
    while ((line = br.readLine()) != null) {
      String[] instructions = line.split(" -> ");

      pairInsertionRules.put(instructions[0], instructions[1]);
    }

    polymerTemplateChars.forEach(polymerCharacter -> {
      lettersCount.merge(polymerCharacter, 1, Integer::sum);
    });

    for (int x = 0; x < 10; x++) {
      polymerize();
    }

    IO.println(findDiffBetweenMostLeastCommonElements());
  } catch (IOException e) {
    IO.println("Error reading file.");
  }
}

private void polymerize() {
  String[] lettersToInsert = new String[polymerTemplateChars.size()-1];

  for (int x = 0; x < polymerTemplateChars.size() - 1; x++) {
    String pairKey = polymerTemplateChars.get(x) + polymerTemplateChars.get(x + 1);
    lettersToInsert[x] = pairInsertionRules.get(pairKey);
    lettersCount.merge(pairInsertionRules.get(pairKey), 1, Integer::sum);
  }

  for (int x = 0; x < lettersToInsert.length; x++) {
    polymerTemplateChars.add((x*2) + 1, lettersToInsert[x]);
  }
}

private int findDiffBetweenMostLeastCommonElements() {
  int max = 1;
  int min = 1;
  int i = 0;

  for (Map.Entry<String, Integer> entry : lettersCount.entrySet()) {
    String key = entry.getKey();
    Integer value = entry.getValue();

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
