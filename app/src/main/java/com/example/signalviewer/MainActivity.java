package com.example.signalviewer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = "MAIN_ACTIVITY";

    public static final String STR_KEY_AMP = "AMPLITUDE_VALUE";
    public static final String STR_KEY_FREQ = "FREQUENCY_VALUE";
    public static final String STR_KEY_PHASE = "PHASE_VALUE";
    public static final String STR_KEY_DUR = "DURATION_VALUE";

    private EditText Amplitude;
    private EditText Frequency;
    private EditText Phase;
    private EditText Duration;

    private Button button_goToGraphs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Amplitude = findViewById(R.id.main_val_Amplitude);
        Frequency = findViewById(R.id.main_val_Frequency);
        Phase = findViewById(R.id.main_val_Phase);
        Duration = findViewById(R.id.main_val_Duration);

        button_goToGraphs = findViewById(R.id.btn_goToGraphs);
        button_goToGraphs.setOnClickListener(view -> {
            try {
                Intent intent = new Intent(this, GraphActivity.class);

                Double Amp = Double.valueOf(Amplitude.getText().toString());
                Log.d(LOG_TAG, "Amp: " + Amplitude.getText().toString());
                Double Freq = Double.valueOf(Frequency.getText().toString());
                Log.d(LOG_TAG, "Freq: " + Frequency.getText().toString());
                Double Ph = Double.valueOf(Phase.getText().toString());
                Log.d(LOG_TAG, "Ph: " + Phase.getText().toString());
                Double Dur = Double.valueOf(Duration.getText().toString());
                Log.d(LOG_TAG, "Ph: " + Duration.getText().toString());

                intent.putExtra(STR_KEY_AMP, Amp);
                intent.putExtra(STR_KEY_FREQ, Freq);
                intent.putExtra(STR_KEY_PHASE, Ph);
                intent.putExtra(STR_KEY_PHASE, Dur);

                startActivity(intent);
                Log.d(LOG_TAG, "New Activity");
            }
            catch (Exception e) {
                Toast.makeText(this, "Неккоректный ввод данных", Toast.LENGTH_LONG).show();
            }
            ;
        });
    }


    @Override
    protected void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d(LOG_TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
    }
}