package com.iglesias.c.appgym.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iglesias.c.appgym.Pojo.DeviceInfo;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.View.SelectBtView;

import java.util.List;

/**
 * Created by Ciglesias on 2/06/2018.
 */

public class RecyclerDevicesAdapter extends RecyclerView.Adapter<RecyclerDevicesAdapter.DevicewHolder> {
    List<DeviceInfo> list;
    Context context;
    SelectBtView selectBtView;

    public RecyclerDevicesAdapter(Context context, List<DeviceInfo> list, SelectBtView selectBtView) {
        this.context = context;
        this.list = list;
        this.selectBtView = selectBtView;
    }

    public void clear() {
        list.clear();
    }

    public void setListDevices(List<DeviceInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addDevice(DeviceInfo info) {
        list.add(info);
        notifyDataSetChanged();
    }

    @Override
    public DevicewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_device, parent, false);
        return new DevicewHolder(view);
    }

    @Override
    public void onBindViewHolder(DevicewHolder holder, int position) {
        final DeviceInfo deviceInfo = list.get(position);
        holder.txtMac.setText(deviceInfo.getMac());
        holder.txtNombre.setText(deviceInfo.getNombre());
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectBtView.onItemClick(deviceInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class DevicewHolder extends RecyclerView.ViewHolder {
        private TextView txtNombre, txtMac;


        private View v;

        public DevicewHolder(View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.id_txt_nombre_dispositivo_conexion);
            txtMac = itemView.findViewById(R.id.id_txt_mac_dispositivo_conexion);


            v = itemView;
        }
    }
}
