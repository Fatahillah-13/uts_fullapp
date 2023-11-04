package com.example.utsbuku.API;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AddBookAPI {
    @FormUrlEncoded
    @POST("add-book.php")
    Call<ResponseBody> addbook(@Field("isbn") String isbn,
                               @Field("judul") String judul,
                               @Field("penulis") String penulis,
                               @Field("penerbit") String penerbit,
                               @Field("tahun") String tahun,
                               @Field("jmlhalaman") String jmlhalaman,
                               @Field("harga") String harga);
}
