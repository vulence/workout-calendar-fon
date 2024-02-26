package com.vule.workoutcalendar.workoutexercise;

import com.vule.workoutcalendar.exercise.ExerciseRepository;
import com.vule.workoutcalendar.workout.WorkoutRepository;
import com.vule.workoutcalendar.workoutexercise.dto.WorkoutExerciseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WorkoutExerciseService {
    private final WorkoutRepository workouts;

    private final ExerciseRepository exercises;

    private final WorkoutExerciseRepository workoutExercises;

    public WorkoutExerciseService(WorkoutRepository workouts, ExerciseRepository exercises, WorkoutExerciseRepository workoutExercises) {
        this.workouts = workouts;
        this.exercises = exercises;
        this.workoutExercises = workoutExercises;
    }

    public List<WorkoutExercise> getWorkoutExercises(Integer userId, Integer id) {
        if (workouts.findByIdAndUserId(id, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

        List<WorkoutExercise> allExercises = workoutExercises.findAllByWorkoutId(id).orElse(null);

        if (allExercises == null) return null;

        for (WorkoutExercise we : allExercises) {
            we.setExerciseName(exercises.findExerciseName(we.getExerciseId()));
        }

        return allExercises;
    }

    public void addWorkoutExercise(Integer userId, Integer workoutId, WorkoutExerciseDto workoutExerciseDto) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

        WorkoutExercise we = WorkoutExercise.from(workoutExerciseDto);
        we.setWorkoutId(workoutId);
        we.setExerciseId(workoutExerciseDto.getExerciseId());

        workoutExercises.save(we);
    }

    public void updateWorkoutExercise(Integer userId, Integer workoutId, WorkoutExercise workoutExercise) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

        WorkoutExercise existingWE = workoutExercises.findById(workoutExercise.getId()).get();
        existingWE.setExerciseName(workoutExercise.getExerciseName());
        existingWE.setWeight(workoutExercise.getWeight());
        existingWE.setSets(workoutExercise.getSets());
        existingWE.setReps(workoutExercise.getReps());
        existingWE.setCompleted(workoutExercise.isCompleted());

        workoutExercises.save(existingWE);
    }

    public void deleteWorkoutExercise(Integer userId, Integer workoutId, Integer WorkoutExerciseId) {
        if (workouts.findByIdAndUserId(workoutId, userId).orElse(null) == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WorkoutID for this UserID doesn't exist.");

        WorkoutExercise we = workoutExercises.findById(WorkoutExerciseId).get();
        workoutExercises.delete(we);
    }
}
