package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day13Test {

    private final Puzzle puzzle = new Day13();
    private final String testInput = """
            [1,1,3,1,1]
            [1,1,5,1,1]
                    
            [[1],[2,3,4]]
            [[1],4]
                    
            [9]
            [[8,7,6]]
                    
            [[4,4],4,4]
            [[4,4],4,4,4]
                    
            [7,7,7,7]
            [7,7,7]
                    
            []
            [3]
                    
            [[[]]]
            [[]]
                    
            [1,[2,[3,[4,[5,6,7]]]],8,9]
            [1,[2,[3,[4,[5,6,0]]]],8,9]
            """;

    @Test
    void itShouldSumPairIndices() {
        var result = puzzle.solve(testInput);
        assertEquals("13", result.partOne);
    }

    @Test
    void itShouldCalcDecoderKey() {
        var result = puzzle.solve(testInput);
        assertEquals("140", result.partTwo);
    }
}
