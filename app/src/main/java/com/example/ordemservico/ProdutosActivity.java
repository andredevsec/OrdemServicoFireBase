package com.example.ordemservico;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.ordemservico.fragments.BuscarFragment;
import com.example.ordemservico.fragments.NovoFragment;
import com.example.ordemservico.fragments.ProdutosFragment;

public class ProdutosActivity extends AppCompatActivity {

    private Button btnPrduto, btnNovo, btnBuscar;
    private ProdutosFragment produtosFragment;
    private NovoFragment novoFragment;
    private BuscarFragment buscarFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produtos_activity);

        btnBuscar = findViewById(R.id.btnBuscar);
        btnNovo = findViewById(R.id.btnNovo);
        btnPrduto = findViewById(R.id.btnProdutos);

        produtosFragment = new ProdutosFragment();
        //Configurar o fragmento
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameProduto, produtosFragment);
        transaction.commit();

        btnPrduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameProduto, produtosFragment);
                transaction.commit();
            }
        });

        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                novoFragment = new NovoFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameProduto, novoFragment);
                transaction.commit();
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buscarFragment=new BuscarFragment();
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameProduto, buscarFragment);
                transaction.commit();
            }
        });
    }
}