package com.naziyazh.interactivestory.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.naziyazh.interactivestory.R;
import com.naziyazh.interactivestory.ui.StoryActivity;

public class MainActivity extends AppCompatActivity {
    Button startButton ;
    TextView nameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startButton = findViewById(R.id.startButton);
        nameEditText = findViewById(R.id.nameEditText);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nameEditText.getText().toString();
                if (name.isEmpty()) {
                    nameEditText.setError("Please type your name");
                }else{
                    startStory(name);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        nameEditText.setText("");
    }

    private void startStory(String name) {
        Intent intent = new Intent(this, StoryActivity.class);
        Resources resources = getResources();
        String key = resources.getString(R.string.user_name);
        intent.putExtra(key, name);
        startActivity(intent);

    }
}