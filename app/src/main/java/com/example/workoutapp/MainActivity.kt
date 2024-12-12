package com.example.workoutapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.workoutapp.data.Workout
import com.example.workoutapp.data.WorkoutAdapter
import com.example.workoutapp.data.WorkoutsRepository
import com.example.workoutapp.ui.AddWorkoutActivity
import com.example.workoutapp.ui.ExerciseActivity


class MainActivity : AppCompatActivity() {

    private lateinit var workoutAdapter: WorkoutAdapter
    private lateinit var workoutRepository: WorkoutsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workoutRepository = WorkoutsRepository(this) // Pass context to the repository

        val recyclerView: RecyclerView = findViewById(R.id.recyclerViewWorkouts)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val addWorkoutButton: Button = findViewById(R.id.addWorkoutButton)
        addWorkoutButton.setOnClickListener { openAddWorkoutScreen() }

        loadWorkouts()
    }

    private fun loadWorkouts() {
        workoutRepository.getAllWorkouts().observe(this, Observer { workouts ->
            workoutAdapter = WorkoutAdapter(workouts, this@MainActivity::openWorkoutDetails)
            val recyclerView: RecyclerView = findViewById(R.id.recyclerViewWorkouts)
            recyclerView.adapter = workoutAdapter
        })
    }

    private fun openWorkoutDetails(workout: Workout) {
        val intent = Intent(this, ExerciseActivity::class.java)
        intent.putExtra("WORKOUT_ID", workout.id)
        startActivity(intent)
    }

    private fun openAddWorkoutScreen() {
        val intent = Intent(this, AddWorkoutActivity::class.java)
        startActivity(intent)
    }
}