package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Day12 implements Puzzle {

    record Point(int x, int y) {
    }

    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {

        var lines = inputText.split("\n");
        var start = findPoints(lines, 'S').get(0);
        var end = findPoints(lines, 'E').get(0);

        var partOne = calcMinSteps(start, end, lines);

        var lowPoints = findPoints(lines, 'a');
        var partTwo = lowPoints.stream()
                .mapToInt(point -> calcMinSteps(point, end, lines))
                .min().orElse(Integer.MAX_VALUE);

        return new PuzzleResult(Integer.toString(partOne),Integer.toString(partTwo));
    }

    private int calcMinSteps(Point start, Point end, String[] lines) {
        var costSoFar = new HashMap<Point, Integer>();
        costSoFar.put(start, 0);

        var frontier = new PriorityQueue<Point>(Comparator.comparingInt(costSoFar::get));
        frontier.add(start);

        while (!frontier.isEmpty()) {
            var current = frontier.poll();
            if (current.equals(end)) {
                return costSoFar.get(current);
            }

            var neighbours = getNeighbours(current, lines);
            for (var neighbour : neighbours) {
                var newCost = costSoFar.get(current) + 1;
                if (!costSoFar.containsKey(neighbour) || newCost < costSoFar.get(neighbour)) {
                    costSoFar.put(neighbour, newCost);
                    frontier.add(neighbour);
                }
            }
        }
        return Integer.MAX_VALUE;
    }

    private List<Point> getNeighbours(Point current, String[] lines) {
        var result = new ArrayList<Point>();
        var currentHeight = getCurrentHeight(current, lines);
        var right = new Point(current.x + 1, current.y);
        if (canVisit(right, currentHeight, lines)) {
            result.add(right);
        }
        var down = new Point(current.x, current.y + 1);
        if (canVisit(down, currentHeight, lines)) {
            result.add(down);
        }
        var left = new Point(current.x - 1, current.y);
        if (canVisit(left, currentHeight, lines)) {
            result.add(left);
        }
        var up = new Point(current.x, current.y - 1);
        if (canVisit(up, currentHeight, lines)) {
            result.add(up);
        }
        return result;
    }

    private boolean canVisit(Point point, char currentHeight, String[] lines) {
        if (point.y < 0 || point.y >= lines.length) {
            return false;
        }
        if (point.x < 0 || point.x >= lines[point.y].length()) {
            return false;
        }
        var newHeight = getCurrentHeight(point, lines);
        if (newHeight == 'E') {
            return currentHeight == 'y' || currentHeight == 'z';
        }

        return newHeight - currentHeight < 2;
    }

    private static char getCurrentHeight(Point point, String[] lines) {
        var currentHeight = lines[point.y].charAt(point.x);
        if (currentHeight == 'S') {
            return 'a';
        }
        return currentHeight;
    }

    @NotNull
    private List<Point> findPoints(String[] lines, char character) {
        var result = new ArrayList<Point>();
        for (int y = 0; y < lines.length; y++) {
            for (int x = 0; x < lines[y].length(); x++) {
                if (lines[y].charAt(x) == character) {
                    result.add(new Point(x, y));
                }
            }
        }

        return result;
    }
}
