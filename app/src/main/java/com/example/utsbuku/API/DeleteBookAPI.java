package com.example.utsbuku.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface DeleteBookAPI {
    @FormUrlEncoded
    @POST("delete-book.php")
    Call<ResponseBody> delete(@Field("isbn")String isbn);
}
