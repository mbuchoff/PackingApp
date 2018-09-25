package buchoff.michael.packingapp.Models;

import android.databinding.ObservableArrayList;

public class TodoList extends ObservableArrayList<TodoItem> {
    private static TodoList _instance = new TodoList();

    // Private constructor
    private TodoList()
    {
        add(new TodoItem("First todo item"));
        add(new TodoItem("Second todo item..."));
    }

    public static TodoList get_instance() { return _instance; }
}
