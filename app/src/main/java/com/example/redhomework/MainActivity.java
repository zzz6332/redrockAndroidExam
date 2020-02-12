package com.example.redhomework;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.redhomework.Activity.CityManagerActivity;
import com.example.redhomework.Broadcast.InternetReiceiver;
import com.example.redhomework.Class.Air;
import com.example.redhomework.Class.LifeStyle;
import com.example.redhomework.Class.Predict_Weather_day_1_3;
import com.example.redhomework.HttpUrl.HttpCallBack;
import com.example.redhomework.HttpUrl.HttpUtil;
import com.example.redhomework.RecyclerView.MyAdapter;
import com.example.redhomework.Tools.ActivityCollector;
import com.example.redhomework.Tools.Broadcast;
import com.example.redhomework.Tools.ImageResource;
import com.example.redhomework.Tools.Internet;

import java.io.BufferedOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<Predict_Weather_day_1_3> mlist = new ArrayList();
    List<Air> air_list = new ArrayList<>();
    List<LifeStyle> lifeStyle_list = new ArrayList<>();
    SharedPreferences sharedPreferences;
    boolean isAir;
    static final int NORMAL_TYPE = 0;
    static final int SWIPE_TYPE = 1;
    static final int IMAGE_TYPE = 2;
    static final int BACKGROUND_TYPE = 3;
    static final int NO_INTERNET_TYPE = 4;
    static final int NO_INTERNET_SWIPE_TYPE = 5;
    static final int INTENT_TYPE = 6;
    View view;
    TextView tv_location;
    TextView tv_update_time;
    TextView tv_weather;
    TextView tv_temp_now;
    TextView tv_temp_physical;
    TextView tv_can_see;
    TextView tv_temp;
    ImageView iv_weather_type;
    SwipeRefreshLayout swipe;
    String mweather;
    InternetReiceiver receiver;
    Toolbar toolbar;
    RecyclerView recyclerView;
    SharedPreferences.Editor editor;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NORMAL_TYPE:
                    MyAdapter adapter = new MyAdapter(MainActivity.this, true, mlist, air_list, lifeStyle_list);
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(adapter);
                    break;
                case SWIPE_TYPE:
                    swipe.setRefreshing(false);
                    MyAdapter adapter1 = new MyAdapter(MainActivity.this, true, mlist, air_list, lifeStyle_list);
                    LinearLayoutManager manager1 = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(manager1);
                    recyclerView.setAdapter(adapter1);
                    Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                    break;
                case NO_INTERNET_SWIPE_TYPE:
                    swipe.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "当前无网络，刷新失败", Toast.LENGTH_SHORT).show();
                    break;
                case NO_INTERNET_TYPE:
                    MyAdapter adapter2 = new MyAdapter(MainActivity.this, false, isAir);
                    LinearLayoutManager manager2 = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(manager2);
                    recyclerView.setAdapter(adapter2);
                    break;
                case INTENT_TYPE:
                    MyAdapter adapter3 = new MyAdapter(MainActivity.this, true, mlist, air_list, lifeStyle_list);
                    LinearLayoutManager manager3 = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setAdapter(adapter3);
                    recyclerView.setLayoutManager(manager3);
                    break;
                case IMAGE_TYPE:
                    ImageResource.weatherSetImageResource(iv_weather_type, mweather);
                    break;
                case BACKGROUND_TYPE:
                    ImageResource.mainSetBackground(view, mweather);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ActivityCollector.addActivity(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        //----实例化控件
        view = findViewById(R.id.main_view);
        tv_location = findViewById(R.id.tv_location);
        tv_weather = findViewById(R.id.tv_weather);
        tv_update_time = findViewById(R.id.tv_update_time);
        tv_temp_now = findViewById(R.id.tv_now_temp);
        tv_temp_physical = findViewById(R.id.tv_physical_temp);
        tv_can_see = findViewById(R.id.tv_cansee);
        iv_weather_type = findViewById(R.id.iv_weather_type);
        tv_temp = findViewById(R.id.tv_temp);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.main_recycler);
        swipe = findViewById(R.id.main_swipe);
        Intent intent = getIntent();   //获取启动活动的intent
        sharedPreferences = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE);
        editor = getSharedPreferences((getString(R.string.file_name)), MODE_PRIVATE).edit();
        final String default_city = sharedPreferences.getString("default_city", "error");
        final String city = intent.getStringExtra("city");
        setSupportActionBar(toolbar);
        final Boolean isInternet = Internet.networkIsavailable(MainActivity.this); //是否有网络
        //如果有网络
        if (isInternet) {
            if (city != null) {  //从管理城市活动，或添加城市活动启动到主活动会时进入这个if
                getData(NORMAL_TYPE, city);
            } else if (!default_city.equals("error")) {  //打开app的时候，如果有默认城市进入这个if
                getData(NORMAL_TYPE, default_city);
            } else if (default_city.equals("error")) {  //打开app的时候，如果没有设置默认城市进入这个if
                getData(NORMAL_TYPE, "重庆");
            }
        }
        //如果没有网络
        else {
            //得到缓存数据
            String buffer_city = sharedPreferences.getString("buffer_city", "error");
            String buffer_temp = sharedPreferences.getString("buffer_temp", "error");
            String buffer_update_time = sharedPreferences.getString("buffer_update_time", "error");
            String buffer_temp_now = sharedPreferences.getString("buffer_temp_now", "error");
            String buffer_temp_physical = sharedPreferences.getString("buffer_temp_physical", "error");
            String buffer_can_see = sharedPreferences.getString("buffer_can_see", "error");
            String buffer_weather = sharedPreferences.getString("buffer_weather", "error");
            isAir = sharedPreferences.getBoolean("buffer_isAir", false);
            mweather = buffer_weather;
            Message message_no_Internet = new Message();
            Message message_image = new Message();
            Message message_background = new Message();
            message_no_Internet.what = NO_INTERNET_TYPE;
            message_image.what = IMAGE_TYPE;
            message_background.what = BACKGROUND_TYPE;
            handler.sendMessage(message_no_Internet);
            handler.sendMessage(message_image);
            handler.sendMessage(message_background);
            tv_location.setText(buffer_city);
            tv_temp.setText(buffer_temp);
            tv_update_time.setText("数据更新于：" + buffer_update_time);
            tv_temp_now.setText("现在温度： " + buffer_temp_now + "°C");
            tv_temp_physical.setText("体感温度：" + buffer_temp_physical + "°C");
            tv_can_see.setText("能见度： " + buffer_can_see + "千米");
            tv_weather.setText(buffer_weather);
            Toast.makeText(MainActivity.this, "当前无网络，部分功能将无法使用", Toast.LENGTH_SHORT).show();
        }
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (isInternet) {
                    mlist.clear();
                    lifeStyle_list.clear();
                    air_list.clear();
                    if (city != null) {  //刷新时，此处的if同上
                        getData(SWIPE_TYPE, city);
                    } else if (default_city != null) {
                        getData(SWIPE_TYPE, default_city);
                    } else if (city == null && default_city == null) {
                        getData(SWIPE_TYPE, "重庆");
                    }
                } else {  //--如果没有网络
                    Message message = new Message();
                    message.what = NO_INTERNET_TYPE;
                    handler.sendMessage(message);
                }
            }
        });
        //注册广播
        receiver = new InternetReiceiver();
        Broadcast.register(MainActivity.this, receiver, "android.net.conn.CONNECTIVITY_CHANGE");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Broadcast.unRegister(MainActivity.this, receiver);
    }

    public void getData(final int count, final String city) {
        HttpUtil.sendHttpRequestForNow("https://free-api.heweather.net/s6/weather/now?location=" + city + "&key=69eb00f8b34e4c3cb3969e9a94416c70", new HttpCallBack() {
            @Override
            public void OnFinishNow(String location, String time, String weather, String weather_type, final String can_see, final String temp_now, String temp_physical, String status) {
                tv_location.setText(location);
                tv_temp.setText(temp_now);
                tv_update_time.setText("数据更新于：" + time);
                tv_weather.setText(weather);
                tv_can_see.setText("能见度：" + can_see + "千米");
                tv_temp_now.setText("现在温度：" + temp_now + "°C");
                tv_temp_physical.setText("体感温度：" + temp_physical + "°C");
                mweather = weather;
                editor.putString("buffer_city", location);
                editor.putString("buffer_temp", temp_now);
                editor.putString("buffer_update_time", time);
                editor.putString("buffer_weather", weather);
                editor.putString("buffer_can_see", can_see);
                editor.putString("buffer_temp_now", temp_now);
                editor.putString("buffer_temp_physical", temp_physical);
                editor.commit();
                //  ImageResource.SetImageResource(iv_weather_type, weather);  这句话会出错，不知道why
                final Message message1 = new Message();
                Message message2 = new Message();
                message1.what = IMAGE_TYPE;
                message2.what = BACKGROUND_TYPE;
                handler.sendMessage(message1); //设置天气的图片
                handler.sendMessage(message2); //设置背景图片
                //完成后发送天气预报的网络请求
                HttpUtil.sendHttpRequestForForecast("https://free-api.heweather.net/s6/weather/forecast?location=" + city + "&key=69eb00f8b34e4c3cb3969e9a94416c70", new HttpCallBack() {
                    @Override
                    public void OnFinishNow(String location, String time, String weather, String weather_type, String can_see, String temp_now, String temp_physical, String status) {
                    }

                    @Override
                    public void OnFinishForecast(List list) {
                        mlist = list;
                        int size = mlist.size();
                        if (size != 3) {  //从管理城市和设置默认城市活动进入这个活动时会进入这个if
                            for (int i = 0; i <= size - 4; i++) { //去掉list的前3个数据,相当于更新数据
                                mlist.remove(0);
                            }
                        }
                        //对数据缓存
                        Predict_Weather_day_1_3 one = mlist.get(0);
                        Predict_Weather_day_1_3 two = mlist.get(1);
                        Predict_Weather_day_1_3 three = mlist.get(2);
                        editor.putString("buffer_day_1_date", one.getDate());
                        editor.putString("buffer_day_1_weather", one.getWeather());
                        editor.putString("buffer_day_1_temp_low", one.getTemp_low());
                        editor.putString("buffer_day_1_temp_high", one.getTemp_high());
                        editor.putString("buffer_day_2_date", two.getDate());
                        editor.putString("buffer_day_2_weather", two.getWeather());
                        editor.putString("buffer_day_2_temp_low", two.getTemp_low());
                        editor.putString("buffer_day_2_temp_high", two.getTemp_high());
                        editor.putString("buffer_day_3_date", three.getDate());
                        editor.putString("buffer_day_3_weather", three.getWeather());
                        editor.putString("buffer_day_3_temp_low", three.getTemp_low());
                        editor.putString("buffer_day_3_temp_high", three.getTemp_high());
                        editor.commit();
                        //完成后发送空气质量的网络请求
                        HttpUtil.sendHttpRequestForAir("https://free-api.heweather.net/s6/air/now?location=" + city + "&key=69eb00f8b34e4c3cb3969e9a94416c70", new HttpCallBack() {
                            @Override
                            public void OnFinishNow(String location, String time, String weather, String weather_type, String can_see, String temp_now, String temp_physical, String status) {

                            }

                            @Override
                            public void OnFinishForecast(List list) {

                            }

                            @Override
                            public void OnFinishAir(List list, Boolean isAir) {
                                // 部分城市没有空气质量一栏
                                if (isAir) {  //如果有空气质量一栏
                                    air_list = list;
                                    int size = air_list.size();
                                    if (size != 1) {  //如果不是第一次进入主活动，会进入这个if，相当于更新数据
                                        for (int i = 0; i <= size - 2; i++) {
                                            air_list.remove(0);
                                        }
                                    }
                                    //---对数据缓存
                                    Air air = air_list.get(0);
                                    editor.putBoolean("buffer_isAir", true);
                                    editor.putString("buffer_PM10", air.getPM10());
                                    editor.putString("buffer_PM25", air.getPM25());
                                    editor.putString("buffer_NO2", air.getNO2());
                                    editor.putString("buffer_SO2", air.getSO2());
                                    editor.putString("buffer_O3", air.getO3());
                                    editor.putString("buffer_CO", air.getCO());
                                    editor.putString("buffer_air_count", air.getAir_count());
                                    editor.putString("buffer_air_quality", air.getAir_qulity());
                                    editor.commit();
                                } else {  //如果没有空气质量一栏
                                    air_list.clear();
                                }
                                ///   MyAdapter adapter = new MyAdapter(mlist, air_list, lifeStyle_list);
                                //    recyclerView.setLayoutManager(manager);
                                //    recyclerView.setAdapter(adapter);
                                //完成后发送生活指数的网络请求
                                HttpUtil.sendHttpRequestForLifeStyle("https://free-api.heweather.net/s6/weather/lifestyle?location=" + city + "&key=69eb00f8b34e4c3cb3969e9a94416c70", new HttpCallBack() {
                                    @Override
                                    public void OnFinishNow(String location, String time, String weather, String weather_type, String can_see, String temp_now, String temp_physical, String status) {

                                    }

                                    @Override
                                    public void OnFinishForecast(List list) {

                                    }

                                    @Override
                                    public void OnFinishAir(List list, Boolean isAir) {

                                    }

                                    @Override
                                    public void OnFinishLifeStyle(List list) {
                                        lifeStyle_list = list;
                                        int size = lifeStyle_list.size();
                                        if (size != 1) {   //此处if同上
                                            for (int i = 0; i <= size - 2; i++) {
                                                lifeStyle_list.remove(0);
                                            }
                                        }
                                        //---对数据缓存
                                        LifeStyle style = lifeStyle_list.get(0);
                                        editor.putString("buffer_comf_txt", style.getComf_txt());
                                        editor.putString("buffer_comf_brf", style.getComf_brf());
                                        editor.putString("buffer_air_brf", style.getAir_brf());
                                        editor.putString("buffer_air_txt", style.getAir_txt());
                                        editor.putString("buffer_cw_brf", style.getCw_brf());
                                        editor.putString("buffer_cw_txt", style.getCw_txt());
                                        editor.putString("buffer_drsg_brf", style.getDrsg_brf());
                                        editor.putString("buffer_drsg_txt", style.getDrsg_txt());
                                        editor.putString("buffer_flu_brf", style.getFlu_brf());
                                        editor.putString("buffer_flu_txt", style.getFlu_txt());
                                        editor.putString("buffer_sport_brf", style.getSport_brf());
                                        editor.putString("buffer_sport_txt", style.getSport_txt());
                                        editor.putString("buffer_trav_brf", style.getTrav_brf());
                                        editor.putString("buffer_trav_txt", style.getTrav_txt());
                                        editor.putString("buffer_uv_brf", style.getUv_brf());
                                        editor.putString("buffer_uv_txt", style.getUv_txt());
                                        editor.commit();
                                        //    MyAdapter adapter = new MyAdapter(mlist, air_list, lifeStyle_list);
                                        //    recyclerView.setLayoutManager(manager);
                                        //    recyclerView.setAdapter(adapter);
                                        Message message = new Message();
                                        if (count == NORMAL_TYPE) {
                                            message.what = NORMAL_TYPE;
                                        } else if (count == SWIPE_TYPE) {
                                            message.what = SWIPE_TYPE;
                                        }
                                        handler.sendMessage(message);
                                    }

                                    @Override
                                    public boolean OnError(Exception e) {
                                        return false;
                                    }
                                });


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

                    @Override
                    public void OnFinishAir(List list, Boolean isAir) {

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

            @Override
            public void OnFinishForecast(List list) {

            }

            @Override
            public void OnFinishAir(List list, Boolean isAir) {

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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.charge_city:
                Intent intent = new Intent(MainActivity.this, CityManagerActivity.class);
                startActivity(intent);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_activity, menu);
        return true;
    }
}
