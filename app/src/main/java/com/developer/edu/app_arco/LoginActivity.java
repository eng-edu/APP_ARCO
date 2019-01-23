package com.developer.edu.app_arco;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.developer.edu.app_arco.conectionAPI.ConfigRetrofit;
import com.developer.edu.app_arco.controller.ControllerLogin;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

public class LoginActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.id_login_email);
        final EditText senha = findViewById(R.id.id_login_senha);
        Button entrar = findViewById(R.id.id_login_entrar);
        Button cadstrar = findViewById(R.id.id_login_cadastro);
        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String result = sharedPreferences.getString("ID", "");

        if (result != null && !result.equals("") && result != "") {
            startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            finish();
        }


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().length() > 0 && senha.getText().length() > 0) {
                    ControllerLogin.logar(LoginActivity.this, email.getText().toString(), senha.getText().toString());
                }

            }
        });

        cadstrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PreCadastroActivity.class));
                finish();
            }
        });



    }
}
