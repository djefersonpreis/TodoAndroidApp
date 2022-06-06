package com.example.todoapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.todoapp.model.Todo;

import java.util.ArrayList;
import java.util.List;

public class TodoDAO extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String TABLE = "todos";
    private static final String DATABASE = "TodoApp";
    private static final String[] COLUMNS = { "id", "title", "comment" };

    public TodoDAO(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String ddl = "CREATE TABLE " + TABLE + " (" +
                " id INTEGER PRIMARY KEY, " +
                " title VARCHAR(100) NOT NULL, " +
                " comment TEXT NOT NULL)";

        db.execSQL(ddl);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS " + TABLE;
        db.execSQL(sql);
        onCreate(db);
    }

    public List<Todo> getList() {
        List<Todo> todos = new ArrayList<>();

        Cursor cursor = getWritableDatabase().query(
                TABLE,
                COLUMNS,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            Todo todo = new Todo();

            todo.setId(cursor.getLong(0));
            todo.setTitle(cursor.getString(1));
            todo.setComment(cursor.getString(2));

            todos.add(todo);
        }

        cursor.close();

        return todos;
    }

    public void insert(Todo todo) {
        ContentValues values = new ContentValues();

        values.put("title", todo.getTitle());
        values.put("comment", todo.getComment());

        getWritableDatabase().insert(TABLE, null, values);
    }

    public void delete(Todo todo) {
        String[] args = {todo.getId().toString()};

        getWritableDatabase().delete(TABLE, "id=?", args);
    }

    public void alter(Todo todo) {
        ContentValues values = new ContentValues();

        values.put("title", todo.getTitle());
        values.put("comment", todo.getComment());

        String[] args = {todo.getId().toString()};
        getWritableDatabase().update(TABLE, values, "id=?", args);
    }

}
