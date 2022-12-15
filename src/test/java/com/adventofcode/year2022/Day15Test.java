package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day15Test {

    private final Puzzle puzzle = new Day15(10, 20);
    private final String testInput = """
            Sensor at x=2, y=18: closest beacon is at x=-2, y=15
            Sensor at x=9, y=16: closest beacon is at x=10, y=16
            Sensor at x=13, y=2: closest beacon is at x=15, y=3
            Sensor at x=12, y=14: closest beacon is at x=10, y=16
            Sensor at x=10, y=20: closest beacon is at x=10, y=16
            Sensor at x=14, y=17: closest beacon is at x=10, y=16
            Sensor at x=8, y=7: closest beacon is at x=2, y=10
            Sensor at x=2, y=0: closest beacon is at x=2, y=10
            Sensor at x=0, y=11: closest beacon is at x=2, y=10
            Sensor at x=20, y=14: closest beacon is at x=25, y=17
            Sensor at x=17, y=20: closest beacon is at x=21, y=22
            Sensor at x=16, y=7: closest beacon is at x=15, y=3
            Sensor at x=14, y=3: closest beacon is at x=15, y=3
            Sensor at x=20, y=1: closest beacon is at x=15, y=3
            """;

    @Test
    void singleSensorCannotReachRow() {
        var input = "Sensor at x=0, y=0: closest beacon is at x=1, y=1";
        var puzzleRow3 = new Day15(3, 0);
        var result = puzzleRow3.solve(input);
        assertEquals("0", result.partOne);
    }

    @Test
    void singleSensorCanRuleOutOneIndex() {
        var input = "Sensor at x=0, y=0: closest beacon is at x=1, y=1";
        var puzzleRow2 = new Day15(2, 0);
        var result = puzzleRow2.solve(input);
        assertEquals("1", result.partOne);
    }

    @Test
    void singleSensorCannotRuleOutBeaconOnRow() {
        var input = "Sensor at x=0, y=0: closest beacon is at x=0, y=2";
        var puzzleRow2 = new Day15(2, 0);
        var result = puzzleRow2.solve(input);
        assertEquals("0", result.partOne);
    }

    @Test
    void singleSensorCanRuleOutThreeIndices() {
        var input = "Sensor at x=0, y=0: closest beacon is at x=0, y=2";
        var puzzleRow2 = new Day15(1, 0);
        var result = puzzleRow2.solve(input);
        assertEquals("3", result.partOne);
    }

    @Test
    void itShouldCountPositionsWhereBeaconCannotBe() {
        var result = puzzle.solve(testInput);
        assertEquals("26", result.partOne);
    }

    @Test
    void itShouldGetTuningFreqOfMissingBeacon() {
        var result = puzzle.solve(testInput);
        assertEquals("56000011", result.partTwo);
    }
}
