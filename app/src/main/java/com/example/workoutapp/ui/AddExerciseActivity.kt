package com.example.workoutapp.ui

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.workoutapp.R
import com.example.workoutapp.data.Exercise
import com.example.workoutapp.data.WorkoutDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddExerciseActivity : AppCompatActivity() {

    private lateinit var db: WorkoutDatabase
    private var workoutId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)

        db = WorkoutDatabase.getInstance(this)
        workoutId = intent.getIntExtra("WORKOUT_ID", -1)

        val exerciseNameEditText: EditText = findViewById(R.id.editTextExerciseName)
        val setsEditText: EditText = findViewById(R.id.editTextExerciseSets)
        val repsEditText: EditText = findViewById(R.id.editTextExerciseReps)
        val saveExerciseButton: Button = findViewById(R.id.buttonAddExercise)

        saveExerciseButton.setOnClickListener {
            val exerciseName = exerciseNameEditText.text.toString().trim()
            val sets = setsEditText.text.toString().toIntOrNull() ?: 0
            val reps = repsEditText.text.toString().toIntOrNull() ?: 0

            if (exerciseName.isNotEmpty() && sets > 0 && reps > 0) {
                CoroutineScope(Dispatchers.IO).launch {
                    val exercise = Exercise(workoutId = workoutId, name = exerciseName, sets = sets, reps = reps)
                    db.exerciseDao().insertExercise(exercise)
                    Log.d("AddExercise", "Inserting exercise: workoutId = $workoutId, name = $exerciseName, sets = $sets, reps = $reps")
                    finish()
                }
            }
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            // This will navigate back to the previous screen
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}