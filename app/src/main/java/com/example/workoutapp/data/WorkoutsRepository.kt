package com.example.workoutapp.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WorkoutsRepository(private val context: Context) {

    private val workoutDao: WorkoutDao
    private val exerciseDao: ExerciseDao
    private val allWorkouts: LiveData<List<Workout>>

    init {
        val workoutDatabase = WorkoutDatabase.getInstance(context)  // Get the database instance
        workoutDao = workoutDatabase.workoutDao()
        exerciseDao = workoutDatabase.exerciseDao()
        allWorkouts = workoutDao.getAllWorkouts()
    }

    // Get all workouts
    fun getAllWorkouts(): LiveData<List<Workout>> {
        return allWorkouts
    }

    // Get all exercises for a specific workout
    fun getExercisesForWorkout(workoutId: Int): LiveData<List<Exercise>> {
        return exerciseDao.getExercisesForWorkout(workoutId)
    }

    // Insert a new workout
    suspend fun insertWorkout(workout: Workout) {
        withContext(Dispatchers.IO) {
            workoutDao.insertWorkout(workout)
        }
    }

    // Insert a new exercise
    suspend fun insertExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.insertExercise(exercise)
        }
    }

    // Delete a workout
    suspend fun deleteWorkout(workout: Workout) {
        withContext(Dispatchers.IO) {
            workoutDao.deleteWorkout(workout)
        }
    }

    // Delete an exercise
    suspend fun deleteExercise(exercise: Exercise) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExercise(exercise)
        }
    }

    // Delete all exercises for a specific workout
    suspend fun deleteExercisesForWorkout(workoutId: Int) {
        withContext(Dispatchers.IO) {
            exerciseDao.deleteExercisesForWorkout(workoutId)
        }
    }
}
