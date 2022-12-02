package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day2Test {

    private final Puzzle puzzle = new Day2();
    private final String testInput = """
            A Y
            B X
            C Z
            """;

    @Test
    void itShouldCalculateScoreForRockLoss() {
        var result = puzzle.solve("B X");
        assertEquals("1", result.partOne);
    }

    @Test
    void itShouldCalculateScoreForPaperLoss() {
        var result = puzzle.solve("C Y");
        assertEquals("2", result.partOne);
    }

    @Test
    void itShouldCalculateScoreForScissorsLoss() {
        var result = puzzle.solve("A Z");
        assertEquals("3", result.partOne);
    }

    @Test
    void itShouldCalculateScoreForRockDraw() {
        var result = puzzle.solve("A X");
        assertEquals("4", result.partOne);
    }

    @Test
    void itShouldCalculateScoreForPaperDraw() {
        var result = puzzle.solve("B Y");
        assertEquals("5", result.partOne);
    }

    @Test
    void itShouldCalculateScoreForScissorsDraw() {
        var result = puzzle.solve("C Z");
        assertEquals("6", result.partOne);
    }

    @Test
    void itShouldCalculateScore() {
        var result = puzzle.solve(testInput);
        assertEquals("15", result.partOne);
    }

    @Test
    void itShouldCalculateScoreForOpponentRockLoss() {
        var result = puzzle.solve("A X");
        assertEquals("3", result.partTwo);
    }

    @Test
    void itShouldCalculateScoreForPart2() {
        var result = puzzle.solve(testInput);
        assertEquals("12", result.partTwo);
    }
}
