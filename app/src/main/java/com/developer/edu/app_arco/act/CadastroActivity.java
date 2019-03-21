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
import android.widget.Toast;

import com.developer.edu.app_arco.R;
import com.developer.edu.app_arco.util.Util;
import com.developer.edu.app_arco.controller.ControllerCadastro;
import com.developer.edu.app_arco.util.MaskEditUtil;

public class CadastroActivity extends AppCompatActivity {

    public static final int IMAGEM_INTERNA = 12;
    public static final int IMAGEM_CAMERA = 13;
    private ImageView fotoPerfil;
    private String pathfoto = "";
    ArrayAdapter<CharSequence> adapter;

    boolean cpfvalido = false;
    boolean datanascvalido = false;
    boolean emailvalido = false;
    boolean senhavalido = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        pedirPermisssaoParaAcessarArmazenamento();

        final String tipo = getIntent().getStringExtra("tipo");

        final Spinner spinner_escolaridade = (Spinner) findViewById(R.id.spinner_escolaridade);
        final EditText nome = findViewById(R.id.id_cadastro_nome);
        final EditText sobrenome = findViewById(R.id.id_cadastro_sobrenome);
        final EditText bio = findViewById(R.id.id_cadastro_bio);
        final EditText data_nasc = findViewById(R.id.id_cadastro_datanasc);
        final RadioButton sexoM = findViewById(R.id.id_cadastro_sexo_m);
        final RadioButton sexoF = findViewById(R.id.id_cadstro_sexo_f);
        final EditText email = findViewById(R.id.id_cadastro_email);
        final EditText senha = findViewById(R.id.id_cadastro_senha);
        final EditText csenha = findViewById(R.id.id_cadastro_csenha);
        final EditText cpf = findViewById(R.id.id_cadastro_cpf);
        final Button cadastrar = findViewById(R.id.id_cadastro_cadastrar);


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

        senha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (senha.getText().length() > 4) {
                    senha.setTextColor(Color.BLACK);
                    senhavalido = true;
                } else {
                    senha.setTextColor(Color.RED);
                    senhavalido = false;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        csenha.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (senha.getText().toString().equals(csenha.getText().toString()) == true) {
                    csenha.setTextColor(Color.BLACK);
                } else {
                    csenha.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        if (tipo.equals("1")) {
            adapter = ArrayAdapter.createFromResource(this, R.array.escolaridade_tipo1, android.R.layout.simple_spinner_item);

        } else if (tipo.equals("2")) {
            cpf.setVisibility(View.GONE);
            cpf.setText("");
            cpfvalido = true;
            adapter = ArrayAdapter.createFromResource(this, R.array.escolaridade_tipo2, android.R.layout.simple_spinner_item);
        }


        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_escolaridade.setAdapter(adapter);

        data_nasc.addTextChangedListener(MaskEditUtil.mask(data_nasc, MaskEditUtil.FORMAT_DATE));
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));

        cadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sexo = "";

                if (sexoM.isChecked()) {
                    sexo = "M";
                } else if (sexoF.isChecked()) {
                    sexo = "F";
                }

                String.valueOf(spinner_escolaridade.getSelectedItem());

                if (!cpfvalido) {
                    Toast.makeText(CadastroActivity.this, "CPF inválido!", Toast.LENGTH_SHORT).show();
                } else if (!emailvalido) {
                    Toast.makeText(CadastroActivity.this, "Email inválido!", Toast.LENGTH_SHORT).show();
                } else if (!datanascvalido) {
                    Toast.makeText(CadastroActivity.this, "Data de nascimento inválida!", Toast.LENGTH_SHORT).show();
                } else if (senha.getText().toString().equals(csenha.getText().toString()) == false) {
                    Toast.makeText(CadastroActivity.this, "As senhas não correspondem!", Toast.LENGTH_SHORT).show();
                } else if (pathfoto.length() < 0) {
                    Toast.makeText(CadastroActivity.this, "Adicione uma foto de perfil!", Toast.LENGTH_SHORT).show();
                } else if (!senhavalido) {
                    Toast.makeText(CadastroActivity.this, "Senha muito curta!", Toast.LENGTH_SHORT).show();
                } else if (pathfoto.length() > 0
                        && bio.getText().length() > 0
                        && nome.getText().length() > 0
                        && sobrenome.getText().length() > 0
                        && cpfvalido == true
                        && datanascvalido == true
                        && emailvalido == true
                        && senhavalido == true) {

                    ControllerCadastro.cadastrar(CadastroActivity.this,
                            pathfoto,
                            bio.getText().toString(),
                            nome.getText().toString(),
                            sobrenome.getText().toString(),
                            cpf.getText().toString(),
                            sexo,
                            data_nasc.getText().toString(),
                            String.valueOf(spinner_escolaridade.getSelectedItem()),
                            email.getText().toString(),
                            senha.getText().toString(),
                            tipo,
                            email);

                } else {
                    Toast.makeText(CadastroActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        fotoPerfil = (ImageView) findViewById(R.id.id_cadastro_image_perfil);
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
        if (ActivityCompat.checkSelfPermission(CadastroActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
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

            ImageView imageView = (ImageView) findViewById(R.id.id_cadastro_image_perfil);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            pathfoto = picturePath;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(CadastroActivity.this, PreCadastroActivity.class));
        finish();
    }


}
