package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day14Test {

    private final Puzzle puzzle = new Day14();
    private final String testInput = """
            498,4 -> 498,6 -> 496,6
            503,4 -> 502,4 -> 502,9 -> 494,9
            """;

    @Test
    void itShouldCreatePyramid() {
        var input = "497,4 -> 503,4";
        var result = puzzle.solve(input);
        assertEquals("9", result.partOne);
        assertEquals("24", result.partTwo);
    }

    @Test
    void itShouldCountRestingUnitsOfSand() {
        var result = puzzle.solve(testInput);
        assertEquals("24", result.partOne);
    }

    @Test
    void itShouldCountRestingUnitsOfSandWithFloor() {
        var result = puzzle.solve(testInput);
        assertEquals("93", result.partTwo);
    }
}
