package com.example.fiter;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Steps_Activity extends AppCompatActivity implements SensorEventListener {
    Sensor sensor = null;
    SensorManager sensorManager = null;
    TextView steps;
    private int stepCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_steps);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        steps = findViewById(R.id.Steps);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sensor == null) {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show();
        } else {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            stepCount++;

            runOnUiThread(() -> steps.setText(stepCount));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        if (i == SensorManager.SENSOR_STATUS_UNRELIABLE) {
            Toast.makeText(this, "Sensor accuracy is unreliable", Toast.LENGTH_SHORT).show();
        }
    }
}