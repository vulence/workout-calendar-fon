package com.vule.workoutcalendar.workout;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vule.workoutcalendar.exercisedone.ExerciseDone;
import com.vule.workoutcalendar.exercisedone.dto.ExerciseDoneDto;
import com.vule.workoutcalendar.jwt.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/workouts")
@CrossOrigin
public class WorkoutController {

    private final WorkoutService workoutService;
    private final JwtService jwtService;

    public WorkoutController(WorkoutService workoutService, JwtService jwtService) {
        this.workoutService = workoutService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAll(@CookieValue(name = "jwt", required = false) String jwtToken) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        return ResponseEntity.ok(workoutService.findAll(jwtService.parseUsernameFromJwt(jwtToken)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        return ResponseEntity.ok(workoutService.findById(jwtService.parseUsernameFromJwt(jwtToken), id));
    }

    @GetMapping("/{id}/exercises")
    public ResponseEntity<?> getWorkoutExercises(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        return ResponseEntity.ok(workoutService.getWorkoutExercises(jwtService.parseUsernameFromJwt(jwtToken), id));
    }

    @PostMapping("/new")
    public ResponseEntity<?> create(@CookieValue(name = "jwt", required = false) String jwtToken, @Valid @RequestBody Workout workout) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        workoutService.create(jwtService.parseUsernameFromJwt(jwtToken), workout);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Workout created successfully."));
    }

    @PutMapping("/{id}/setNotes")
    public ResponseEntity<?> updateNotes(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody String notes) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        workoutService.updateNotes(jwtService.parseUsernameFromJwt(jwtToken), id, notes);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setDuration")
    public ResponseEntity<?> updateDuration(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody Integer duration) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        workoutService.updateDuration(jwtService.parseUsernameFromJwt(jwtToken), id, duration);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setRating")
    public ResponseEntity<?> updateRating(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody Integer rating) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized - Bearer token missing or invalid.");

        workoutService.updateRating(jwtService.parseUsernameFromJwt(jwtToken), id, rating);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/setCompleted")
    public ResponseEntity<?> updateCompleted(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody ObjectNode objectNode) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - Bearer token missing or invalid."));

        workoutService.updateCompleted(jwtService.parseUsernameFromJwt(jwtToken), id, objectNode.get("exerciseDoneId").asInt(), objectNode.get("completed").asBoolean());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        workoutService.delete(jwtService.parseUsernameFromJwt(jwtToken), id);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/exercises/new")
    public ResponseEntity<?> addWorkoutExercise(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @Valid @RequestBody ExerciseDoneDto exerciseDoneDto) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        workoutService.addWorkoutExercise(jwtService.parseUsernameFromJwt(jwtToken), id, exerciseDoneDto);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/exercises/update")
    public ResponseEntity<?> updateWorkoutExercise(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @Valid @RequestBody ExerciseDone exerciseDone) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        workoutService.updateWorkoutExercise(jwtService.parseUsernameFromJwt(jwtToken), id, exerciseDone);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/exercises")
    public ResponseEntity<?> deleteWorkoutExercise(@CookieValue(name = "jwt", required = false) String jwtToken, @PathVariable Integer id, @RequestBody Integer exerciseDoneId) {
        if (jwtToken == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized - HttpOnly cookie missing or invalid."));

        workoutService.deleteWorkoutExercise(jwtService.parseUsernameFromJwt(jwtToken), id, exerciseDoneId);

        return ResponseEntity.noContent().build();    }
}