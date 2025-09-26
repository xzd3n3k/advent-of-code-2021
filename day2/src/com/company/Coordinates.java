package com.company;

public class Coordinates {
    int horizontalPosition;
    int depth;
    int aim;

    public Coordinates(int horizontalPosition, int depth, int aim) {
        this.horizontalPosition = horizontalPosition;
        this.depth = depth;
        this.aim = aim;
    }

    public Coordinates(int horizontalPosition, int depth) {
        this.horizontalPosition = horizontalPosition;
        this.depth = depth;
        this.aim = 0;
    }

    public Coordinates() {
        this.horizontalPosition = 0;
        this.depth = 0;
        this.aim = 0;
    }
}
