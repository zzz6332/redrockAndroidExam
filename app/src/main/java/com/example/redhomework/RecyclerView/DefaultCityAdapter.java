package com.example.redhomework.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.redhomework.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DefaultCityAdapter extends RecyclerView.Adapter {
    Boolean isBind;
    Context context;
    String default_city;
    List<String> list_city;
    Map<Integer, Boolean> map;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int count_check;
    private int count_uncheck;

    public DefaultCityAdapter(Context context, List list) {
        this.context = context;
        this.list_city = list;
    }

    public class DefalutCityViewHolder extends RecyclerView.ViewHolder {
        TextView tv_city;
        CheckBox checkBox;

        public DefalutCityViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_city = itemView.findViewById(R.id.tv_default_city);
            checkBox = itemView.findViewById(R.id.checkbox_default);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        map = new HashMap<>();
        editor = context.getSharedPreferences((context.getString(R.string.file_name)),Context.MODE_PRIVATE).edit();
        sharedPreferences = context.getSharedPreferences((context.getString(R.string.file_name)),Context.MODE_PRIVATE);
        default_city = sharedPreferences.getString("default_city","error");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_default,parent,false);
        DefalutCityViewHolder holder = new DefalutCityViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
      final DefalutCityViewHolder defalutCityViewHolder = (DefalutCityViewHolder)holder;
      defalutCityViewHolder.tv_city.setText(list_city.get(position));
      defalutCityViewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
          @Override
          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if (isChecked){
                  count_check++; //勾选一次count就加1
                  map.clear();
                  map.put(position,true);
                  Log.d("eeeeeee","勾选执行" + position);
                  if (default_city.equals("error")){ //如果没有设置默认城市
                      editor.putInt("default_city_position",position+1);
                      editor.commit();
                  }
                  else {  //如果设置了默认城市
                      editor.putInt("default_city_position_second",position+1);
                      editor.putBoolean("isSecond",true);
                      editor.commit();
                  }
                  editor.putBoolean("isdefault",false); //设置是否点击了按钮为false
                  editor.putBoolean("isChoose",true); //设置是否勾选为true
                  editor.commit();
              }
              else {  //如果取消了勾选
                  count_uncheck++; //取消勾选一次,count_uncheck加一
                  Log.d("eeeeeee","取消勾选执行" + position);
                  map.remove(position);
                  if (count_uncheck == count_check - 1){ //如果勾选了一个后，再次勾选了另外一个城市就会满足这个条件
                      editor.putBoolean("isChoose",true); //设置是否勾选为true
                  }
                 else if (count_uncheck == count_check){ //如果勾选一个后，取消勾选当前城市会满足这个条件
                      if (default_city.equals("error")){ //如果没有设置默认城市
                          editor.remove("default_city_position");
                          editor.commit();
                      }
                      else { //否则如果设置了默认城市
                          editor.remove("default_city_position_second");
                          editor.commit();
                      }
                      editor.putBoolean("isChoose",false);
                  }

              }
              if (!isBind){
                  notifyDataSetChanged();
              }

          }
      });
      isBind = true;
      if (map != null && map.containsKey(position)){
            defalutCityViewHolder.checkBox.setChecked(true);
        }
        else {
            defalutCityViewHolder.checkBox.setChecked(false);
        }
        isBind = false;
    }

    @Override
    public int getItemCount() {
        return list_city.size();
    }
}
