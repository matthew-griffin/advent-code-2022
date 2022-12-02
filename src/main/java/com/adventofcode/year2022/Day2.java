package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;

import java.util.Arrays;

public class Day2 implements Puzzle {
    public enum Shape {
        ROCK,
        PAPER,
        SCISSORS
    }

    public enum Result {
        LOSS,
        DRAW,
        WIN
    }

    public interface ShapeStrategy
    {
        Shape getShape(Shape opponentShape, String inputCode);
    }

    @Override
    public PuzzleResult solve(String inputText) {
        int partOne = getFullScore(inputText, (shape, code) -> parseShape(code));
        int partTwo = getFullScore(inputText, (shape, code) -> getShapeForResult(shape, parseResult(code)));
        return new PuzzleResult(Integer.toString(partOne), Integer.toString(partTwo));
    }

    private int getFullScore(String inputText, ShapeStrategy strategy) {
        return Arrays.stream(inputText.split("\n"))
                .mapToInt(match -> {
                    var shapes = match.split(" ");
                    var opponentShape = parseShape(shapes[0]);
                    var playerShape = strategy.getShape(opponentShape, shapes[1]);
                    return getShapeScore(playerShape) +
                        getRoundScore(opponentShape, playerShape);
                })
                .sum();
    }

    private static Shape parseShape(String shape) {
        return switch (shape) {
            case "A", "X" -> Shape.ROCK;
            case "B", "Y" -> Shape.PAPER;
            case "C", "Z" -> Shape.SCISSORS;
            default -> throw new RuntimeException("Unsupported shape code");
        };
    }

    private static Result parseResult(String result) {
        return switch (result) {
            case "X" -> Result.LOSS;
            case "Y" -> Result.DRAW;
            case "Z" -> Result.WIN;
            default -> throw new RuntimeException("Unsupported result code");
        };
    }

    private static Shape getShapeForResult(Shape opponentShape, Result result) {
        return switch (result) {
            case DRAW -> opponentShape;
            case LOSS -> getLosingShape(opponentShape);
            case WIN -> getWinningShape(opponentShape);
        };
    }

    private static Shape getLosingShape(Shape opponentShape) {
        return switch (opponentShape) {
            case ROCK -> Shape.SCISSORS;
            case PAPER -> Shape.ROCK;
            case SCISSORS -> Shape.PAPER;
        };
    }

    private static Shape getWinningShape(Shape opponentShape) {
        return switch (opponentShape) {
            case ROCK -> Shape.PAPER;
            case PAPER -> Shape.SCISSORS;
            case SCISSORS -> Shape.ROCK;
        };
    }

    private static int getShapeScore(Shape shape) {
        return switch (shape) {
            case ROCK -> 1;
            case PAPER -> 2;
            case SCISSORS -> 3;
        };
    }

    private int getRoundScore(Shape opponentShape, Shape playerShape) {
        if (opponentShape == playerShape) {
            return 3;
        }
        return switch (opponentShape) {
            case ROCK -> playerShape == Shape.PAPER ? 6 : 0;
            case PAPER -> playerShape == Shape.SCISSORS ? 6 : 0;
            case SCISSORS -> playerShape == Shape.ROCK ? 6 : 0;
        };
    }
}
