package com.example.ordemservico;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ordemservico.db.ClienteDAO;
import com.example.ordemservico.db.DAOObserver;
import com.example.ordemservico.domain.Cliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class Clientes extends AppCompatActivity implements DAOObserver {

    private ListView todoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clientes);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Clientes.this, FormCliente.class); // abrir a partir da mainActivity,  a formActivity
                startActivity(intent); // passar a intent que queremos criar
            }
        });

        todoList = findViewById(R.id.todo_list);
        todoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Cliente cliente = (Cliente) todoList.getItemAtPosition(position);
                Intent intent = new Intent(Clientes.this, FormCliente.class);

                intent.putExtra("cliente-to-edit", cliente);
                startActivity(intent);

            }
        });
        loadClientes();

        registerForContextMenu(todoList);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem menuItemRemover = menu.add("Remover");
        MenuItem menuItemAtivar = menu.add("Ativar/Desativar");

        menuItemRemover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo adapterView = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Cliente cliente = (Cliente) todoList.getItemAtPosition(adapterView.position);
                ClienteDAO dao = new ClienteDAO(Clientes.this);
                dao.delete(cliente);

                loadClientes();

                Toast.makeText(Clientes.this, "Tarefa excluída com sucesso", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        menuItemAtivar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                AdapterView.AdapterContextMenuInfo adapterView = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Cliente cliente = (Cliente) todoList.getItemAtPosition(adapterView.position);
                ClienteDAO dao = new ClienteDAO(Clientes.this);
                dao.update(cliente);

                loadClientes();

                Toast.makeText(Clientes.this, "Tarefa desativada com sucesso", Toast.LENGTH_SHORT).show();

                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClientes();
    }

    private void updateTaskList() {
        ClienteDAO clienteDAO = new ClienteDAO(this);
        clienteDAO.loadTasks();
    }

    @Override
    public void loadSuccess(List<Cliente> cliente) {

        ArrayAdapter<Cliente> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, cliente);

        todoList.setAdapter(adapter);
    }

    private void loadClientes () {
        ClienteDAO dao = new ClienteDAO(Clientes.this);

        List<Cliente> clientes = dao.listAll();


    }
}