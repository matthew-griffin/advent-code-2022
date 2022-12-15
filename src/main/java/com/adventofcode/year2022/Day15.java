package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day15 implements Puzzle {
    private static final long TUNING_MULTIPLIER = 4000000L;

    private record Point(int x, int y) {
    }

    private record SensorBeaconPair(Point sensor, Point beacon) {

        int getManhattanDistance() {
                return Math.abs(beacon.x - sensor.x) + Math.abs(beacon.y - sensor.y);
        }
    }

    private record InclusiveInterval(int min, int max) {

        public int size() {
                return max - min + 1;
        }
    }


    private final int rowOfInterest;
    private final int searchMaximum;
    private final static Pattern sensorPattern = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

    public Day15(int rowOfInterest, int searchMaximum) {
        this.rowOfInterest = rowOfInterest;
        this.searchMaximum = searchMaximum;
    }

    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {
        var entries = parseSensorBeaconPairs(inputText);
        var partOne = findNonBeaconIndices(rowOfInterest, entries).size();
        var beaconPoint = findMissingBeacon(entries);
        var partTwo = beaconPoint.x * TUNING_MULTIPLIER + beaconPoint.y;

        return new PuzzleResult(Integer.toString(partOne), Long.toString(partTwo));
    }

    @NotNull
    private static List<SensorBeaconPair> parseSensorBeaconPairs(String inputText) {
        return sensorPattern.matcher(inputText)
                .results()
                .map(m -> {
                    var sensor = new Point(Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)));
                    var beacon = new Point(Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)));
                    return new SensorBeaconPair(sensor, beacon);
                })
                .toList();
    }

    private Set<Long> findNonBeaconIndices(int row, List<SensorBeaconPair> entries) {
        return entries.stream()
                .map(entry -> {
                    var results = new ArrayList<Long>();
                    var manDist = entry.getManhattanDistance();
                    var verticalDist = Math.abs(entry.sensor.y - row);
                    if (manDist < verticalDist) {
                        return results;
                    }

                    var remainder = manDist - verticalDist;
                    for (long x = entry.sensor.x - remainder; x <= entry.sensor.x + remainder; x++) {
                        if (x == entry.beacon.x && row == entry.beacon.y) {
                            continue;
                        }
                        results.add(x);
                    }

                    return results;
                })
                .flatMap(List::stream)
                .collect(Collectors.toSet());
    }

    private List<InclusiveInterval> findNonBeaconIntervals(int row, List<SensorBeaconPair> entries, int searchMaximum) {
        var sortedIntervals = entries.stream()
                .map(entry -> {
                    var result = new ArrayList<InclusiveInterval>();
                    var manDist = entry.getManhattanDistance();
                    var verticalDist = Math.abs(entry.sensor.y - row);
                    if (manDist < verticalDist) {
                        return result;
                    }
                    var remainder = manDist - verticalDist;
                    result.add(new InclusiveInterval(Math.max(0, entry.sensor.x - remainder), Math.min(searchMaximum, entry.sensor.x + remainder)));
                    return result;
                })
                .flatMap(List::stream)
                .sorted(Comparator.comparingLong(a -> a.min))
                .toList();
        var stack = new Stack<InclusiveInterval>();
        stack.push(sortedIntervals.get(0));
        for (int i = 1; i < sortedIntervals.size(); i++) {
            var top = stack.peek();
            var current = sortedIntervals.get(i);
            if (current.min > top.max + 1) {
                stack.push(current);
            } else if (current.max > top.max) {
                stack.pop();
                stack.push(new InclusiveInterval(top.min, current.max));
            }
        }

        return stack;
    }

    private Point findMissingBeacon(List<SensorBeaconPair> entries) {
        for (int y = 0; y <= searchMaximum; y++) {
            var nonBeaconIntervals = findNonBeaconIntervals(y, entries, searchMaximum);
            if (nonBeaconIntervals.size() == 0) {
                continue;
            }
            if (nonBeaconIntervals.size() == 1 && nonBeaconIntervals.get(0).size() == searchMaximum + 1) {
                continue;
            }
            if (nonBeaconIntervals.size() == 2) {
                return new Point(nonBeaconIntervals.get(0).max + 1, y);
            }
            if (nonBeaconIntervals.get(0).min > 0) {
                return new Point(0, y);
            } else {
                return new Point(searchMaximum, y);
            }
        }
        return new Point(0, 0);
    }
}
