package com.siap.jakor.retrofit;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    public static String FORM_ID = "125tA8EGtXH9IAfTcSyBJ_zNMJxObncy_imiGszp3Ha0";

    public static Retrofit getInstance() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request newRequest = originalRequest.newBuilder()
                                .header("Accept", "application/json")
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .build();
                        return chain.proceed(newRequest);
                    }
                })
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
//                .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                .build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://docs.google.com/forms/d/e/")
                    .client(okHttpClient)
                    .build();
        }
        return retrofit;

//        retrofit = new Retrofit.Builder()
//                .baseUrl("https://docs.google.com/forms/d/e/") // Ganti dengan URL formulir Google Anda
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//        return retrofit;
    }

    public static GoogleFormApiService GoogleFormService() {
        GoogleFormApiService apiService = getInstance().create(GoogleFormApiService.class);
        return apiService;
    }
}
