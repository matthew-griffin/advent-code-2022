package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day9Test {

    private final Puzzle puzzle = new Day9();
    private final String testInput = """
            R 4
            U 4
            L 3
            D 1
            R 4
            D 1
            L 5
            R 2
            """;

    @Test
    void itShouldCountTailRopePositions() {
        var result = puzzle.solve(testInput);
        assertEquals("13", result.partOne);
    }

    @Test
    void itShouldCountTailRopePositionsFor10Knots() {
        var result = puzzle.solve(testInput);
        assertEquals("1", result.partTwo);
    }

    @Test
    void itShouldCountTailRopePositionsForLongExample() {
        var input = """
                R 5
                U 8
                L 8
                D 3
                R 17
                D 10
                L 25
                U 20
                """;
        var result = puzzle.solve(input);
        assertEquals("36", result.partTwo);
    }
}
