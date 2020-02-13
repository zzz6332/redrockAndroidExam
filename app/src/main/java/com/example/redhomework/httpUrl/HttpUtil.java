package com.example.redhomework.httpUrl;

import android.util.Log;

import com.example.redhomework.someClass.Air;
import com.example.redhomework.someClass.LifeStyle;
import com.example.redhomework.someClass.PredictWeather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpUtil {
    static List<PredictWeather> mlist = new ArrayList<PredictWeather>();
    static List<Air> air_list = new ArrayList<>();
    static List<LifeStyle> lifeStyle_list = new ArrayList();
    private static String location;
    private static String update_time;
    private static String weather;
    private static String weather_type;
    private static String can_see;
    private static String temp_now;
    private static String temp_physical;

    public static void sendHttpRequestForNow(final String address, final HttpCallBack httpCallBackListener) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.connect();
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String status = jsonObject1.getString("status");
                    if (status.equals("ok")) {
                        JSONObject basic = jsonObject1.getJSONObject("basic");
                        JSONObject update = jsonObject1.getJSONObject("update");
                        JSONObject now = jsonObject1.getJSONObject("now");
                        location = basic.getString("location");
                        update_time = update.getString("loc");
                        weather = now.getString("cond_txt");
                        weather_type = now.getString("cond_txt");
                        can_see = now.getString("vis");
                        temp_now = now.getString("tmp");
                        temp_physical = now.getString("fl");
                    }
                    httpCallBackListener.OnFinishNow(location, update_time, weather, weather_type, can_see, temp_now, temp_physical, status);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void sendHttpRequestForForecast(final String address, final HttpCallBack httpCallBack) {

        new Thread(new Runnable() {
            HttpURLConnection connection1 = null;

            @Override
            public void run() {
                try {
                    URL url = null;
                    url = new URL(address);
                    connection1 = (HttpURLConnection) url.openConnection();
                    connection1.setRequestMethod("GET");
                    connection1.setConnectTimeout(8000);
                    connection1.setReadTimeout(8000);
                    connection1.connect();
                    InputStream in = connection1.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    JSONArray daily_forceast = jsonObject1.getJSONArray("daily_forecast");
                    JSONObject day_1 = daily_forceast.getJSONObject(0);
                    JSONObject day_2 = daily_forceast.getJSONObject(1);
                    JSONObject day_3 = daily_forceast.getJSONObject(2);
                    Log.d("eee", daily_forceast.toString());
                    String date_day_1 = day_1.getString("date");
                    String temp_low_day_1 = day_1.getString("tmp_min");
                    String temp_high_day_1 = day_1.getString("tmp_max");
                    String weather_day_1 = day_1.getString("cond_txt_d");
                    PredictWeather first = new PredictWeather(date_day_1, temp_low_day_1, temp_high_day_1, weather_day_1);
                    String date_day_2 = day_2.getString("date");
                    String temp_low_day_2 = day_2.getString("tmp_min");
                    String temp_high_day_2 = day_2.getString("tmp_max");
                    String weather_day_2 = day_2.getString("cond_txt_d");
                    PredictWeather second = new PredictWeather(date_day_2, temp_low_day_2, temp_high_day_2, weather_day_2);
                    String date_day_3 = day_3.getString("date");
                    String temp_low_day_3 = day_3.getString("tmp_min");
                    String temp_high_day_3 = day_3.getString("tmp_max");
                    String weather_day_3 = day_3.getString("cond_txt_d");
                    PredictWeather third = new PredictWeather(date_day_3, temp_low_day_3, temp_high_day_3, weather_day_3);
                    mlist.add(first);
                    mlist.add(second);
                    mlist.add(third);
                    httpCallBack.OnFinishForecast(mlist);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void sendHttpRequestForAir(final String address, final HttpCallBack httpCallBack) {
        new Thread(new Runnable() {
            HttpURLConnection connection1 = null;

            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(address);
                    connection1 = (HttpURLConnection) url.openConnection();
                    connection1.setRequestMethod("GET");
                    connection1.setConnectTimeout(8000);
                    connection1.setReadTimeout(8000);
                    connection1.connect();
                    InputStream in = connection1.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String status = jsonObject1.getString("status");
                    Boolean isAir = false;
                    if (status.equals("ok")) {
                        isAir = true;
                        JSONObject jsonObject2 = jsonObject1.getJSONObject("air_now_city");
                        String air_count = jsonObject2.getString("aqi");
                        String PM10 = jsonObject2.getString("pm10");
                        String PM25 = jsonObject2.getString("pm25");
                        String NO2 = jsonObject2.getString("no2");
                        String SO2 = jsonObject2.getString("so2");
                        String O3 = jsonObject2.getString("o3");
                        String CO = jsonObject2.getString("co");
                        String qlty = jsonObject2.getString("qlty");
                        Air air = new Air(PM10, PM25, NO2, SO2, O3, CO, air_count, qlty);
                        air_list.add(air);
                    }
                    httpCallBack.OnFinishAir(air_list,isAir);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public static void sendHttpRequestForLifeStyle(final String address, final HttpCallBack httpCallBack) {
        new Thread(new Runnable() {
            HttpURLConnection connection1 = null;

            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(address);
                    connection1 = (HttpURLConnection) url.openConnection();
                    connection1.setRequestMethod("GET");
                    connection1.setConnectTimeout(8000);
                    connection1.setReadTimeout(8000);
                    connection1.connect();
                    InputStream in = connection1.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    JSONObject jsonObject = new JSONObject(response.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    String status = jsonObject1.getString("status");
                    if (status.equals("ok")) {
                        JSONArray life = jsonObject1.getJSONArray("lifestyle");
                        JSONObject comf = life.getJSONObject(0);
                        JSONObject drsg = life.getJSONObject(1);
                        JSONObject flu = life.getJSONObject(2);
                        JSONObject sport = life.getJSONObject(3);
                        JSONObject travel = life.getJSONObject(4);
                        JSONObject cw = life.getJSONObject(5);
                        JSONObject uv = life.getJSONObject(6);
                        JSONObject air = life.getJSONObject(7);
                        String comf_txt = comf.getString("txt");
                        String comf_brf = comf.getString("brf");
                        String drsg_txt = drsg.getString("txt");
                        String drsg_brf = drsg.getString("brf");
                        String flu_txt = flu.getString("txt");
                        String flu_brf = flu.getString("brf");
                        String uv_txt = uv.getString("txt");
                        String uv_brf = uv.getString("brf");
                        String sport_txt = sport.getString("txt");
                        String sport_brf = sport.getString("brf");
                        String trav_txt = travel.getString("txt");
                        String trav_brf = travel.getString("brf");
                        String cw_txt = cw.getString("txt");
                        String cw_brf = cw.getString("brf");
                        String air_txt = air.getString("txt");
                        String air_brf = air.getString("brf");
                        LifeStyle comf_style = new LifeStyle(comf_txt, comf_brf, drsg_txt, drsg_brf, flu_txt, flu_brf, uv_txt, uv_brf, cw_txt, cw_brf, trav_txt, trav_brf, sport_txt, sport_brf, air_brf, air_txt);
                        lifeStyle_list.add(comf_style);
                    }
                    httpCallBack.OnFinishLifeStyle(lifeStyle_list);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
