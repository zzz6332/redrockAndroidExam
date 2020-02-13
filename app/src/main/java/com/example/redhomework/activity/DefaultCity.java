package com.example.redhomework.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.redhomework.MainActivity;
import com.example.redhomework.R;
import com.example.redhomework.recyclerviewAdapter.DefaultCityAdapter;
import com.example.redhomework.tools.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

public class DefaultCity extends AppCompatActivity {
    RecyclerView recyclerView;
    Button button;
    List<String> list_city;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;
    String default_city;
    String default_city_second;
    Button button_add;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE);
        ActivityCollector.addActivity(this);
        editor = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE).edit();
        list_city = new ArrayList<>();
        int count = sharedPreferences.getInt("count", 0);
        if (count != 0) { //如果添加了城市
            setContentView(R.layout.activity_default_city);
            toolbar = findViewById(R.id.default_toolbar);
            recyclerView = findViewById(R.id.rv_default);
            button = findViewById(R.id.btn_default);
            if (count != 0) {
                for (int i = 1; i <= count; i++) {
                    String city = sharedPreferences.getString("city" + i, "error");
                    list_city.add(city);
                }
            }
            DefaultCityAdapter adapter = new DefaultCityAdapter(DefaultCity.this, list_city);
            LinearLayoutManager manager = new LinearLayoutManager(DefaultCity.this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean isChoose = sharedPreferences.getBoolean("isChoose",false);//是否勾选了
                    int position = sharedPreferences.getInt("default_city_position", -1);
                    int position_second = sharedPreferences.getInt("default_city_position_second", -1);
                    Boolean isSecond = sharedPreferences.getBoolean("isSecond", false);
                    if (!isChoose) { //如果什么都没有选择就点击了
                        Toast.makeText(DefaultCity.this, "请选择城市了之后再点击~！", Toast.LENGTH_SHORT).show();
                    }
                    if (position != -1 && !isSecond) { //如果是第一次设置默认城市
                        String default_city = list_city.get(position - 1);
                        editor.putString("default_city", default_city);
                        editor.putBoolean("isdefault", true);
                        editor.commit();
                        Toast.makeText(DefaultCity.this, "设置成功!", Toast.LENGTH_SHORT).show();
                    }
                    if (position_second != -1 && isSecond) { //如果是第二次设置默认城市
                        String default_city = list_city.get(position_second - 1);
                        editor.putString("default_city", default_city).putBoolean("isdefault", true);
                        editor.commit();
                        Toast.makeText(DefaultCity.this, "设置成功!", Toast.LENGTH_SHORT).show();
                    }
                    editor.putBoolean("isChoose",false);
                    Intent intent = new Intent(DefaultCity.this, MainActivity.class);
                    startActivity(intent);
                    ActivityCollector.finishAll();

                }
            });
        } else {  //如果没有添加城市
            setContentView(R.layout.no_add_city);
            toolbar = findViewById(R.id.no_add_toolbar);
            button_add = findViewById(R.id.no_add_btn);
            button_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DefaultCity.this,AddCityActivity.class);
                    startActivity(intent);
                    finish();
                    ActivityCollector.removeActivity(DefaultCity.this);
                }
            });
        }
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);

        Boolean isdefault = sharedPreferences.getBoolean("isdefault", false);
        Boolean isSecond = sharedPreferences.getBoolean("isSecond", false); //是否是第二次设置默认城市
        if (!isdefault && !isSecond) { //如果退出当前活动时，勾选了城市但是没有点击设置默认城市按钮，就remove写入的键值对
            editor.remove("default_city").remove("default_city_position");
            editor.commit();
        }
        if (!isdefault && isSecond) { //这里跟上面一样
            editor.remove("default_city_second").remove("default_city_second_position");
            editor.commit();
        }
    }
}
