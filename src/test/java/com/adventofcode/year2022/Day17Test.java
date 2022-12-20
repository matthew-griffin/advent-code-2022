package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day17Test {

    private final Puzzle puzzle = new Day17();
    private final String testInput = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>\n";

    @Test
    void itShouldCountHeightOfTower() {
        var result = puzzle.solve(testInput);
        assertEquals("3068", result.partOne);
    }

    @Test
    void itShouldDoSomething() {
        var result = puzzle.solve(testInput);
        assertEquals("45000", result.partTwo);
    }
}
