package com.naziyazh.interactivestory.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.naziyazh.interactivestory.R;
import com.naziyazh.interactivestory.model.Page;
import com.naziyazh.interactivestory.model.Story;

import java.util.Stack;

public class StoryActivity extends AppCompatActivity {
    private String name;
    private Story story;
    private TextView storyTextView;
    private ImageView storyImageView;
    private Button choice1Button;
    private Button choice2Button;
    private Stack<Integer> pageStack = new Stack<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story);

        storyTextView = findViewById(R.id.storyTextView);
        storyImageView = findViewById(R.id.storyImageView);
        choice1Button = findViewById(R.id.choice1);
        choice2Button = findViewById(R.id.choice2);

        Intent intent = getIntent();
        name = intent.getStringExtra(getString(R.string.user_name));
        if (name == null || name.isEmpty()){
            name = "Friend";
        }
        story = new Story();
        loadPage(0);
    }
    private void loadPage(int pageNumber) {
        pageStack.push(pageNumber);
        final Page page = story.getPage(pageNumber);
        Drawable image = ContextCompat.getDrawable(this, page.getImageId());
        storyImageView.setImageDrawable(image);
        String pageText = getString(page.getTextId());
        pageText = String.format(pageText, name);
        storyTextView.setText(pageText);
        if (page.isFinalPage()){
            choice1Button.setVisibility(View.INVISIBLE);
            choice2Button.setText(R.string.play_again_button);
            choice2Button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    loadPage(0);
                }
            });
        }else {
            loadButtons(page);
        }
    }

    private void loadButtons(final Page page) {
        choice1Button.setVisibility(View.VISIBLE);
        choice1Button.setText(getString(page.getChoice1().getTextId()));
        choice1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPage = page.getChoice1().getNextPage();
                loadPage(nextPage);
            }
        });

        choice2Button.setText(getString(page.getChoice2().getTextId()));
        choice2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int nextPage = page.getChoice2().getNextPage();
                loadPage(nextPage);
            }
        });
    }

    @Override
    public void onBackPressed() {
        pageStack.pop();
        if (pageStack.isEmpty()) {
            super.onBackPressed();
        }else{
            loadPage(pageStack.pop());
        }
    }
}