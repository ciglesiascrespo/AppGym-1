package com.iglesias.c.appgym.Activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iglesias.c.appgym.Pojo.DeviceInfo;
import com.iglesias.c.appgym.Presenter.RegistrarPresenterImpl;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.Ui.BaseActivity;
import com.iglesias.c.appgym.View.RegistrarView;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.DeviceCallback;
import static com.iglesias.c.appgym.Activity.LoginActivity.EXTRA_DEVICE_MAC;

public class RegistraActivity extends BaseActivity implements RegistrarView {

    private final String TAG = getClass().getName();
    private ProgressDialog loading;
    AlertDialog dialog;
    private EditText edtUsr;
    private Button btnRegistrar, imgBtnHuella;
    RegistrarPresenterImpl presenter;

    String arryId[] = {"", "", ""};
    int indexId = 0;

    String id = "", mac;
    Boolean flagHuella = false;

    Bluetooth bt;
    private DeviceInfo deviceInfo = new DeviceInfo("", "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);
        setupViews();
        setupLoading();
        presenter = new RegistrarPresenterImpl(this);
        mac = getIntent().getStringExtra(EXTRA_DEVICE_MAC);
        setupBt();
    }

    private void setupBt() {
        bt = new Bluetooth(this);
        bt.onStart();
        bt.enable();
        bt.connectToAddress(mac);
        bt.setDeviceCallback(new DeviceCallback() {
            @Override public void onDeviceConnected(BluetoothDevice device) {
                bt.send("start");
                try { Thread.sleep(1000); } catch(Exception ignored) { }
                bt.send("start");
            }
            @Override public void onDeviceDisconnected(BluetoothDevice device, String message) {}
            @Override public void onMessage(String message) {
                Log.d("Prueba", message);
                presenter.receiveMsj(message);
            }
            @Override public void onError(String message) {}
            @Override public void onConnectError(BluetoothDevice device, String message) {}
        });
    }

    private void setupLoading() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getResources().getString(R.string.str_loading_registrar));
    }

    void setupViews() {
        edtUsr = findViewById(R.id.id_edt_nro_registrar);
        btnRegistrar = findViewById(R.id.id_btn_registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.registrarUsuario(edtUsr.getText().toString(), id, flagHuella);
            }
        });

        imgBtnHuella = findViewById(R.id.id_btn_huella);

        imgBtnHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanMode();

            }
        });
    }

    private void scanMode() {
        String dato = "enroll";
        bt.send(dato);
    }

    @Override
    public void showLoading() {
        if (!loading.isShowing()) loading.show();
    }

    @Override
    public void hideLoading() {
        if (loading.isShowing()) loading.dismiss();
    }

    @Override
    public void showErrorLoginDialog(String msj, final boolean finish) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistraActivity.this, R.style.myDialog);

        builder.setTitle(getResources().getString(R.string.str_menu_registrar));
        builder.setMessage(msj);

        if (finish) {
            builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (finish) finish();
                }
            });
        }


        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public Context getContextApp() {
        return getApplicationContext();
    }

    @Override
    public void setId(String id) {
        this.id = id;

        //HuellaTrigger - Descomentar

        if (indexId < arryId.length) {
            arryId[indexId] = id;
            indexId++;
        }
        compareId();

    }

    private void compareId() {
        byte[] tempId, antId;
        for (int i = 1; i < arryId.length; i++) {
            tempId = arryId[i].getBytes();
            antId = arryId[i - 1].getBytes();

            if(tempId.length > 0 && antId.length > 0){
                flagHuella = true;
            }else {
                flagHuella = false;
            }
            flagHuella = true;
        }

        if(indexId == arryId.length){
            showErrorLoginDialog("Número de intentos alcanzados",true);
        }else{
            //scanMode();
        }
    }

    @Override
    public void setFlagHuella(Boolean flag) {
        this.flagHuella = flag;
    }

    @Override
    public void activarModoScaner() {
        String dato = "2";
        //bt.sendMessage(dato);
    }
}
