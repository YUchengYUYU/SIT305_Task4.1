package moutai.yuyucheng.task41;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Timer variables
    private CountDownTimer exerciseTimer;
    private CountDownTimer restTimer;
    private long exerciseTimeLeft;
    private long restTimeLeft;


    // UI components
    private ProgressBar progressBar;
    private long exerciseDuration;
    private long restDuration;
    private TextView exerciseTimeTextView;
    private TextView restTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        exerciseTimeTextView = findViewById(R.id.exercise_time_text);
        restTimeTextView = findViewById(R.id.rest_time_text);
        progressBar = findViewById(R.id.progress_bar);
        Button startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get exercise duration from input field
                EditText exerciseDurationEditText = findViewById(R.id.exercise_duration_edit);
                String exerciseDurationString = exerciseDurationEditText.getText().toString();
                exerciseDuration = Long.parseLong(exerciseDurationString);
                // Get rest duration from input field
                EditText restDurationEditText = findViewById(R.id.rest_duration_edit);
                String restDurationString = restDurationEditText.getText().toString();
                restDuration = Long.parseLong(restDurationString);
                // Start exercise timer
                startExerciseTimer(exerciseDuration * 1000);
            }
        });
        // Stop button
        Button stopButton = findViewById(R.id.stop_button);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopExerciseTimer();
                stopRestTimer();
            }
        });
    }
    // Start exercise timer
    private void startExerciseTimer(long duration) {
        progressBar.setMax((int) duration);// set maximum progress bar value
        progressBar.setMax(100); // set maximum
        exerciseTimer = new CountDownTimer(duration, 1000) {
            public void onTick(long millisUntilFinished) {
                exerciseTimeLeft = millisUntilFinished;
                updateExerciseTimerUI();// update exercise timer UI
                progressBar.setProgress((int) millisUntilFinished);
                progressBar.setProgress((int) (100 - millisUntilFinished / (float) duration * 100));
            }

            public void onFinish() {
                exerciseTimeLeft = 0;
                updateExerciseTimerUI();
                startRestTimer(restDuration * 1000);// start rest timer
                // reset progress bar for rest time
                progressBar.setProgress(0);// reset progress bar
            }
        }.start();
    }
    // Start rest timer
    private void startRestTimer(long duration) {
        progressBar.setMax((int) duration);// set maximum progress bar value
        progressBar.setMax(100); // set maximum
        restTimer = new CountDownTimer(duration, 1000) {
            public void onTick(long millisUntilFinished) {
                restTimeLeft = millisUntilFinished;
                //progressBar.setProgress((int) (duration - restTimeLeft));
                updateRestTimerUI();// update Rest timer UI
                progressBar.setProgress((int) millisUntilFinished);
                progressBar.setProgress((int) (100 - millisUntilFinished / (float) duration * 100));
            }

            public void onFinish() {
                restTimeLeft = 0;
                progressBar.setProgress(0);// reset progress bar
                updateRestTimerUI();// update rest timer UI
            }
        }.start();
    }
    //Updates the exercise timer UI with the remaining time in minutes and seconds
    private void updateExerciseTimerUI(){
        long minutes = this.exerciseTimeLeft / 1000 / 60;
        long seconds = (this.exerciseTimeLeft / 1000) % 60;
        exerciseTimeTextView.setText(String.format("%02d:%02d", minutes, seconds));
    }
    //Updates the rest timer UI with the remaining time in minutes and seconds
    private void updateRestTimerUI() {
        long minutes = this.restTimeLeft / 1000 / 60;
        long seconds = (this.restTimeLeft / 1000) % 60;
        restTimeTextView.setText(String.format("%02d:%02d", minutes, seconds));
    }
    //Stops the exercise timer
    private void stopExerciseTimer() {
        if (exerciseTimer != null) {
            exerciseTimer.cancel();
        }
    }
    //Stops the Rest timer
    private void stopRestTimer() {
        if (restTimer != null) {
            restTimer.cancel();
        }
    }
}
