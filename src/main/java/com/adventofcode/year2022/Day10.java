package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

public class Day10 implements Puzzle {
    private int cycle;
    private int xRegister;
    private int partOne;
    private StringBuilder partTwo;

    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {
        cycle = 1;
        xRegister = 1;
        partOne = 0;
        partTwo = new StringBuilder();
        var lines = inputText.split("\n");
        for (var line : lines) {
            if (line.equals("noop")) {
                processCycle();
            } else {
                var parts = line.split(" ");
                var value = Integer.parseInt(parts[1]);
                processCycle();
                processCycle();
                xRegister += value;
            }
        }
        return new PuzzleResult(Integer.toString(partOne), partTwo.toString());
    }

    private void processCycle()
    {
        var linePos = (cycle-1) % 40;
        if (linePos >= xRegister-1 && linePos <= xRegister + 1) {
            partTwo.append("#");
        } else {
            partTwo.append(".");
        }
        if (linePos == 39) {
            partTwo.append("\n");
        }
        if ((cycle - 20) % 40 == 0) {
            partOne += cycle * xRegister;
        }
        cycle++;
    }
}
