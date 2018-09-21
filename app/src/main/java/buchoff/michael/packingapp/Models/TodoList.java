package buchoff.michael.packingapp.Models;

import java.util.ArrayList;

public class TodoList extends ArrayList<TodoItem> {
    private static TodoList _instance = new TodoList();

    // Private constructor
    private TodoList()
    {
        add(new TodoItem("First todo item"));
        add(new TodoItem("Second todo item..."));
    }

    public static TodoList get_instance() { return _instance; }
}
