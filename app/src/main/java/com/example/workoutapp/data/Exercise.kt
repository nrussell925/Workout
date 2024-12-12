package com.example.workoutapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "exercises",
    foreignKeys = [ForeignKey(entity = Workout::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("workout_id"),
        onDelete = ForeignKey.CASCADE)])
data class Exercise(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "workout_id") val workoutId: Int,
    val name: String,
    val sets: Int,
    val reps: Int
)