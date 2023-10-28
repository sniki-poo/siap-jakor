package com.siap.jakor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.siap.jakor.databinding.ActivityMainBinding;
import com.siap.jakor.model.Answer;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Fragment fragment;
    private String name, phone;
    private ArrayList<Answer> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");

        firstStep();
    }

    private void firstStep() {
        Bundle bundle = new Bundle();
        bundle.putInt("question_id", 1);
        bundle.putInt("last_point", 0);
        bundle.putString("name", name);
        bundle.putString("phone", phone);
        bundle.putParcelableArrayList("answers", arrayList);

        fragment = new OptionFragment();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, fragment)
                .commit();
    }
}