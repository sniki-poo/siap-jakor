package com.siap.jakor.tool;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Helpers {

    private Context context;

    public Helpers(Context context) {
        this.context = context;
    }

    public boolean validateInput(ArrayList<TextInputLayout> inputLayouts, ArrayList<TextInputEditText> editTexts) {
        boolean result = true;
        for (int a = 0; a < editTexts.size(); a++) {
            TextInputEditText editText = editTexts.get(a);
            TextInputLayout inputLayout = inputLayouts.get(a);

            String data = editText.getText().toString().trim();
            if (TextUtils.isEmpty(data)) {
                result = false;
                inputLayout.setError("Kolom ini wajib diisi!");
            }
        }
        return result;
    }

    public void customTextWatcher(TextInputLayout inputLayout, TextInputEditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = context.getAssets().open("data.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
