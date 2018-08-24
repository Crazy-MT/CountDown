package com.mt.countdown;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editText;

    private CountDownView countDownView;

    public void stop(View view) {
        countDownView.stop();
    }

    public void pause(View view) {
        countDownView.pause();
    }

    public void start(View view) {
        countDownView.setTotalTime(Integer.valueOf(editText.getText().toString()));
        countDownView.start();
    }

    public void goon(View view) {
        countDownView.goon();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.id_total_time);
        countDownView = findViewById(R.id.id_count_down);
    }
}
