package com.adventofcode;

import com.adventofcode.year2022.*;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class PuzzleService {
    private final Map<PuzzleDate, Puzzle> puzzles = new HashMap<>();

    PuzzleService() {
        puzzles.put(new PuzzleDate(2021, 1), new com.adventofcode.year2021.Day1());
        puzzles.put(new PuzzleDate(2022, 1), new Day1());
        puzzles.put(new PuzzleDate(2022, 2), new Day2());
        puzzles.put(new PuzzleDate(2022, 3), new Day3());
        puzzles.put(new PuzzleDate(2022, 4), new Day4());
        puzzles.put(new PuzzleDate(2022, 5), new Day5());
        puzzles.put(new PuzzleDate(2022, 6), new Day6());
        puzzles.put(new PuzzleDate(2022, 7), new Day7());
        puzzles.put(new PuzzleDate(2022, 8), new Day8());
        puzzles.put(new PuzzleDate(2022, 9), new Day9());
        puzzles.put(new PuzzleDate(2022, 10), new Day10());
        puzzles.put(new PuzzleDate(2022, 11), new Day11());
        puzzles.put(new PuzzleDate(2022, 12), new Day12());
    }

    Collection<PuzzleDate> getPuzzles() {
        return puzzles.keySet();
    }

    Puzzle getPuzzle(PuzzleDate date) {
        return puzzles.get(date);
    }
}
