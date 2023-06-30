package com.example.ordemservico.db;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.ordemservico.domain.Cliente;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class ClienteDAO {
    private DAOObserver observer;

    private static final String COLLECTION = "cliente";
    private static final String NOME = "nome";
    private static final String TIPO = "tipo";
    private static final String CPF = "cpf";
    private static final String CEP = "cep";
    private static final String ENDERECO = "endereco";
    private static final String DT_CHANGE = "date_changed";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();



    public ClienteDAO(DAOObserver observer) {
        this.observer=observer;
    }

    public void loadTasks() {
        List<Cliente> clientes = new ArrayList<>();

        db.collection(COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(com.google.android.gms.tasks.Task<QuerySnapshot> clienteFire) {

                if(clienteFire.isSuccessful()){
                    for(QueryDocumentSnapshot doc : clienteFire.getResult()){
                        String id = doc.getId();
                        String nome = doc.get(NOME, String.class);
                        String tipo = doc.get(TIPO, String.class);
                        String cpf = doc.get(CPF, String.class);
                        String cep = doc.get(CEP, String.class);
                        String endereco = doc.get(ENDERECO, String.class);


                        Cliente cliente = new Cliente(id, nome, tipo, cpf, cep, endereco);

                    }

                    observer.loadSuccess(clientes);
                }
            }
        });
    }

    public boolean save(Cliente cliente) {
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put(NOME, cliente.getNome());
        clienteMap.put(TIPO, cliente.getTipo());
        clienteMap.put(CPF, cliente.getCpf());
        clienteMap.put(CEP, cliente.getCep());
        clienteMap.put(ENDERECO, cliente.getEndereco());
        clienteMap.put(DT_CHANGE, cliente.getNome());

        db.collection(COLLECTION).add(clienteMap);
        return true;
    }

    public void update(Cliente cliente ){
        Map<String, Object> clienteMap = new HashMap<>();
        clienteMap.put(NOME, cliente.getNome());
        clienteMap.put(TIPO, cliente.getTipo());
        clienteMap.put(CPF, cliente.getCpf());
        clienteMap.put(CEP, cliente.getCep());
        clienteMap.put(ENDERECO, cliente.getEndereco());
        clienteMap.put(DT_CHANGE, cliente.getNome());

        db.collection(COLLECTION)// Recuperando coleção
                .document(cliente.getId()// recupera
                        .toString()).update(clienteMap); //  atualiza
    }
    public void delete(Cliente cliente ){
        db.collection(COLLECTION)
                .document(cliente.getId().toString())
                .delete();

    }

    public List<Cliente> listAll () {
        List<Cliente> clientes = new ArrayList<>();

        db.collection(COLLECTION).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(com.google.android.gms.tasks.Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        String id = doc.getId();
                        String nome = doc.getString(NOME);
                        String tipo = doc.getString(TIPO);
                        String cpf = doc.getString(CPF);
                        String cep = doc.getString(CEP);
                        String endereco = doc.getString(ENDERECO);

                        Cliente cliente = new Cliente(id, nome, tipo, cpf, cep, endereco);
                        clientes.add(cliente);
                    }

                    observer.loadSuccess(clientes);
                }
            }
        });

        return clientes;
    }

}