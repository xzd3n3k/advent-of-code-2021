package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static final String FILE_PATH = "src/com/company/input.txt";

    static void main(String[] args) {
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
            Coordinates coordinates = new Coordinates();

            while (sc.hasNextLine()) {
                String[] command = sc.nextLine().split(" ");
                int value = Integer.parseInt(command[1]);

                switch (command[0]) {
                    case "forward":
                        coordinates.horizontalPosition += value;
                        break;
                    case "up":
                        coordinates.depth -= value;
                        break;
                    case "down":
                        coordinates.depth += value;
                        break;
                }
            }

            return coordinates.horizontalPosition * coordinates.depth;
        }
    }

    private static int secondPart() throws IOException {
        try (
                FileInputStream fis = new FileInputStream(FILE_PATH);
                Scanner sc = new Scanner(fis)
        ) {
            Coordinates coordinates = new Coordinates();

            while (sc.hasNextLine()) {
                String[] command = sc.nextLine().split(" ");

                int value = Integer.parseInt(command[1]);

                switch (command[0]) {
                    case "forward":
                        coordinates.horizontalPosition += value;
                        coordinates.depth += coordinates.aim * value;
                        break;
                    case "up":
                        coordinates.aim -= value;
                        break;
                    case "down":
                        coordinates.aim += value;
                        break;
                }
            }
            return coordinates.horizontalPosition * coordinates.depth;
        }
    }
}
