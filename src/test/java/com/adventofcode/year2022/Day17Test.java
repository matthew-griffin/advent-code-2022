package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Disabled;
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
    @Disabled
    void itShouldCountHeightAfterMoreDrops() {
        var result = puzzle.solve(testInput);
        assertEquals("1514285714288", result.partTwo);
    }
}
