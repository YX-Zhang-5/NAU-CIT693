package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import edu.NAU.seclass.jobcompare.database.UserDao;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText password2;
    private TextInputLayout layoutUsername;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutPassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        layoutUsername = findViewById(R.id.layout_username);
        layoutPassword = findViewById(R.id.layout_password);
        layoutPassword2 = findViewById(R.id.layout_password2);

        // 注册按钮点击事件
        findViewById(R.id.register).setOnClickListener(view -> register());

        // 自动带入登录页输入的用户名
        username.setText(getIntent().getStringExtra("account"));

        // 有默认用户名时，焦点在密码输入框上，否则在用户名输入框上
        if (username.getText().length() == 0) {
            username.requestFocus();
        } else {
            password.requestFocus();
        }
    }

    /**
     * 注册事件
     */
    private void register() {
        // 重置错误提示
        layoutUsername.setError(null);
        layoutPassword.setError(null);
        layoutPassword2.setError(null);

        String userNameStr = username.getText().toString();
        String passwordStr = password.getText().toString();
        String password2Str = password2.getText().toString();
        if (userNameStr.isEmpty()) {
            layoutUsername.setError(layoutUsername.getHint());
        } else if (passwordStr.isEmpty()) {
            layoutPassword.setError(layoutPassword.getHint());
        } else if (passwordStr.equals(password2Str)) {
            if (new UserDao(this).insert(userNameStr, passwordStr)) {
                // 设置新注册的用户名作为返回值
                Intent intent = new Intent();
                intent.putExtra("account", userNameStr);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                layoutUsername.setError("该用户已存在");
            }
        } else {
            layoutPassword2.setError("两次输入的密码不一致");
        }
    }
}
