package com.adventofcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PuzzleController.class)
public class PuzzleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PuzzleService puzzleService;

    @MockBean
    private PuzzleInputService inputService;

    public String toJson(Object object) throws JsonProcessingException {
        var writer = new ObjectMapper().writer();
        return writer.writeValueAsString(object);
    }

    @Test
    public void itShouldReturnCollectionOfPuzzleDates() throws Exception {

        Collection<PuzzleDate> puzzles = List.of(new PuzzleDate(2022, 1));
        when(puzzleService.getPuzzles()).thenReturn(puzzles);
        mockMvc.perform(get("/api/puzzles")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(puzzles)));

    }

    @Test
    public void itShouldSolvePuzzleUsingInput() throws Exception {
        var mockPuzzle = mock(Puzzle.class);
        when(puzzleService.getPuzzle(any())).thenReturn(mockPuzzle);
        when(inputService.getPuzzleInput(any())).thenReturn("Test Input");
        var result = new PuzzleResult("testPartOne", "testPartTwo");
        when(mockPuzzle.solve(any())).thenReturn(result);
        mockMvc.perform(get("/api/puzzles/2022/1")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(result)));
        var date = new PuzzleDate(2022, 1);
        verify(puzzleService).getPuzzle(date);
        verify(inputService).getPuzzleInput(date);
    }

    @Test
    public void itShouldReturnNotFoundForMissingPuzzles() throws Exception {
        when(puzzleService.getPuzzle(any())).thenReturn(null);
        mockMvc.perform(get("/api/puzzles/2022/1")).andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void itShouldReturnFailedDependencyForMissingInput() throws Exception {
        var mockPuzzle = mock(Puzzle.class);
        when(puzzleService.getPuzzle(any())).thenReturn(mockPuzzle);
        when(inputService.getPuzzleInput(any())).thenThrow(new IOException());
        mockMvc.perform(get("/api/puzzles/2022/1")).andDo(print())
                .andExpect(status().isFailedDependency());
    }
}
