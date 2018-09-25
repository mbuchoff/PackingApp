package buchoff.michael.packingapp.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Color;

import buchoff.michael.packingapp.Models.TodoItem;

public class TodoListItemViewModel extends ViewModel {
    public interface Listener {
        void editTodoList();
    }

    private TodoItem _model;
    private Listener _listener;

    TodoListItemViewModel(Listener listener, TodoItem todoItem)
    {
        _model = todoItem;
        _listener = listener;

        updateName();
        updateBackgroundColor();

        _model.get_name().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateName();
            }
        });
        _model.get_status().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateBackgroundColor();
            }
        });
    }

    public TodoItem getData() {
        return _model;
    }

    private void updateName()
    {
        Name.set(_model.get_name().get());
    }

    private void updateBackgroundColor()
    {
        TodoItem.Status status = _model.get_status().get();
        if (status == TodoItem.Status.ACTIVE) {
            BackgroundColor.set(Color.rgb(255,255,0));
        } else if (status == TodoItem.Status.PENDING) {
            BackgroundColor.set(Color.rgb(255,255,255));
        } else {
            BackgroundColor.set(Color.rgb(0,255,0));
        }
    }

    public final ObservableField<String> Name = new ObservableField<>();
    public final ObservableField<Integer> BackgroundColor = new ObservableField<>();

    public void itemClicked()
    {
        _listener.editTodoList();
    }
}