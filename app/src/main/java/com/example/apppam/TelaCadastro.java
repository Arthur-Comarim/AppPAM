package com.example.apppam;

import android.annotation.SuppressLint;
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

        btnGaleria.setOnClickListener(v -> galeriaLauncher.launch("image/*"));
        btnCamera.setOnClickListener(v -> cameraLauncher.launch(null));


        EditText editSenha = findViewById(R.id.editSenha);
        Button btnMostrarSenha = findViewById(R.id.btnMostrarSenha);

        btnMostrarSenha.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                editSenha.setInputType(InputType.TYPE_CLASS_TEXT); // mostra o texto
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                editSenha.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD); // esconde de novo
            }
            editSenha.setSelection(editSenha.getText().length()); // mantém o cursor no fim
            return true; // true = "eu tratei esse toque, não precisa fazer mais nada"
        });




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }








}