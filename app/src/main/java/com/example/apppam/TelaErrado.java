package com.example.apppam;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class TelaErrado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_errado);

        TextView textTimer = findViewById(R.id.textTimer);

        int tentativas =getIntent().getIntExtra("tentativas", 0);

        new CountDownTimer(5000, 1000) {
                    public void onTick(long millisUntilFinished) {
                        textTimer.setText("Voltando em" + String.valueOf(millisUntilFinished / 1000) + "...");
                    }
                    public void onFinish() {
                    textTimer.setText("0");

                        Intent intent;
                        if (tentativas>= 3){
                            intent = new Intent(TelaErrado.this, MainActivity.class);
                        }
                        else {
                            intent = new Intent(TelaErrado.this, TelaLogin.class);
                            intent.putExtra("tentativas", tentativas);
                        }
                        startActivity(intent);
                        finish();
                    }
        }.start();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}