package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day21Test {

    private final Puzzle puzzle = new Day21();
    private final String testInput = """
            root: pppw + sjmn
            dbpl: 5
            cczh: sllz + lgvd
            zczc: 2
            ptdq: humn - dvpt
            dvpt: 3
            lfqf: 4
            humn: 5
            ljgn: 2
            sjmn: drzm * dbpl
            sllz: 4
            pppw: cczh / lfqf
            lgvd: ljgn * ptdq
            drzm: hmdt - zczc
            hmdt: 32
            """;

    @Test
    void itShouldCalculateSumOfRoot() {
        var result = puzzle.solve(testInput);
        assertEquals("152", result.partOne);
    }

    @Test
    void itShouldFigureOutValueToShout() {
        var result = puzzle.solve(testInput);
        assertEquals("301", result.partTwo);
    }
}
