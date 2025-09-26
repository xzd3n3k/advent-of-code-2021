static final String FILE_PATH = "src/input.txt";

void main() {
    try {
        System.out.println(firstPart());
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

        final int halfCount = count/2;
        final int sumsSize = sums.size();
        char[] gammaRateChars = new char[sumsSize];
        char[] epsilonRateChars = new char[sumsSize];

        for (int i = 0; i < sumsSize; i++) {
            if (sums.get(i) > halfCount) {
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
