package com.arsoft.workmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.work.Constraints;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

/**
 * MainActivity of the WorkManagerApp.
 *
 * This activity demonstrates the basic usage of WorkManager in Android
 * with constraints. It creates a one-time background task (WorkRequest)
 * that only runs when the device is charging.
 *
 * Features:
 * - Defines execution constraints using WorkManager's Constraints API.
 * - Uses a Button to trigger the background task.
 * - Enqueues a OneTimeWorkRequest to perform work defined in MyWorker.class.
 *
 * Usage:
 * - Press the button to enqueue the work.
 * - The task will run only if the defined constraints are met (e.g., charging).
 *
 * Dependencies:
 * - androidx.work:work-runtime
 *
 * Author: Rayhan (or your name)
 * Date: [Insert Date]
 */

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);

        // Constraints
        Constraints constraints = new Constraints
                .Builder()
                .setRequiresCharging(true)
                .build();


        // Making use of Worker:
        WorkRequest w = new OneTimeWorkRequest
                .Builder(MyWorker.class)
                .setConstraints(constraints)
                .build();

        // Enqueue the request with WorkManager
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkManager.getInstance(
                        getApplicationContext()
                ).enqueue(w);
            }
        });


    }
}