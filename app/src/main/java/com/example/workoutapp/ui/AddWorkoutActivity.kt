package com.example.workoutapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutapp.R
import com.example.workoutapp.data.Workout
import com.example.workoutapp.data.WorkoutDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddWorkoutActivity : AppCompatActivity() {

    private lateinit var db: WorkoutDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_workout)

        db = WorkoutDatabase.getInstance(this)
        val workoutNameEditText: EditText = findViewById(R.id.workoutNameEditText)
        val saveWorkoutButton: Button = findViewById(R.id.saveWorkoutButton)

        saveWorkoutButton.setOnClickListener {
            val workoutName = workoutNameEditText.text.toString().trim()
            if (workoutName.isNotEmpty()) {
                CoroutineScope(Dispatchers.IO).launch {
                    db.workoutDao().insertWorkout(Workout(name = workoutName))
                    finish()
                }
            }
        }
    }
}