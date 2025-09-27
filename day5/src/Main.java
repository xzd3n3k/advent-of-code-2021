static final String FILE_PATH = "src/input.txt";
public static Hashtable<String, Integer> dictionary = new Hashtable<>();

void main() {
    try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {

        String line;
        while ((line = br.readLine()) != null) {
           executeMove(parseLine(line));
        }

        long count = dictionary.values()
                .stream()
                .filter(v -> v > 1)
                .count();

        System.out.println(count);

    } catch (IOException e) {
        System.out.println("Error reading file.");
    }
}

private static Move parseLine(String line) {
    String[] parts = line.split(" -> ");
    Coordinates start = parseCoordinates(parts[0]);
    Coordinates end = parseCoordinates(parts[1]);

    return new Move(start, end);
}

private static Coordinates parseCoordinates(String part) {
    String[] xy = part.split(",");

    int x = Integer.parseInt(xy[0]);
    int y = Integer.parseInt(xy[1]);

    return new Coordinates(x, y);
}

private static void executeMove(Move move) {
    boolean xEquals = move.start.x == move.end.x;
    boolean yEquals = move.start.y == move.end.y;
    if (!xEquals && !yEquals) {
        return;
    }

    if (xEquals) {
        int startY = Math.min(move.start.y, move.end.y);
        int endY = Math.max(move.start.y, move.end.y);
        for (int y = startY; y <= endY; y++) {
            String location = move.start.x + "," + y;
            dictionary.put(location, dictionary.getOrDefault(location, 0) + 1);
        }
    } else {
        int startX = Math.min(move.start.x, move.end.x);
        int endX = Math.max(move.start.x, move.end.x);
        for (int x = startX; x <= endX; x++) {
            String location = x + "," + move.start.y;
            dictionary.put(location, dictionary.getOrDefault(location, 0) + 1);
        }
    }
}
