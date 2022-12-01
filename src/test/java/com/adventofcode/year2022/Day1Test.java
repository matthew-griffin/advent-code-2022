package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day1Test {

    private final Puzzle puzzle = new Day1();
    private final String testInput = """
        1000
        2000
        3000
                    
        4000
                    
        5000
        6000
                    
        7000
        8000
        9000
                    
        10000
        """;

    @Test
    void itShouldCountMostCalories() {
        var result = puzzle.solve(testInput);
        assertEquals("24000", result.partOne);
    }

    @Test
    void itShouldSumTop3Calories() {
        var result = puzzle.solve(testInput);
        assertEquals("45000", result.partTwo);
    }
}
