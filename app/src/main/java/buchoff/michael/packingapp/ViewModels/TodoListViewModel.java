package buchoff.michael.packingapp.ViewModels;

import java.util.ArrayList;

import buchoff.michael.packingapp.Models.TodoItem;
import buchoff.michael.packingapp.Models.TodoList;

public class TodoListViewModel {
    private static TodoListViewModel _instance = new TodoListViewModel(TodoList.get_instance());
    private final TodoList _model;
    private final ArrayList<TodoItemViewModel> _todoItemViewModels = new ArrayList<>();

    // Private constructor
    private TodoListViewModel(TodoList model)
    {
        _model = model;
        for (TodoItem todoItem : _model) {
            _todoItemViewModels.add(new TodoItemViewModel(todoItem));
        }
    }

    public TodoItemViewModel get_todoItemViewModel(int position)
    {
        return _todoItemViewModels.get(position);
    }

    public static TodoListViewModel get_instance() { return _instance; }
}
