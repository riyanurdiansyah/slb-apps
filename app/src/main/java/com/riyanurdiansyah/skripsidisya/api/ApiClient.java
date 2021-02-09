package com.riyanurdiansyah.skripsidisya.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String base_url = "http://192.168.0.101/slb/api/user/";
    public static final String IMAGE_PROFIL = "http://192.168.0.101/slb/assets/profil/";
    public static final String MATERI_BACA = "http://192.168.0.101/slb/assets/baca huruf/";
    public static final String MATERI_TEBAK = "http://192.168.0.101/slb/assets/tebak huruf/";
    public static final String MATERI_TEBAK_KATA = "http://192.168.0.101/slb/assets/tebak kata/";

    public static final String KATEGORI_GAMBAR = "http://192.168.0.101/slb/assets/tebak gambar/kategori/";
    public static final String GAMBAR = "http://192.168.0.101/slb/assets/tebak gambar/gambar/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
}
