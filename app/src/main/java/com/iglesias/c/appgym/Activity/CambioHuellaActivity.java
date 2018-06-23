package com.iglesias.c.appgym.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iglesias.c.appgym.Presenter.ActualizaPresenterImpl;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.Service.Bluetooth;
import com.iglesias.c.appgym.View.RegistrarView;

public class CambioHuellaActivity extends AppCompatActivity implements RegistrarView{
    private final String TAG = getClass().getName();
    private ProgressDialog loading;
    AlertDialog dialog;
    private EditText edtUsr;
    private Button btnRegistrar, imgBtnHuella;
    ActualizaPresenterImpl presenter;


    String id = "";
    Boolean flagHuella = false;

    Bluetooth bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_huella);
        setupViews();
        setupLoading();
        presenter = new ActualizaPresenterImpl(this);

        setupBt();
    }

    private void setupBt() {
        bt = Bluetooth.getInstance(this, mHandler);
    }

    private void setupLoading() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getResources().getString(R.string.str_loading_actualizar));
    }

    void setupViews() {
        edtUsr = findViewById(R.id.id_edt_nro_registrar);
        btnRegistrar = findViewById(R.id.id_btn_registrar);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.actualizaUsuario(edtUsr.getText().toString(), id, flagHuella);
            }
        });

        imgBtnHuella = findViewById(R.id.id_btn_huella);

        imgBtnHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dato = "1";
                bt.sendMessage(dato);

            }
        });
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
        AlertDialog.Builder builder = new AlertDialog.Builder(CambioHuellaActivity.this, R.style.myDialog);

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
    }

    @Override
    public void setFlagHuella(Boolean flag) {
        this.flagHuella = flag;
    }

    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:

                    Log.e(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.e(TAG, "MESSAGE_WRITE: " + String.valueOf(msg.arg1));

                    break;
                case Bluetooth.MESSAGE_READ:

                    Log.e(TAG, "MESSAGE_READ: " + msg.obj);
                    String msj = String.valueOf(msg.obj);
                    presenter.receiveMsj(msj);
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
}
