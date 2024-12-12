package com.example.workoutapp.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

/**
 * Repository that provides insert, update, delete, and retrieve of [Exercise] from a given data source.
 */
class ExercisesRepository(private val exerciseDao: ExerciseDao) {

    // Get all exercises from the database
    fun getAllExercises(): LiveData<List<Exercise>> {
        return exerciseDao.getAllExercises()
    }

    // Insert a new exercise into the database
    suspend fun insertExercise(exercise: Exercise) {
        exerciseDao.insertExercise(exercise)
    }
}