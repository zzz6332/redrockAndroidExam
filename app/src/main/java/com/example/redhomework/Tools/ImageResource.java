package com.example.redhomework.Tools;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.redhomework.R;

public class ImageResource {

    public static void weatherSetImageResource(ImageView imageView, String weather) {
        if (weather.equals("晴")) {
            imageView.setImageResource(R.drawable.hundred);
        } else if (weather.equals("多云")) {
            imageView.setImageResource(R.drawable.hundred_one);
        } else if (weather.equals("少云")) {
            imageView.setImageResource(R.drawable.hundred_two);
        } else if (weather.equals("阴")) {
            imageView.setImageResource(R.drawable.hundred_four);
        } else if (weather.equals("阵雨")) {
            imageView.setImageResource(R.drawable.three_hundred);
        } else if (weather.equals("强阵雨")) {
            imageView.setImageResource(R.drawable.three_hundred_one);
        } else if (weather.equals("雷阵雨")) {
            imageView.setImageResource(R.drawable.three_hundred_two);
        } else if (weather.equals("强雷阵雨")) {
            imageView.setImageResource(R.drawable.three_hundred_three);
        } else if (weather.equals("小雨")) {
            imageView.setImageResource(R.drawable.three_hundred_five);
        } else if (weather.equals("中雨")) {
            imageView.setImageResource(R.drawable.three_hundred_six);
        } else if (weather.equals("大雨")) {
            imageView.setImageResource(R.drawable.three_hundred_seven);
        } else if (weather.equals("小雪")) {
            imageView.setImageResource(R.drawable.four_hundred);
        } else if (weather.equals("中雪")) {
            imageView.setImageResource(R.drawable.four_hundred_one);
        } else if (weather.equals("大雪")) {
            imageView.setImageResource(R.drawable.four_hundred_two);
        } else if (weather.equals("雪")) {
            imageView.setImageResource(R.drawable.four_hundred_ninty_nine);
        }else if (weather.equals("雾")){
            imageView.setImageResource(R.drawable.five_hundred_one);
        }else if (weather.equals("霾")){
            imageView.setImageResource(R.drawable.five_hundred_two);
        }else {
            imageView.setImageResource(R.drawable.nine_hundred_ninty_nine);
        }
    }

    public static void mainSetBackground(View view, String type) {
        if (type.equals("晴")) {
            view.setBackgroundResource(R.drawable.background_sun_main);
        } else if (type.equals("多云") || type.equals("少云") || type.equals("阴")) {
            view.setBackgroundResource(R.drawable.background_cloundy_and_overcast_main);
        } else if (type.equals("小雨") || type.equals("雨") || type.equals("中雨") || type.equals("大雨")) {
            view.setBackgroundResource(R.drawable.background_rain_main);
        } else if (type.equals("小雪") || type.equals("中雪") || type.equals("大雪") || type.equals("雪")) {
            view.setBackgroundResource(R.drawable.background_snow_main);
        } else {
            view.setBackgroundResource(R.drawable.background_main);
        }
    }
}
