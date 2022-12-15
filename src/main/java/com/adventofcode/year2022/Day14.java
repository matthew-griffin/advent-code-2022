package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Day14 implements Puzzle {

    public record Point(int x, int y) {
    }

    public enum PointType {
        ROCK,
        SAND
    }

    public static class Scene {
        Map<Point, PointType> scenePoints = new HashMap<>();
        int maxY = 0;
        int minX = Integer.MAX_VALUE;
        int maxX = 0;
        int restingSandCount = 0;
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType") // Class is not intended to be serialized
        Optional<Integer> floorLevel = Optional.empty();

        public void addRockLine(Point start, Point end) {
            scenePoints.put(start, PointType.ROCK);
            minX = Math.min(start.x, minX);
            maxX = Math.max(start.x, maxX);
            maxY = Math.max(start.y, maxY);
            scenePoints.put(end, PointType.ROCK);
            minX = Math.min(end.x, minX);
            maxX = Math.max(end.x, maxX);
            maxY = Math.max(end.y, maxY);
            var xDir = Integer.signum(end.x - start.x);
            var yDir = Integer.signum(end.y - start.y);
            if (xDir == 0) {
                for (int y = start.y; y != end.y; y += yDir) {
                    scenePoints.put(new Point(start.x, y), PointType.ROCK);
                }
            } else {
                for (int x = start.x; x != end.x; x += xDir) {
                    scenePoints.put(new Point(x, start.y), PointType.ROCK);
                }
            }
        }

        public boolean isOutsideArea(Point point) {
            return point.y > maxY || point.x > maxX || point.x < minX;
        }

        public boolean isFree(Point point) {
            if (floorLevel.isPresent() && floorLevel.get() == point.y) {
                return false;
            }
            return !scenePoints.containsKey(point);
        }

        public void addRestingSand(Point sand) {
            scenePoints.put(sand, PointType.SAND);
            minX = Math.min(sand.x, minX);
            maxX = Math.max(sand.x, maxX);
            maxY = Math.max(sand.y, maxY);
            restingSandCount++;
        }

        public String createTextDescription(Point sand) {
            var builder = new StringBuilder();
            var localMinX = Math.min(minX, sand.x);
            var localMaxX = Math.max(maxX, sand.x);
            var localMaxY = Math.max(maxY, sand.y);
            if (floorLevel.isPresent()) {
                localMaxY = Math.max(localMaxY, floorLevel.get());
            }
            for (int y = 0; y <= localMaxY; y++) {
                boolean atFloor = floorLevel.isPresent() && floorLevel.get() == y;
                for (int x = localMinX; x <= localMaxX; x++) {
                    if (atFloor) {
                        builder.append('#');
                        continue;
                    }
                    var point = new Point(x, y);
                    if (point.equals(sand)) {
                        builder.append('~');
                        continue;
                    }
                    if (!scenePoints.containsKey(point)) {
                        builder.append('.');
                        continue;
                    }
                    builder.append(scenePoints.get(point) == PointType.ROCK ? '#' : 'o');
                }
                builder.append('\n');
            }
            return builder.toString();
        }

        public int getRestingSandCount() {
            return restingSandCount;
        }

        public void addFloor() {
            floorLevel = Optional.of(maxY + 2);
        }
    }

    Scene scene;


    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {
        var inputLines = inputText.split("\n");
        scene = new Scene();
        for (var inputLine : inputLines) {
            var points = Arrays.stream(inputLine.split("\\s*->\\s*"))
                    .map(pointString -> {
                        var parts = pointString.split(",");
                        var x = Integer.parseInt(parts[0]);
                        var y = Integer.parseInt(parts[1]);
                        return new Point(x, y);
                    }).toList();
            for (int i = 1; i < points.size(); i++) {
                var start = points.get(i - 1);
                var end = points.get(i);
                scene.addRockLine(start, end);
            }
        }

        var abyssReached = false;
        while (!abyssReached) {
            var sand = new Point(500, 0);
            var sandSettled = false;
            while (!sandSettled && !abyssReached) {
                if (scene.isOutsideArea(sand)) {
                    abyssReached = true;
                    continue;
                }
                var testDown = new Point(sand.x, sand.y + 1);
                if (scene.isFree(testDown)) {
                    sand = testDown;
                    continue;
                }
                var testLeft = new Point(sand.x - 1, testDown.y);
                if (scene.isFree(testLeft)) {
                    sand = testLeft;
                    continue;
                }
                var testRight = new Point(sand.x + 1, testDown.y);
                if (scene.isFree(testRight)) {
                    sand = testRight;
                    continue;
                }
                scene.addRestingSand(sand);
                sandSettled = true;
            }
            System.out.println(scene.createTextDescription(sand));
        }
        var partOne = scene.getRestingSandCount();
        scene.addFloor();
        var settledAtStart = false;
        while (!settledAtStart) {
            var sand = new Point(500, 0);
            var sandSettled = false;
            while (!sandSettled) {
                var testDown = new Point(sand.x, sand.y + 1);
                if (scene.isFree(testDown)) {
                    sand = testDown;
                    continue;
                }
                var testLeft = new Point(sand.x - 1, testDown.y);
                if (scene.isFree(testLeft)) {
                    sand = testLeft;
                    continue;
                }
                var testRight = new Point(sand.x + 1, testDown.y);
                if (scene.isFree(testRight)) {
                    sand = testRight;
                    continue;
                }
                scene.addRestingSand(sand);
                if (sand.y == 0) {
                    settledAtStart = true;
                }
                sandSettled = true;
            }
            System.out.println(scene.createTextDescription(sand));
        }
        var partTwo = scene.getRestingSandCount();
        return new PuzzleResult(Integer.toString(partOne), Integer.toString(partTwo));
    }
}
