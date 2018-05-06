package com.iglesias.c.appgym.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iglesias.c.appgym.Presenter.RegistrarPresenterImpl;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.View.RegistrarView;

public class RegistraActivity extends AppCompatActivity implements RegistrarView {
    private ProgressDialog loading;
    private EditText edtUsr;
    private Button btnRegistrar;
    RegistrarPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registra);
        setupViews();
        setupLoading();
        presenter = new RegistrarPresenterImpl(this);
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
    public void showErrorLoginDialog(String msj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(RegistraActivity.this, R.style.myDialog);

        builder.setTitle(getResources().getString(R.string.str_menu_registrar));
        builder.setMessage(msj);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    @Override
    public Context getContextApp() {
        return getApplicationContext();
    }
}
