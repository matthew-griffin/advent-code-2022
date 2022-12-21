package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Day21 implements Puzzle {
    record MonkeyOperation(String first, String second, String type) {
    }

    static final Pattern EQUATION_PATTERN = Pattern.compile("([a-z]{4}): ([a-z]{4}) ([+\\-*/]) ([a-z]{4})");
    static final Pattern NUMBER_PATTERN = Pattern.compile("([a-z]{4}): (\\d+)");
    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {
        Map<String, Long> knownNumbers = new HashMap<>();
        Map<String, MonkeyOperation> operations = new HashMap<>();
        var lines = inputText.split("\n");
        for (var line : lines) {
            var numberMatch = NUMBER_PATTERN.matcher(line);
            if (numberMatch.find()) {
                knownNumbers.put(numberMatch.group(1), Long.parseLong(numberMatch.group(2)));
                continue;
            }
            var equationMatch = EQUATION_PATTERN.matcher(line);
            if (!equationMatch.find()) {
                continue;
            }
            var name = equationMatch.group(1);
            var firstName = equationMatch.group(2);
            var operationType = equationMatch.group(3);
            var secondName = equationMatch.group(4);
            operations.put(name, new MonkeyOperation(firstName, secondName, operationType));
        }

        var partOne = calculateResult("root", operations, knownNumbers);

        return new PuzzleResult(Long.toString(partOne),"");
    }

    private long calculateResult(String name, Map<String, MonkeyOperation> operations, Map<String, Long> knownNumbers) {
        var number = knownNumbers.get(name);
        if (number != null) {
            return number;
        }
        var operation = operations.get(name);
        var firstNumber = knownNumbers.get(operation.first());
        if (firstNumber == null) {
            firstNumber = calculateResult(operation.first(), operations, knownNumbers);
        }
        var secondNumber = knownNumbers.get(operation.second());
        if (secondNumber == null) {
            secondNumber = calculateResult(operation.second(), operations, knownNumbers);
        }

        var result = performOperation(firstNumber, secondNumber, operation.type());
        knownNumbers.put(name, result);
        return result;
    }

    private long performOperation(long firstNumber, long secondNumber, String operation) {
        return switch (operation) {
            case "+" -> firstNumber + secondNumber;
            case "-" -> firstNumber - secondNumber;
            case "*" -> firstNumber * secondNumber;
            case "/" -> firstNumber / secondNumber;
            default -> throw new IllegalStateException("Unexpected operation: " + operation);
        };
    }
}
