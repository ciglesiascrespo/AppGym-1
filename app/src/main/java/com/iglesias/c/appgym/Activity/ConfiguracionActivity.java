package com.iglesias.c.appgym.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.iglesias.c.appgym.Pojo.DeviceInfo;
import com.iglesias.c.appgym.Presenter.ConfigPresenterImpl;
import com.iglesias.c.appgym.R;
import com.iglesias.c.appgym.Ui.BaseActivity;
import com.iglesias.c.appgym.View.ConfigView;

public class ConfiguracionActivity extends BaseActivity implements ConfigView {

    public static final String EXTRA_NOMBRE_DISPOSITIVO = "nombre";
    public static final String EXTRA_MAC_DISPOSITIVO = "mac";
    public static final int REQUEST_SELECCION_DISPOSITIVO = 1;
    private CardView cardViewDispositivo;
    private CardView cardViewSettings;
    private TextView txtNombreDispositivo, txtMacDispositivo;
    private ConfigPresenterImpl presenter;

    private DeviceInfo deviceInfo = new DeviceInfo("", "");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);

        setupViews();
        presenter = new ConfigPresenterImpl(this);
        presenter.getInfoDevice();
    }

    private void setupViews() {
        getSupportActionBar().setTitle(getResources().getString(R.string.str_menu_configuracion));

        txtMacDispositivo = findViewById(R.id.id_txt_mac_dispositivo_conexion);
        txtNombreDispositivo = findViewById(R.id.id_txt_nombre_dispositivo_conexion);

        cardViewDispositivo = findViewById(R.id.id_cardview_dispoditivo);
        cardViewSettings = findViewById(R.id.id_cardview_settings);

        cardViewSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_SETTINGS), 0);
            }
        });

        cardViewDispositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfiguracionActivity.this, SelectBtDActivity.class);
                startActivityForResult(i, REQUEST_SELECCION_DISPOSITIVO);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.configuracion_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_menu_guardar_configuracion:
                presenter.guardarInfoDevice(deviceInfo);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setNombreDispositivoConexion() {
        txtNombreDispositivo.setText(deviceInfo.getNombre());
    }

    @Override
    public void setMacDispositivoConexion() {
        txtMacDispositivo.setText(deviceInfo.getMac());
    }

    @Override
    public void setDispositivoConexion(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
    @Override
    public void onGuardar(){
        Toast.makeText(this,"Guardado con éxito",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_SELECCION_DISPOSITIVO) {
                deviceInfo.setMac(data.getStringExtra(EXTRA_MAC_DISPOSITIVO));
                deviceInfo.setNombre(data.getStringExtra(EXTRA_NOMBRE_DISPOSITIVO));

                setMacDispositivoConexion();
                setNombreDispositivoConexion();
            }
        }
    }
}
