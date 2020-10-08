package com.example.course2kp;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.course2kp.Model.UserAccount;
import com.example.course2kp.Retrofit.IMyService;
import com.example.course2kp.Retrofit.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;
import dmax.dialog.SpotsDialog;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity {
    Button login_btn;
    TextView sign_up,forgot_password;
    EditText Email, password;
    CompositeDisposable compositeDisposable =new CompositeDisposable();
    IMyService iMyService;
    String TaiKhoan, MatKhau;
    AlertDialog alertDialog;
    UserAccount userAccount;
    SharedPreferences sharedPreferences;
    boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        setContentView(R.layout.login);

        setUIReference();

        Retrofit retrofitClient= RetrofitClient.getInstance();
        iMyService=retrofitClient.create(IMyService.class);
        alertDialog= new SpotsDialog.Builder().setContext(this).build();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CheckValidInput())
                    Login();
            }
        });
    }

    private void Login() {
        login_btn.setClickable(false);
        login_btn.setEnabled(false);

        try {
            // alertDialog.show();
            iMyService.loginUser(TaiKhoan, MatKhau)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Response<String>>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(Response<String> stringResponse) {
                            if(stringResponse.isSuccessful()){


                                if(stringResponse.body().toString().contains("name"))
                                {
                                    String responseString=stringResponse.body().toString();
                                    try {
                                        JSONObject jo=new JSONObject(responseString);
                                        userAccount=new UserAccount(jo.getString("name"),"",
                                                jo.getString("phone"),
                                                jo.getString("image"),
                                                jo.getString("email"),
                                                stringResponse.headers().get("Auth-token"),
                                                jo.getString("gender"),
                                                jo.getString("description"),
                                                jo.getString("address"),
                                                MatKhau,
                                                jo.getString("_id")
                                        );

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("name",userAccount.getHoten());
                                        editor.putString("phone",userAccount.getSdt());
                                        editor.putString("image",userAccount.getAva());
                                        editor.putString("email",userAccount.getMail());
                                        editor.putString("token",userAccount.getToken());
                                        editor.putString("gender",userAccount.getGioitinh());
                                        editor.putString("description",userAccount.getMota());
                                        editor.putString("address",userAccount.getDiachia());
                                        editor.putString("password",MatKhau);
                                        editor.putString("id",userAccount.getID());
                                        editor.commit();
                                        flag=true;
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                                else{
                                    flag=false;
                                }}
                            else{
                                flag=false;
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            alertDialog.dismiss();

                                        }
                                    }, 500);
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            login_btn.setClickable(true);
                            login_btn.setEnabled(true);
                        }

                        @Override
                        public void onComplete() {
                            new android.os.Handler().postDelayed(
                                    new Runnable() {
                                        public void run() {
                                            alertDialog.dismiss();

                                        }
                                    }, 500);


                            if(flag==true) {
                                Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                                intent.putExtra("userAcc", userAccount);
                                intent.putExtra("change",0);
                                startActivity(intent);
                                //CustomIntent.customType(LoginActivity.this, "right-to-left");

                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                                login_btn.setClickable(true);
                                login_btn.setEnabled(true);
                            }
                        }
                    });
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    private void setUIReference() {
        login_btn=findViewById(R.id.login_btn);
        sign_up=findViewById(R.id.sign_up);
        Email=findViewById(R.id.Email);
        password=findViewById(R.id.password);
        forgot_password=findViewById(R.id.forgot_password);
    }
    private boolean CheckValidInput() {
        Boolean valid=true;
        TaiKhoan=Email.getText().toString();
        MatKhau=password.getText().toString();
        if(TaiKhoan.isEmpty() ||TaiKhoan.length() < 6 || TaiKhoan.length() >40 )
        {
            Email.setError("Từ 6 đến 40 ký tự");
            valid = false;
        } else {
            Email.setError(null);
        }
        if(MatKhau.isEmpty() || MatKhau.length() <8 ||MatKhau.length()>16 )
        {
            password.setError("Mật khẩu có 8 đến 16 ký tự");
            valid = false;
        } else {
            password.setError(null);
        }
        return valid;
    }
}