package com.developer.edu.app_arco;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class PremioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_premio);

        ImageView fotoPremio = findViewById(R.id.id_premio_fotopremio);

        Picasso.get().load(URL_BASE + "/IMG/premio.jpg").into(fotoPremio);

    }
}
