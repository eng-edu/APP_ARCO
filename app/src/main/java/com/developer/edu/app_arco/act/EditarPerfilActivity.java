package com.developer.edu.app_arco.act;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.bd.DB_usuario;
import com.developer.edu.app_arco.controller.ControllerPerfil;
import com.developer.edu.app_arco.util.MaskEditUtil;
import com.developer.edu.app_arco.model.Usuario;
import com.developer.edu.app_arco.util.Util;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import static com.developer.edu.app_arco.conectionAPI.ConfigRetrofit.URL_BASE;

public class EditarPerfilActivity extends AppCompatActivity {

    public static final int IMAGEM_INTERNA = 12;
    private ImageView fotoPerfil;
    private String pathfoto = "";

    ArrayAdapter<CharSequence> adapter;

    boolean cpfvalido = true;
    boolean datanascvalido = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        pedirPermisssaoParaAcessarArmazenamento();

        final String ID_USUARIO = getIntent().getStringExtra("ID_USUARIO");
        final String MEU_PERFIL = getIntent().getStringExtra("MEU_PERFIL");

        ImageView fotoperfil = findViewById(R.id.id_editarPerfil_image_perfil);
        final EditText bio = findViewById(R.id.id_editarPerfil_bio);
        final EditText nome = findViewById(R.id.id_editarPerfil_nome);
        final EditText sobrenome = findViewById(R.id.id_editarPerfil_sobrenome);
        final EditText cpf = findViewById(R.id.id_editarPerfil_cpf);
        final EditText data_nasc = findViewById(R.id.id_editarPerfil_datanasc);
        final RadioButton sexoM = findViewById(R.id.id_editarPerfil_sexo_m);
        final RadioButton sexoF = findViewById(R.id.id_editarPerfil_sexo_f);
        final Spinner spinner_escolaridade = (Spinner) findViewById(R.id.spinner_editarPerfil_escolaridade);
        final Button salvar = findViewById(R.id.id_editarPerfil_salvar);
        TextView procurarimagem = findViewById(R.id.id_editarPerfil_procurarimagem);


        procurarimagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pegarImgGalery(v);
            }
        });

        Usuario u = new DB_usuario(EditarPerfilActivity.this).buscar(ID_USUARIO);

        Picasso.get().load(URL_BASE + "/IMG/" + ID_USUARIO + "_usuario.jpg").memoryPolicy(MemoryPolicy.NO_CACHE).into(fotoperfil);
        bio.setText(u.getBio());
        nome.setText(u.getNome());
        sobrenome.setText(u.getSobrenome());
        cpf.setText(u.getCpf());
        if (u.getSexo().equals("M")) {
            sexoM.setChecked(true);
        } else if (u.getSexo().equals("F")) {
            sexoF.setChecked(true);
        }
        data_nasc.setText(u.getData_nasc());

        final String tipo = u.getTipo();

        if (tipo.equals("1")) {
            adapter = ArrayAdapter.createFromResource(this, R.array.escolaridade_tipo1, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_escolaridade.setAdapter(adapter);

            if (u.getEscolaridade().equals("Superior")) {
                spinner_escolaridade.setSelection(0);
            } else if (u.getEscolaridade().equals("Pós-Graduado")) {
                spinner_escolaridade.setSelection(1);
            } else if (u.getEscolaridade().equals("Mestrado")) {
                spinner_escolaridade.setSelection(2);
            } else if (u.getEscolaridade().equals("Doutorado")) {
                spinner_escolaridade.setSelection(3);
            } else if (u.getEscolaridade().equals("Pós-Doutorado")) {
                spinner_escolaridade.setSelection(4);
            }

        } else if (tipo.equals("2")) {
            cpf.setVisibility(View.GONE);
            cpf.setText("");
            cpfvalido = true;
            adapter = ArrayAdapter.createFromResource(this, R.array.escolaridade_tipo2, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner_escolaridade.setAdapter(adapter);

            if (u.getEscolaridade().equals("Fundamental")) {
                spinner_escolaridade.setSelection(0);
            } else if (u.getEscolaridade().equals("Médio")) {
                spinner_escolaridade.setSelection(1);
            }
        }


        cpf.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Util.isCPF(cpf.getText().toString()) == true) {
                    cpf.setTextColor(Color.BLACK);
                    cpfvalido = true;
                } else {
                    cpf.setTextColor(Color.RED);
                    cpfvalido = false;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
        data_nasc.addTextChangedListener(MaskEditUtil.mask(data_nasc, MaskEditUtil.FORMAT_DATE));
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (tipo.equals("2")) {
                    cpfvalido = true;
                }

                String sexo = "";

                if (sexoM.isChecked()) {
                    sexo = "M";
                } else if (sexoF.isChecked()) {
                    sexo = "F";
                }

                String.valueOf(spinner_escolaridade.getSelectedItem());

                if (!cpfvalido) {
                    Toast.makeText(EditarPerfilActivity.this, "CPF inválido!", Toast.LENGTH_SHORT).show();
                } else if (!datanascvalido) {
                    Toast.makeText(EditarPerfilActivity.this, "Data de nascimento inválida!", Toast.LENGTH_SHORT).show();
                } else if (pathfoto.length() < 0) {
                    Toast.makeText(EditarPerfilActivity.this, "Adicione uma foto de perfil!", Toast.LENGTH_SHORT).show();
                } else if (bio.getText().length() > 0
                        && nome.getText().length() > 0
                        && sobrenome.getText().length() > 0
                        && cpfvalido == true
                        && datanascvalido == true) {

                    ControllerPerfil.alterarPerfil(EditarPerfilActivity.this,
                            ID_USUARIO,
                            MEU_PERFIL,
                            pathfoto,
                            bio.getText().toString(),
                            nome.getText().toString(),
                            sobrenome.getText().toString(),
                            cpf.getText().toString(),
                            sexo,
                            data_nasc.getText().toString(),
                            String.valueOf(spinner_escolaridade.getSelectedItem()),
                            tipo);

                } else {
                    Toast.makeText(EditarPerfilActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        fotoPerfil = (ImageView) findViewById(R.id.id_editarPerfil_image_perfil);
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
        if (ActivityCompat.checkSelfPermission(EditarPerfilActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) EditarPerfilActivity.this,
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

            ImageView imageView = (ImageView) findViewById(R.id.id_editarPerfil_image_perfil);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            pathfoto = picturePath;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(EditarPerfilActivity.this, PerfilActivity.class);
        intent.putExtra("MEU_PERFIL", getIntent().getStringExtra("MEU_PERFIL"));
        intent.putExtra("ID_USUARIO", getIntent().getStringExtra("ID_USUARIO"));
        startActivity(intent);
        finish();

    }
}
