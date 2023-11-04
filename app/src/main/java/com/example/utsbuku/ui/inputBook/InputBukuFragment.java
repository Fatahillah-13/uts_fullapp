package com.example.utsbuku.ui.inputBook;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.utsbuku.API.AddBookAPI;
import com.example.utsbuku.API.ServerAPI;
import com.example.utsbuku.R;
import com.example.utsbuku.databinding.FragmentInputBukuBinding;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InputBukuFragment extends Fragment {
    Button BtnClear,BtnSimpan;
    TextInputEditText EtISBN,EtJudul,EtPenulis,EtPenerbit,EtTahun,EtHalaman,EtHarga;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_input_buku,container,false);
        BtnClear = view.findViewById(R.id.BtnClear);
        BtnSimpan = view.findViewById(R.id.BtnSimpan);
        EtISBN = view.findViewById(R.id.EtIsbn);
        EtJudul = view.findViewById(R.id.EtJudulBuku);
        EtPenulis = view.findViewById(R.id.EtPenulis);
        EtPenerbit = view.findViewById(R.id.EtPenerbit);
        EtTahun = view.findViewById(R.id.EtTahun);
        EtHalaman = view.findViewById(R.id.EtHalaman);
        EtHarga = view.findViewById(R.id.EtHarga);

        BtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EtISBN.setFocusableInTouchMode(true);
                EtISBN.requestFocus();
                EtISBN.setText("");
                EtJudul.setText("");
                EtPenulis.setText("");
                EtPenerbit.setText("");
                EtTahun.setText("");
                EtHalaman.setText("");
                EtHarga.setText("");
            }
        });
        BtnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddBokkFunction(
                        EtISBN.getText().toString(),
                        EtJudul.getText().toString(),
                        EtPenulis.getText().toString(),
                        EtPenerbit.getText().toString(),
                        EtTahun.getText().toString(),
                        EtHalaman.getText().toString(),
                        EtHarga.getText().toString()
                );
            }
        });
        return view;
    }

    private void AddBokkFunction(String tisbn,String tjudul,String tpenulis,String tpenerbit,String ttahun, String tjmlhalaman,String tharga) {
        ServerAPI urlapi = new ServerAPI();
        String URL = urlapi.BASE_URL;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        AddBookAPI api = retrofit.create(AddBookAPI.class);
        api.addbook(tisbn,tjudul,tpenulis,tpenerbit,ttahun,tjmlhalaman,tharga).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    if(json.getString("status").toString().equals("1")){
                        if (json.getString("result").toString().equals("1")){
                            AlertDialog.Builder msg = new AlertDialog.Builder(getContext());
                            msg.setMessage("Buku Berhasil Di Tambahkan")
                                    .setPositiveButton("Ok",null).create().show();
                            EtISBN.setText("");
                            EtJudul.setText("");
                            EtPenulis.setText("");
                            EtPenerbit.setText("");
                            EtTahun.setText("");
                            EtHalaman.setText("");
                            EtHarga.setText("");
                            EtISBN.setFocusableInTouchMode(true);
                            EtISBN.requestFocus();
                        }else{
                            AlertDialog.Builder msg = new AlertDialog.Builder(getContext());

                            msg.setMessage("Simpan Gagal").setNegativeButton("Retry",null)
                                    .create().show();
                            EtISBN.setText("");
                            EtJudul.setText("");
                            EtPenulis.setText("");
                            EtPenerbit.setText("");
                            EtTahun.setText("");
                            EtHalaman.setText("");
                            EtHarga.setText("");
                            EtISBN.setFocusableInTouchMode(true);
                            EtISBN.requestFocus();
                        }
                    }
                }catch (JSONException | IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("[INFO SIMPAN BUKU]", "onFailure: Simpan Buku Gagal"+t.toString());
            }
        });
    }
}