package com.example.redhomework.someClass;

public class CityManager {
    String city;
    String temp;
    String weather;
    String time;

    public CityManager(String city,String temp,String weather,String time){
        this.city = city;
        this.temp = temp;
        this.weather = weather;
        this.time = time;
    }

    public String getCity() {
        return city;
    }

    public String getTemp() {
        return temp;
    }

    public String getWeather() {
        return weather;
    }

}
