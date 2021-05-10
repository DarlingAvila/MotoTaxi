package com.darling.mototaxi;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dmax.dialog.SpotsDialog;

public class RegisterActivity extends AppCompatActivity {

    //para diferenciar si es conducto o no
    SharedPreferences mPref;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    //instanciamos las vistas
    Button mButtonRegister;
    TextInputEditText mTextInputEmail;
    TextInputEditText mTextInputName;
    TextInputEditText mTextInputPassword;


    Toolbar mToolbar;
    AlertDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //llamamos al toolbar
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Registrar usuario");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //instanciamos el mauth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //instanciamos el dialog
        mDialog = new SpotsDialog.Builder().setContext(RegisterActivity.this).setMessage("Espere un momento").build();

        mPref = getApplicationContext().getSharedPreferences(  "typeUser", MODE_PRIVATE);

       // Toast.makeText(this,"El valor que selecciono fue" + selectUser, Toast.LENGTH_SHORT).show();

        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    void registerUser() {
        final String name = mTextInputName.getText().toString();
        final String email = mTextInputEmail.getText().toString();
        final String password = mTextInputPassword.getText().toString();

        if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 6) {
                mDialog.show();
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        mDialog.hide();
                        if (task.isSuccessful()) {
                            String id = mAuth.getCurrentUser().getUid();
                            saveUser(id, name, email);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "No se pudo registrar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    void registerUser(){
    final String name = mTextInputName.getText().toString();
    final String email = mTextInputEmail.getText().toString();
    final String password = mTextInputPassword.getText().toString();

    if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()){
        if (password.length() >=6){
            mDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                    mDialog.hide();
                    if(task.isSuccessful()){
                        String id = mAuth.getCurrentUser().getUid();
                        saveUser(name, email);
                    }else {

                        Toast.makeText(RegisterActivity.this, "No se puede registar el usuario", Toast.LENGTH_SHORT).show();

                    }
                }
            });

        }
        else {

            Toast.makeText(this, "contraseña debe tener 6 caracteres", Toast.LENGTH_SHORT).show();
        }
    }
    else {

        Toast.makeText(this, "ingrese todos los campos", Toast.LENGTH_SHORT).show();
    }

    } */

    private void saveUser(String id, String name, String email) {

        String selectedUser= mPref.getString( "user", " ");
        User user = new User();
        user.setEmail(email);
        user.setName(name);

        if(selectedUser.equals("driver")){

            mDatabase.child("User").child("driver").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this,"Fallo el registro",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
        else  if(selectedUser.equals("client")){
            mDatabase.child("User").child("client").child(id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Registro exitoso",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this,"Fallo el registro",Toast.LENGTH_SHORT).show();
                    }


                }
            });
        }


    }



}