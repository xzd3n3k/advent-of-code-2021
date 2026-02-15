public class Move {
    Coordinates start;
    Coordinates end;

    Move(Coordinates start, Coordinates end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Move{start=" + start + ", end=" + end + "}";
    }
}
