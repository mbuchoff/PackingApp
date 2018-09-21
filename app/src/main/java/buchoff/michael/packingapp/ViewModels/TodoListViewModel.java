package buchoff.michael.packingapp.ViewModels;

import buchoff.michael.packingapp.Models.TodoList;

public class TodoListViewModel {
    private static TodoListViewModel _instance = new TodoListViewModel(TodoList.get_instance());
    private final TodoList _model;

    // Private constructor
    private TodoListViewModel(TodoList model)
    {
        _model = model;
    }

    public static TodoListViewModel get_instance() { return _instance; }
}
