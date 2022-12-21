package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import lombok.RequiredArgsConstructor;

public class Day4 implements Puzzle {
    public enum Overlap {
        NONE,
        PARTIAL,
        FULL
    }

    @RequiredArgsConstructor
    public static class Range
    {
        private final int min;
        private final int max;

        private static Range createRange(String inputRange) {
            var range = inputRange.split("-");
            return new Range(Integer.parseInt(range[0]), Integer.parseInt(range[1]));
        }

        public Overlap getOverlap(Range other) {
            if (max < other.min || other.max < min) {
                return Overlap.NONE;
            }
            if (other.min > min && other.max > max) {
                return Overlap.PARTIAL;
            }
            if (min > other.min && max > other.max) {
                return Overlap.PARTIAL;
            }
            return Overlap.FULL;
        }
    }


    @Override
    public PuzzleResult solve(String inputText) {
        var partOne = 0;
        var partTwo = 0;
        var inputLines = inputText.split("\n");
        for (var line: inputLines) {
            var sections = line.split(",");
            var range1 = Range.createRange(sections[0]);
            var range2 = Range.createRange(sections[1]);
            var overlap = range1.getOverlap(range2);
            if (overlap == Overlap.PARTIAL) {
                partTwo++;
            } else if (overlap == Overlap.FULL) {
                partOne++;
                partTwo++;
            }

        }
        return new PuzzleResult(Integer.toString(partOne), Integer.toString(partTwo));
    }
}
