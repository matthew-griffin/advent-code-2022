package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;

public class Day5 implements Puzzle {

    private record Instruction(int count, int from, int to){}

    private interface MovementStrategy {
        void move(Deque<Character> fromStack, Deque<Character> toStack, int count);
    }

    private final Pattern instructionPattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");

    @Override
    public PuzzleResult solve(String inputText) {
        var data = inputText.split("\n\n");
        var crates = parseCrates(data[0]);
        var instructions = data.length > 1
                ? parseInstructions(data[1])
                : new ArrayList<Instruction>();
        var cratesCopy = copyCrates(crates);
        moveCrates(crates, instructions, (from, to, count) -> {
            for (int i = 0; i < count; i++) {
                to.push(from.pop());
            }
        });
        moveCrates(cratesCopy, instructions, (from, to, count) -> {
            var tempStack = new ArrayDeque<Character>();
            for (int i = 0; i < count; i++) {
                tempStack.push(from.pop());
            }
            while (!tempStack.isEmpty()) {
                to.push(tempStack.pop());
            }
        });
        return new PuzzleResult(getCrateString(crates), getCrateString(cratesCopy));
    }

    private List<Deque<Character>> parseCrates(String crateDescription) {
        var result = new ArrayList<Deque<Character>>();
        var lines = crateDescription.split("\n");
        for (var line : lines) {
            var column = 0;
            var index = 0;
            for (; index < line.length(); index += 4, column++) {
                if (line.charAt(index) == '[') {
                    while (column >= result.size()) {
                        result.add(new ArrayDeque<>());
                    }
                    result.get(column).addLast(line.charAt(index + 1));
                }
            }
        }

        return result;
    }

    private List<Deque<Character>> copyCrates(List<Deque<Character>> crates) {
        var result = new ArrayList<Deque<Character>>(crates.size());
        for (var stack : crates) {
            result.add(new ArrayDeque<>(stack));
        }
        return result;
    }

    private List<Instruction> parseInstructions(String instructions) {
        var lines = instructions.split("\n");
        var result = new ArrayList<Instruction>(lines.length);
        for (var line : lines) {
            var matcher = instructionPattern.matcher(line);
            if (matcher.find()) {
                var count = Integer.parseInt(matcher.group(1));
                var from = Integer.parseInt(matcher.group(2)) - 1;
                var to = Integer.parseInt(matcher.group(3)) - 1;
                result.add(new Instruction(count, from, to));
            }
        }
        return result;
    }

    private void moveCrates(List<Deque<Character>> crates, List<Instruction> instructions, MovementStrategy strategy) {
        for (var instruction : instructions) {
            var fromStack = crates.get(instruction.from);
            var toStack = crates.get(instruction.to);
            strategy.move(fromStack, toStack, instruction.count);
        }
    }

    @NotNull
    private static String getCrateString(List<Deque<Character>> crates) {
        var builder = new StringBuilder();
        for (var stack : crates) {
            builder.append(stack.peek());
        }
        return builder.toString();
    }
}
