package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day5Test {

    private final Puzzle puzzle = new Day5();
    private final String testInput = """
                [D]   \s
            [N] [C]   \s
            [Z] [M] [P]
             1   2   3\s
                    
            move 1 from 2 to 1
            move 3 from 1 to 3
            move 2 from 2 to 1
            move 1 from 1 to 2
            """;

    @Test
    void itShouldReturnUnmovedStacks() {
        var input = """
                [A] [B]
                 1   2\s
                """;
        var result = puzzle.solve(input);
        assertEquals("AB", result.partOne);
    }

    @Test
    void itShouldRearrangeStacks() {
        var result = puzzle.solve(testInput);
        assertEquals("CMZ", result.partOne);
    }

    @Test
    void itShouldRearrangeStacksAsGroups() {
        var result = puzzle.solve(testInput);
        assertEquals("MCD", result.partTwo);
    }
}
