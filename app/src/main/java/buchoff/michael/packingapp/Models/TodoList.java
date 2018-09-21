package buchoff.michael.packingapp.Models;

import java.util.ArrayList;

public class TodoList extends ArrayList<TodoItem> {
    private static TodoList _instance = new TodoList();

    // Private constructor
    private TodoList() { }

    public static TodoList getInstance() { return _instance; }
}
