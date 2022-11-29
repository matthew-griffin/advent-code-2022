package com.adventofcode;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping("/api/puzzles")
public class PuzzleController {

    private final PuzzleService puzzleService;
    private final PuzzleInputService inputService;

    PuzzleController(PuzzleService puzzleService, PuzzleInputService inputService) {
        this.puzzleService = puzzleService;
        this.inputService = inputService;
    }

    @GetMapping
    Collection<PuzzleDate> getPuzzles() {
        return puzzleService.getPuzzles();
    }

    @GetMapping("/{year}/{day}")
    PuzzleResult solvePuzzle(@PathVariable Integer year, @PathVariable Integer day) {
        var date = new PuzzleDate(year, day);
        var puzzle = puzzleService.getPuzzle(date);
        if (puzzle == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Puzzle Not Implemented");
        }
        try {
            return puzzle.solve(inputService.getPuzzleInput(date));
        } catch (IOException | InterruptedException e) {
            throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY);
        }
    }
}
