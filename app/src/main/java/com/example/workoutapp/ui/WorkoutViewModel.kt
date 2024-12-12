package com.example.workoutapp.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.workoutapp.data.Exercise
import com.example.workoutapp.data.Workout
import com.example.workoutapp.data.WorkoutsRepository
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {

    private val workoutRepository = WorkoutsRepository(application)
    val allWorkouts: LiveData<List<Workout>> = workoutRepository.getAllWorkouts()

    fun insertWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.insertWorkout(workout)
        }
    }

    fun deleteWorkout(workout: Workout) {
        viewModelScope.launch {
            workoutRepository.deleteWorkout(workout)
        }
    }

    fun insertExercise(exercise: Exercise) {
        viewModelScope.launch {
            workoutRepository.insertExercise(exercise)
        }
    }

    fun getExercisesForWorkout(workoutId: Int): LiveData<List<Exercise>> {
        return workoutRepository.getExercisesForWorkout(workoutId)
    }
}