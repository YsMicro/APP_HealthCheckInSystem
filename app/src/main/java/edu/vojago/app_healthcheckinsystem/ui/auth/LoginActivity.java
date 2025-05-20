package edu.vojago.app_healthcheckinsystem.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import edu.vojago.app_healthcheckinsystem.R;
import edu.vojago.app_healthcheckinsystem.data.api.ApiService;
import edu.vojago.app_healthcheckinsystem.ui.main.MainActivity;
import edu.vojago.app_healthcheckinsystem.utils.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 初始化视图
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // 输入校验增强
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "用户名和密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        // 新增：最小长度校验
        if (username.length() < 5 || password.length() < 6) {
            Toast.makeText(this, "用户名至少5位，密码至少6位", Toast.LENGTH_SHORT).show();
            return;
        }

        // 显示加载进度条
        progressBar.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        // 使用 Retrofit 发送登录请求
        try {
            ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
            LoginRequest loginRequest = new LoginRequest(username, password);

            apiService.login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);

                    if (response.isSuccessful() && response.body() != null) {
                        // 登录成功，跳转到主界面
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        // 新增：解析错误体
                        try {
                            String errorBody = response.errorBody().string();
                            Toast.makeText(LoginActivity.this, "服务器错误：" + errorBody, Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(LoginActivity.this, "网络响应解析失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);
                    // 新增：显示完整错误信息
                    Toast.makeText(LoginActivity.this, "网络错误：" + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } catch (Exception e) {
            // 捕获意外异常
            progressBar.setVisibility(View.GONE);
            btnLogin.setEnabled(true);
            Toast.makeText(this, "发生未知错误：" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // 登录请求参数类
    public static class LoginRequest {
        private String username;
        private String password;

        public LoginRequest(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    // 登录响应数据类
    public static class LoginResponse {
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }
}