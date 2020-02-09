package com.example.redhomework.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.redhomework.Class.CityManager;
import com.example.redhomework.HttpUrl.HttpCallBack;
import com.example.redhomework.HttpUrl.HttpUtil;
import com.example.redhomework.R;
import com.example.redhomework.RecyclerView.CityAdapter;
import com.example.redhomework.Tools.ActivityCollector;
import com.example.redhomework.Tools.Internet;

import java.util.ArrayList;
import java.util.List;

public class CityManagerActivity extends AppCompatActivity {
    static final int NORMAL_TYPE = 0;
    static final int SWIPE_TYPE = 1;
    static final int ADDED_TYPE = 2;
    static final int DELETED_TYPE = 3;
    private int count;
    static int all;
    Toolbar toolbar;
    List city_list = new ArrayList();
    RecyclerView recyclerView;
    Button add;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.manager_add:
                Intent intent = new Intent(CityManagerActivity.this, AddCityActivity.class);
                startActivity(intent);
                break;
            case R.id.manager_delete:
                intent = new Intent(CityManagerActivity.this, DeleteActivity.class);
                startActivity(intent);
                break;
            case R.id.manager_default:
                intent = new Intent(CityManagerActivity.this, DefaultCity.class);
                startActivity(intent);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manager_activity, menu);
        return true;
    }


    SwipeRefreshLayout swipe;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case NORMAL_TYPE:
                    final CityAdapter adapter = new CityAdapter(CityManagerActivity.this, city_list);
                    LinearLayoutManager manager = new LinearLayoutManager(CityManagerActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    break;
                case SWIPE_TYPE:
                    swipe.setRefreshing(false);
                    CityAdapter adapter1 = new CityAdapter(CityManagerActivity.this, city_list);
                    LinearLayoutManager manager1 = new LinearLayoutManager(CityManagerActivity.this);
                    recyclerView.setLayoutManager(manager1);
                    recyclerView.setAdapter(adapter1);
                    Toast.makeText(CityManagerActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                    break;
                case ADDED_TYPE:
                    CityAdapter adapter2 = new CityAdapter(CityManagerActivity.this, city_list);
                    LinearLayoutManager manager2 = new LinearLayoutManager(CityManagerActivity.this);
                    recyclerView.setLayoutManager(manager2);
                    recyclerView.setAdapter(adapter2);
                    break;
                case DELETED_TYPE:
                    CityAdapter adapter3 = new CityAdapter(CityManagerActivity.this, city_list);
                    LinearLayoutManager manager3 = new LinearLayoutManager(CityManagerActivity.this);
                    recyclerView.setLayoutManager(manager3);
                    recyclerView.setAdapter(adapter3);
                    break;
            }
        }
    };



    @Override
    protected void onRestart() {
        super.onRestart();
        all = 0;
        SharedPreferences sharedPreferences1 = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE);
        boolean added = sharedPreferences1.getBoolean("added", false);
        boolean deleted = sharedPreferences1.getBoolean("deleted", false);
        if (added) {
            getData(ADDED_TYPE);
            SharedPreferences.Editor editor = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE).edit();
            editor.putBoolean("added", false);
            editor.commit();
        }
        if (deleted) {
            getData(DELETED_TYPE);
            SharedPreferences.Editor editor = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE).edit();
            editor.putBoolean("deleted", false);
            editor.commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_manager);
       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
       //     getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
       //     getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
       // }
        ActivityCollector.addActivity(this);
        recyclerView = findViewById(R.id.city_manager_rv);
        swipe = findViewById(R.id.manager_swipe);
        add = findViewById(R.id.bn_mananger_add_city);
        toolbar = findViewById(R.id.city_manager_toolbar);
        getData(NORMAL_TYPE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CityManagerActivity.this, AddCityActivity.class);
                startActivity(intent);
            }
        });
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(SWIPE_TYPE);
            }
        });
    }

    public void getData(final int type) {
        if (type == SWIPE_TYPE || type == ADDED_TYPE || type == DELETED_TYPE) {
            city_list.clear();
        }
        all = 0;
        final SharedPreferences preferences = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE);
        count = preferences.getInt("count", 0);
        Log.d("eeeeee",count + "");
        if (count != 0) {
            for (int i = 1; i <= count; i++) {
                String city = preferences.getString("city" + i, "error");
                //如果城市不为空
                if (!city.equals("error")) {
                    HttpUtil.sendHttpRequestForNow("https://free-api.heweather.net/s6/weather/now?location=" + city + "&key=69eb00f8b34e4c3cb3969e9a94416c70", new HttpCallBack() {
                        @Override
                        public void OnFinishNow(String location, String time, String weather, String weather_type, String can_see, String temp_now, String temp_physical,String status) {
                            all++;
                            CityManager city = new CityManager(location, temp_now, weather, time);
                            city_list.add(city);
                            if (all == count) {
                                Log.d("eeeeee","eefefefeefeeeeeeeeeeeeeeeeeeeeeeee");
                                Message message = new Message();
                                if (type == NORMAL_TYPE) {
                                    message.what = NORMAL_TYPE;
                                } else if (type == SWIPE_TYPE) {
                                    message.what = SWIPE_TYPE;
                                } else if (type == ADDED_TYPE) {
                                    message.what = ADDED_TYPE;
                                } else if (type == DELETED_TYPE) {
                                    message.what = DELETED_TYPE;
                                }
                                handler.sendMessage(message);
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
            }
        }
        Message message = new Message();
        if (type == NORMAL_TYPE) {
            message.what = NORMAL_TYPE;
        } else if (type == SWIPE_TYPE) {
            message.what = SWIPE_TYPE;
        } else if (type == ADDED_TYPE) {
            message.what = ADDED_TYPE;
        } else if (type == DELETED_TYPE) {
            message.what = DELETED_TYPE;
        }
        handler.sendMessage(message);

    }
}




