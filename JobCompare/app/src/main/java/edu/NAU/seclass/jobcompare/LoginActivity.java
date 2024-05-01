package edu.NAU.seclass.jobcompare;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import edu.NAU.seclass.jobcompare.database.UserDao;

public class LoginActivity extends AppCompatActivity {
    private EditText username;
    private EditText password;
    private CheckBox remember;
    private TextInputLayout layoutUsername;
    private TextInputLayout layoutPassword;

    private ActivityResultLauncher<Intent> mRegisterLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("登录");

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        remember = findViewById(R.id.remember);
        layoutUsername = findViewById(R.id.layout_username);
        layoutPassword = findViewById(R.id.layout_password);

        // 取出上次登录的用户名和保存的密码并设置到对应的输入框中
        SharedPreferences userInfo = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userNameStr = userInfo.getString("account", null);
        username.setText(userNameStr);
        String passwordStr = userInfo.getString("password", null);
        if (passwordStr != null && !passwordStr.isEmpty()) {
            password.setText(passwordStr);
            remember.setChecked(true);
        }

        // 有默认用户名时，焦点在密码输入框上，否则在用户名输入框上
        if (userNameStr != null && !userNameStr.isEmpty()) {
            password.requestFocus();
        } else {
            username.requestFocus();
        }

        // 登录按钮点击事件
        findViewById(R.id.login).setOnClickListener(view -> login(userInfo));
        // 注册按钮点击事件
        findViewById(R.id.register).setOnClickListener(view -> toRegister());

        // 跳转到注册页面的启动器
        mRegisterLauncher = registerForActivityResult(new StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        // 注册成功后，代入新注册的用户名，焦点在密码输入框上
                        Intent data = result.getData();
                        if (data != null) {
                            username.setText(data.getStringExtra("account"));
                            password.requestFocus();
                        }
                    }
                });
    }

    /**
     * 登录事件
     */
    private void login(SharedPreferences userInfo) {
        // 重置错误提示
        layoutUsername.setError(null);
        layoutPassword.setError(null);

        String userNameStr = username.getText().toString();
        String passwordStr = password.getText().toString();
        if (userNameStr.isEmpty()) {
            layoutUsername.setError("请输入" + layoutUsername.getHint());
        } else if (passwordStr.isEmpty()) {
            layoutPassword.setError("请输入" + layoutPassword.getHint());
        } else {
            // 查询数据库的用户表中是否存在该用户
            String pwd = new UserDao(this).getPassword(userNameStr);
            if (pwd == null) {
                // 未注册，提醒用户进行注册
                AlertDialog.Builder builder = new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("该用户未注册，是否进行注册？")
                        .setPositiveButton("确定", (var1, var2) -> toRegister())
                        .setNegativeButton("取消", (var1, var2) -> {
                        });
                builder.create().show();
            } else if (pwd.equals(passwordStr)) {
                // 保存本次登陆的用户名和密码
                SharedPreferences.Editor editor = userInfo.edit();
                editor.putString("account", userNameStr);
                if (remember.isChecked()) {
                    // 记住密码
                    editor.putString("password", passwordStr);
                } else {
                    editor.remove("password");
                }
                editor.apply();

                // 跳转到主页面
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                layoutPassword.setError("密码错误，请重试");
            }
        }
    }

    /**
     * 跳转到注册页
     */
    private void toRegister() {
        // 跳转到注册页，带入当前输入的用户名
        Intent intent = new Intent(this, RegisterActivity.class);
        intent.putExtra("account", username.getText().toString());
        mRegisterLauncher.launch(intent);
    }
}
