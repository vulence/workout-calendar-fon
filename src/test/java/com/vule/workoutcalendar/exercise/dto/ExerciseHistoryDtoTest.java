package com.vule.workoutcalendar.exercise.dto;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ExerciseHistoryDtoTest {

    private ExerciseHistoryDto exerciseHistoryDto;

    @BeforeEach
    void setUp() {
        exerciseHistoryDto = new ExerciseHistoryDto();
    }

    @AfterEach
    void tearDown() {
        exerciseHistoryDto = null;
    }

    @Test
    void testValidExerciseHistory() {
        exerciseHistoryDto.setWeight(120);
        exerciseHistoryDto.setSets(5);
        exerciseHistoryDto.setReps(10);
        exerciseHistoryDto.setDate(LocalDateTime.now());

        assertEquals(120, exerciseHistoryDto.getWeight());
        assertEquals(5, exerciseHistoryDto.getSets());
        assertEquals(10, exerciseHistoryDto.getReps());
        assertEquals(LocalDateTime.now(), exerciseHistoryDto.getDate());
    }

    @Test
    void testNegativeWeightSetter() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> exerciseHistoryDto.setWeight(-1));
        assertEquals("Weight must be a positive integer", e.getMessage());
    }

    @Test
    void testNegativeSetSetter() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> exerciseHistoryDto.setSets(-1));
        assertEquals("Sets must be a positive integer", e.getMessage());
    }

    @Test
    void testNegativeRepSetter() {
        Exception e = assertThrows(IllegalArgumentException.class, () -> exerciseHistoryDto.setReps(-1));
        assertEquals("Reps must be a positive integer", e.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideArgsForEquals")
    void testEquals(Integer id1, LocalDateTime date1, Integer weight1, Integer sets1, Integer reps1,
                    Integer id2, LocalDateTime date2, Integer weight2, Integer sets2, Integer reps2, boolean eq) {
        exerciseHistoryDto.setId(id1);
        exerciseHistoryDto.setDate(date1);
        exerciseHistoryDto.setWeight(weight1);
        exerciseHistoryDto.setSets(sets1);
        exerciseHistoryDto.setReps(reps1);

        ExerciseHistoryDto dto2 = new ExerciseHistoryDto();

        dto2.setId(id2);
        dto2.setDate(date2);
        dto2.setWeight(weight2);
        dto2.setSets(sets2);
        dto2.setReps(reps2);

        assertEquals(eq, exerciseHistoryDto.equals(dto2));
    }

    private static Stream<Arguments> provideArgsForEquals() {
        return Stream.of(
                Arguments.of(1, LocalDateTime.now(), 120, 5, 10, 1, LocalDateTime.now(), 120, 5, 10, true),
                Arguments.of(1, LocalDateTime.now(), 120, 5, 10, 2, LocalDateTime.now(), 120, 5, 10, false)
        );
    }
}