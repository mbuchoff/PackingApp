package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.support.design.widget.TextInputEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;

public class TodoItemViewModel extends ViewModel {
    private TodoItem _todoItem;

    public TodoItemViewModel(TodoItem todoItem)
    {
        _todoItem = todoItem;

        UpdateName();
        _todoItem._name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                UpdateName();
            }
        });
    }

    public void UpdateName()
    {
        Name.set(_todoItem._name.get());
    }

    public final ObservableField<String> Name = new ObservableField<>();
    public final ObservableField<Integer> TextInputVisibility = new ObservableField<>(View.VISIBLE);
    public final ObservableField<Integer> TextReadOnlyVisibility = new ObservableField<>(View.INVISIBLE);

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        _todoItem._name.set(s.toString());
        if (s.toString().contains("\n"))
        {
            TextInputVisibility.set(View.INVISIBLE);
            TextReadOnlyVisibility.set(View.VISIBLE);
        }
    }

    public void PlayButtonClicked(View view){ }
}