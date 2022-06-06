package com.example.todoapp.helpers;

import android.widget.EditText;

import com.example.todoapp.R;
import com.example.todoapp.activities.TodoFormActivity;
import com.example.todoapp.model.Todo;

public class TodoFormHelper {

    private final EditText title;
    private final EditText comment;
    private Todo todo;

    public TodoFormHelper(TodoFormActivity activity){

        title = (EditText) activity.findViewById(R.id.cadastro_todo_input_title);
        comment = (EditText) activity.findViewById(R.id.cadastro_todo_input_comment);

        todo = new Todo();
    }

    public Todo getFromForm() {

        todo.setTitle(title.getEditableText().toString());
        todo.setComment(comment.getEditableText().toString());

        return todo;
    }

    public void setOnForm(Todo todo) {
        this.todo = todo;

        title.setText(todo.getTitle());
        comment.setText(todo.getComment());
    }
}
