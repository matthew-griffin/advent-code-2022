package com.adventofcode.year2021;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {

    @Test
    public void itShouldCountIncreases() {
        var puzzle = new Day1();
        var input = """
                199
                200
                208
                210
                200
                207
                240
                269
                260
                263
                """;
        var result = puzzle.solve(input);
        assertEquals("7", result.partOne);
    }

    @Test
    public void itShouldCountWindowedIncreases() {
        var puzzle = new Day1();
        var input = """
                199
                200
                208
                210
                200
                207
                240
                269
                260
                263
                """;
        var result = puzzle.solve(input);
        assertEquals("5", result.partTwo);
    }
}
