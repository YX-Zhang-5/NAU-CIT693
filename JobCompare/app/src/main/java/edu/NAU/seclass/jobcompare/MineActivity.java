package edu.NAU.seclass.jobcompare;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine);

        ((TextView) findViewById(R.id.title)).setText(getSharedPreferences("userInfo", MODE_PRIVATE).getString("account", "个人中心"));

        findViewById(R.id.modefy_password).setOnClickListener(view -> startActivity(new Intent(this, PasswordActivity.class)));

        findViewById(R.id.logout).setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAffinity();
        });
    }
}
