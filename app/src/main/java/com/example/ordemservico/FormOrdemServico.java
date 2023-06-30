package com.example.ordemservico;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class FormOrdemServico extends AppCompatActivity {
    //Atributos
    private EditText editTextNomeCliente;
    private EditText editTextServico;
    private EditText editTextPrdutos;
    private EditText editTextValorServico;
    private EditText editTextValorProduto;
    private Spinner spinnerStatus;
    private Button buttonGuardar;
    private Button buttonMostrar;
    private Button buttonCompartilhar;
    private TextView textViewInformacaoOS;
    private String [] opcao = {"Aberta", "Cancelada", "Concluída", "Em andamento", "Finalizada" };
    private SharedPreferences preferences;
    private static final String PREFS_KEY = "keyOS";
    private static final String NAME_KEY = "nameOS";
    private static final String SERVICE_KEY = "serviceOS";
    private static final String PRODUCT_KEY = "productOS";
    private static final String VALUE_SERVICE_KEY = "valueServiceOS";
    private static final String VALUE_PRODUCT_KEY = "valueProductOS";
    private static final String VALUE_TOTAL_KEY = "valueTotalOS";
    private static final String STATUS_KEY = "statusOS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_ordem_servico_activity);


        preferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);

        //Carregar componentes
        editTextNomeCliente = findViewById(R.id.editTextNomeProduto);
        editTextServico = findViewById(R.id.editTextCodigo);
        editTextPrdutos = findViewById(R.id.editTextPrdutos);
        editTextValorServico = findViewById(R.id.editTextValor);
        editTextValorProduto = findViewById(R.id.editTextValorProduto);
        spinnerStatus = findViewById(R.id.spinnerStatus);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonMostrar = findViewById(R.id.buttonMostrar);
        buttonCompartilhar = findViewById(R.id.buttonCompartilhar);
        textViewInformacaoOS = findViewById(R.id.textViewInformacaoProduto);


        textViewInformacaoOS.setVisibility(TextView.INVISIBLE);
        buttonCompartilhar.setVisibility(Button.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line, opcao);
        spinnerStatus.setAdapter(adapter);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeStr = editTextNomeCliente.getText().toString();
                String serviceStr = editTextServico.getText().toString();
                String productStr = editTextPrdutos.getText().toString();
                String valorServicoStr = editTextValorServico.getText().toString();
                String valorProdutoStr = editTextValorProduto.getText().toString();


                if (nomeStr.equals((""))) {
                    Toast.makeText(getApplicationContext() , "Campo nome não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (serviceStr.equals((""))) {
                    Toast.makeText(getApplicationContext() , "Campo serviço não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (productStr.equals((""))) {
                    Toast.makeText(getApplicationContext() , "Campo produto não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valorServicoStr.equals((""))) {
                    Toast.makeText(getApplicationContext() , "Campo valor não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valorProdutoStr.equals((""))) {
                    Toast.makeText(getApplicationContext() , "Campo valor não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                // CAST VALOR PARA NUMERO + CALCULO

                Double valorProduto = Double.parseDouble(valorProdutoStr);
                Double valoServico = Double.parseDouble(valorServicoStr);
                Double valorTotal = valorProduto + valoServico;

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString(NAME_KEY, editTextNomeCliente.getText().toString());
                editor.putString(SERVICE_KEY, editTextServico.getText().toString());
                editor.putString(PRODUCT_KEY, editTextPrdutos.getText().toString());
                editor.putString(VALUE_SERVICE_KEY, editTextValorServico.getText().toString());
                editor.putString(VALUE_PRODUCT_KEY, editTextValorProduto.getText().toString());
                editor.putString(VALUE_TOTAL_KEY, String.valueOf(valorTotal));
                editor.putString(STATUS_KEY, opcao[spinnerStatus.getSelectedItemPosition()]);
                editor.apply();
                Toast.makeText(FormOrdemServico.this, "Gravado com Sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        buttonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome, service, product, valorService,valorProduto,valorTotal, status;
                nome = preferences.getString(NAME_KEY, "");
                service = preferences.getString(SERVICE_KEY, "");
                product = preferences.getString(PRODUCT_KEY, "");
                valorService = preferences.getString(VALUE_SERVICE_KEY, "");
                valorProduto = preferences.getString(VALUE_PRODUCT_KEY, "");
                valorTotal = preferences.getString(VALUE_TOTAL_KEY, "");
                status = preferences.getString(STATUS_KEY, "");

                textViewInformacaoOS.setText("Nome do cliente: " + nome + "\n" +
                        "Serviço realizado: " + service + "\n" +
                        "Valor Serviço: R$" + valorService + "\n" +
                        "Produtos gastos: " + product + "\n" +
                        "Valor Produto: R$" + valorProduto + "\n" +
                        "Status da OS: " + status + "\n" +
                        "Valor Total: R$" + valorTotal + "\n");

                textViewInformacaoOS.setVisibility(TextView.VISIBLE);
                buttonCompartilhar.setVisibility(Button.VISIBLE);
            }
        });

        buttonCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormOrdemServico.this);

                builder.setTitle("Ordem de Serviço");

                LayoutInflater inflater = getLayoutInflater();
                View alertView = inflater.inflate(R.layout.alert_dialog_form_os_view, null);

                builder.setView(alertView);

                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editTextDetalheOS = alertView.findViewById(R.id.editTextAlertDialogOS);
                        String nomeEmpresa = editTextDetalheOS.getText().toString();

                        String nome = preferences.getString(NAME_KEY, "");
                        String service = preferences.getString(SERVICE_KEY, "");
                        String product = preferences.getString(PRODUCT_KEY, "");
                        String valorService = preferences.getString(VALUE_SERVICE_KEY, "");
                        String valorProduto = preferences.getString(VALUE_PRODUCT_KEY, "");
                        String valorTotal = preferences.getString(VALUE_TOTAL_KEY, "");
                        String status = preferences.getString(STATUS_KEY, "");

                        String mensagem = "Ordem de Serviço\n" +
                                "Empresa prestadora de serviço: " + nomeEmpresa + "\n" +
                                "Nome do cliente: " + nome + "\n" +
                                "Serviço realizado: " + service + "\n" +
                                "Valor serviço: R$" + valorService + "\n" +
                                "Produtos utilizados: " + product + "\n" +
                                "Valor produtos: R$" + valorProduto + "\n" +
                                "Valor total: R$" + valorTotal + "\n" +
                                "Status da ordem: " + status + "\n";

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, mensagem);
                        startActivity(Intent.createChooser(intent, "Compartilhar Ordem de Serviço"));
                    }
                });

                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
}

