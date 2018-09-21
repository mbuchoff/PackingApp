package buchoff.michael.packingapp.ViewModels;

import java.util.ArrayList;

import buchoff.michael.packingapp.Models.TodoItem;
import buchoff.michael.packingapp.Models.TodoList;

public class TodoListViewModel {
    public interface Listener
    {
        void editTodoList(TodoItem todoItem);
    }

    private final ArrayList<TodoListItemViewModel> _todoListItemViewModels = new ArrayList<>();
    private final Listener _listener;

    // Private constructor
    public TodoListViewModel(Listener listener)
    {
        _listener = listener;

        for (TodoItem todoItem : TodoList.get_instance()) {
            _todoListItemViewModels.add(new TodoListItemViewModel(todoItem));
        }
    }

    public TodoListItemViewModel get_todoItemViewModel(int position)
    {
        while (position >= _todoListItemViewModels.size())
        {
            int index = _todoListItemViewModels.size();
            TodoItem todoItem = TodoList.get_instance().get(index);
            TodoListItemViewModel todoItemViewModel = new TodoListItemViewModel(todoItem);
            _todoListItemViewModels.add(todoItemViewModel);
        }
        return _todoListItemViewModels.get(position);
    }

    public void plusButtonClicked()
    {
        TodoItem todoItem = new TodoItem("Hello");
        _listener.editTodoList(todoItem);
    }
}
