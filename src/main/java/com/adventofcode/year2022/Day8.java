package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class Day8 implements Puzzle {
    private record GridPoint(int x, int y){}
    private record ViewResult(boolean visible, int viewDistance){}

    private int width;
    private int height;
    private int[][] grid;

    @Override
    public PuzzleResult solve(String inputText) {
        populateGrid(inputText);
        return calculatePuzzleResult();
    }

    private void populateGrid(String inputText) {
        var lines = inputText.split("\n");
        if (lines.length == 0) {
            throw new RuntimeException("Empty Input");
        }
        width = lines[0].length();
        height = lines.length;
        grid = new int[width][height];
        for (var y = 0; y < height; y++) {
            var line = lines[y];
            for (var x = 0; x < width; x++) {
                grid[x][y] = Character.getNumericValue(line.charAt(x));
            }
        }
    }

    @NotNull
    private PuzzleResult calculatePuzzleResult() {
        var visibleTrees = width * 2 + (height - 2) * 2;
        var maxScenicScore = 0;
        for (var y = 1; y < height-1; y++) {
            for (var x = 1; x < width-1; x++) {
                List<ViewResult> views = new ArrayList<>(4);
                var point = new GridPoint(x, y);
                views.add(getLeftView(point));
                views.add(getTopView(point));
                views.add(getRightView(point));
                views.add(getBelowView(point));
                if (views.stream().anyMatch(viewResult -> viewResult.visible)) {
                    visibleTrees++;
                }
                var scenicScore = views.stream()
                        .map(viewResult -> viewResult.viewDistance)
                        .reduce(1, (a, b) -> a * b);

                maxScenicScore = Math.max(maxScenicScore, scenicScore);
            }
        }
        return new PuzzleResult(Integer.toString(visibleTrees), Integer.toString(maxScenicScore));
    }

    private ViewResult getLeftView(GridPoint point) {
        for (var x = point.x-1; x >= 0; x--) {
            if (grid[x][point.y] >= grid[point.x][point.y]) {
                return new ViewResult(false, point.x - x);
            }
        }
        return new ViewResult(true, point.x);
    }

    private ViewResult getTopView(GridPoint point) {
        for (var y = point.y-1; y >= 0; y--) {
            if (grid[point.x][y] >= grid[point.x][point.y]) {
                return new ViewResult(false, point.y - y);
            }
        }
        return new ViewResult(true, point.y);
    }

    private ViewResult getRightView(GridPoint point) {
        for (var x = point.x+1; x < width; x++) {
            if (grid[x][point.y] >= grid[point.x][point.y]) {
                return new ViewResult(false, x - point.x);
            }
        }
        return new ViewResult(true, width - point.x - 1);
    }

    private ViewResult getBelowView(GridPoint point) {
        for (var y = point.y+1; y < height; y++) {
            if (grid[point.x][y] >= grid[point.x][point.y]) {
                return new ViewResult(false, y - point.y);
            }
        }
        return new ViewResult(true, height - point.y - 1);
    }
}
