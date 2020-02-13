package com.example.redhomework.someClass;

public class CityManager {
    String city;
    String temp;

    public String getTime() {
        return time;
    }

    String weather;
    String time;
    public void setCity(String city) {
        this.city = city;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public void setWeather(String weather) {
        this.weather = weather;
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


    public CityManager(String city,String temp,String weather,String time){
        this.city = city;
        this.temp = temp;
        this.weather = weather;
        this.time = time;
    }
    public CityManager(String city){
        this.city = city;
    }
}
