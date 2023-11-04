package com.example.utsbuku.API;

import android.renderscript.Sampler;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetBookAPI {
    @GET("index.php")
    Call<Sampler.Value> get();
}
