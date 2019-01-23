package com.developer.edu.app_arco;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.developer.edu.app_arco.controller.Controllerperfil;

public class PerfilActivity extends AppCompatActivity {

    int click = 0;
    public static final int IMAGEM_INTERNA = 12;
    ImageView fotoPerfil;
    String pathfoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        pedirPermisssaoParaAcessarArmazenamento();

        TextView pontos = findViewById(R.id.id_perfil_pontos);
        fotoPerfil = findViewById(R.id.Id_perfil_image_perfil);
        final EditText nome = findViewById(R.id.id_nome_perfil);
        final EditText idade = findViewById(R.id.id_idade_perfil);
        final RadioButton sexoM = findViewById(R.id.id_perfil_sexo_m);
        final RadioButton sexoF = findViewById(R.id.id_perfil_sexo_f);
        final EditText escolaridade = findViewById(R.id.id_perfil_escolaridade);
        final EditText email = findViewById(R.id.id_email_perfil);
        final Button alterar = findViewById(R.id.id_perfil_alterar);

        if (getIntent().getStringExtra("meu_perfil") == null || !getIntent().getStringExtra("meu_perfil").equals("S")) {
            alterar.setVisibility(View.INVISIBLE);
        }


        //BUSCA NO SERVER O PERFIL .........
        Controllerperfil.buscarUsuario(PerfilActivity.this, pontos, fotoPerfil, nome, idade, sexoM, sexoF, escolaridade, email);

        fotoPerfil.setEnabled(false);
        nome.setEnabled(false);
        idade.setEnabled(false);
        sexoM.setEnabled(false);
        sexoF.setEnabled(false);
        escolaridade.setEnabled(false);
        email.setEnabled(false);


        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (click == 0) {
                    click = 1;

                    //alterar libera pra edicao
                    fotoPerfil.setEnabled(true);
                    nome.setEnabled(true);
                    idade.setEnabled(true);
                    sexoM.setEnabled(true);
                    sexoF.setEnabled(true);
                    email.setEnabled(false);
                    escolaridade.setEnabled(true);
                    alterar.setText("SALVAR");

                } else if (click == 1) {

                    //salva as alterações no server...


                    //bloqueia as edições
                    fotoPerfil.setEnabled(false);
                    nome.setEnabled(false);
                    idade.setEnabled(false);
                    sexoM.setEnabled(false);
                    sexoF.setEnabled(false);
                    escolaridade.setEnabled(false);
                    email.setEnabled(false);
                    alterar.setText("EDITAR");

                    String sexo = "";
                    if (sexoM.isChecked()) {
                        sexo = "M";
                    } else if (sexoF.isChecked()) {
                        sexo = "F";
                    }

                    //.........
                    Controllerperfil.alterarUsuario(PerfilActivity.this, nome, idade, sexo, pathfoto, escolaridade, fotoPerfil);


                    click = 0;
                }
            }
        });


       // fotoPerfil = (ImageView) findViewById(R.id.Id_perfil_image_perfil);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarImg(v);
            }
        });

    }

    public void pegarImg(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem: "), IMAGEM_INTERNA);
    }

    public void pedirPermisssaoParaAcessarArmazenamento() {
        if (ActivityCompat.checkSelfPermission(PerfilActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //e caso ainda não tenha dado, ele solicita...
            ActivityCompat.requestPermissions((Activity) PerfilActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == IMAGEM_INTERNA && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

           // ImageView imageView = (ImageView) findViewById(R.id.Id_cadastro_image_perfil);
            fotoPerfil.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            pathfoto = picturePath;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(PerfilActivity.this, MenuActivity.class));
        finish();
    }
}
