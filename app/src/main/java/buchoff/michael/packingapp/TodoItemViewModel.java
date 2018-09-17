package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.view.View;

public class TodoItemViewModel extends ViewModel {
    private TodoItem _todoItem;
    private Listener _listener = null;

    interface Listener
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

    public void set_listener(Listener listener) {
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
    public final ObservableField<Integer> InputTextVisibility = new ObservableField<>(View.VISIBLE);
    public final ObservableField<Integer> TextViewVisibility = new ObservableField<>(View.INVISIBLE);

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String string = s.toString();
        ObservableField<String> todoName = _todoItem.get_name();
        if (string.contains("\n")) {
            InputTextVisibility.set(View.INVISIBLE);
            TextViewVisibility.set(View.VISIBLE);

            // Refresh the input field that the user pressed ENTER on
            this.Name.notifyChange();
        } else {
            todoName.set(s.toString());
        }
    }

    public void deleteButtonClicked(View view) {
        if (_listener != null) {
            _listener.requestDeletion(this);
        }
    }
}