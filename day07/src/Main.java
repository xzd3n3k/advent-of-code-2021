static final String FILE_PATH = "src/input.txt";

void main() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

        String line = br.readLine();
        List<Integer> numbers = new ArrayList<>(Arrays.stream(line.split(","))
                .map(Integer::parseInt)
                .toList());

        final int median = calculateMedian(numbers);
        final int fuelNeeded = calculateFuelConsumption(numbers, median);

        float avrg = calculateAvrg(numbers);
        long top = (long) Math.ceil(avrg);
        long bottom = (long) Math.floor(avrg);

        long topConsumption = calculateFuelConsumptionWithOutMedian(numbers, top);
        long bottomConsumption = calculateFuelConsumptionWithOutMedian(numbers, bottom);

        long fuelNeededPartTwo = Math.min(topConsumption, bottomConsumption);

        System.out.println(fuelNeeded);
        System.out.println(fuelNeededPartTwo);

    } catch (IOException e) {
        System.out.println("Error reading file.");
    }
}

private static int calculateMedian(List<Integer> numbers) {
    Collections.sort(numbers);
    int listSize = numbers.size();
    int halfListSize = numbers.size() / 2;
    int median;

    if (listSize % 2 == 1) {
        median = numbers.get((listSize - 1) / 2);
    } else {
        int firstMiddleNumber = numbers.get(halfListSize);
        int secondMiddleNumber = numbers.get((halfListSize) - 1);

        median = (firstMiddleNumber + secondMiddleNumber) / 2;
    }

    return median;
}

private static int calculateFuelConsumption(List<Integer> numbers, int median) {
    int fuelNeeded = 0;

    for (Integer number : numbers) {
        fuelNeeded += Math.abs(median - number);
    }

    return fuelNeeded;
}

private static float calculateAvrg(List<Integer> numbers) {
    float allNumbersSum = 0;
    for (int number : numbers) {
        allNumbersSum += number;
    }

    return allNumbersSum/numbers.size();
}

private static long calculateFuelConsumptionWithOutMedian(List<Integer> numbers, long target) {
    long fuelNeeded = 0;
    for (int posInt : numbers) {
        long pos = posInt;
        long d = Math.abs(pos - target);
        fuelNeeded += d * (d + 1) / 2;
    }
    return fuelNeeded;
}
