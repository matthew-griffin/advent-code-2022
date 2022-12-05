package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day4Test {

    private final Puzzle puzzle = new Day4();
    private final String testInput = """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
            """;

    @Test
    void itShouldCountCompleteOverlaps() {
        var result = puzzle.solve(testInput);
        assertEquals("2", result.partOne);
    }

    @Test
    void itShouldCountAnyOverlaps() {
        var result = puzzle.solve(testInput);
        assertEquals("4", result.partTwo);
    }
}
