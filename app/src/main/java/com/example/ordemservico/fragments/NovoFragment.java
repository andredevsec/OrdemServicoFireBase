package com.example.ordemservico.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordemservico.FormOrdemServico;
import com.example.ordemservico.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NovoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NovoFragment extends Fragment {
    private EditText editTextNomeProduto;
    private EditText editTextCodigo;
    private EditText editTextValor;
    private Spinner spinnerCategoria;
    private Button buttonGuardar;
    private Button buttonMostrar;
    private Button buttonCompartilhar;
    private TextView textViewInformacaoProduto;
    private String [] opcao = {"Eletronico", "Acesssorio", "Equipamento", "Brinquedos", "Diversos" };
    private SharedPreferences preferences;
    private static final String PREFS_KEY = "keyOS";
    private static final String NAME_KEY = "nome";
    private static final String Code_KEY = "code";
    private static final String VALUE_KEY = "value";
    private static final String CATEGORY_KEY = "category";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NovoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NovoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NovoFragment newInstance(String param1, String param2) {
        NovoFragment fragment = new NovoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_novo, container, false);

        preferences = requireContext().getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE);

        //Carregar componentes
        editTextNomeProduto = view.findViewById(R.id.editTextNomeProduto);
        editTextCodigo = view.findViewById(R.id.editTextCodigo);
        editTextValor = view.findViewById(R.id.editTextValor);
        spinnerCategoria = view.findViewById(R.id.spinnerCategoria);
        buttonGuardar = view.findViewById(R.id.buttonGuardar);
        buttonMostrar = view.findViewById(R.id.buttonMostrar);
        buttonCompartilhar = view.findViewById(R.id.buttonCompartilhar);
        textViewInformacaoProduto = view.findViewById(R.id.textViewInformacaoProduto);


        textViewInformacaoProduto.setVisibility(TextView.INVISIBLE);
        buttonCompartilhar.setVisibility(Button.INVISIBLE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireContext(),android.R.layout.simple_dropdown_item_1line, opcao);
        spinnerCategoria.setAdapter(adapter);

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nomeStr = editTextNomeProduto.getText().toString();
                String codeStr = editTextCodigo.getText().toString();
                String valorStr = editTextValor.getText().toString();


                if (nomeStr.equals((""))) {
                    Toast.makeText(requireContext() , "Campo nome não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (codeStr.equals((""))) {
                    Toast.makeText(requireContext() , "Campo serviço não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                if (valorStr.equals((""))) {
                    Toast.makeText(requireContext() , "Campo valor não pode ser vazio" , Toast.LENGTH_SHORT).show();
                    return;
                }

                // CAST VALOR PARA NUMERO + CALCULO

                SharedPreferences.Editor editor = preferences.edit();

                editor.putString(NAME_KEY, editTextNomeProduto.getText().toString());
                editor.putString(Code_KEY, editTextCodigo.getText().toString());
                editor.putString(VALUE_KEY, editTextValor.getText().toString());
                editor.putString(CATEGORY_KEY, opcao[spinnerCategoria.getSelectedItemPosition()]);
                editor.apply();
                Toast.makeText(requireContext(), "Gravado com Sucesso", Toast.LENGTH_SHORT).show();
            }
        });

        buttonMostrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome, code,valorProduto, status;
                nome = preferences.getString(NAME_KEY, "");
                code = preferences.getString(Code_KEY, "");
                valorProduto = preferences.getString(VALUE_KEY, "");
                status = preferences.getString(CATEGORY_KEY, "");

                textViewInformacaoProduto.setText("Nome do produto: " + nome + "\n" +
                        "Codigo: " + code + "\n" +
                        "Valor Produto: R$" + valorProduto + "\n" +
                        "Categoria: " + status + "\n");

                textViewInformacaoProduto.setVisibility(TextView.VISIBLE);
                buttonCompartilhar.setVisibility(Button.VISIBLE);
            }
        });

        buttonCompartilhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());

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
                        String codigo = preferences.getString(Code_KEY, "");
                        String valorProduto = preferences.getString(VALUE_KEY, "");
                        String categoria = preferences.getString(CATEGORY_KEY, "");

                        String mensagem = "Produto\n" +
                                "Nome do produto: " + nome + "\n" +
                                "Codigo: " + codigo + "\n" +
                                "Valor produtos: R$" + valorProduto + "\n" +
                                "Status da ordem: " + categoria + "\n";

                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, mensagem);
                        startActivity(Intent.createChooser(intent, "Compartilhar Produto"));
                    }
                });

                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }
}