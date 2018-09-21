package buchoff.michael.packingapp.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.view.View;

import buchoff.michael.packingapp.Models.TodoItem;

public class TodoItemViewModel extends ViewModel {
    private TodoItem _todoItem;
    private boolean _isEditing = true;
    private TodoItemViewModel.Listener _listener = null;

    public interface Listener
    {
        void requestDeletion(TodoItemViewModel viewModel);
    }

    public TodoItemViewModel(TodoItem todoItem)
    {
        _todoItem = todoItem;

        updateName();
        updateBackgroundColor();

        _todoItem.get_name().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateName();
            }
        });
        _todoItem.get_status().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                updateBackgroundColor();
            }
        });
    }

    public void set_listener(TodoItemViewModel.Listener listener) {
        _listener = listener;
    }

    public TodoItem getData() {
        return _todoItem;
    }

    private void updateName()
    {
        Name.set(_todoItem.get_name().get());
    }

    private void updateBackgroundColor()
    {
        TodoItem.Status status = _todoItem.get_status().get();
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

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        _todoItem.get_name().set(s.toString());
    }

    public void deleteButtonClicked(View view) {
        if (_listener != null) {
            _listener.requestDeletion(this);
        }
    }
}