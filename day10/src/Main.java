static final String FILE_PATH = "src/input.txt";
static final String OPENING_OPERATORS = "({[<";
static final String CLOSING_OPERATORS = ")}]>";
static final Map<Character, Integer> CHAR_VALUE_MAP_PART1 = Map.of(
    ')', 3,
    ']', 57,
    '}', 1197,
    '>', 25137
);
static final Map<Character, Integer> CHAR_VALUE_MAP_PART2 = Map.of(
    ')', 1,
    ']', 2,
    '}', 3,
    '>', 4
);

public record ValidationResult(Character incorrectChar, double value) {};

void main() {
  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

    int value = 0;
    List<Double> values = new ArrayList<Double>();
    String line;

    while ((line = br.readLine()) != null) {
      ValidationResult validationResult = validateLine(line.toCharArray());
      Character incorrectChar = validationResult.incorrectChar;

      if (incorrectChar != null) {
        value += CHAR_VALUE_MAP_PART1.get(incorrectChar);
      } else {
        values.add(validationResult.value);
      }
    }

    Collections.sort(values);

    System.out.printf("Part1 value: %d%n", value);
    System.out.printf("Part 2 value: %f%n", values.get(values.size() / 2));
  } catch (IOException e) {
    IO.println("Error reading file.");
  }
}

private static ValidationResult validateLine(char[] characters) {
  var leftOperatorStack = new ArrayList<Character>();
  Character invalidOperator = null;
  double valuePart2 = 0;

  // finds first incorrect closing character if exists
  for (char value : characters) {
    // if character is opening operator, it puts him into stack
    if (OPENING_OPERATORS.contains("" + value)) {
      leftOperatorStack.add(value);
      // if character is closing operator, looks at top of the stack and if its opposing operator, it pops
    } else if (CLOSING_OPERATORS.contains("" + value)) {
      if (leftOperatorStack.getLast().equals(getOppositeOperator(value))) {
        leftOperatorStack.removeLast();
        // else its the incorrect closing operator so it just stores it and break the cycle
      } else {
        System.out.printf("Expected %c, but found %c instead.%n", getOppositeOperator(leftOperatorStack.getLast()), value);
        invalidOperator = value;
        break;
      }
    }
  }

  // if no invalid operator was found, then there are some missing operators
  if (invalidOperator == null) {
    // replace operators with opposite ones
    leftOperatorStack = leftOperatorStack
        .reversed()
        .stream()
        .map(c -> getOppositeOperator(c))
        .collect(Collectors.toCollection(ArrayList::new));

    for (Character character : leftOperatorStack) {
      valuePart2 *= 5;
      valuePart2 += CHAR_VALUE_MAP_PART2.get(character);
    }
  }

  return new ValidationResult(invalidOperator, valuePart2);
}

private static Character getOppositeOperator(char character) {
  final String _character = ""+character;

  if (OPENING_OPERATORS.contains(_character)) {
    return CLOSING_OPERATORS.charAt(OPENING_OPERATORS.indexOf(_character));
  } else if (CLOSING_OPERATORS.contains(_character)) {
    return OPENING_OPERATORS.charAt(CLOSING_OPERATORS.indexOf(_character));
  }

  return null;
}
