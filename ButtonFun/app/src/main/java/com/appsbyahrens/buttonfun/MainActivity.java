package com.appsbyahrens.buttonfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOGTAG = MainActivity.class.getSimpleName();
    private Button first;
    private Button second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOGTAG, "onCreate called");

        // Find the buttons
        first = findViewById(R.id.first_button);
        second = findViewById(R.id.second_button);

        // First Method
        second.setOnClickListener(this);

        // Second Method
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOGTAG, "First was clicked but different way!!!!!");
            }
        });
    }

    @Override
    public void onClick(View v) {
        Log.d(LOGTAG, "onClick called");
        int viewID = v.getId();
        if (viewID == second.getId()) {
            Log.d(LOGTAG, "Second Button Clicked");
        } else {
            Log.e(LOGTAG, "Unknown View Clicked");
        }
    }

    // Third method. Hooks up android:onClick in XML
    public void performThirdButtonAction(View view) {
        Log.d(LOGTAG, "Third Button Clicked");
    }
}
