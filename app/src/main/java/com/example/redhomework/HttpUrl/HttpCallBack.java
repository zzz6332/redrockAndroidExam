package com.example.redhomework.HttpUrl;

import java.util.List;

public interface HttpCallBack {
    void OnFinishNow(String location, String time, String weather, String weather_type, String can_see, String temp_now, String temp_physical, String status);

    void OnFinishForecast(List list);

    void OnFinishAir(List list,Boolean isAir);

    void OnFinishLifeStyle(List list);

    boolean OnError(Exception e);
}
