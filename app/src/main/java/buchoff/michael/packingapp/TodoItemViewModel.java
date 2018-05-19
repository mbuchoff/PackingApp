package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.view.View;

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

    public void onClick(View view)
    {
        _todoItem.makeNameLonger();
    }
}