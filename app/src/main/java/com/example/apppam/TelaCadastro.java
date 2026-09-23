package com.example.apppam;

import static android.os.Build.VERSION_CODES_FULL.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.InputType;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;

public class TelaCadastro extends AppCompatActivity {


    private Bitmap fotoSelecionada;
    private ImageView imgFoto;
    private final ActivityResultLauncher<String> galeriaLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    try {
                        fotoSelecionada = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgFoto.setImageBitmap(fotoSelecionada);
                    } catch (IOException e) {
                        Toast.makeText(this, "Erro ao carregar imagem", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private final ActivityResultLauncher<Void> cameraLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicturePreview(), bitmap -> {
                if (bitmap != null) {
                    fotoSelecionada = bitmap;
                    imgFoto.setImageBitmap(bitmap);
                }
            });



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro);


        imgFoto = findViewById(R.id.imgFoto);
        Button btnGaleria = findViewById(R.id.btnGaleria);
        Button btnCamera = findViewById(R.id.btnCamera);
        Button btnFinalizar = findViewById(R.id.btnFinalizar);

        btnGaleria.setOnClickListener(v -> galeriaLauncher.launch("image/*"));
        btnCamera.setOnClickListener(v -> cameraLauncher.launch(null));

        EditText editNome = findViewById(R.id.editNome);
        EditText editSobrenome = findViewById(R.id.editSobrenome);
        EditText editDataNascimento = findViewById(R.id.editDataNascimento);
        EditText editTelefone = findViewById(R.id.editTelefone);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editConfirmaEmail = findViewById(R.id.editConfirmaEmail);
        EditText editSenha = findViewById(R.id.editSenha);
        EditText editConfirmaSenha = findViewById(R.id.editConfirmaSenha);

        btnFinalizar.setOnClickListener(v -> {
            String nome = editNome.getText().toString().trim();
            String sobrenome = editSobrenome.getText().toString().trim();
            String dataNascimento = editDataNascimento.getText().toString().trim();
            String telefone = editTelefone.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String confirmaEmail = editConfirmaEmail.getText().toString().trim();
            String senha = editSenha.getText().toString();
            String confirmaSenha = editConfirmaSenha.getText().toString();

            if (!nome.matches("[\\p{L}\\s]+") || !sobrenome.matches("[\\p{L}\\s]+")) {
                Toast.makeText(this, "Nome e sobrenome devem conter só letras", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.contains("@")) {
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!email.equals(confirmaEmail)) {
                Toast.makeText(this, "Os emails não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!senha.equals(confirmaSenha)) {
                Toast.makeText(this, "As senhas não coincidem", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!validarData(dataNascimento)) {
                Toast.makeText(this, "Data de nascimento inválida", Toast.LENGTH_SHORT).show();
                return;
            }

            // tudo certo — salva no "cofre" static
            Usuario.nome = nome;
            Usuario.sobrenome = sobrenome;
            Usuario.dataNascimento = dataNascimento;
            Usuario.telefone = telefone;
            Usuario.email = email;
            Usuario.senha = senha;
            Usuario.foto = fotoSelecionada;

            Intent intent = new Intent(TelaCadastro.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        Button btnMostrarSenha = findViewById(R.id.btnMostrarSenha);

        btnMostrarSenha.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                editSenha.setInputType(InputType.TYPE_CLASS_TEXT); // mostra o texto
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // esconde de novo
            }
            editSenha.setSelection(editSenha.getText().length()); // mantém o cursor no fim
            return true; // finalizei essa merda, se preocupa não
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }








}