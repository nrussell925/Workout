package com.example.workoutapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ExerciseDao {

    @Insert
    suspend fun insertExercise(exercise: Exercise)

    @Delete
    suspend fun deleteExercise(exercise: Exercise)

    @Query("SELECT * FROM exercises WHERE workout_id = :workoutId")
    fun getExercisesForWorkout(workoutId: Int): LiveData<List<Exercise>>

    @Query("DELETE FROM exercises WHERE workout_id = :workoutId")
    suspend fun deleteExercisesForWorkout(workoutId: Int)

    @Query("SELECT * FROM exercises")
    fun getAllExercises(): LiveData<List<Exercise>>
}