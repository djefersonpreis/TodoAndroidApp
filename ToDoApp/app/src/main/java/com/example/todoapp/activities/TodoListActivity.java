package com.example.todoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.todoapp.R;
import com.example.todoapp.adapters.TodoListAdapter;
import com.example.todoapp.dao.TodoDAO;
import com.example.todoapp.model.Todo;

import java.net.SocketOption;
import java.sql.SQLOutput;
import java.util.List;

public class TodoListActivity extends Activity {

    private ListView todoList;
    protected Todo todoSelected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        TextView addNewElementButton = findViewById(R.id.addButton);

        addNewElementButton.setOnClickListener(v -> {
            Intent intent = new Intent(TodoListActivity.this, TodoFormActivity.class);
            startActivity(intent);
        });

        todoList = findViewById(R.id.activity_todo_list_todo_list);
        registerForContextMenu(todoList);
        todoList.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(TodoListActivity.this, TodoFormActivity.class);

            Todo todoSelected = (Todo) todoList.getItemAtPosition(position);

            intent.putExtra("todoSelected", todoSelected);
            startActivity(intent);
        });

        todoList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapter, View view,
                                           int posicao, long id) {

                todoSelected = (Todo) adapter.getItemAtPosition(posicao);

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        this.loadTodoList();
        super.onResume();
    }

    private void loadTodoList() {
        List<Todo> list;
        TodoDAO todoDAO = new TodoDAO(this);
        list = todoDAO.getList();
        todoDAO.close();

        TodoListAdapter adapter = new TodoListAdapter(list, this);

        todoList.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem editar = menu.add("Editar");
        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent = new Intent(TodoListActivity.this, TodoFormActivity.class);

                intent.putExtra("todoSelected", todoSelected);
                startActivity(intent);
                return false;
            }
        });

        MenuItem deletar = menu.add("Deletar");
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                delete();
                loadTodoList();
                return false;
            }
        });

        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void delete() {
        TodoDAO todoDAO = new TodoDAO(this);
        todoDAO.delete(todoSelected);
        todoDAO.close();

        loadTodoList();
    }
}
