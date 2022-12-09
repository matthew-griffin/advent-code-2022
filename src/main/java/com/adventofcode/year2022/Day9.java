package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day9 implements Puzzle {
    private record Point(int x, int y){}

    private static class Rope {
        private final Point[] segments;
        private final Set<Point> tailVisits = new HashSet<>();

        public Rope(int length) {
            if (length < 2) {
                throw new RuntimeException("Cannot model rope with less than 2 segments");
            }
            segments = new Point[length];
            Arrays.setAll(segments, i -> new Point(0,0));
        }

        public void updateHeadPoint(Point direction) {
            segments[0] = add(segments[0], direction);
            for (var i = 1; i < segments.length; i++) {
                var segmentDiff = diff(segments[i-1], segments[i]);
                if (exceedsDistance(segmentDiff)) {
                    segments[i] = moveTowards(segments[i], segmentDiff);
                }
            }

            tailVisits.add(segments[segments.length-1]);
        }

        public String getTailVisits() {
            return Integer.toString(tailVisits.size());
        }

        private Point moveTowards(Point point, Point dir) {
            var moveX = restrictToUnit(dir.x);
            var moveY = restrictToUnit(dir.y);
            return add(point, new Point(moveX, moveY));
        }

        private int restrictToUnit(int value) {
            return value >= 0 ? Math.min(1, value) : -1;
        }

        private boolean exceedsDistance(Point diff) {
            return Math.abs(diff.x) > 1 || Math.abs(diff.y) > 1;
        }

        private Point add(Point a, Point b) {
            return new Point(a.x + b.x, a.y + b.y);
        }

        private Point diff(Point a, Point b) {
            return new Point(a.x - b.x, a.y - b.y);
        }
    }

    private final Map<String, Point> dirs = Map.of(
            "R", new Point(1, 0),
            "L", new Point(-1, 0),
            "U", new Point(0, 1),
            "D", new Point(0, -1)
    );

    @Override
    @NotNull
    public PuzzleResult solve(String inputText) {
        var rope1 = new Rope(2);
        var rope2 = new Rope(10);

        var lines = inputText.split("\n");
        for (var line : lines) {
            var parts = line.split(" ");
            var count = Integer.parseInt(parts[1]);
            var dir = dirs.get(parts[0]);
            for (var i = 0; i < count; i++) {
                rope1.updateHeadPoint(dir);
                rope2.updateHeadPoint(dir);
            }
        }
        return new PuzzleResult(rope1.getTailVisits(), rope2.getTailVisits());
    }
}
