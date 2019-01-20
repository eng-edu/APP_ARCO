package com.developer.edu.app_arco;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button perfil = findViewById(R.id.id_menu_perfil);
        Button sair = findViewById(R.id.id_menu_sair);

        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, PerfilActivity.class));
            }
        });
        sair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuActivity.this, LoginActivity.class));
                final SharedPreferences.Editor editor = getSharedPreferences("MY_PREF", MODE_PRIVATE).edit();
                editor.clear();
                editor.apply();
                finish();
            }
        });



    }
}
