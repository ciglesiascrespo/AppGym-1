package com.iglesias.c.appgym.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.iglesias.c.appgym.Presenter.LoginPresenterImpl;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.RestApi.Model.InfoLogin;
import com.iglesias.c.appgym.View.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {
    public static final String EXTRA_NOMBRE = "NOMBRE";
    public static final String EXTRA_DOCUMENTO = "DOCUMENTO";
    public static final String EXTRA_DIAS = "DIAS";
    public static final String EXTRA_URL_IMAGEN = "URL";

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnx, btnIr;
    EditText edtNro;
    private ProgressDialog loading;
    LoginPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setupViews();
        setupLoading();

        presenter = new LoginPresenterImpl(this);
    }

    private void setupLoading() {
        loading = new ProgressDialog(this);
        loading.setCancelable(false);
        loading.setMessage(getResources().getString(R.string.str_loading_login));
    }

    void setupViews() {
        btn0 = findViewById(R.id.numero_0);
        btn1 = findViewById(R.id.numero_1);
        btn2 = findViewById(R.id.numero_2);
        btn3 = findViewById(R.id.numero_3);
        btn4 = findViewById(R.id.numero_4);
        btn5 = findViewById(R.id.numero_5);
        btn6 = findViewById(R.id.numero_6);
        btn7 = findViewById(R.id.numero_7);
        btn8 = findViewById(R.id.numero_8);
        btn9 = findViewById(R.id.numero_9);

        btnx = findViewById(R.id.borrar);
        btnIr = findViewById(R.id.sign_in);

        edtNro = findViewById(R.id.cedula);
    }

    public void pressNumber(View v) {
        String nro = edtNro.getText().toString();
        if (nro.length() <= 9) {
            edtNro.setText(nro + ((Button) v).getText().toString().trim());
        }
    }

    public void deleteChar(View v) {
        String nro = edtNro.getText().toString();
        if (nro.length() > 0) {
            edtNro.setText(nro.substring(0, nro.length() - 1));
        }
    }

    public void onClickValidateUser(View v) {
        presenter.validateUser(edtNro.getText().toString());
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
    public void goToMainActivity(InfoLogin infoLogin) {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        i.putExtra(EXTRA_NOMBRE, infoLogin.getNombre());
        i.putExtra(EXTRA_DIAS, infoLogin.getDias());
        i.putExtra(EXTRA_DOCUMENTO, infoLogin.getNroDocumento());
        i.putExtra(EXTRA_URL_IMAGEN, infoLogin.getUrlImg());

        startActivity(i);

    }

    @Override
    public void showErrorLoginDialog(String msj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

        builder.setTitle(getResources().getString(R.string.str_title_alert_error_login));
        builder.setMessage(msj);
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
