package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day3Test {

    private final Puzzle puzzle = new Day3();
    private final String testInput = """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw
            """;

    @Test
    void itShouldGetPriorityOfLowercaseA() {
        var result = puzzle.solve("aa");
        assertEquals("1", result.partOne);
    }

    @Test
    void itShouldGetPriorityOfLowercaseZ() {
        var result = puzzle.solve("azbz");
        assertEquals("26", result.partOne);
    }

    @Test
    void itShouldGetPriorityOfUppercaseA() {
        var result = puzzle.solve("AbcA");
        assertEquals("27", result.partOne);
    }

    @Test
    void itShouldSumPrioritiesForDuplicates() {
        var result = puzzle.solve(testInput);
        assertEquals("157", result.partOne);
    }

    @Test
    void itShouldGetLowercaseAPriorityFromGroup() {
        var input = """
                abcb
                bcac
                cdca
                """;
        assertEquals("1", puzzle.solve(input).partTwo);
    }

    @Test
    void itShouldSumPrioritiesOfCommonItemInGroup() {
        var result = puzzle.solve(testInput);
        assertEquals("70", result.partTwo);
    }
}
