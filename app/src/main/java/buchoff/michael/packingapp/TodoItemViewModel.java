package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.view.View;

public class TodoItemViewModel extends ViewModel {
    private TodoItem _todoItem;

    public TodoItemViewModel(TodoItem todoItem)
    {
        _todoItem = todoItem;
        firstName = new ObservableField<>(_todoItem._name.get() + " INIT");

        _todoItem._name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                firstName.set(_todoItem._name.get() + " MODIFIED");
            }
        });
    }

    public ObservableField<String> firstName;
    public final String name = "TheName";

    public void onClick(View view)
    {
        _todoItem.makeNameLonger();
    }
}