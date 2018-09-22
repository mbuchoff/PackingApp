package buchoff.michael.packingapp.ViewModels;

import android.databinding.ObservableList;

import java.util.ArrayList;
import java.util.Observable;

import buchoff.michael.packingapp.Models.TodoItem;
import buchoff.michael.packingapp.Models.TodoList;

public class TodoListViewModel extends Observable {
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

        TodoList.get_instance().addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<TodoItem>>() {
            @Override
            public void onChanged(ObservableList<TodoItem> sender) {
                somethingChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<TodoItem> sender, int positionStart, int itemCount) {
                somethingChanged();
            }

            @Override
            public void onItemRangeInserted(ObservableList<TodoItem> sender, int positionStart, int itemCount) {
                somethingChanged();
            }

            @Override
            public void onItemRangeMoved(ObservableList<TodoItem> sender, int fromPosition, int toPosition, int itemCount) {
                somethingChanged();
            }

            @Override
            public void onItemRangeRemoved(ObservableList<TodoItem> sender, int positionStart, int itemCount) {
                somethingChanged();
            }

            private void somethingChanged() {
                setChanged();
                notifyObservers();
            }
        });
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
