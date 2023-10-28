package com.siap.jakor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.siap.jakor.databinding.ActivityLoginBinding;
import com.siap.jakor.tool.Helpers;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Helpers(this).customTextWatcher(binding.nameInputLayout, binding.name);

        ArrayList<TextInputLayout> inputLayouts = new ArrayList<>();
        ArrayList<TextInputEditText> editTexts = new ArrayList<>();

        inputLayouts.add(binding.nameInputLayout);
        inputLayouts.add(binding.phoneInputLayout);
        editTexts.add(binding.name);
        editTexts.add(binding.phone);

        binding.submit.setOnClickListener(view1 -> {
            String name = binding.name.getText().toString().trim();
            String phone = binding.phone.getText().toString().trim();

            if (new Helpers(LoginActivity.this).validateInput(inputLayouts, editTexts)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                startActivity(intent);
            }
        });
    }
}