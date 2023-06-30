package com.example.ordemservico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final static String USER = "Andre";
    private final static String PW = "1234";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextUsuario = (EditText) findViewById(R.id.editTextUser);
                String usuarioStr = editTextUsuario.getText().toString();

                EditText editTextPW = (EditText) findViewById(R.id.editTextPW2);
                String senhaStr = editTextPW.getText().toString();

                if (usuarioStr.equals("") || senhaStr.equals("")) {
                    Toast toast = Toast.makeText(view.getContext(), R.string.usuario_ou_senha_invalido, Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if (usuarioStr.equals(USER) && senhaStr.equals(PW)) {
                    // Criar uma Intent
                    Intent intent = new Intent(MainActivity.this, CadastrosActivity.class); //.class pelo fato de ser compilado, e o que chama a tela e executa

                    //Parâmetro a ser passado tela que abrir
                    intent.putExtra("USER", usuarioStr);

                    // Inicializar a activity
                    startActivity(intent);
                } else {
                    Toast.makeText(view.getContext(), R.string.usuario_ou_senha_invalido, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}