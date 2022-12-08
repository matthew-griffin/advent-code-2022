package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day8Test {

    private final Puzzle puzzle = new Day8();
    private final String testInput = """
            30373
            25512
            65332
            33549
            35390
            """;

    @Test
    void allTreesIn2x2GridAreVisible() {
        var input = "00\n00";
        var result = puzzle.solve(input);
        assertEquals("4", result.partOne);
    }

    @Test
    void allTreesIn3x3GridWithHighCentreAreVisible() {
        var input = "000\n010\n000";
        var result = puzzle.solve(input);
        assertEquals("9", result.partOne);
    }

    @Test
    void lowCentreTreeNotVisible() {
        var input = "999\n919\n999";
        var result = puzzle.solve(input);
        assertEquals("8", result.partOne);
    }

    @Test
    void lowCentreTreeVisibleFromRight() {
        var input = "999\n910\n999";
        var result = puzzle.solve(input);
        assertEquals("9", result.partOne);
    }

    @Test
    void lowCentreTreeVisibleFromBelow() {
        var input = "999\n919\n909";
        var result = puzzle.solve(input);
        assertEquals("9", result.partOne);
    }

    @Test
    void lowCentreTreeVisibleFromBelowAndRight() {
        var input = "999\n910\n909";
        var result = puzzle.solve(input);
        assertEquals("9", result.partOne);
    }

    @Test
    void itShouldCountVisibleTrees() {
        var result = puzzle.solve(testInput);
        assertEquals("21", result.partOne);
    }

    @Test
    void itShouldCalculateHighestScenicScore() {
        var result = puzzle.solve(testInput);
        assertEquals("8", result.partTwo);
    }
}
