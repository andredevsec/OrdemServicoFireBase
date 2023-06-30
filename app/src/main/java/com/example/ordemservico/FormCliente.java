package com.example.ordemservico;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ordemservico.db.ClienteDAO;
import com.example.ordemservico.db.DAOObserver;
import com.example.ordemservico.domain.Cliente;
import com.google.firebase.FirebaseApp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FormCliente extends AppCompatActivity implements DAOObserver {

    //Atributos
    private Cliente cliente;
    private EditText editTextNomeCliente;
    private EditText editTextEndereco;
    private EditText editTextCpf;
    private EditText editTextCep;
    private Spinner spinnerTipo;
    private Button buttonGuardar;
    private Button buttonMostrar;
    private Button buttonCompartilhar;
    private TextView textViewInformacaoCliente;
    private String [] pessoa = {"Pessoa Física", "Pessoa Jurídica"};
    private SharedPreferences preferences;
    private static final String PREFS_KEY = "keyOS";
    private static final String NAME_KEY = "nomeCliente";

    private static final String ENDERECO_KEY = "enderecoCliente";
    private static final String CPF_KEY = "cpfCliente";
    private static final String TIPO_KEY = "tipoCliente";
    private static final String CEP_KEY = "cepCliente";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_cliente_activity);
        preferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);
        //Carregar componentes
        editTextNomeCliente = findViewById(R.id.editTextNomeCliente);
        editTextEndereco = findViewById(R.id.editTextEndereco);
        editTextCpf = findViewById(R.id.editTextCpf);
        spinnerTipo = findViewById(R.id.spinnerTipo);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        buttonMostrar = findViewById(R.id.buttonMostrar);
        buttonCompartilhar = findViewById(R.id.buttonCompartilhar);
        textViewInformacaoCliente = findViewById(R.id.textViewInformacaoCliente);
        textViewInformacaoCliente.setVisibility(TextView.INVISIBLE);
        buttonCompartilhar.setVisibility(Button.INVISIBLE);
        editTextCep = findViewById(R.id.editTextCep);

        // recupoerar a intent chamadora
        Intent intent = getIntent();
        // pegar o cliente
        cliente = (Cliente) intent.getSerializableExtra("cliente-to-edit");
        //Colocar textos
        if(cliente != null) {
            editTextNomeCliente.setText(cliente.getNome());
            editTextEndereco.setText(cliente.getEndereco());
            editTextCpf.setText(cliente.getCpf());
            editTextCep.setText(cliente.getCep());
            spinnerTipo.setSelection(getIndexByValue(spinnerTipo, cliente.getTipo()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line, pessoa);
        spinnerTipo.setAdapter(adapter);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeStr = editTextNomeCliente.getText().toString();
                String enderecoStr = editTextEndereco.getText().toString();
                String cpfStr = editTextCpf.getText().toString();
                String cepStr = editTextCep.getText().toString();

                if (nomeStr.equals((""))) {
                    Toast.makeText(getApplicationContext() , "Campo nome não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (enderecoStr.equals((""))) {
                    Toast.makeText(getApplicationContext() , "Campo endereço não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (cpfStr.equals((""))) {
                    Toast.makeText(getApplicationContext() , "Campo CPF ou CNPJ não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                // CAST VALOR PARA NUMERO + CALCULO

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString(NAME_KEY, editTextNomeCliente.getText().toString());
                editor.putString(ENDERECO_KEY, editTextEndereco.getText().toString());
                editor.putString(CPF_KEY, editTextCpf.getText().toString());
                editor.putString(CEP_KEY, editTextCep.getText().toString());
                editor.putString(TIPO_KEY, pessoa[spinnerTipo.getSelectedItemPosition()]);
                editor.apply();
                Toast.makeText(FormCliente.this, "Gravado com Sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        buttonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome, endereco, cpf, cep, pessoa;
                nome = preferences.getString(NAME_KEY, "");
                endereco = preferences.getString(ENDERECO_KEY, "");
                cpf = preferences.getString(CPF_KEY, "");
                cep = preferences.getString(CEP_KEY, "");
                pessoa = preferences.getString(TIPO_KEY, "");

                textViewInformacaoCliente.setText("Nome do cliente: " + nome + "\n" +
                        "Enderço: " + endereco + "\n" +
                        "CPF ou CNPJ: " + cpf + "\n" +
                        "CEP: " + cep + "\n" +
                        "Tipo de Pessoa: " + pessoa + "\n");

                textViewInformacaoCliente.setVisibility(TextView.VISIBLE);
                buttonCompartilhar.setVisibility(Button.VISIBLE);
            }
        });

        buttonCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FormCliente.this);

                builder.setTitle("Cliente");

                LayoutInflater inflater = getLayoutInflater();
                View alertView = inflater.inflate(R.layout.alert_dialog_form_os_view, null);

                builder.setView(alertView);

                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText editTextDetalheOS = alertView.findViewById(R.id.editTextAlertDialogOS);
                        String nomeEmpresa = editTextDetalheOS.getText().toString();

                        String nome = preferences.getString(NAME_KEY, "");
                        String endereco = preferences.getString(ENDERECO_KEY, "");
                        String cpf = preferences.getString(CPF_KEY, "");
                        String cep = preferences.getString(CEP_KEY, "");
                        String pessoa = preferences.getString(TIPO_KEY, "");

                        String mensagem = "Cliente\n" +
                                "Nome: " + nome + "\n" +
                                "Endereço: " + endereco + "\n" +
                                "CPF ou CNPJ: " + cpf + "\n" +
                                "CEP: " + cep + "\n" +
                                "Tipo: " + pessoa + "\n";

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, mensagem);
                        startActivity(Intent.createChooser(intent, "Compartilhar dados Cliente"));
                    }
                });

                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        editTextCep.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchCEP(editTextCep.getText().toString());
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String nomeStr = editTextNomeCliente.getText().toString();
        String enderecoStr = editTextEndereco.getText().toString();
        String cpfStr = editTextCpf.getText().toString();
        String cepStr = editTextCep.getText().toString();
        String tipoStr = spinnerTipo.getSelectedItem().toString();
        if (nomeStr.equals((""))) {
            Toast.makeText(getApplicationContext() , "Campo nome não pode ser vazio" , Toast.LENGTH_SHORT).show();
            return false;
        }

        if (enderecoStr.equals((""))) {
            Toast.makeText(getApplicationContext() , "Campo endereço não pode ser vazio" , Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cpfStr.equals((""))) {
            Toast.makeText(getApplicationContext() , "Campo CPF ou CNPJ não pode ser vazio" , Toast.LENGTH_SHORT).show();
            return false;
        }


        ClienteDAO dao = new ClienteDAO(FormCliente.this);
        if(cliente == null) {
            cliente = new Cliente (null, nomeStr, tipoStr, cpfStr, cepStr, enderecoStr);
            dao.save(cliente);
        } else {
            cliente.setNome(nomeStr);
            cliente.setTipo(tipoStr);
            cliente.setCpf(cpfStr);
            cliente.setCep(cepStr);
            cliente.setEndereco(enderecoStr);
            dao.update(cliente);
        }
        finish();
        return true;
    }
    //percorre cada item no Spinner e compara seu valor com o valor fornecido.
    private int getIndexByValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(value)) {
                return i;
            }
        }
        return 0; // Valor padrão, caso o valor não seja encontrado
    }

    private void searchCEP(String cep) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEPResponse> call = cepService.getCEP(cep);

        call.enqueue(new Callback<CEPResponse>() {
            @Override
            public void onResponse(Call<CEPResponse> call, Response<CEPResponse> response) {
                if (response.isSuccessful()) {
                    CEPResponse cepResponse = response.body();
                    if (cepResponse != null) {
                        String result = "CEP: " + cepResponse.getCep() +
                                "\nLogradouro: " + cepResponse.getLogradouro() +
                                "\nBairro: " + cepResponse.getBairro() +
                                "\nCidade: " + cepResponse.getLocalidade();

                        // Outros campos que você queira exibir
                        editTextEndereco.setText(cepResponse.getLocalidade());
                    }
                } else {
                    showErrorMessage();
                }
            }

            @Override
            public void onFailure(Call<CEPResponse> call, Throwable t) {
                showErrorMessage();
            }
        });
    }
    private void showErrorMessage() {
        Toast.makeText(this, "Erro ao buscar o CEP", Toast.LENGTH_LONG).show();
    }
}
