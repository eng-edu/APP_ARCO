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
import android.widget.Toast;

import com.developer.edu.app_arco.controller.ControllerCadastro;

public class CadastroActivity extends AppCompatActivity {

    public static final int IMAGEM_INTERNA = 12;
    ImageView fotoPerfil;
    String pathfoto = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        pedirPermisssaoParaAcessarArmazenamento();

        final EditText nome = findViewById(R.id.id_nome_cadastro);
        final EditText idade = findViewById(R.id.id_idade_cadastro);
        final RadioButton sexoM = findViewById(R.id.id_cadastro_sexo_m);
        final RadioButton sexoF = findViewById(R.id.id_cadstro_sexo_f);
        final EditText escolaridade = findViewById(R.id.id_cadastro_escolaridade);
        final EditText email = findViewById(R.id.id_email_cadastro);
        final EditText senha = findViewById(R.id.id_senha_castro);
        final Button cadastrar = findViewById(R.id.id_cadastro_cadastrar);
        final String tipo = getIntent().getStringExtra("tipo");

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sexo = "";

                if (sexoM.isChecked()) {
                    sexo = "M";
                } else if (sexoF.isChecked()) {
                    sexo = "F";
                }

                if (nome.getText().length() > 0
                        && idade.getText().length() > 0
                        && escolaridade.getText().length() > 0
                        && email.getText().length() > 0
                        && senha.getText().length() > 0) {

                    ControllerCadastro.cadastrar(CadastroActivity.this, nome.getText().toString(),
                            idade.getText().toString(),
                            sexo,
                            escolaridade.getText().toString(),
                            email.getText().toString(),
                            senha.getText().toString(),
                            pathfoto,
                            tipo);

                }else {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        fotoPerfil = (ImageView) findViewById(R.id.Id_cadastro_image_perfil);
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
        if (ActivityCompat.checkSelfPermission(CadastroActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //e caso ainda não tenha dado, ele solicita...
            ActivityCompat.requestPermissions((Activity) CadastroActivity.this,
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

            ImageView imageView = (ImageView) findViewById(R.id.Id_cadastro_image_perfil);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            pathfoto = picturePath;

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
        finish();
    }
}
