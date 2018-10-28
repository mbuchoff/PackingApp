package buchoff.michael.packingapp.viewmodels;

import android.app.Activity;
import android.databinding.ObservableList;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Observable;

import buchoff.michael.packingapp.models.TodoItem;
import buchoff.michael.packingapp.models.TodoList;
import buchoff.michael.packingapp.TodoItemsTraverser;
import buchoff.michael.packingapp.views.MainActivity;

public class TodoListViewModel extends Observable {
    Toast _toast;

    public interface Listener
    {
        void editTodoListItem(int index);
        void addTodoListItem();
    }

    TodoItemsTraverser _todoItemsTraverser;
    private final ArrayList<TodoListItemViewModel> _todoListItemViewModels = new ArrayList<>();
    private final Listener _listener;

    // Private constructor
    public TodoListViewModel(final Activity activity, Listener listener)
    {
        _listener = listener;
        _toast = Toast.makeText(activity.getApplicationContext(), "", Toast.LENGTH_SHORT);

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

        _todoItemsTraverser = new TodoItemsTraverser(activity);
        _todoItemsTraverser.setListener(new TodoItemsTraverser.Listener() {

            @Override
            public void onWordsSpoken(String wordsSpoken) {
                _toast.setText(wordsSpoken);
                _toast.show();
            }

            @Override
            public void onSpeechRecognitionReady() {
                _toast.setText("HIT IT!");
                _toast.show();
            }
        });
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
        _toast.setText("Starting speech recognition...");
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
