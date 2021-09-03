package com.apps.ambulancegratis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.apps.ambulancegratis.R;
import com.apps.ambulancegratis.model.Driver;
import com.apps.ambulancegratis.model.User;

import org.w3c.dom.Text;

import java.util.List;

public class RVAdapterDriver extends RecyclerView.Adapter<RVAdapterDriver.ViewHolder> {
    private List<Driver> drivers;
    private Context context;

    public RVAdapterDriver(List<Driver> drivers, Context context) {
        this.drivers = drivers;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view_driver,parent,false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Driver driver = drivers.get(position);
        holder.tvfullname.setText(driver.getFullname());
        holder.tvplat.setText(driver.getNo_plat());
        holder.tvtelpon.setText(driver.getNo_telpon());
    }

    @Override
    public int getItemCount() {
        return  drivers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvfullname, tvplat, tvtelpon;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvfullname = itemView.findViewById(R.id.textnama);
            tvplat = itemView.findViewById(R.id.plat);
            tvtelpon = itemView.findViewById(R.id.telpon);
        }
    }
}
