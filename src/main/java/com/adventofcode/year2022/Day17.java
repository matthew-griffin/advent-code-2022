package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Day17 implements Puzzle {
    record Point(int x, int y) {
        @NotNull
        public Point add(Point point) {
            return new Point(x + point.x, y + point.y);
        }
    }

    static final int MAX_ROCK_DROPS = 2022;
    static final int WIDTH = 7;
    static final int Y_OFFSET = 3;
    static final int X_OFFSET = 2;

    static class ShapedRock {
        boolean isResting = false;
        Point cornerPoint;
        int width;
        int height;
        private final Point[] shapePoints;

        private static final Point[][] SHAPES = {
                {new Point(0,0), new Point(1, 0), new Point(2, 0), new Point(3,0)},
                {new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(1, 2)},
                {new Point(0,0), new Point(1, 0), new Point(2, 0), new Point(2, 1), new Point(2,2)},
                {new Point(0,0), new Point(0, 1), new Point(0, 2), new Point(0, 3)},
                {new Point(0,0), new Point(1, 0), new Point(0, 1), new Point(1,1)}
        };

        private static int nextShapeType = 0;

        public static ShapedRock createFirst() {
            nextShapeType = 0;
            return create(0);
        }

        public static ShapedRock create(int currentTowerHeight) {
            var cornerPoint = new Point(X_OFFSET, currentTowerHeight + Y_OFFSET);
            var created = new ShapedRock(cornerPoint, SHAPES[nextShapeType]);
            nextShapeType = (nextShapeType + 1) % SHAPES.length;
            return created;
        }

        private ShapedRock(Point cornerPoint, Point[] shapePoints){
            this.cornerPoint = cornerPoint;
            this.shapePoints = shapePoints;
            for (var point : shapePoints) {
                width = Math.max(width, point.x);
                height = Math.max(height, point.y);
            }
        }

        void tryLateralMove(char movementCode, Set<Point> restingPoints) {
            if (isResting) {
                return;
            }
            var dir = movementCode == '<' ? -1 : 1;
            var testLoc = new Point(cornerPoint.x + dir, cornerPoint.y);
            if (testLoc.x < 0) {
                return;
            }
            if (testLoc.x + width >= WIDTH) {
                return;
            }
            if (intersectsResting(testLoc, restingPoints)) {
                return;
            }
            cornerPoint = testLoc;
        }

        void tryVerticalMove(Set<Point> restingPoints) {
            if (isResting) {
                return;
            }
            var testLoc = new Point(cornerPoint.x, cornerPoint.y - 1);
            if (testLoc.y < 0) {
                isResting = true;
                return;
            }
            if (intersectsResting(testLoc, restingPoints)) {
                isResting = true;
                return;
            }
            cornerPoint = testLoc;
        }

        private boolean intersectsResting(Point testLoc, Set<Point> restingPoints) {
            var testPoints = getShapePoints(testLoc);
            for (var point : testPoints) {
                if (restingPoints.contains(point)) {
                    return true;
                }
            }
            return false;
        }

        Point[] getShapePoints() {
            return getShapePoints(cornerPoint);
        }

        int getVerticalMax() {
            return cornerPoint.y + height;
        }

        private Point[] getShapePoints(Point basePoint) {
            var result = new Point[shapePoints.length];
            for (int i = 0; i < shapePoints.length; i++) {
                result[i] = shapePoints[i].add(basePoint);
            }
            return result;
        }
    }



    @NotNull
    @Override
    public PuzzleResult solve(String inputText) {
        int currentMovementChar = 0;
        int currentTowerHeight = 0;
        int currentDroppedBlocks = 0;

        var movementPattern = inputText.trim();
        var movementLength = movementPattern.length();

        var restingPoints = new HashSet<Point>();
        var currentRock = ShapedRock.createFirst();

        while (currentDroppedBlocks < MAX_ROCK_DROPS) {
            currentRock.tryLateralMove(movementPattern.charAt(currentMovementChar), restingPoints);
            currentMovementChar = (currentMovementChar + 1) % movementLength;
            currentRock.tryVerticalMove(restingPoints);
            if (currentRock.isResting) {
                restingPoints.addAll(Arrays.asList(currentRock.getShapePoints()));
                currentTowerHeight = Math.max(currentTowerHeight, currentRock.getVerticalMax() + 1);
                currentRock = ShapedRock.create(currentTowerHeight);
                currentDroppedBlocks++;
            }
        }

        return new PuzzleResult(Integer.toString(currentTowerHeight),"");
    }
}
