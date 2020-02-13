package com.example.redhomework.recyclerviewAdapter;

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

public class DeleteAdapter extends RecyclerView.Adapter {
   private static int delete_number = 0;
    private Map<Integer, Boolean> map;
    private List<String> city_list;
    private Context context;
    private SharedPreferences.Editor editor;
    private SharedPreferences sharedPreferences;
    public DeleteAdapter(Context context, List list) {
        this.context = context;
        this.city_list = list;
        map = new HashMap<>();
    }

    public class DeleteViewHolder extends RecyclerView.ViewHolder {
        CheckBox box;
        TextView tv_city;

        public DeleteViewHolder(@NonNull View itemView) {
            super(itemView);
            box = itemView.findViewById(R.id.delete_checkbox);
            tv_city = itemView.findViewById(R.id.tv_delete_city);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_delete, parent, false);
        DeleteViewHolder holder = new DeleteViewHolder(view);
        editor = context.getSharedPreferences((context.getString(R.string.file_name)), Context.MODE_PRIVATE).edit();
        sharedPreferences = context.getSharedPreferences((context.getString(R.string.file_name)),Context.MODE_PRIVATE);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final DeleteViewHolder viewHolder = (DeleteViewHolder) holder;
        String city = city_list.get(position);
        ((DeleteViewHolder) holder).tv_city.setText(city);
        ((DeleteViewHolder) holder).box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    delete_number = sharedPreferences.getInt("delete_number",0); ;
                    //如果被选中,delete_number就加1
                    delete_number++;//删除了几个城市的计数器
                    map.put(position, true);
                    viewHolder.tv_city.setTextColor(context.getResources().getColor(R.color.colorRed));
                    Log.d("执行!!", "delete_number为： " + delete_number);
                    editor.putInt("delete_city_" + delete_number, (position+1));
                    editor.putInt("delete_number", delete_number);
                    editor.commit();
                } else {
                    map.remove(position);
                    viewHolder.tv_city.setTextColor(context.getResources().getColor(R.color.colorBlack));
                    editor.remove("delete_city_" + delete_number);
                    delete_number--;
                    editor.putInt("delete_number", delete_number);
                    editor.commit();

                }
            }
        });
        if (map != null && map.containsKey(position)) {
            ((DeleteViewHolder) holder).box.setChecked(true);
        } else {
            viewHolder.box.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return city_list.size();
    }
}
