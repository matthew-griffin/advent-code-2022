package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day12Test {

    private final Puzzle puzzle = new Day12();
    private final String testInput = """
            Sabqponm
            abcryxxl
            accszExk
            acctuvwj
            abdefghi
            """;

    @Test
    void minimumStepsWillBe25() {
        var result = puzzle.solve("SbcdefghijklmnopqrstuvwxyE");
        assertEquals("25", result.partOne);
    }

    @Test
    void itShouldGetFewestSteps() {
        var result = puzzle.solve(testInput);
        assertEquals("31", result.partOne);
    }

    @Test
    void itShouldFindFewestStepsFromAnyLowPoint() {
        var result = puzzle.solve(testInput);
        assertEquals("29", result.partTwo);
    }
}
