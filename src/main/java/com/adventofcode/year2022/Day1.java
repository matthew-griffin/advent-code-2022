package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;

import java.util.Arrays;
import java.util.Comparator;

public class Day1 implements Puzzle {
    @Override
    public PuzzleResult solve(String inputText) {
        var sortedSumCalories = Arrays.stream(inputText.split("\n\n"))
                .map(elfCalories -> Arrays.stream(elfCalories.split("\n"))
                        .mapToInt(Integer::parseInt)
                        .sum())
                .sorted(Comparator.reverseOrder())
                .toList();
        var partOne = sortedSumCalories.isEmpty() ? "" : sortedSumCalories.get(0).toString();
        var partTwo = Integer.toString(sortedSumCalories.stream().mapToInt(Integer::intValue).limit(3).sum());
        return new PuzzleResult(partOne, partTwo);
    }
}
