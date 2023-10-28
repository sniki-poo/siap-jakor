package com.siap.jakor.retrofit;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface GoogleFormApiService {

    @POST("1FAIpQLSfX6oIFUjf3ss1vO2UkkmbdL9p7I1qpY3vtG_i5l4-heQpUwQ/formResponse")
    Call<Void> formData(
        @Body RequestBody body
    );
}
