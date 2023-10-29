package com.siap.jakor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.siap.jakor.databinding.ActivityResultBinding;
import com.siap.jakor.model.Answer;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    private ActivityResultBinding binding;
    private final String TAG = "mita4";
    private ArrayList<Answer> answers = new ArrayList<>();
    private String name, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        answers = getIntent().getParcelableArrayListExtra("answers");
        int point = getIntent().getIntExtra("point", 0);

        for (Answer item: answers) {
            Log.d(TAG, item.getQuestionID() + ". " + item.getQuestion() + ": " + item.getAnswer());
        }

        binding.title.setText("Halo, " + name);
        binding.description.setText("Berdasarkan form yang Anda isi, skor resiko penyakit jantung koroner dalam 10 tahun kedepan adalah " + String.valueOf(point) + "%");
        binding.restart.setOnClickListener(view -> {
            finish();
        });

        binding.suggestion.setOnClickListener(view -> {
            startActivity(new Intent(this, SuggestionActivity.class)
                    .putParcelableArrayListExtra("answers", answers)
                    .putExtra("name", name)
                    .putExtra("phone", phone)
                    .putExtra("point", point));
        });
    }
}