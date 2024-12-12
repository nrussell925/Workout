package com.example.workoutapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.workoutapp.data.Exercise
import com.example.workoutapp.data.ExerciseDao
import com.example.workoutapp.data.ExercisesRepository
import com.example.workoutapp.data.WorkoutDatabase

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val exerciseDao: ExerciseDao = WorkoutDatabase.getInstance(application).exerciseDao()

    fun getExercisesByWorkoutId(workoutId: Int): LiveData<List<Exercise>> {
        return exerciseDao.getExercisesForWorkout(workoutId)
    }
    private val exercisesRepository: ExercisesRepository
    val allExercises: LiveData<List<Exercise>>

    init {
        val exerciseDao = WorkoutDatabase.getDatabase(application).exerciseDao()
        exercisesRepository = ExercisesRepository(exerciseDao)
        allExercises = exercisesRepository.getAllExercises()
    }
}