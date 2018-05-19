package buchoff.michael.packingapp;

import android.database.Observable;
import android.databinding.ObservableField;

public class TodoItem {
    public ObservableField<String> _name;

    public TodoItem(String name) {
        _name = new ObservableField<>(name);
    }

    public void makeNameLonger()
    {
        _name.set(_name.get() + "_");
    }
}