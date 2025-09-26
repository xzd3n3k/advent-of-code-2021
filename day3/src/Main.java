static final String FILE_PATH = "src/input.txt";
static enum Occurence {
    MOST,
    LEAST
}

void main() {
    try {
        System.out.println(firstPart());
        System.out.println(secondPart());
    } catch (IOException e) {
        e.printStackTrace();
    }
}

private static int firstPart() throws IOException {
    try (
            FileInputStream fis = new FileInputStream(FILE_PATH);
            Scanner sc = new Scanner(fis)
    ) {

        ArrayList<Integer> sums = new ArrayList<Integer>();
        int count = 0;

        while (sc.hasNextLine()) {
            String[] binaryParts = sc.nextLine().split("(?<=.)");

            for (int i = 0; i < binaryParts.length; i++) {
                if (count == 0) {
                    sums.add(0);
                }

                if (binaryParts[i].equals("0")) {
                    sums.set(i, sums.get(i)+1);
                }
            }

            count++;
        }

        final int sumsSize = sums.size();
        char[] gammaRateChars = new char[sumsSize];
        char[] epsilonRateChars = new char[sumsSize];

        for (int i = 0; i < sumsSize; i++) {
            if (sums.get(i) > (count - sums.get(i))) {
                gammaRateChars[i] = '0';
                epsilonRateChars[i] = '1';
            } else {
                gammaRateChars[i] = '1';
                epsilonRateChars[i] = '0';
            }
        }

        String gammaRateString = new String(gammaRateChars);
        String epsilonRateString = new String(epsilonRateChars);

        int gammaRate = Integer.parseInt(gammaRateString, 2);
        int epsilonRate = Integer.parseInt(epsilonRateString, 2);

        return gammaRate * epsilonRate;
    }
}

private static int secondPart() throws IOException {
    List<String> binaryCodes = Files.lines(Paths.get(FILE_PATH))
            .map(String::trim)
            .filter(line -> !line.isBlank())
            .toList();

    List<String> binaryCodesCopy = new ArrayList<>(binaryCodes);

    while (binaryCodesCopy.size() > 1) {

        for (int i = 0; i < binaryCodes.getFirst().length(); i++) {
            final int position = i;
            char character = findOccurence(position, '1', Occurence.MOST, binaryCodesCopy);
            binaryCodesCopy = binaryCodesCopy.stream().filter(code -> code.charAt(position) == character).toList();

            if (binaryCodesCopy.size() == 1) {
                break;
            }
        }
    }

    final int oxygenGeneratorRating = Integer.parseInt(binaryCodesCopy.getFirst(), 2);

    while (binaryCodes.size() > 1) {

        for (int i = 0; i < binaryCodes.getFirst().length(); i++) {
            final int position = i;
            char character = findOccurence(position, '0', Occurence.LEAST, binaryCodes);
            binaryCodes = binaryCodes.stream().filter(code -> code.charAt(position) == character).toList();

            if (binaryCodes.size() == 1) {
                break;
            }
        }
    }

    final int co2ScrubberRating = Integer.parseInt(binaryCodes.getFirst(), 2);

    return oxygenGeneratorRating * co2ScrubberRating;
}

private static char findOccurence(int bitIndex, char fallbackBitIfEquals, Occurence occurence, List<String> codes) {
    int count = 0;

    for (int i = 0; i < codes.size(); i++) {
        String code = codes.get(i);

        if (code.charAt(bitIndex) == '1') {
            count++;
        }
    }

    switch (occurence) {
        case MOST:
            if (count > (codes.size() - count)) {
                return '1';
            }
            break;

        case LEAST:
            if (count < (codes.size() - count)) {
                return '1';
            }
            break;
    }

    if (count == (codes.size() - count)) {
        return fallbackBitIfEquals;
    } else {
        return '0';
    }
}
