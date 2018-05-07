package com.iglesias.c.appgym.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.iglesias.c.appgym.Presenter.RegistrarPresenterImpl;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.Service.UsbService;
import com.iglesias.c.appgym.View.RegistrarView;

import java.lang.ref.WeakReference;

public class RegistraActivity extends AppCompatActivity implements RegistrarView {
    private ProgressDialog loading;
    private EditText edtUsr;
    private Button btnRegistrar;
    RegistrarPresenterImpl presenter;
    MyHandler myHandler;
    ImageButton imgBtnHuella;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);
        setupViews();
        setupLoading();
        presenter = new RegistrarPresenterImpl(this);
        myHandler = new MyHandler(this);
        LoginActivity.usbService.setHandler(myHandler);
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
                presenter.registrarUsuario(edtUsr.getText().toString());
            }
        });

        imgBtnHuella = findViewById(R.id.id_btn_huella);

        imgBtnHuella.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dato = "1";
                LoginActivity.usbService.write(dato.getBytes());
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
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistraActivity.this, R.style.myDialog);

        builder.setTitle(getResources().getString(R.string.str_menu_registrar));
        builder.setMessage(msj);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (finish) finish();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public Context getContextApp() {
        return getApplicationContext();
    }

    private class MyHandler extends Handler {
        private final WeakReference<RegistraActivity> mActivity;

        public MyHandler(RegistraActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbService.MESSAGE_FROM_SERIAL_PORT:
                    String data = (String) msg.obj;
                    presenter.receiveMsj(data);
                    break;
            }
        }
    }
}
