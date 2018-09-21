package buchoff.michael.packingapp.ViewModels;

import java.util.ArrayList;

import buchoff.michael.packingapp.Models.TodoItem;
import buchoff.michael.packingapp.Models.TodoList;

public class TodoListViewModel {
    public interface Listener
    {
        void editTodoList(TodoItem todoItem);
    }

    private final ArrayList<TodoItemViewModel> _todoItemViewModels = new ArrayList<>();
    private final Listener _listener;

    // Private constructor
    public TodoListViewModel(Listener listener)
    {
        _listener = listener;

        for (TodoItem todoItem : TodoList.get_instance()) {
            _todoItemViewModels.add(new TodoItemViewModel(todoItem));
        }
    }

    public TodoItemViewModel get_todoItemViewModel(int position)
    {
        return _todoItemViewModels.get(position);
    }

    public void plusButtonClicked()
    {
        TodoItem todoItem = new TodoItem("Hello");
        _listener.editTodoList(todoItem);
    }
}
