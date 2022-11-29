package com.adventofcode.year2021;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;

import java.util.Arrays;
import java.util.List;

public class Day1 implements Puzzle {
    @Override
    public PuzzleResult solve(String inputText) {
        var input = Arrays.stream(inputText.split("\n")).map(Integer::parseInt).toList();
        var partOne = getWindowedIncreases(input, 1);
        var partTwo = getWindowedIncreases(input, 3);

        return new PuzzleResult(Integer.toString(partOne), Integer.toString(partTwo));
    }

    private int getWindowedIncreases(List<Integer> input, int window)
    {
        var increases = 0;
        Integer lastValue = null;
        for (var i = 0; i < input.size() - (window - 1); i++) {
            var value = input.subList(i, i + window).stream().reduce(0, Integer::sum);
            if (lastValue != null && value > lastValue)
                increases++;
            lastValue = value;
        }
        return increases;
    }
}
