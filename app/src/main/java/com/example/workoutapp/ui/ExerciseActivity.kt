package com.example.workoutapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.R
import com.example.workoutapp.data.Exercise
import com.example.workoutapp.data.ExerciseAdapter
import com.example.workoutapp.data.WorkoutDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExerciseActivity : AppCompatActivity() {

    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var exerciseAdapter: ExerciseAdapter
    private lateinit var db: WorkoutDatabase
    private var workoutId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        db = WorkoutDatabase.getInstance(this)
        workoutId = intent.getIntExtra("WORKOUT_ID", -1)

        // Initialize ViewModel
        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)

        val workoutNameTextView: TextView = findViewById(R.id.workoutNameTextView)

        // Fetch the workout name from the database
        CoroutineScope(Dispatchers.IO).launch {
            val workout = db.workoutDao().getWorkoutById(workoutId) // Assuming you have this method in the DAO
            withContext(Dispatchers.Main) {
                workoutNameTextView.text = workout?.name ?: "Unknown Workout"
            }
        }

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewExercisesList)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addExerciseButton: Button = findViewById(R.id.addExerciseButton)
        addExerciseButton.setOnClickListener { openAddExerciseScreen() }

        exerciseViewModel.allExercises.observe(this, Observer { exercises ->
            // Filter exercises based on the workoutId
            val filteredExercises = exercises.filter { it.workoutId == workoutId }
            exerciseAdapter = ExerciseAdapter(filteredExercises)
            recyclerView.adapter = exerciseAdapter
        })

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun openAddExerciseScreen() {
        val intent = Intent(this, AddExerciseActivity::class.java)
        intent.putExtra("WORKOUT_ID", workoutId)
        startActivity(intent)
    }

    private fun deleteExercise(exercise: Exercise) {
        CoroutineScope(Dispatchers.IO).launch {
            db.exerciseDao().deleteExercise(exercise)
        }
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
