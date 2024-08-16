package com.example.fiter;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BMI_Activity extends AppCompatActivity {
    EditText hei, wt;
    Button but;
    TextView res, tip;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_bmi);

        hei = findViewById(R.id.Height);
        wt = findViewById(R.id.Weight);
        but = findViewById(R.id.button);
        res = findViewById(R.id.Result);
        tip = findViewById(R.id.Tip);
        pb = findViewById(R.id.progress_bar);
        ViewGroup.LayoutParams params = pb.getLayoutParams();
        params.height = 500;
        pb.setLayoutParams(params);
        pb.setMax(100);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hei.getText().toString().isEmpty() || wt.getText().toString().isEmpty()) {
                    Toast.makeText(BMI_Activity.this, "Values Can't be Empty", Toast.LENGTH_SHORT).show();
                } else {
                    double he = Double.parseDouble(hei.getText().toString());
                    double w = Double.parseDouble(wt.getText().toString());
                    double bmi = w / (he * he);
                    setTip(bmi);
                    updateProgressBar(bmi);
                    String formattedBmi = String.format("%.2f", bmi);
                    res.setText("Your BMI is " + formattedBmi);
                }
            }
        });
    }

    private void setTip(double bmi) {
        if (bmi < 18.0) {
            tip.setText("You are Underweight");
        } else if (bmi >= 18.0 && bmi <= 24.0) {
            tip.setText("You are Healthy");
        } else if (bmi >= 25.0 && bmi <= 29.0) {
            tip.setText("You are Overweight");
        }
        else{
            tip.setText("You are Obese");
        }
    }
    private void updateProgressBar(double bmi) {
        pb.setVisibility(View.VISIBLE);
        int progress = mapBmiToProgress(bmi);
        pb.setProgress(progress);
    }
    private int mapBmiToProgress(double bmi) {
        if (bmi < 18.5) {
            return (int) ((bmi / 18.5) * 100); // Underweight
        }
        else if (bmi < 25) {
            return (int) (((bmi - 18.5) / (25 - 18.5)) * 100); // Normal (Corrected)
        } else if (bmi < 30) {
            return (int) (((bmi - 25) / (30 - 25)) * 100 + 50); // Overweight
        } else {
            return (int) (((bmi - 30) / (40 - 30)) * 100 + 75); // Obese (adjust 40 if needed)
        }
    }
}