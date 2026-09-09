package com.example.apppam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.atomic.AtomicInteger;

public class TelaLogin extends AppCompatActivity {

    int tentativas = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_login);

        tentativas = getIntent().getIntExtra("tentativas", 0);

        EditText editNome = findViewById(R.id.editNome);
        EditText editSenha = findViewById(R.id.editSenha);
        Button btnEntrar = findViewById(R.id.btnEntrar);



        btnEntrar.setOnClickListener(v ->{

            String Nome = editNome.getText().toString();
            String NomeMin = Nome.toLowerCase();
            String Senha = editSenha.getText().toString();

            if (NomeMin.equals("arthur") && Senha.equals("1234")){
                Intent intent = new Intent(TelaLogin.this, TelaAcerto.class);
                startActivity(intent);
            }else {
                tentativas++;
                Intent intent = new Intent(TelaLogin.this, TelaErrado.class);
                intent.putExtra("tentativas", tentativas);
                startActivity(intent);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());

            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}