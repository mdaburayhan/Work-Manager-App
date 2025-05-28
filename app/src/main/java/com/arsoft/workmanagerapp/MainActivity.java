package com.arsoft.workmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

/**
 * MainActivity of the WorkManagerApp.
 *
 * This activity demonstrates how to use WorkManager in Android
 * with execution constraints, input/output data, and live status observation.
 *
 * Features:
 * - Sets constraints to execute work only when the device is charging.
 * - Passes input data (e.g., "max_limit") to the worker.
 * - Observes the state of the WorkRequest using LiveData and displays
 *   real-time status updates via Toast.
 * - Retrieves output data from the worker after it finishes execution.
 *
 * Usage:
 * - Tap the button to enqueue a one-time background task.
 * - The task will run only when the device meets the defined constraints.
 * - Progress and result will be shown via Toast messages.
 *
 * Dependencies:
 * - androidx.work:work-runtime
 *
 * Author: Rayhan
 * Date: May 28, 2025
 */

public class MainActivity extends AppCompatActivity {

    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);

        // Data
        Data data = new Data.Builder()
                .putInt("max_limit", 8888).build();


        // Constraints
        Constraints constraints = new Constraints
                .Builder()
                .setRequiresCharging(true)
                .build();


        // Making use of Worker:
        WorkRequest w = new OneTimeWorkRequest
                .Builder(MyWorker.class)
                .setConstraints(constraints)
                .setInputData(data)
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

        // Let's Display the Status of Our Worker
        WorkManager.getInstance(getApplicationContext())
                .getWorkInfoByIdLiveData(w.getId())
                .observe(this, new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                        if (workInfo != null){
                            Toast.makeText(getApplicationContext(),
                                    "Status: "+workInfo.getState().name(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        if(workInfo.getState().isFinished()){
                            Data data1 = workInfo.getOutputData();
                            Toast.makeText(MainActivity.this,
                                    ""+data1.getString("msg"),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }
}