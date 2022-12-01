package com.adventofcode;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class PuzzleService {
    private final Map<PuzzleDate, Puzzle> puzzles = new HashMap<>();

    PuzzleService() {
        puzzles.put(new PuzzleDate(2021, 1), new com.adventofcode.year2021.Day1());
        puzzles.put(new PuzzleDate(2022, 1), new com.adventofcode.year2022.Day1());
    }

    Collection<PuzzleDate> getPuzzles() {
        return puzzles.keySet();
    }

    Puzzle getPuzzle(PuzzleDate date) {
        return puzzles.get(date);
    }
}
