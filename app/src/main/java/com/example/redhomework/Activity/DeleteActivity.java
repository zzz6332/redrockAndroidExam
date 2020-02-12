package com.example.redhomework.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.session.MediaSession;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;


import com.example.redhomework.R;
import com.example.redhomework.RecyclerView.DeleteAdapter;
import com.example.redhomework.Tools.ActivityCollector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeleteActivity extends AppCompatActivity {
    int count;
    int delete_number;
    static final int DELETED_TYPE = 1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toobar;
    Map<Integer, Boolean> map;
    List<String> list = new ArrayList<>();
    RecyclerView recyclerView;
    Button delete;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DELETED_TYPE:
                    DeleteAdapter adapter = new DeleteAdapter(DeleteActivity.this, list);
                    LinearLayoutManager manager = new LinearLayoutManager(DeleteActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
            }
        }
    };

    @Override
    protected void onRestart() {
        super.onRestart();
        list.clear();
        count = sharedPreferences.getInt("count", 0);
        if (count != 0) {
            for (int i = 1; i <= count; i++) {
                String city = sharedPreferences.getString("city" + i, "error");//如果没有对应城市返回error字符串
                if (!city.equals("error")) {       //如果查找到了城市
                    list.add(city);
                    map.put(i - 1, false);
                }
            }
        } else {
            //如果count为0（没有添加城市）
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (count == 0) {
            toobar = findViewById(R.id.no_add_toolbar);
            setSupportActionBar(toobar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            toobar.setTitle("删除城市");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        sharedPreferences = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE);
        count = sharedPreferences.getInt("count",0);
        if (count != 0){
            setContentView(R.layout.activity_delete);
            recyclerView = findViewById(R.id.delete_rv);
            delete = findViewById(R.id.btn_delete);
            toobar = findViewById(R.id.delete_toolbar);
            setSupportActionBar(toobar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            map = new HashMap<>();
            Intent intent = getIntent();
            editor = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE).edit();
            if (count != 0) {
                for (int i = 1; i <= count; i++) {
                    String city = sharedPreferences.getString("city" + i, "error");//如果没有对应城市返回error字符串
                    if (!city.equals("error")) {       //如果查找到了城市
                        list.add(city);
                    }
                }
            } else {
                //如果count为0（没有添加城市）
            }
            DeleteAdapter adapter = new DeleteAdapter(DeleteActivity.this, list);
            LinearLayoutManager manager = new LinearLayoutManager(DeleteActivity.this);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(adapter);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete_number = sharedPreferences.getInt("delete_number", 0);//delete_number是删除了几个城市
                    List<Integer> delelte_number_list = new ArrayList();
                    if (delete_number != 0) {
                        for (int i = 1; i <= delete_number; i++) { //这里用list储存删除的城市的position
                            int position = sharedPreferences.getInt("delete_city_" + i, 0);
                            delelte_number_list.add(position);
                        }

                        Collections.sort(delelte_number_list, Collections.reverseOrder());  //将list从大到小排序
                        for (int i = 0; i < delete_number; i++) { //这里删除指定的城市
                            list.remove((delelte_number_list.get(i)) - 1);
                            editor.remove("delete_city_" + (i + 1));
                            editor.commit();
                        }
                        for (int i = 1; i <= count; i++) {   //这里清除删除后的city的列表
                            editor.remove("city" + i);
                            editor.commit();
                        }
                        for (int i = 1; i <= count - delete_number; i++) { //这里写入删除后的城市列表
                            editor.putString("city" + i, list.get(i - 1));
                            editor.commit();
                        }
                        editor.putInt("count", count - delete_number);
                        editor.putInt("delete_number", 0);
                        editor.putBoolean("deleted", true);
                        editor.commit();
                        Message message = new Message();
                        message.what = DELETED_TYPE;
                        handler.sendMessage(message);
                        Toast.makeText(DeleteActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
            });
        }
        else {
            setContentView(R.layout.no_add_city);
            Button button = findViewById(R.id.no_add_btn);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DeleteActivity.this,AddCityActivity.class);
                    startActivity(intent);
                    finish();
                    ActivityCollector.removeActivity(DeleteActivity.this);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
        int delete_number = sharedPreferences.getInt("delete_number",0);
        if (delete_number != 0){ //如果退出当前活动时，勾选了城市但是没有点击删除按钮，就remove写入的键值对
            for (int i = 1; i <= delete_number ; i++) {
                editor.remove("delete_city" + i);
                editor.commit();
            }
            editor.putInt("delete_number",0).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return true;
    }
}
