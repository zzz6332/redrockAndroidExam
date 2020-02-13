package com.example.redhomework.recyclerviewAdapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redhomework.someClass.Air;
import com.example.redhomework.someClass.LifeStyle;
import com.example.redhomework.someClass.PredictWeather;
import com.example.redhomework.R;
import com.example.redhomework.tools.ImageResource;
import com.example.redhomework.tools.Internet;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter {
    private final static int predict_type = 0;
    private final static int air_type = 1;
    private final static int life_type = 2;
    private int conut = 0;
    private Boolean isInternet;
    private Boolean isAir;
    private Context context;
    private List<PredictWeather> predict_weather_list = new ArrayList();
    private List<Air> air_list = new ArrayList<>();
    private List<LifeStyle> lifeStyle_list = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String one_date;
    private String one_weater;
    private String one_temp_low;
    private String one_temp_high;
    private String two_date;
    private String two_weather;
    private String two_temp_low;
    private String two_temp_high;
    private String three_date;
    private String three_weather;
    private String three_temp_low;
    private String three_temp_high;
    private String NO2;
    private String SO2;
    private String O3;
    private String CO;
    private String PM10;
    private String PM25;
    private String air_count;
    private String air_qulity;
    private String comf_txt;
    private String comf_brf;
    private String drsg_txt;
    private String drsg_brf;
    private String flu_txt;
    private String flu_brf;
    private String uv_brf;
    private String uv_txt;
    private String cw_txt;
    private String cw_brf;
    private String trav_txt;
    private String trav_brf;
    private String sport_txt;
    private String sport_brf;
    private String air_brf;
    private String air_txt;

    public MyAdapter(Context context, boolean isInternet, List mlist, List mlist_air, List mlist_life) {
        this.context = context;
        this.isInternet = isInternet;
        predict_weather_list = mlist;
        air_list = mlist_air;
        lifeStyle_list = mlist_life;
    }

    public MyAdapter(Context context, Boolean isInternet, Boolean isAir) {
        this.context = context;
        this.isAir = isAir;
        this.isInternet = isInternet;
    }

    public class Predict_ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_date_day_1;
        TextView tv_temp_low_day_1;
        TextView tv_temp_high_day_1;
        ImageView im_weather_day_1;
        TextView tv_date_day_2;
        TextView tv_temp_low_day_2;
        TextView tv_temp_high_day_2;
        ImageView im_weather_day_2;
        TextView tv_date_day_3;
        TextView tv_temp_low_day_3;
        TextView tv_temp_high_day_3;
        ImageView im_weather_day_3;

        public Predict_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date_day_1 = itemView.findViewById(R.id.tv_predict_date_day_1);
            im_weather_day_1 = itemView.findViewById(R.id.im_predict_weather_day_1);
            tv_temp_high_day_1 = itemView.findViewById(R.id.tv_predict_temp_high_day_1);
            tv_temp_low_day_1 = itemView.findViewById(R.id.tv_predict_temp_low_day_1);
            tv_date_day_2 = itemView.findViewById(R.id.tv_predict_date_day_2);
            im_weather_day_2 = itemView.findViewById(R.id.im_predict_weather_day_2);
            tv_temp_high_day_2 = itemView.findViewById(R.id.tv_predict_temp_high_day_2);
            tv_temp_low_day_2 = itemView.findViewById(R.id.tv_predict_temp_low_day_2);
            tv_date_day_3 = itemView.findViewById(R.id.tv_predict_date_day_3);
            im_weather_day_3 = itemView.findViewById(R.id.im_predict_weather_day_3);
            tv_temp_high_day_3 = itemView.findViewById(R.id.tv_predict_temp_high_day_3);
            tv_temp_low_day_3 = itemView.findViewById(R.id.tv_predict_temp_low_day_3);
        }

    }

    public class Air_ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_air_count;
        TextView tv_PM10;
        TextView tv_PM25;
        TextView tv_NO2;
        TextView tv_SO2;
        TextView tv_O3;
        TextView tv_CO;
        TextView tv_air_type;

        public Air_ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_air_count = itemView.findViewById(R.id.tv_air_conut);
            tv_air_type = itemView.findViewById(R.id.tv_air_type);
            tv_PM10 = itemView.findViewById(R.id.tv_PM10);
            tv_PM25 = itemView.findViewById(R.id.tv_PM25);
            tv_NO2 = itemView.findViewById(R.id.tv_NO2);
            tv_SO2 = itemView.findViewById(R.id.tv_SO2);
            tv_O3 = itemView.findViewById(R.id.tv_O3);
            tv_CO = itemView.findViewById(R.id.tv_CO);
        }
    }

    public class LifeStyle_ViewHolder extends RecyclerView.ViewHolder {
        TextView comf_txt;
        TextView comf_brf;
        TextView drsg_txt;
        TextView drsg_brf;
        TextView flu_txt;
        TextView flu_brf;
        TextView uv_brf;
        TextView uv_txt;
        TextView cw_txt;
        TextView cw_brf;
        TextView sport_txt;
        TextView sport_brf;
        TextView trav_txt;
        TextView trav_brf;
        TextView air_brf;
        TextView air_txt;

        public LifeStyle_ViewHolder(@NonNull View itemView) {
            super(itemView);
            comf_txt = itemView.findViewById(R.id.comf_txt);
            comf_brf = itemView.findViewById(R.id.comf_brf);
            drsg_txt = itemView.findViewById(R.id.drsg_txt);
            ;
            drsg_brf = itemView.findViewById(R.id.drsg_brf);
            flu_txt = itemView.findViewById(R.id.flu_txt);
            flu_brf = itemView.findViewById(R.id.flu_brf);
            uv_brf = itemView.findViewById(R.id.uv_brf);
            uv_txt = itemView.findViewById(R.id.uv_txt);
            cw_txt = itemView.findViewById(R.id.cw_txt);
            cw_brf = itemView.findViewById(R.id.cw_brf);
            sport_brf = itemView.findViewById(R.id.sport_brf);
            sport_txt = itemView.findViewById(R.id.sport_txt);
            trav_txt = itemView.findViewById(R.id.trav_txt);
            trav_brf = itemView.findViewById(R.id.trav_brf);
            air_brf = itemView.findViewById(R.id.air_brf);
            air_txt = itemView.findViewById(R.id.air_txt);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        conut = viewType;
        isInternet = Internet.networkIsavailable(context); //是否有网络
        if (!isInternet) { //如果没有网络
            sharedPreferences = context.getSharedPreferences((context.getString(R.string.file_name)), Context.MODE_PRIVATE);
            editor = context.getSharedPreferences((context.getString(R.string.file_name)), Context.MODE_PRIVATE).edit();
            one_date = sharedPreferences.getString("buffer_day_1_date", "error");
            one_weater = sharedPreferences.getString("buffer_day_1_weather", "error");
            one_temp_low = sharedPreferences.getString("buffer_day_1_temp_low", "error");
            one_temp_high = sharedPreferences.getString("buffer_day_1_temp_high", "error");
            two_date = sharedPreferences.getString("buffer_day_2_date", "error");
            two_weather = sharedPreferences.getString("buffer_day_2_weather", "error");
            two_temp_low = sharedPreferences.getString("buffer_day_2_temp_low", "error");
            two_temp_high = sharedPreferences.getString("buffer_day_2_temp_high", "error");
            three_date = sharedPreferences.getString("buffer_day_3_date", "error");
            three_weather = sharedPreferences.getString("buffer_day_3_weather", "error");
            three_temp_low = sharedPreferences.getString("buffer_day_3_temp_low", "error");
            three_temp_high = sharedPreferences.getString("buffer_day_3_temp_high", "error");
            PM10 = sharedPreferences.getString("buffer_PM10", "error");
            PM25 = sharedPreferences.getString("buffer_PM25", "error");
            NO2 = sharedPreferences.getString("buffer_NO2", "error");
            SO2 = sharedPreferences.getString("buffer_SO2", "error");
            O3 = sharedPreferences.getString("buffer_O3", "error");
            CO = sharedPreferences.getString("buffer_CO", "error");
            air_count = sharedPreferences.getString("buffer_air_count", "error");
            air_qulity = sharedPreferences.getString("buffer_air_quality", "error");
            comf_txt = sharedPreferences.getString("buffer_comf_txt", "error");
            comf_brf = sharedPreferences.getString("buffer_comf_brf", "error");
            drsg_txt = sharedPreferences.getString("buffer_drsg_txt", "error");
            drsg_brf = sharedPreferences.getString("buffer_drsg_brf", "error");
            flu_txt = sharedPreferences.getString("buffer_flu_txt", "error");
            flu_brf = sharedPreferences.getString("buffer_flu_brf", "error");
            uv_brf = sharedPreferences.getString("buffer_uv_brf", "error");
            uv_txt = sharedPreferences.getString("buffer_uv_txt", "error");
            cw_txt = sharedPreferences.getString("buffer_cw_txt", "error");
            cw_brf = sharedPreferences.getString("buffer_cw_txt", "error");
            trav_txt = sharedPreferences.getString("buffer_trav_txt", "error");
            trav_brf = sharedPreferences.getString("buffer_trav_brf", "error");
            sport_txt = sharedPreferences.getString("buffer_sport_txt", "error");
            sport_brf = sharedPreferences.getString("buffer_sport_brf", "error");
            air_brf = sharedPreferences.getString("buffer_air_brf", "error");
            air_txt = sharedPreferences.getString("buffer_air_txt", "error");
        }
        if (viewType == predict_type) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.recyclerview_predict, parent, false);
            Predict_ViewHolder predict_viewHolder = new Predict_ViewHolder(view);
            return predict_viewHolder;
        } else if (viewType == air_type) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.recyclerview_air, parent, false);
            Air_ViewHolder air_viewHolder = new Air_ViewHolder(view);
            return air_viewHolder;
        } else if (viewType == life_type) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.recyclerview_lifestyle, parent, false);
            LifeStyle_ViewHolder lifeStyle_viewHolder = new LifeStyle_ViewHolder(view);
            return lifeStyle_viewHolder;
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (isInternet) {  //如果有网络
            if (holder instanceof Predict_ViewHolder) {
                if (predict_weather_list.size() != 0) {
                    PredictWeather predict_weather_day_1 = predict_weather_list.get(0);
                    PredictWeather predict_weather_day_2 = predict_weather_list.get(1);
                    PredictWeather predict_weather_day_3 = predict_weather_list.get(2);
                    ((Predict_ViewHolder) holder).tv_date_day_1.setText(predict_weather_day_1.getDate().substring(6, 7) + "月" + predict_weather_day_1.getDate().substring(8, 10) + "日" + "今天");
                    ((Predict_ViewHolder) holder).tv_temp_low_day_1.setText(predict_weather_day_1.getTemp_low() + "°C");
                    ((Predict_ViewHolder) holder).tv_temp_high_day_1.setText("/" + predict_weather_day_1.getTemp_high() + "°C");
                    ImageResource.weatherSetImageResource(((Predict_ViewHolder) holder).im_weather_day_1, predict_weather_day_1.getWeather());
                    ((Predict_ViewHolder) holder).tv_date_day_2.setText(predict_weather_day_2.getDate().substring(6, 7) + "月" + predict_weather_day_2.getDate().substring(8, 10) + "日" + "明天");
                    ((Predict_ViewHolder) holder).tv_temp_low_day_2.setText(predict_weather_day_2.getTemp_low() + "°C");
                    ((Predict_ViewHolder) holder).tv_temp_high_day_2.setText("/" + predict_weather_day_2.getTemp_high() + "°C");
                    ImageResource.weatherSetImageResource(((Predict_ViewHolder) holder).im_weather_day_2, predict_weather_day_2.getWeather());
                    ((Predict_ViewHolder) holder).tv_date_day_3.setText(predict_weather_day_3.getDate().substring(6, 7) + "月" + predict_weather_day_3.getDate().substring(8, 10) + "日" + "后天");
                    ((Predict_ViewHolder) holder).tv_temp_low_day_3.setText(predict_weather_day_3.getTemp_low() + "°C");
                    ((Predict_ViewHolder) holder).tv_temp_high_day_3.setText("/" + predict_weather_day_3.getTemp_high() + "°C");
                    ImageResource.weatherSetImageResource(((Predict_ViewHolder) holder).im_weather_day_3, predict_weather_day_3.getWeather());
                }
            } else if (holder instanceof Air_ViewHolder) {
                if (air_list.size() != 0) {
                    Air air = air_list.get(0);
                    ((Air_ViewHolder) holder).tv_air_count.setText(air.getAir_count());
                    ((Air_ViewHolder) holder).tv_PM10.setText(air.getPM10());
                    ((Air_ViewHolder) holder).tv_PM25.setText(air.getPM25());
                    ((Air_ViewHolder) holder).tv_NO2.setText(air.getNO2());
                    ((Air_ViewHolder) holder).tv_SO2.setText(air.getSO2());
                    ((Air_ViewHolder) holder).tv_O3.setText(air.getO3());
                    ((Air_ViewHolder) holder).tv_CO.setText(air.getCO());
                    ((Air_ViewHolder) holder).tv_air_type.setText(air.getAir_qulity());
                }

            } else if (holder instanceof LifeStyle_ViewHolder) {
                if (lifeStyle_list.size() != 0) {
                    LifeStyle lifeStyle = lifeStyle_list.get(0);
                    ((LifeStyle_ViewHolder) holder).comf_txt.setText(lifeStyle.getComf_txt());
                    ((LifeStyle_ViewHolder) holder).comf_brf.setText(lifeStyle.getComf_brf() + "。");
                    ((LifeStyle_ViewHolder) holder).drsg_txt.setText(lifeStyle.getDrsg_txt());
                    ((LifeStyle_ViewHolder) holder).drsg_brf.setText(lifeStyle.getDrsg_brf() + "。");
                    ((LifeStyle_ViewHolder) holder).flu_txt.setText(lifeStyle.getFlu_txt());
                    ((LifeStyle_ViewHolder) holder).flu_brf.setText(lifeStyle.getFlu_brf() + "。");
                    ((LifeStyle_ViewHolder) holder).uv_txt.setText(lifeStyle.getUv_txt());
                    ((LifeStyle_ViewHolder) holder).uv_brf.setText(lifeStyle.getUv_brf() + "。");
                    ((LifeStyle_ViewHolder) holder).cw_brf.setText(lifeStyle.getCw_brf() + "。");
                    ((LifeStyle_ViewHolder) holder).cw_txt.setText(lifeStyle.getComf_txt());
                    ((LifeStyle_ViewHolder) holder).sport_brf.setText(lifeStyle.getSport_brf() + "。");
                    ((LifeStyle_ViewHolder) holder).sport_txt.setText(lifeStyle.getSport_txt());
                    ((LifeStyle_ViewHolder) holder).trav_brf.setText(lifeStyle.getTrav_brf() + "。");
                    ((LifeStyle_ViewHolder) holder).trav_txt.setText(lifeStyle.getTrav_txt());
                    ((LifeStyle_ViewHolder) holder).air_brf.setText(lifeStyle.getAir_brf() + "。");
                    ((LifeStyle_ViewHolder) holder).air_txt.setText(lifeStyle.getAir_txt());
                }
            }
        } else {  //如果没有网络
            if (holder instanceof Predict_ViewHolder) {
                ((Predict_ViewHolder) holder).tv_date_day_1.setText(one_date.substring(6, 7) + "月" + one_date.substring(8, 10) + "日" + "今天");
                ((Predict_ViewHolder) holder).tv_temp_low_day_1.setText(one_temp_low + "°C");
                ((Predict_ViewHolder) holder).tv_temp_high_day_1.setText("/" + one_temp_high + "°C");
                ImageResource.weatherSetImageResource(((Predict_ViewHolder) holder).im_weather_day_1, one_weater);
                ((Predict_ViewHolder) holder).tv_date_day_2.setText(two_date.substring(6, 7) + "月" + two_date.substring(8, 10) + "日" + "明天");
                ((Predict_ViewHolder) holder).tv_temp_low_day_2.setText(two_temp_low + "°C");
                ((Predict_ViewHolder) holder).tv_temp_high_day_2.setText("/" + two_temp_high + "°C");
                ImageResource.weatherSetImageResource(((Predict_ViewHolder) holder).im_weather_day_2, two_weather);
                ((Predict_ViewHolder) holder).tv_date_day_3.setText(three_date.substring(6, 7) + "月" + three_date.substring(8, 10) + "日" + "后天");
                ((Predict_ViewHolder) holder).tv_temp_low_day_3.setText(three_temp_low + "°C");
                ((Predict_ViewHolder) holder).tv_temp_high_day_3.setText("/" + three_temp_high + "°C");
                ImageResource.weatherSetImageResource(((Predict_ViewHolder) holder).im_weather_day_3, three_weather);

            } else if (holder instanceof Air_ViewHolder) {
                ((Air_ViewHolder) holder).tv_air_count.setText(air_count);
                ((Air_ViewHolder) holder).tv_PM10.setText(PM10);
                ((Air_ViewHolder) holder).tv_PM25.setText(PM25);
                ((Air_ViewHolder) holder).tv_NO2.setText(NO2);
                ((Air_ViewHolder) holder).tv_SO2.setText(SO2);
                ((Air_ViewHolder) holder).tv_O3.setText(O3);
                ((Air_ViewHolder) holder).tv_CO.setText(CO);
                ((Air_ViewHolder) holder).tv_air_type.setText(air_qulity);
            } else if (holder instanceof LifeStyle_ViewHolder) {
                ((LifeStyle_ViewHolder) holder).comf_txt.setText(comf_txt);
                ((LifeStyle_ViewHolder) holder).comf_brf.setText(comf_brf);
                ((LifeStyle_ViewHolder) holder).drsg_txt.setText(drsg_txt);
                ((LifeStyle_ViewHolder) holder).drsg_brf.setText(drsg_brf);
                ((LifeStyle_ViewHolder) holder).flu_txt.setText(flu_txt);
                ((LifeStyle_ViewHolder) holder).flu_brf.setText(flu_brf);
                ((LifeStyle_ViewHolder) holder).uv_txt.setText(uv_txt);
                ((LifeStyle_ViewHolder) holder).uv_brf.setText(uv_brf);
                ((LifeStyle_ViewHolder) holder).cw_brf.setText(cw_brf);
                ((LifeStyle_ViewHolder) holder).cw_txt.setText(cw_txt);
                ((LifeStyle_ViewHolder) holder).sport_brf.setText(sport_brf);
                ((LifeStyle_ViewHolder) holder).sport_txt.setText(sport_txt);
                ((LifeStyle_ViewHolder) holder).trav_brf.setText(trav_brf);
                ((LifeStyle_ViewHolder) holder).trav_txt.setText(trav_txt);
                ((LifeStyle_ViewHolder) holder).air_brf.setText(air_brf);
                ((LifeStyle_ViewHolder) holder).air_txt.setText(air_txt);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return predict_type;
        } else if (position == 1 && conut == 3) {
            return air_type;
        } else if (position == 1 && conut == 2) {
            return life_type;
        } else if (position == 2) {
            return life_type;
        }
        return 1;
    }

    @Override
    public int getItemCount() {
        if (isInternet) { //如果有网络
            if (predict_weather_list.size() != 0 && air_list.size() == 0 && lifeStyle_list.size() == 0) {
                conut = 1;
                return 1;
            } else if (predict_weather_list.size() == 0 && air_list.size() != 0 && lifeStyle_list.size() == 0) {
                conut = 1;
                return 1;
            } else if (predict_weather_list.size() == 0 && air_list.size() == 0 && lifeStyle_list.size() != 0) {
                conut = 1;
                return 1;
            } else if (predict_weather_list.size() != 0 && air_list.size() != 0 && lifeStyle_list.size() == 0) {
                conut = 2;
                return 2;
            } else if (predict_weather_list.size() != 0 && air_list.size() == 0 && lifeStyle_list.size() != 0) {
                conut = 2;
                return 2;
            } else if (predict_weather_list.size() == 0 && air_list.size() != 0 && lifeStyle_list.size() != 0) {
                conut = 2;
                return 2;
            } else if (predict_weather_list.size() != 0 && air_list.size() != 0 && lifeStyle_list.size() != 0) {
                conut = 3;
                return 3;
            }
        } else { //如果没有网络
            if (isAir) { //如果有空气质量
                conut = 3;
                return 3;
            } else {
                conut = 2;
                return 2;
            }
        }

        return 1;
    }
}
