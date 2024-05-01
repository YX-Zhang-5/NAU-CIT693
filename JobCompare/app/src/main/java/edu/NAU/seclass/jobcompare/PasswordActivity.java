package edu.NAU.seclass.jobcompare;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import edu.NAU.seclass.jobcompare.database.UserDao;

public class PasswordActivity extends AppCompatActivity {

    private EditText password;
    private EditText password2;
    private TextInputLayout layoutPassword;
    private TextInputLayout layoutPassword2;

    private String mUserName;
    private boolean isCurrentAccount = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        mUserName = getIntent().getStringExtra("account");
        if (mUserName == null) {
            mUserName = getSharedPreferences("userInfo", MODE_PRIVATE).getString("account", null);
            isCurrentAccount = true;
        }

        password = findViewById(R.id.password);
        password2 = findViewById(R.id.password2);
        layoutPassword = findViewById(R.id.layout_password);
        layoutPassword2 = findViewById(R.id.layout_password2);

        // 修改按钮点击事件
        findViewById(R.id.modify).setOnClickListener(view -> modify());

        password.requestFocus();
    }

    /**
     * 修改密码事件
     */
    private void modify() {
        // 重置错误提示
        layoutPassword.setError(null);
        layoutPassword2.setError(null);

        String passwordStr = password.getText().toString();
        String password2Str = password2.getText().toString();
        if (passwordStr.isEmpty()) {
            layoutPassword.setError(layoutPassword.getHint());
        } else if (passwordStr.equals(password2Str)) {
            if (new UserDao(this).update(mUserName, passwordStr)) {
                Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
                if (isCurrentAccount) {
                    // 清除保存的密码
                    getSharedPreferences("userInfo", MODE_PRIVATE).edit().remove("password").apply();
                }
                finish();
            } else {
                Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
            }
        } else {
            layoutPassword2.setError("两次输入的密码不一致");
        }
    }
}
