package buchoff.michael.packingapp.viewmodels;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ObservableList;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.Observable;

import buchoff.michael.packingapp.RequestPermissionsListener;
import buchoff.michael.packingapp.models.TodoItem;
import buchoff.michael.packingapp.models.TodoList;
import buchoff.michael.packingapp.TodoItemsTraverser;

public class TodoListViewModel extends Observable {
    public interface Listener
    {
        void editTodoListItem(int index);
        void addTodoListItem();
        void notifyUser(String message);
    }

    private final TodoItemsTraverser _todoItemsTraverser;
    private final ArrayList<TodoListItemViewModel> _todoListItemViewModels = new ArrayList<>();
    private final Listener _listener;

    // Private constructor
    public TodoListViewModel(final Context context,
                             Listener listener,
                             RequestPermissionsListener requestPermissionsListener)
    {
        _listener = listener;

        for (TodoItem todoItem : TodoList.get_instance()) {
            addTodoItem(todoItem);
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
        });

        TodoItemsTraverser.Listener todoItemsTraverserListener = new TodoItemsTraverser.Listener() {

            @Override
            public void onWordsSpoken(String wordsSpoken) {
                _listener.notifyUser(wordsSpoken);
            }

            @Override
            public void onSpeechRecognitionReady() {
                _listener.notifyUser("HIT IT!");
            }
        };
        _todoItemsTraverser = new TodoItemsTraverser(context, todoItemsTraverserListener, requestPermissionsListener);
    }

    private void somethingChanged() {
        setChanged();
        notifyObservers();
    }

    public TodoListItemViewModel get_todoItemViewModel(int position)
    {
        while (position >= _todoListItemViewModels.size())
        {
            int index = _todoListItemViewModels.size();
            TodoItem todoItem = TodoList.get_instance().get(index);
            addTodoItem(todoItem);
        }
        return _todoListItemViewModels.get(position);
    }

    public void plusButtonClicked()
    {
        _listener.addTodoListItem();
    }

    public void listenButtonClicked() {
        _todoItemsTraverser.start();
    }



    private void addTodoItem(final TodoItem todoItem) {
        final int index = _todoListItemViewModels.size();
        TodoListItemViewModel.Listener todoItemListener = new TodoListItemViewModel.Listener() {
            @Override
            public void editTodoList() {
                _listener.editTodoListItem(index);
            }
        };

        _todoListItemViewModels.add(new TodoListItemViewModel(todoItemListener, todoItem));
    }
}
