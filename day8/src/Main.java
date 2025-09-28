static final String FILE_PATH = "src/input.txt";

void main() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

        String line;
        int count = 0;

        while ((line = br.readLine()) != null) {
            count += countUniqueDigitNumbers(parseLine(line).get(1));
        }

        System.out.println(count);
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