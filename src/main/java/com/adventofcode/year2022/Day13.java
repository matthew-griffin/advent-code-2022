package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Optional;

public class Day13 implements Puzzle {
    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {
        var pairs = inputText.split("\n\n");
        var partOne = 0;
        var packetList = new ArrayList<JsonNode>();
        var markerOne = readJson("[[2]]");
        var markerTwo = readJson("[[6]]");
        packetList.add(markerOne);
        packetList.add(markerTwo);
        for (int i = 0; i < pairs.length; i++) {
            var splitPair = pairs[i].split("\n");
            var left = readJson(splitPair[0]);
            packetList.add(left);
            var right = readJson(splitPair[1]);
            packetList.add(right);
            var inOrder = isInOrder(left, right);
            if (inOrder.isPresent() && inOrder.get()) {
                partOne += i + 1;
            }
        }

        packetList.sort((a, b) -> {
            var result = isInOrder(a, b);
            return result.map(inOrder -> inOrder ? -1 : 1).orElse(0);
        });
        var index1 = packetList.indexOf(markerOne) + 1;
        var index2 = packetList.indexOf(markerTwo) + 1;
        return new PuzzleResult(Integer.toString(partOne),Integer.toString(index1*index2));
    }

    private static JsonNode readJson(String content) {
        var mapper = new ObjectMapper();
        try {
            return mapper.readTree(content);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Optional<Boolean> isInOrder(JsonNode left, JsonNode right) {
        var leftSize = left.size();
        var rightSize = right.size();
        for (int i = 0; i < leftSize; i++)
        {
            if (i >= rightSize) {
                return Optional.of(false);
            }
            var leftEntry = left.get(i);
            var rightEntry = right.get(i);
            if (leftEntry.isNumber() && rightEntry.isNumber()) {
                int leftValue = leftEntry.asInt();
                int rightValue = rightEntry.asInt();
                if (leftValue < rightValue) {
                    return Optional.of(true);
                } else if (leftValue > rightValue) {
                    return Optional.of(false);
                }
            } else if (leftEntry.isArray() && rightEntry.isArray()) {
                var inOrder = isInOrder(leftEntry, rightEntry);
                if (inOrder.isPresent()) {
                    return inOrder;
                }
            } else if (leftEntry.isArray()) {
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayNode rightArray = objectMapper.createArrayNode();
                rightArray.add(rightEntry);
                var inOrder = isInOrder(leftEntry, rightArray);
                if (inOrder.isPresent()) {
                    return inOrder;
                }
            } else {
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayNode leftArray =  objectMapper.createArrayNode();
                leftArray.add(leftEntry);
                var inOrder = isInOrder(leftArray, rightEntry);
                if (inOrder.isPresent()) {
                    return inOrder;
                }
            }
        }
        if (leftSize < rightSize) {
            return Optional.of(true);
        }
        return Optional.empty();
    }
}
