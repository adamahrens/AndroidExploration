package com.appsbyahrens.buttonfun;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String LOGTAG = MainActivity.class.getSimpleName();
    private Button first;
    private Button second;
    private Button fourth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOGTAG, "onCreate called");

        // Find the buttons
        first = findViewById(R.id.first_button);
        second = findViewById(R.id.second_button);
        fourth = findViewById(R.id.fourth_button);

        // First Method
        second.setOnClickListener(this);

        // Second Method
        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(LOGTAG, "First was clicked but different way!!!!!");

                // Call this as opposed to MainActivity.this
//                getApplicationContext()
                Toast toast = Toast.makeText(MainActivity.this, R.string.toast_main_activity_text, Toast.LENGTH_LONG);

                // Positioning Toast

                toast.setGravity(Gravity.TOP, 0, 0);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
            }
        });

        fourth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = getLayoutInflater();
                View customToast = inflater.inflate(R.layout.custom_toast, (ViewGroup)findViewById(R.id.custom_toast_root_layout));
                Toast toast = new Toast(MainActivity.this);
                toast.setGravity(Gravity.TOP, 0, 0);
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setView(customToast);
                toast.show();
                Log.d(LOGTAG, "Showing custom Toast");
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        int orientation = newConfig.orientation;

        switch (orientation) {
            case Configuration.ORIENTATION_PORTRAIT : {
                Log.d(LOGTAG, "Portrait!");
                Toast.makeText(getApplicationContext(), "In Portrait", Toast.LENGTH_LONG).show();
                break;
            }
            case Configuration.ORIENTATION_LANDSCAPE : {
                Log.d(LOGTAG, "Landscape!");
                Toast.makeText(getApplicationContext(), "In Landscape", Toast.LENGTH_LONG).show();
                break;
            }
            default : {
                Log.d(LOGTAG, "No clue what orientation");
            }
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(LOGTAG, "onClick called");
        int viewID = v.getId();
        if (viewID == second.getId()) {
            Log.d(LOGTAG, "Second Button Clicked");
            Intent intent = new Intent("android.intent.action.BOOKMARK");
            startActivity(intent);
        } else {
            Log.e(LOGTAG, "Unknown View Clicked");
        }
    }

    // Third method. Hooks up android:onClick in XML
    public void performThirdButtonAction(View view) {
        Intent intent = new Intent(MainActivity.this, BookmarkActivity.class);
        startActivity(intent);
        Log.d(LOGTAG, "Third Button Clicked");
    }
}
