package com.iglesias.c.appgym.Activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.iglesias.c.appgym.Adapter.RecyclerDevicesAdapter;
import com.iglesias.c.appgym.Pojo.DeviceInfo;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.Service.Bluetooth;
import com.iglesias.c.appgym.Ui.BaseActivity;
import com.iglesias.c.appgym.View.SelectBtView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SelectBtDActivity extends BaseActivity implements SelectBtView{
    Bluetooth bt;
    BluetoothAdapter btAdapter;
    RecyclerView recyclerViewVinculados, recyclerViewDisponibles;
    RecyclerDevicesAdapter adapterVinculados, adapterDisponibles;
    private final String TAG = getClass().getName();
    private static final int REQUEST_ENABLE_BT = 1;
    private TextView txtNoVinculados, txtNoDisponibles;
    ProgressBar progressBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bt_d);
        setupViews();
        setupBt();

    }

    private void setupViews() {

        getSupportActionBar().setTitle(getResources().getString(R.string.str_seleccion_dispositivo));
        LinearLayoutManager managerVinculados = new LinearLayoutManager(this);
        LinearLayoutManager managerDisponibles = new LinearLayoutManager(this);
        List<DeviceInfo> listDevices = new ArrayList<>();

        progressBusqueda = findViewById(R.id.id_progress_disponibles);
        txtNoDisponibles = findViewById(R.id.id_txt_no_dispositivo_disponibles);
        txtNoVinculados = findViewById(R.id.id_txt_no_dispositivo_vinculado);

        recyclerViewDisponibles = findViewById(R.id.id_recycler_disponibles);
        recyclerViewVinculados = findViewById(R.id.id_recycler_vinculados);

        recyclerViewDisponibles.setLayoutManager(managerVinculados);
        recyclerViewVinculados.setLayoutManager(managerDisponibles);

        adapterDisponibles = new RecyclerDevicesAdapter(this, listDevices, this);
        adapterVinculados = new RecyclerDevicesAdapter(this, listDevices, this);

        recyclerViewVinculados.setAdapter(adapterVinculados);
        recyclerViewDisponibles.setAdapter(adapterDisponibles);


        hideListDisponibles();
        hideListVinculados();
    }

    private void setupBt() {
        bt = Bluetooth.getInstance(this, mHandler);
        btAdapter = bt.getBtAdapter();
        if (!btAdapter.isEnabled()) {
            Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
        } else {
            setListVinculados();
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dispositivos_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.id_menu_buscar:
                buscarDispositivos();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buscarDispositivos() {
        if (btAdapter.isDiscovering()) {

            Toast.makeText(this, "Busqueda en progreso", Toast.LENGTH_SHORT).show();
        } else {
            adapterDisponibles.clear();
            hideListDisponibles();
            progressBusqueda.setVisibility(View.VISIBLE);
            btAdapter.startDiscovery();
            registerReceiver(bReceiver, new IntentFilter(BluetoothDevice.ACTION_FOUND));
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
       LocalBroadcastManager.getInstance(this).unregisterReceiver(bReceiver);

    }


    final BroadcastReceiver bReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                adapterDisponibles.addDevice(new DeviceInfo(device.getName(), device.getAddress()));

                if (!btAdapter.isDiscovering()) {
                    progressBusqueda.setVisibility(View.GONE);
                }

                if (adapterDisponibles.getItemCount() > 0) {
                    showListDisponibles();
                } else {
                    hideListDisponibles();
                }
            }else if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                progressBusqueda.setVisibility(View.GONE);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_ENABLE_BT) {
            if (btAdapter.isEnabled()) {
                Toast.makeText(this, "Bluetooth encendido", Toast.LENGTH_SHORT).show();
                setListVinculados();
            } else {
                Toast.makeText(this, "Se requiere encender el bluetooth", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void setListVinculados() {
        adapterVinculados.clear();
        Set<BluetoothDevice> pairedSet = btAdapter.getBondedDevices();

        for (BluetoothDevice device : pairedSet) {
            adapterVinculados.addDevice(new DeviceInfo(device.getName(), device.getAddress()));
        }


        if (adapterVinculados.getItemCount() > 0) {
            showListVinculados();
        } else {
            hideListVinculados();
        }

    }

    private void showListVinculados() {
        txtNoVinculados.setVisibility(View.GONE);
        recyclerViewVinculados.setVisibility(View.VISIBLE);
    }

    private void hideListVinculados() {
        txtNoVinculados.setVisibility(View.VISIBLE);
        recyclerViewVinculados.setVisibility(View.GONE);
    }


    private void showListDisponibles() {
        txtNoDisponibles.setVisibility(View.GONE);
        recyclerViewDisponibles.setVisibility(View.VISIBLE);
    }

    private void hideListDisponibles() {
        txtNoDisponibles.setVisibility(View.VISIBLE);
        recyclerViewDisponibles.setVisibility(View.GONE);
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:
                    Log.d(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.d(TAG, "MESSAGE_WRITE ");
                    break;
                case Bluetooth.MESSAGE_READ:
                    Log.d(TAG, "MESSAGE_READ ");
                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "MESSAGE_DEVICE_NAME " + msg);
                    break;
                case Bluetooth.MESSAGE_TOAST:
                    Log.d(TAG, "MESSAGE_TOAST " + msg);
                    break;
            }
        }
    };

    @Override
    public void onItemClick(DeviceInfo deviceInfo) {
        Intent i = new Intent();
        i.putExtra(ConfiguracionActivity.EXTRA_NOMBRE_DISPOSITIVO, deviceInfo.getNombre());
        i.putExtra(ConfiguracionActivity.EXTRA_MAC_DISPOSITIVO, deviceInfo.getMac());

        setResult(RESULT_OK, i);
        finish();
    }
}
