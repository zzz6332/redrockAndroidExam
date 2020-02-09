package com.example.redhomework.Class;

public class Predict_Weather_day_1_3 {
    String date;
    String temp_low;
    String temp_high;
    String weather;

    public Predict_Weather_day_1_3(String date,String temp_low,String temp_high,String weather){
        this.setDate(date);
        this.setTemp_low(temp_low);
        this.setTemp_high(temp_high);
        this.setWeather(weather);
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void setTemp_low(String temp_low) {
        this.temp_low = temp_low;
    }

    public String getDate() {
        return date;
    }

    public String getTemp_low() {
        return temp_low;
    }

    public String getTemp_high() {
        return temp_high;
    }

    public String getWeather() {
        return weather;
    }

    public void setTemp_high(String temp_high) {
        this.temp_high = temp_high;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }


}
