package com.siap.jakor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.siap.jakor.databinding.FragmentOptionBinding;
import com.siap.jakor.model.Answer;
import com.siap.jakor.model.Data;
import com.siap.jakor.tool.Helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;


public class OptionFragment extends Fragment {

    private FragmentOptionBinding binding;
    private final String TAG = "mita";
    private Fragment fragment;
    private String name, phone;
    private int questionID = 1, nextQuestionID = 2, lastPoint;
    private int lastSelected, point;
    private boolean isLast = false;
    private ArrayList<Answer> answers = new ArrayList<>();
    private Answer answer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOptionBinding.inflate(getLayoutInflater(), container, false);

        Bundle bundle = this.getArguments();
        questionID = bundle.getInt("question_id");
        lastPoint = bundle.getInt("last_point");
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        answers = bundle.getParcelableArrayList("answers");

        fragment = new OptionFragment();

        if (questionID == 1) {
            binding.previous.setVisibility(View.INVISIBLE);
        } else {
            binding.previous.setVisibility(View.VISIBLE);
            binding.previous.setOnClickListener(view -> {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("question_id", questionID - 1);
                bundle1.putInt("last_point", lastPoint);
                bundle1.putString("name", name);
                bundle1.putString("phone", phone);
                bundle1.putParcelableArrayList("answers", answers);

                fragment.setArguments(bundle1);
                getParentFragmentManager().beginTransaction()
                        .remove(this)
                        .commit();
            });
        }

        binding.next.setOnClickListener(view -> {
            answers.add(answer);
            if (isLast) {
                Intent intent = new Intent(getContext(), ResultActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("point", lastPoint + point);
                intent.putParcelableArrayListExtra("answers", answers);
                startActivity(intent);
                ((Activity) getContext()).finish();
            } else {
                Bundle bundle1 = new Bundle();
                bundle1.putInt("question_id", nextQuestionID);
                bundle1.putInt("last_point", lastPoint + point);
                bundle1.putString("name", name);
                bundle1.putString("phone", phone);
                bundle1.putParcelableArrayList("answers", answers);

                Log.d(TAG, "point dikirim: " + String.valueOf(lastPoint + point));

                fragment.setArguments(bundle1);
                getParentFragmentManager().beginTransaction()
                        .add(R.id.container, fragment)
                        .commit();
            }
        });

        setOptions();

        return binding.getRoot();
    }

    private void setOptions() {
        String stringJSON = new Helpers(getContext()).loadJSONFromAsset();

        Gson gson = new Gson();
        TypeToken<List<Data>> typeToken = new TypeToken<List<Data>>(){};
        List<Data> data = gson.fromJson(stringJSON, typeToken.getType());

        for (int a = 0; a < data.size(); a++) {
//            Data item: data
            Data item = data.get(a);
            if (item.getId() == questionID) {
                // cek jika ini bagian terakhir
                if (a == data.size() - 1) {
                    // last activity
                    isLast = true;
                } else {
                    isLast = false;
                    nextQuestionID = questionID + 1;
                }

                binding.question.setText(item.getQuestion());

                ArrayList<String> options = new ArrayList<>();
                ArrayList<Integer> points = new ArrayList<>();

                for (Data item1: item.getOptions()) {
                    options.add(item1.getQuestion());
                    points.add(item1.getPoint());
                }

                // Set list
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, options);
                binding.listview.setAdapter(adapter);
                binding.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        point = points.get(position);
                        Log.d(TAG, "onItemClick: " + String.valueOf(point));
                        binding.listview.getChildAt(lastSelected).setBackgroundColor(getResources().getColor(R.color.white));
                        lastSelected = position;
                        binding.listview.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.blue));

                        answer = new Answer();
                        answer.setQuestionID(String.valueOf(questionID));
                        answer.setQuestion(item.getQuestion());
                        answer.setAnswer(options.get(position));
                    }
                });
            }
        }
    }
}