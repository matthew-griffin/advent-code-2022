package com.adventofcode;

import com.adventofcode.year2021.Day1;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class PuzzleService {
    private final Map<PuzzleDate, Puzzle> puzzles = new HashMap<>();

    PuzzleService() {
        puzzles.put(new PuzzleDate(2021, 1), new Day1());
    }

    Collection<PuzzleDate> getPuzzles() {
        return puzzles.keySet();
    }

    Puzzle getPuzzle(PuzzleDate date) {
        return puzzles.get(date);
    }
}
