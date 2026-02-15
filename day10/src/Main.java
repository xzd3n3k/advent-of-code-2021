static final String FILE_PATH = "src/input.txt";
static final String OPENING_OPERATORS = "({[<";
static final String CLOSING_OPERATORS = ")}]>";
static final Map<Character, Integer> CHAR_VALUE_MAP = Map.of(
    ')', 3,
    ']', 57,
    '}', 1197,
    '>', 25137
);

void main() {
  try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

    int value = 0;
    String line;

    while ((line = br.readLine()) != null) {
      Character incorrectChar = findFirstIncorrectClosingCharacter(line.toCharArray());

      if (incorrectChar != null) {
        value += CHAR_VALUE_MAP.get(incorrectChar);
      }
    }

    IO.println(value);

  } catch (IOException e) {
    IO.println("Error reading file.");
  }
}

private static Character findFirstIncorrectClosingCharacter(char[] characters) {
  var leftOperatorStack = new ArrayList<Character>();
  var rightOperatorStack = new ArrayList<Character>();
  Character invalidOperator = null;

  for (int x = 0; x < characters.length; x++) {
    if (OPENING_OPERATORS.contains("" + characters[x])) {
      leftOperatorStack.add(characters[x]);
    } else if (CLOSING_OPERATORS.contains("" + characters[x])) {
      if (leftOperatorStack.getLast().equals(getOppositeOperator(characters[x]))) {
        leftOperatorStack.removeLast();
      } else {
        rightOperatorStack.add(characters[x]);
        System.out.printf("Expected %c, but found %c instead.%n", getOppositeOperator(leftOperatorStack.getLast()), characters[x]);
        invalidOperator = characters[x];
        break;
      }
    }
  }

  return invalidOperator;
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
