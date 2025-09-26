package com.company;

import java.nio.file.Path;
import java.util.List;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    static final String FILE_PATH = "src/com/company/input.txt";

    static void main(String[] args) {
        try {
            int test = firstPart();
            System.out.println(test);
            System.out.println(secondPart());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int firstPart() throws IOException {
        try (
                FileInputStream fis = new FileInputStream(FILE_PATH);
                Scanner sc = new Scanner(fis)
        ) {
            int lastNumber = Integer.parseInt(sc.nextLine());
            int count = 0;

            while (sc.hasNextLine()) {
                int number = Integer.parseInt(sc.nextLine());

                if (number > lastNumber) {
                    count += 1;
                }

                lastNumber = number;
            }

            return count;
        }
    }

    private static int secondPart() throws IOException {
        Path path = Paths.get(FILE_PATH);

        List<Integer> numbers = Files.lines(path)
                .map(String::trim)
                .filter(line -> !line.isEmpty())
                .map(Integer::parseInt)
                .toList();

        return countSlidingIncreases(numbers, 3);
    }

    private static int countSlidingIncreases(List<Integer> measurements, int windowSize) {
        if (measurements.size() < windowSize) {
            return 0;
        }

        int count = 0;
        int prevSum = 0;

        for (int i = 0; i < windowSize; i++) {
            prevSum += measurements.get(i);
        }

        for (int i = windowSize; i < measurements.size(); i++) {
            int currSum = prevSum - measurements.get(i - windowSize) + measurements.get(i);

            if (currSum > prevSum) {
                count++;
            }
            prevSum = currSum;
        }

        return count;
    }
}
