package com.vule.workoutcalendar.exercise.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A DTO used to transfer history data about an exercise. This includes a unique ID, the date when it was done,
 * the weight in kgs, the number of sets and reps.
 *
 * @author vulence
 * @version 1.0
 *
 */
@Setter
@Getter
public class ExerciseHistoryDto {

    /**
     * A unique identifier of the exercise
     */
    private Integer id;

    /**
     * The date when the exercise was performed
     */
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDateTime date;

    /**
     * The weight with which the exercise was performed
     */
    private Integer weight;

    /**
     * The number of sets for which the exercise was done
     */
    private Integer sets;

    /**
     * The number of reps in each set for which the exercise was done
     */
    private Integer reps;

    /**
     * Sets the weight (in kg) with which the exercise was executed to the input value
     *
     * The inputted weight cannot be less than 0
     *
     * @param weight Exercise weight as an integer
     *
     * @throws IllegalArgumentException if the entered weight is less than 0
     */
    public void setWeight(Integer weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be a positive integer");
        }

        this.weight = weight;
    }

    /**
     * Sets the number of sets with which the exercise was done to the input value
     *
     * The inputted number of sets cannot be less than 0
     *
     * @param sets Number of sets as an integer
     *
     * @throws IllegalArgumentException if the entered sets are less than 0
     */
    public void setSets(Integer sets) {
        if (sets < 0) {
            throw new IllegalArgumentException("Sets must be a positive integer");
        }

        this.sets = sets;
    }

    /**
     * Sets the number of reps for each set to the input value
     *
     * The inputted number of reps cannot be less than 0
     *
     * @param reps Number of reps as an integer
     *
     * @throws IllegalArgumentException if the entered reps are less than 0
     */
    public void setReps(Integer reps) {
        if (reps < 0) {
            throw new IllegalArgumentException("Reps must be a positive integer");
        }

        this.reps = reps;
    }

    /**
     * Compares two ExerciseHistoryDtos according to their ids, dates, weights, sets and reps.
     *
     * @param o The second ExerciseHistoryDto to be compared with
     * @return
     * <ul>
     *     <li><b>true</b> - if both objects are initialized, belong to the ExerciseHistoryDto class,
     *     and have the same ids, dates, weights, sets and reps</li>
     *     <li><b>false</b> - if objects don't belong to the ExerciseHistoryDto class, if the inputted object is null
     *     or if they don't have the same ids, dates, weights, sets and reps</li>
     * </ul>
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExerciseHistoryDto that = (ExerciseHistoryDto) o;
        return Objects.equals(id, that.id) && Objects.equals(date, that.date) && Objects.equals(weight, that.weight) && Objects.equals(sets, that.sets) && Objects.equals(reps, that.reps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, weight, sets, reps);
    }
}
