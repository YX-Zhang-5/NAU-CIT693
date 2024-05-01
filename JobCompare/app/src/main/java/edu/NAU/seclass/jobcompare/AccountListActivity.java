package edu.NAU.seclass.jobcompare;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

import edu.NAU.seclass.jobcompare.database.ConfigDao;
import edu.NAU.seclass.jobcompare.database.UserDao;

public class AccountListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_list);

        UserDao userDao = new UserDao(this);

        ArrayList<HashMap<String, String>> accounts = userDao.query();
        SimpleAdapter adapter = new SimpleAdapter(this, accounts, R.layout.item_account, new String[] {"name"}, new int[] {R.id.name});

        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(this, PasswordActivity.class);
            intent.putExtra("account", accounts.get(i).get("name"));
            startActivity(intent);
        });
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("是否删除该用户？")
                    .setPositiveButton("确定", (dialogInterface, i1) -> {
                        if (userDao.delete(accounts.get(i).get("name"), new ConfigDao(this))) {
                            accounts.remove(i);
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("取消", (dialogInterface, i1) -> {
                    });
            builder.create().show();
            return true;
        });
    }

    public void onReturnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
