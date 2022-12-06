package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day6Test {

    private final Puzzle puzzle = new Day6();
    private final String testInput = """
            mjqjpqmgbljsphdztnvjfqwrcgsmlb
            """;

    @Test
    void itShouldReturnPacketMarker() {
        var result = puzzle.solve(testInput);
        assertEquals("7", result.partOne);
    }

    @Test
    void itShouldPassOtherExamples(){
        assertEquals("5", puzzle.solve("bvwbjplbgvbhsrlpgdmjqwftvncz").partOne);
        assertEquals("6", puzzle.solve("nppdvjthqldpwncqszvftbrmjlhg").partOne);
        assertEquals("10", puzzle.solve("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").partOne);
        assertEquals("11", puzzle.solve("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").partOne);
    }

    @Test
    void itShouldReturnMessageMarker() {
        var result = puzzle.solve(testInput);
        assertEquals("19", result.partTwo);
    }

    @Test
    void itShouldPassOtherPartTwoExamples(){
        assertEquals("23", puzzle.solve("bvwbjplbgvbhsrlpgdmjqwftvncz").partTwo);
        assertEquals("23", puzzle.solve("nppdvjthqldpwncqszvftbrmjlhg").partTwo);
        assertEquals("29", puzzle.solve("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg").partTwo);
        assertEquals("26", puzzle.solve("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw").partTwo);
    }
}
