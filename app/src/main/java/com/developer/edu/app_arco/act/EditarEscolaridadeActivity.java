package com.developer.edu.app_arco.act;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.bd.DB_escolaridade;
import com.developer.edu.app_arco.controller.ControllerPerfil;
import com.developer.edu.app_arco.model.Escolaridade;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class EditarEscolaridadeActivity extends AppCompatActivity {

    public static final int IMAGEM_INTERNA = 12;
    private ImageView fotoPerfil;
    private String pathfoto = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_escolaridade);

        pedirPermisssaoParaAcessarArmazenamento();


        final String ID_USUARIO = getIntent().getStringExtra("ID_USUARIO");
        final String MEU_PERFIL = getIntent().getStringExtra("MEU_PERFIL");

        ImageView anexo = findViewById(R.id.id_editar_escolaridade_anexo);
        TextView tituloimg = findViewById(R.id.id_editar_escolaridade_titiulo_imagem);
        final TextView instituicao = findViewById(R.id.id_editar_escolaridade_instituicao);
        final TextView area = findViewById(R.id.id_editar_escolaridade_area);
        final TextView ano = findViewById(R.id.id_editar_escolaridade_ano);
        final TextView grupos = findViewById(R.id.id_editar_escolaridade_grupos);
        final TextView descricao = findViewById(R.id.id_editar_escolaridade_descricao);
        Button salvar = findViewById(R.id.id_editar_escolaridade_salvar);
        TextView procurarimagem = findViewById(R.id.id_editar_escolaridade_procurarimagem);


        procurarimagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarImgGalery(v);
            }
        });


        Picasso.get().load(URL_BASE + "/IMG/" + ID_USUARIO + "_escolaridade.jpg").memoryPolicy(MemoryPolicy.NO_CACHE).into(anexo);


        if (new DB_escolaridade(EditarEscolaridadeActivity.this).listarTodos().size() > 0) {
            Escolaridade escolaridade = new DB_escolaridade(EditarEscolaridadeActivity.this).buscar(ID_USUARIO);

            instituicao.setText(escolaridade.getInstituicao());
            area.setText(escolaridade.getArea());
            ano.setText(escolaridade.getAno());
            grupos.setText(escolaridade.getGrupos());
            descricao.setText(escolaridade.getDescricao());
        }


        SharedPreferences sharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE);
        final String TIPO = sharedPreferences.getString("TIPO", "");

        if (TIPO.equals("1")) {
            tituloimg.setText("Anexe uma imagem que comprove a sua escolaridade! (não obrigatório)");
            instituicao.setHint("Qual o nome da instituição\nque você se formou?");
            area.setHint("Qual a sua área de estudo?");
            ano.setHint("Em que ano você se formou?");
            grupos.setHint("Você partitcipa de grupos de\nestudo, pesquisa, dança, música, artes ou etc?");
            descricao.setHint("Descreva um pouco mais\nsobre sua formação aqui...");
        } else if (TIPO.equals("2")) {
            tituloimg.setText("Anexe uma imagem que comprove a sua escolaridade! (não obrigatório)");
            instituicao.setHint("Qual o nome da Escola\nem que você estuda?");
            area.setHint("O que você mais gosta de estudar?");
            ano.setHint("Qual ano está cursando?");
            grupos.setHint("Você partitcipa de grupos de\nestudo, pesquisa, dança, música, artes ou etc?");
            descricao.setHint("Descreva um pouco mais\nsobre sua escola aqui...");
        }


        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (instituicao.getText().length() > 0 &&
                        area.getText().length() > 0 &&
                        ano.getText().length() > 0 &&
                        grupos.getText().length() > 0 &&
                        descricao.getText().length() > 0) {

                    ControllerPerfil.alterarEscolaridade(EditarEscolaridadeActivity.this, ID_USUARIO,
                            MEU_PERFIL,
                            TIPO,
                            instituicao.getText().toString(),
                            area.getText().toString(),
                            ano.getText().toString(),
                            grupos.getText().toString(),
                            descricao.getText().toString(),
                            pathfoto);
                } else {
                    Toast.makeText(EditarEscolaridadeActivity.this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
                }


            }
        });


        fotoPerfil = (ImageView) findViewById(R.id.id_editar_escolaridade_anexo);
        fotoPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarImgGalery(v);
            }
        });

    }

    public void pegarImgGalery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem: "), IMAGEM_INTERNA);
    }

    public void pedirPermisssaoParaAcessarArmazenamento() {
        if (ActivityCompat.checkSelfPermission(EditarEscolaridadeActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) EditarEscolaridadeActivity.this,
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

            ImageView imageView = (ImageView) findViewById(R.id.id_editar_escolaridade_anexo);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            pathfoto = picturePath;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(EditarEscolaridadeActivity.this, PerfilActivity.class);
        intent.putExtra("MEU_PERFIL", getIntent().getStringExtra("MEU_PERFIL"));
        intent.putExtra("ID_USUARIO", getIntent().getStringExtra("ID_USUARIO"));
        startActivity(intent);
        finish();

    }
}
