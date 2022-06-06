package com.example.todoapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.model.Todo;

import java.util.List;

@SuppressLint("ViewHolder")
public class TodoListAdapter extends BaseAdapter {
    private final List<Todo> todos;
    private final Activity activity;

    public TodoListAdapter(List<Todo> todos, Activity activity) {
        this.todos = todos;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return todos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = activity.getLayoutInflater().inflate(R.layout.cardview_todo, null);

        Todo todo = todos.get(position);

        TextView title = (TextView) view.findViewById(R.id.cardview_todo_title);
        TextView comment = (TextView) view.findViewById(R.id.cardview_todo_comment);

        title.setText(todo.getTitle());
        comment.setText(todo.getComment());

        return view;
    }
}
