package com.example.redhomework.recyclerviewAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redhomework.someClass.CityManager;
import com.example.redhomework.MainActivity;
import com.example.redhomework.R;
import com.example.redhomework.tools.ActivityCollector;

import java.util.ArrayList;
import java.util.List;

public class CityAdapter extends RecyclerView.Adapter  {
    int count = 0;
    SharedPreferences.Editor editor;
    List<CityManager> city_list;
    Context mcontext;
    public CityAdapter(Context context,List list)
    {    mcontext = context;
         city_list = new ArrayList<>();
         city_list = list;
    }


    public class CityViewHolder extends RecyclerView.ViewHolder {
        TextView tv_city;
        TextView tv_temp;
        TextView tv_weather;
        View view;
        public CityViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_city = itemView.findViewById(R.id.city_manager_tv_city);
            tv_temp = itemView.findViewById(R.id.city_manager_tv_temp);
            tv_weather = itemView.findViewById(R.id.city_manager_tv_weather);
            view = itemView;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         editor = mcontext.getSharedPreferences((mcontext.getString(R.string.file_name)),Context.MODE_PRIVATE).edit();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_city_manager,parent,false);
        CityViewHolder cityViewHolder = new CityViewHolder(view);
        return cityViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (city_list.size()!=0){
            count++;
            final CityManager manager = city_list.get(position);
            editor.putString("city" + (position+1),manager.getCity());//将当前城市列表写入
          //  editor.putInt("count",count);
            editor.commit();
            CityViewHolder cityViewHolder = (CityViewHolder)holder;
             cityViewHolder.tv_city.setText(manager.getCity());
            cityViewHolder.tv_temp.setText(manager.getTemp());
            cityViewHolder.tv_weather.setText(manager.getWeather());
            cityViewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mcontext,MainActivity.class);
                    String city = manager.getCity();
                    intent.putExtra("city",city);
                    mcontext.startActivity(intent);
                    ActivityCollector.finishAll();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return city_list.size();
    }

}

