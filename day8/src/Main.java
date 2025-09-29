static final String FILE_PATH = "src/input.txt";

void main() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

        String line;
        int count = 0;
        int count2 = 0;

        while ((line = br.readLine()) != null) {
            List<String> parts = parseLine(line);

            String[] patterns = parts.get(0).split(" ");
            String[] output = parts.get(1).split(" ");

            count2 += decode(patterns, output);
            count += countUniqueDigitNumbers(parseLine(line).get(1));
        }

        System.out.println(count);
        System.out.println(count2);
    } catch (IOException e) {
        System.out.println("Error reading file.");
    }
}

private static List<String> parseLine(String line) {
    return Arrays.stream(line.split(" \\| ")).toList();
}

private static int countUniqueDigitNumbers(String linePart) {
    int count = 0;
    String[] parts = linePart.split(" ");

    for (int i = 0; i < parts.length; i++) {
        String codedNumber = parts[i];

        switch (codedNumber.length()) {
            case 2:
            case 3:
            case 4:
            case 7:
                count++;
                break;
            default:
                break;
        }
    }

    return count;
}

private static int decode(String[] patterns, String[] output) {
    Map<Integer, Set<Character>> digits = new HashMap<>();
    List<Set<Character>> fiveSeg = new ArrayList<>();
    List<Set<Character>> sixSeg = new ArrayList<>();

    for (String p : patterns) {
        Set<Character> set = toCharSet(p);
        switch (set.size()) {
            case 2 -> digits.put(1, set);
            case 3 -> digits.put(7, set);
            case 4 -> digits.put(4, set);
            case 7 -> digits.put(8, set);
            case 5 -> fiveSeg.add(set);
            case 6 -> sixSeg.add(set);
        }
    }

    for (Set<Character> s : sixSeg) {
        if (s.containsAll(digits.get(4))) {
            digits.put(9, s);
        }
        else if (s.containsAll(digits.get(1))) {
            digits.put(0, s);
        }
        else {
            digits.put(6, s);
        }
    }

    for (Set<Character> s : fiveSeg) {
        if (s.containsAll(digits.get(1))) {
            digits.put(3, s);
        }
        else if (digits.get(6).containsAll(s)) {
            digits.put(5, s);
        }
        else {
            digits.put(2, s);
        }
    }

    Map<Set<Character>, Integer> lookup = new HashMap<>();
    for (Map.Entry<Integer, Set<Character>> e : digits.entrySet()) {
        lookup.put(e.getValue(), e.getKey());
    }

    int value = 0;
    for (String o : output) {
        value = value * 10 + lookup.get(toCharSet(o));
    }
    return value;
}

private static Set<Character> toCharSet(String s) {
    Set<Character> set = new HashSet<>();
    for (char c : s.toCharArray()) {
        set.add(c);
    }
    return set;
}
