package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;

import java.util.HashSet;

public class Day6 implements Puzzle {

    static final int PACKET_SEQ_LEN = 4;
    static final int MESSAGE_SEQ_LEN = 14;

    @Override
    public PuzzleResult solve(String inputText) {
        int partOne = findUniqueSequencePosition(inputText, PACKET_SEQ_LEN);
        int partTwo = findUniqueSequencePosition(inputText, MESSAGE_SEQ_LEN);
        return new PuzzleResult(Integer.toString(partOne), Integer.toString(partTwo));
    }

    private int findUniqueSequencePosition(String inputText, int sequenceLength) {
        for (var possiblePos = sequenceLength; possiblePos <= inputText.length(); possiblePos++)
        {
            var set = new HashSet<Character>();
            for (var i = possiblePos - sequenceLength; i < possiblePos; i++) {
                set.add(inputText.charAt(i));
            }
            if (set.size() == sequenceLength) {
                return possiblePos;
            }
        }
        throw new RuntimeException("No packet found");
    }
}
