package com.adventofcode.year2022;

import com.adventofcode.Puzzle;
import com.adventofcode.PuzzleResult;

import java.util.ArrayList;
import java.util.List;

public class Day7 implements Puzzle {
    static final int MAX_SIZE = 100000;
    static final int TOTAL_SIZE = 70000000;
    static final int REQUIRED_SIZE = 30000000;

    interface FileEntry
    {
        int size();
    }

    record File(int size) implements FileEntry {
    }

    static class Directory implements FileEntry {
        private final Directory parent;
        private final List<FileEntry> children = new ArrayList<>();

        Directory(Directory parent) {
            this.parent = parent;
        }

        void addChild(FileEntry fileEntry) {
            children.add(fileEntry);
        }

        @Override
        public int size() {
            return children.stream()
                    .mapToInt(FileEntry::size)
                    .sum();
        }

        public Directory getParent() {
            return parent;
        }
    }

    private static Directory createDirectory(Directory parent)
    {
        var directory = new Directory(parent);
        if (parent != null) {
            parent.addChild(directory);
        }
        return directory;
    }

    @Override
    public PuzzleResult solve(String inputText) {
        var directories = new ArrayList<Directory>();
        var root = createDirectory(null);
        directories.add(root);
        Directory currentDirectory = root;
        var lines = inputText.split("\n");

        for (var line : lines) {
            if (line.startsWith("$ ls") || line.startsWith("dir")) {
                continue;
            }
            if (line.startsWith("$ cd")) {
                if (line.endsWith("/")) {
                    currentDirectory = root;
                } else if (line.endsWith("..")) {
                    currentDirectory = currentDirectory.getParent();
                } else {
                    var directory = createDirectory(currentDirectory);
                    directories.add(directory);
                    currentDirectory = directory;
                }
            } else {
                var parts = line.split(" ");
                currentDirectory.addChild(new File(Integer.parseInt(parts[0])));
            }
        }

        var partOne = directories.stream()
                .mapToInt(FileEntry::size)
                .filter(size -> size <= MAX_SIZE)
                .sum();
        var requiredSize = REQUIRED_SIZE - (TOTAL_SIZE - root.size());
        var partTwo = directories.stream()
                .mapToInt(FileEntry::size)
                .filter(size -> size >= requiredSize)
                .sorted()
                .findFirst().orElse(0);
        return new PuzzleResult(Integer.toString(partOne), Integer.toString(partTwo));
    }
}
