package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;

public class Day3 implements Puzzle {
    @Override
    public PuzzleResult solve(String inputText) {
        var lines = inputText.split("\n");
        var partOne = 0;
        var partTwo = 0;
        String[] group = new String[3];
        for (int i = 0; i < lines.length; i++) {
            var bagCode = lines[i];
            var halfSize = bagCode.length() / 2;
            var parts = new String[]{
                    bagCode.substring(0, halfSize),
                    bagCode.substring(halfSize)
            };
            partOne += getPriority(findCommonChar(parts));

            var memberIndex = i % 3;
            group[memberIndex] = bagCode;
            if (memberIndex == 2) {
                partTwo += getPriority(findCommonChar(group));
            }
        }
        return new PuzzleResult(Integer.toString(partOne), Integer.toString(partTwo));
    }

    private char findCommonChar(String[] strings) {
        if (strings.length < 2)
            throw new RuntimeException("Not enough strings to compare");

        for (int i = 0; i < strings[0].length(); i++) {
            char possibleCommon = strings[0].charAt(i);
            var allContain = true;
            for (int j = 1; j < strings.length; j++) {
                if (!strings[j].contains(String.valueOf(possibleCommon))) {
                    allContain = false;
                    break;
                }
            }
            if (allContain) {
                return possibleCommon;
            }
        }

        throw new RuntimeException("No common char");
    }

    private int getPriority(char character) {
        if (character >= 'a' && character <= 'z') {
            return (character - 'a') + 1;
        } else if (character >= 'A' && character <= 'Z'){
            return (character - 'A') + 27;
        }

        throw new RuntimeException("Unsupported character");
    }
}
