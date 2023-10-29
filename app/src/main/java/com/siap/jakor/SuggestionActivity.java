package com.siap.jakor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.siap.jakor.adapter.SuggestionAdapter;
import com.siap.jakor.databinding.ActivitySuggestionBinding;
import com.siap.jakor.model.Answer;
import com.siap.jakor.model.SuggestionModel;
import com.siap.jakor.retrofit.ApiClient;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuggestionActivity extends AppCompatActivity {

    private ActivitySuggestionBinding binding;
    private ArrayList<Answer> answers = new ArrayList<>();
    private String name, phone;
    private int point;
    private final String TAG = "mita1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySuggestionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        name = getIntent().getStringExtra("name");
        phone = getIntent().getStringExtra("phone");
        answers = getIntent().getParcelableArrayListExtra("answers");
        point = getIntent().getIntExtra("point", 0);

        ArrayList<SuggestionModel> arrayList = new ArrayList<>();
        arrayList.add(new SuggestionModel("Cek kesehatan rutin"));
        arrayList.add(new SuggestionModel("Enyahkan asap rokok"));
        arrayList.add(new SuggestionModel("Rajin melakukan  aktivitas fisik, minimal 30 menit per hari"));
        arrayList.add(new SuggestionModel("Diet sehat dan seimbang"));
        arrayList.add(new SuggestionModel("Batasi konsumsi gula 4 sendok makan per hari"));
        arrayList.add(new SuggestionModel("Batasi konsumsi garam 1 sendok teh per hari"));
        arrayList.add(new SuggestionModel("Batasi konsumsi minyak/lemak 5 sendok makan per hari"));
        arrayList.add(new SuggestionModel("Istirahat yang cukup 7-8 jam per hari"));
        arrayList.add(new SuggestionModel("Kelola stres"));

        SuggestionAdapter adapter = new SuggestionAdapter(this, arrayList);
        binding.recyclerview.setAdapter(adapter);


        binding.submit.setOnClickListener((view -> {
            submitToGoogleForm();
        }));
    }

    private void submitToGoogleForm() {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);

        if (answers.size() != 0) {
            builder.addFormDataPart("entry.1037896118", name);
            builder.addFormDataPart("entry.193066813", phone);
            builder.addFormDataPart("entry.1115263876", answers.get(0).getAnswer()); // gender
            builder.addFormDataPart("entry.133786702", answers.get(1).getAnswer()); // age
            builder.addFormDataPart("entry.960305528", answers.get(2).getAnswer()); // perokok
            builder.addFormDataPart("entry.1123752606", answers.get(3).getAnswer()); // diabetes
            builder.addFormDataPart("entry.1443895919", answers.get(4).getAnswer()); // tekanan darah
            builder.addFormDataPart("entry.146427865", answers.get(5).getAnswer()); // kolestrol
            builder.addFormDataPart("entry.493492910", String.valueOf(point) + "%"); // Hasil Resiko

            RequestBody body = builder.build();

            ApiClient.GoogleFormService().formData(body).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(SuggestionActivity.this, R.style.TransparentAlertDialog);
                        builder1.setCancelable(true);

                        View view = LayoutInflater.from(SuggestionActivity.this).inflate(R.layout.dialog_success, null);
                        builder1.setView(view);
                        AppCompatButton ok = view.findViewById(R.id.ok);

                        AlertDialog dialog = builder1.create();
                        ok.setOnClickListener(view2 -> {
                            dialog.dismiss();
                        });

                        dialog.show();
                    } else if (response.code() == 404) {
                        Toast.makeText(SuggestionActivity.this, "Data gagal dikirim, form telah berubah", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SuggestionActivity.this, "Terjadi kesalahan, harap coba lagi", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(SuggestionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "onFailure: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Terjadi masalah, harap hubungi developer", Toast.LENGTH_SHORT).show();
        }
    }
}