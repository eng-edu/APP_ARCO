package com.developer.edu.app_arco;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.developer.edu.app_arco.controller.ControllerLogin;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.id_login_email);
        final EditText senha = findViewById(R.id.id_login_senha);
        Button entrar = findViewById(R.id.id_login_entrar);
        Button cadstrar = findViewById(R.id.id_login_cadastro);


        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email.getText().length() > 0 && senha.getText().length() > 0) {
                    ControllerLogin.logar(getApplicationContext(), email.getText().toString(), senha.getText().toString());
                }

            }
        });

        cadstrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PreCadastroActivity.class));
                finish();
            }
        });



    }
}
