package com.example.utsbuku;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utsbuku.API.LoginAPI;
import com.example.utsbuku.API.ServerAPI;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    ProgressDialog pd;
    Button btnLogin;
    TextInputEditText etEmail, etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        etEmail=(TextInputEditText) findViewById(R.id.EtEmail);
        etPassword=(TextInputEditText) findViewById(R.id.EtPassword);

        btnLogin=(Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd= new ProgressDialog(view.getContext());
                pd.setTitle("Proses Login.....");
                pd.setMessage("Tunggu Sebentar");
                pd.setCancelable(true);
                pd.setIndeterminate(true);

                prosesLogin(etEmail.getText().toString(),etPassword.getText().toString());
            }
        });
    }

    void prosesLogin (String temail,String tpassword){
        ServerAPI urlAPI = new ServerAPI();
        String URL = urlAPI.BASE_URL;
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        LoginAPI api = retrofit.create(LoginAPI.class);
        if (!isEmailValid(etEmail.getText().toString())) {
            AlertDialog.Builder msg = new AlertDialog.Builder(LoginActivity.this);

            msg.setMessage("Email tidak Valid").setNegativeButton("Retry",null)
                    .create().show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    msg.create().dismiss();
                }
            }, 3000);
            return;
        }
        api.login(temail,tpassword).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONObject json = new JSONObject(response.body().string());
                    if (json.getString("status").equals("1")) {
                        Toast.makeText(LoginActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder msg = new AlertDialog.Builder(LoginActivity.this);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        intent.putExtra("username",json.getJSONObject("data").getString("username"));
                        intent.putExtra("email",json.getJSONObject("data").getString("email"));
                        startActivity(intent);
                        finish();
                        pd.dismiss();
                    }else {
                        AlertDialog.Builder msg = new AlertDialog.Builder(LoginActivity.this);
                        msg.setMessage("Login gagal")
                                .setNegativeButton("Retry",null).create().show();
                        etEmail.setText("");
                        etPassword.setText("");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                msg.create().dismiss();
                            }
                        }, 3000);
                    }
                    pd.dismiss();
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Info Login", "onFailure: Login Gagal"+t.toString());
                pd.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog.Builder msg = new AlertDialog.Builder(LoginActivity.this);
                        msg.setMessage("Login gagal").setNegativeButton("Retry", null).create().show();
                    }
                }, 3000);
            }
        });
    }
    public boolean isEmailValid(String email){
        boolean isValid = false;

        String expression ="^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches()) {
            isValid=true;
        }
        return  isValid;
    }
}