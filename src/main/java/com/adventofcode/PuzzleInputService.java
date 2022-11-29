package com.adventofcode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class PuzzleInputService {
    private static final Logger log = LoggerFactory.getLogger(PuzzleInputService.class);
    private final HttpClient client = HttpClient.newBuilder().build();

    private final Path inputStorage;
    private final Path cookieFile;

    PuzzleInputService() {
        this.inputStorage = Path.of(".input");
        this.cookieFile = Path.of("cookie.txt");
    }

    @Cacheable("input")
    public String getPuzzleInput(PuzzleDate date) throws IOException, InterruptedException {
        try {
            return getInputFromFile(date);
        } catch (IOException e) {
            log.warn("Could not find Puzzle Input on disk");
        }

        String input;
        try {
            input = getInputFromWeb(date);
        } catch (IOException | InterruptedException e) {
            log.warn("Could not retrieve input from web: {}", e.toString());
            throw e;
        }

        storeInputInFile(date, input);
        return input;
    }

    private String getInputFromFile(PuzzleDate date) throws IOException {
        Path filePath = getInputFilePath(date);
        log.info("Getting puzzle input from disk: '{}'", filePath);
        return Files.readString(filePath);
    }

    private void storeInputInFile(PuzzleDate date, String input) throws IOException {
        var path = getInputFilePath(date);
        Files.createDirectories(path.getParent());
        Files.writeString(path, input);
    }

    private Path getInputFilePath(PuzzleDate date) {
        return inputStorage.resolve(date.year().toString()).resolve(date.day().toString() + ".txt");
    }

    private String getInputFromWeb(PuzzleDate date) throws IOException, InterruptedException {
        var url = getPuzzleInputUrl(date);
        log.info("Getting puzzle input from web: '{}'", url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("cookie", getCookie())
                .build();
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }

    private String getPuzzleInputUrl(PuzzleDate date) {
        return String.format("https://adventofcode.com/%d/day/%d/input", date.year(), date.day());
    }

    private String getCookie() throws IOException {
        return Files.readString(cookieFile);
    }
}
