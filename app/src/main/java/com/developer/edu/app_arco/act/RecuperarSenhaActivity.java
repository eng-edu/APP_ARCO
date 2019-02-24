package com.developer.edu.app_arco.act;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.util.Util;
import com.developer.edu.app_arco.controller.ControllerRecuperarSenha;
import com.developer.edu.app_arco.model.MaskEditUtil;

public class RecuperarSenhaActivity extends AppCompatActivity {

    boolean datanascvalido = false;
    boolean emailvalido = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_senha);

        final EditText email = findViewById(R.id.id_recuperar_senha_email);
        final EditText data_nasc = findViewById(R.id.id_recuperar_senha_datanasc);
        final Button recuperar = findViewById(R.id.id_recuperar_recuperar);
        data_nasc.addTextChangedListener(MaskEditUtil.mask(data_nasc, MaskEditUtil.FORMAT_DATE));
        data_nasc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.data(data_nasc.getText().toString()) && data_nasc.getText().length() == 10) {
                    data_nasc.setTextColor(Color.BLACK);
                    datanascvalido = true;
                } else {
                    data_nasc.setTextColor(Color.RED);
                    datanascvalido = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.validaEmail(email.getText().toString())) {
                    email.setTextColor(Color.BLACK);
                    emailvalido = true;
                } else {
                    email.setTextColor(Color.RED);
                    emailvalido = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!emailvalido) {
                    Toast.makeText(RecuperarSenhaActivity.this, "Email inválido!", Toast.LENGTH_SHORT).show();
                } else if (!datanascvalido) {
                    Toast.makeText(RecuperarSenhaActivity.this, "Data de nascimento inválida!", Toast.LENGTH_SHORT).show();
                } else {
                    ControllerRecuperarSenha.recuperarSenha(RecuperarSenhaActivity.this, email.getText().toString(), data_nasc.getText().toString());
                }
            }
        });

    }
}
