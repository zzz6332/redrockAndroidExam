package com.example.redhomework.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextPaint;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.redhomework.HttpUrl.HttpCallBack;
import com.example.redhomework.HttpUrl.HttpUtil;
import com.example.redhomework.MainActivity;
import com.example.redhomework.R;
import com.example.redhomework.RecyclerView.MyAdapter;
import com.example.redhomework.Tools.ActivityCollector;
import com.example.redhomework.Tools.ImageResource;
import com.example.redhomework.Tools.Internet;

import java.util.List;

public class AddCityActivity extends AppCompatActivity {
    EditText editText;
    Button button_add;
    Toolbar toolbar;
    static final int TOAST_TYPE_SUCCESS = 2;
    static final int TOAST_TYPE_FAIL = 3;
    SharedPreferences.Editor editor;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case TOAST_TYPE_SUCCESS:
                    Toast.makeText(AddCityActivity.this, "添加成功!", Toast.LENGTH_SHORT).show();
                    break;
                case TOAST_TYPE_FAIL:
                    Toast.makeText(AddCityActivity.this, "没有这个城市!", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setContentView(R.layout.activity_add_city);
        editText = findViewById(R.id.edit_city);
        button_add = findViewById(R.id.bt_add_city);
        toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        editor = getSharedPreferences(getString(R.string.file_name), MODE_PRIVATE).edit();
        final SharedPreferences sharedPreferences = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String city = editText.getText().toString();
                HttpUtil.sendHttpRequestForNow("https://free-api.heweather.net/s6/weather/now?location=" + city + "&key=69eb00f8b34e4c3cb3969e9a94416c70", new HttpCallBack() {
                    @Override
                    public void OnFinishNow(String location, String time, String weather, String weather_type, String can_see, String temp_now, String temp_physical, String status) {
                        if (status.equals("ok")) { //如果有该城市
                            int count = sharedPreferences.getInt("count", 0);
                            Message message_success = new Message();
                            message_success.what = TOAST_TYPE_SUCCESS;
                            if (count == 0) {  // 如果是第一次添加城市
                                editor.putString("city1", city);
                                editor.putInt("count", 1);
                                editor.putBoolean("added", true);
                                editor.commit();
                                editText.setText("");
                                handler.sendMessage(message_success);
                            } else {  //如果添加的城市不是第一个
                                count++;
                                editor.putString("city" + count, city);
                                editor.putInt("count", count);
                                editor.putBoolean("added", true);
                                editor.commit();
                                editText.setText("");
                                handler.sendMessage(message_success);
                            }
                            //添加完毕后跳转到主活动
                            Intent intent = new Intent(AddCityActivity.this, MainActivity.class);
                            intent.putExtra("city",city);
                            startActivity(intent);
                            ActivityCollector.finishAll();
                        } else {  //如果查找不到输入的城市
                            editText.setText("");
                            Message message_fail = new Message();
                            message_fail.what = TOAST_TYPE_FAIL;
                            handler.sendMessage(message_fail);

                        }
                    }

                    @Override
                    public void OnFinishForecast(List list) {

                    }

                    @Override
                    public void OnFinishAir(List list,Boolean isAir) {

                    }

                    @Override
                    public void OnFinishLifeStyle(List list) {

                    }

                    @Override
                    public boolean OnError(Exception e) {
                        return false;
                    }
                });

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
