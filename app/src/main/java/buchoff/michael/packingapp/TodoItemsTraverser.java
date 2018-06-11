package buchoff.michael.packingapp;

import android.widget.ArrayAdapter;

public class TodoItemsTraverser {
    ArrayAdapter<TodoItemViewModel> _todoItemViewModels;
    int _todoItemIndex = 0;

    public TodoItemsTraverser(ArrayAdapter<TodoItemViewModel> todoItemViewModels) {
        _todoItemViewModels = todoItemViewModels;
    }

    public void start()
    {
        TodoItemViewModel todoItemViewModel = _todoItemViewModels.getItem(_todoItemIndex);
    }
}
