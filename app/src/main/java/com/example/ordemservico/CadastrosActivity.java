package com.example.ordemservico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CadastrosActivity extends AppCompatActivity {
    private Button buttonClientes;
    private Button buttonOrdemServico;
    private Button buttonProdutos;
    private Button buttonVendas;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_activity);

        // Carregando componentes
        buttonClientes = findViewById(R.id.buttonClientes);
        buttonOrdemServico = findViewById(R.id.buttonOrdemServico);
        buttonProdutos = findViewById(R.id.buttonProdutos);
        buttonVendas = findViewById(R.id.buttonVendas);

        buttonClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastrosActivity.this, Clientes.class); // abrir a partir da mainActivity,  a formActivity
                startActivity(intent);
            }
        });

        buttonOrdemServico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastrosActivity.this, FormOrdemServico.class); // abrir a partir da mainActivity,  a formActivity
                startActivity(intent);
            }
        });

        buttonProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CadastrosActivity.this, ProdutosActivity.class); // abrir a partir da mainActivity,  a formActivity
                startActivity(intent);
            }
        });

        buttonVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Em desenvolvimento!", Toast.LENGTH_SHORT).show();
                return;
            }
        });
    }

}