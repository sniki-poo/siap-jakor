package com.siap.jakor.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("id")
    private int id;

    @SerializedName("question")
    private String question;

    @SerializedName("type")
    private String type;

    @SerializedName("option")
    private List<Data> options;

    // Tambahan field untuk opstion
    @SerializedName("point")
    private int point;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Data> getOptions() {
        return options;
    }

    public void setOptions(List<Data> options) {
        this.options = options;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
