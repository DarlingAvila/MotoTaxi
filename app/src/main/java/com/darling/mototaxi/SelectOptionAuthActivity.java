package com.darling.mototaxi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOptionAuthActivity extends AppCompatActivity {

    Toolbar mToolbar;
    //botones para login y registro
    Button mButtonGoToLogin;
    Button mButtonGoToRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_option_auth);


        mToolbar = findViewById(R.id.toolbar);
        //recibe el setsupp el mtoolbar que se instancio
        setSupportActionBar(mToolbar);
        //titulodeltoolbar
        getSupportActionBar().setTitle("Seleccione una opcion");
        //si el toolbar tendra boton para regresar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //instanciamos los botones
        mButtonGoToLogin = findViewById(R.id.btnGoToLogin);
        //le ponemos el onclicklistener
        mButtonGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin(); //creamos este metodo
            }
        });
        //instanciamos el buton y ponemos el onclic
        mButtonGoToRegister = findViewById(R.id.btnGoToRegister);

        mButtonGoToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
    }

    public  void  goToLogin(){
        Intent intent = new Intent(SelectOptionAuthActivity.this, LoginActivity.class);
    }
    public  void  goToRegister(){
        Intent intent = new Intent(SelectOptionAuthActivity.this, RegisterActivity.class);
    }
}