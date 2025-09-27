static final String FILE_PATH = "src/input.txt";

void main() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

        String line = br.readLine();
        long[] fishCounts = new long[9];

        Arrays.stream(line.split(","))
                .mapToInt(Integer::parseInt)
                .forEach(timer -> fishCounts[timer]++);

        for (int day = 0; day < 256; day++) {
            long newFish = fishCounts[0];

            for (int i = 0; i < 8; i++) {
                fishCounts[i] = fishCounts[i + 1];
            }

            fishCounts[6] += newFish;
            fishCounts[8] = newFish;
        }

        long total = Arrays.stream(fishCounts).sum();
        System.out.println(total);

    } catch (IOException e) {
        System.out.println("Error reading file.");
    }
}

private static void slowSolution() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

        String line = br.readLine();
        List<Integer> fishLives = Arrays.stream(line.split(","))
                .map(Integer::parseInt)
                .toList();

        List<Lanternfish> fishes = fishLives.stream()
                .map(Lanternfish::new)
                .collect(Collectors.toCollection(ArrayList::new));


        for (int i = 0; i < 80; i++) {
            liveNextDay(fishes);
        }

        System.out.println(fishes.size());

    } catch (IOException e) {
        System.out.println("Error reading file.");
    }
}

private static void liveNextDay(List<Lanternfish> fishes) {
    int originalSize = fishes.size();
    for (int i = 0; i < originalSize; i++) {
        Lanternfish fish = fishes.get(i);
        if (fish.nextDay()) {
            fishes.add(new Lanternfish());
        }
    }
}

