package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day7Test {

    private final Puzzle puzzle = new Day7();
    private final String testInput = """
            $ cd /
            $ ls
            dir a
            14848514 b.txt
            8504156 c.dat
            dir d
            $ cd a
            $ ls
            dir e
            29116 f
            2557 g
            62596 h.lst
            $ cd e
            $ ls
            584 i
            $ cd ..
            $ cd ..
            $ cd d
            $ ls
            4060174 j
            8033020 d.log
            5626152 d.ext
            7214296 k
            """;

    @Test
    void returnsSizeFolderAndFile() {
        var input = """
                $ cd /
                $ ls
                123 a
                dir b
                $ cd b
                $ ls
                1 c
                """;
        var result = puzzle.solve(input);
        assertEquals("125", result.partOne);
    }

    @Test
    void itShouldSumDirectoriesUnder100000() {
        var result = puzzle.solve(testInput);
        assertEquals("95437", result.partOne);
    }

    @Test
    void itShouldReturnSizeOfSmallestDirThatCanBeDeleted() {
        var result = puzzle.solve(testInput);
        assertEquals("24933642", result.partTwo);
    }
}
