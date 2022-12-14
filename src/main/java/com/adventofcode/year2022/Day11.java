package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day11 implements Puzzle {
    static final int PART_ONE_ROUNDS = 20;
    static final int PART_TWO_ROUNDS = 10000;

    private final Pattern monkeyPattern = Pattern.compile("Monkey (\\d+):");
    private final Pattern itemsPattern = Pattern.compile("Starting items: (\\d+(?:,\\s*\\d+)*)");
    private final Pattern operationPattern = Pattern.compile("Operation: new = old ([+*]) (\\d+|old)");
    private final Pattern testPattern = Pattern.compile("Test: divisible by (\\d+)");
    private final Pattern optionPattern = Pattern.compile("If (true|false): throw to monkey (\\d+)");

    private interface Operation {
        long operate(long old);
    }

    private static class Monkey {
        private long inspectionCount = 0;
        Deque<Long> items;
        Operation inspection;
        long divisor;
        int positiveMonkeyIndex;
        int negativeMonkeyIndex;
    }

    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {
        var partOne = calculateMonkeyBusiness(inputText, PART_ONE_ROUNDS, true);
        var partTwo =  calculateMonkeyBusiness(inputText, PART_TWO_ROUNDS, false);
        return new PuzzleResult(partOne.toString(), partTwo.toString());
    }

    private Long calculateMonkeyBusiness(String input, int rounds, boolean divideWorry) {
        var monkeys = parseMonkeys(input);
        var groupMod = monkeys.stream()
                .mapToLong(monkey -> monkey.divisor)
                .reduce(1L, (a, b) -> a * b);
        for (int round = 0; round < rounds; round++) {
            if (isPrintedRound(round)) {
                System.out.printf("After round %d of %d%n", round, rounds);
            }
            for (int monkeyIndex = 0; monkeyIndex < monkeys.size(); monkeyIndex++) {
                var monkey = monkeys.get(monkeyIndex);
                if (isPrintedRound(round)) {
                    System.out.printf("Monkey %d inspected items %d times.%n", monkeyIndex, monkey.inspectionCount);
                }
                while (!monkey.items.isEmpty()) {
                    var old = monkey.items.pop();
                    monkey.inspectionCount++;
                    var newWorry = monkey.inspection.operate(old);
                    if (divideWorry) {
                        newWorry = newWorry / 3;
                    }
                    newWorry = newWorry % groupMod;
                    if (newWorry % monkey.divisor == 0L) {
                        monkeys.get(monkey.positiveMonkeyIndex).items.addLast(newWorry);
                    } else {
                        monkeys.get(monkey.negativeMonkeyIndex).items.addLast(newWorry);
                    }
                }

            }

        }
        return monkeys.stream()
                .map(monkey -> monkey.inspectionCount)
                .sorted(Comparator.reverseOrder())
                .limit(2)
                .reduce(1L, (a,b) -> a * b);
    }

    @SuppressWarnings("unused")
    private static boolean isPrintedRound(int round) {
        return false;
        /*if (round == 0)
            return false;
        if (round == 1 || round == 20)
            return true;
        return round % 1000 == 0;*/
    }

    @NotNull
    private List<Monkey> parseMonkeys(String inputText) {
        var result = new ArrayList<Monkey>();
        var monkeys = inputText.split("\n\n");
        for (var monkeyDesc : monkeys)
        {
            var lines = monkeyDesc.split("\n");
            var monkey = new Monkey();
            for (var line : lines) {
                var monkeyMatch = monkeyPattern.matcher(line);
                if (monkeyMatch.find()) {
                    var index = Integer.parseInt(monkeyMatch.group(1));
                    result.add(index, monkey);
                    continue;
                }
                var itemsMatch = itemsPattern.matcher(line);
                if (itemsMatch.find()) {
                    var items = itemsMatch.group(1).split(",\\s*");
                    monkey.items = Arrays.stream(items)
                            .map(Long::parseLong)
                            .collect(Collectors.toCollection(ArrayDeque::new));
                    continue;
                }
                var operationMatch = operationPattern.matcher(line);
                if (operationMatch.find()) {
                    monkey.inspection = createOperation(operationMatch.group(1), operationMatch.group(2));
                    continue;
                }
                var testMatch = testPattern.matcher(line);
                if (testMatch.find()) {
                    monkey.divisor = Long.parseLong(testMatch.group(1));
                    continue;
                }
                var optionMatch = optionPattern.matcher(line);
                if (optionMatch.find()) {
                    var index = Integer.parseInt(optionMatch.group(2));
                    if (optionMatch.group(1).equals("true")) {
                        monkey.positiveMonkeyIndex = index;
                    } else {
                        monkey.negativeMonkeyIndex = index;
                    }
                }
            }
        }
        return result;
    }

    private Operation createOperation(String operator, String operand) {
        if (operand.equals("old")) {
            if (operator.equals("*")) {
                return old -> old * old;
            } else {
                return old -> old + old;
            }
        } else {
            var value = Long.parseLong(operand);
            if (operator.equals("*")) {
                return old -> old * value;
            } else {
                return old -> old + value;
            }
        }
    }
}
