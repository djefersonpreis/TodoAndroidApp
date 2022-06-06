package com.example.todoapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.dao.TodoDAO;
import com.example.todoapp.helpers.TodoFormHelper;
import com.example.todoapp.model.Todo;

public class TodoFormActivity extends Activity {

    private TodoFormHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_todo_form);

        Intent intent = getIntent();
        final Todo todoToAlter = (Todo) intent.getSerializableExtra("todoSelected");

        this.helper = new TodoFormHelper(this);

        Button botao = (Button) findViewById(R.id.cadastro_todo_cadastro_btn);

        if (todoToAlter != null) {
            TextView pageTitle = (TextView) findViewById(R.id.cadastro_todo_page_title);

            // Page config change
            pageTitle.setText("Alterar");
            botao.setText("Alterar");

            // Set info to edit
            helper.setOnForm(todoToAlter);
        }

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo todo = helper.getFromForm();

                TodoDAO todoDAO = new TodoDAO(TodoFormActivity.this);

                if(todoToAlter == null){
                    todoDAO.insert(todo);
                } else {
                    todo.setId(todoToAlter.getId());
                    todoDAO.alter(todo);
                }

                todoDAO.close();

                finish();
            }
        });
    }
}
