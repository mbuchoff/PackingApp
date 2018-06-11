package buchoff.michael.packingapp;

import android.database.Observable;
import android.databinding.ObservableField;

public class TodoItem {
    ObservableField<String> _name;
    ObservableField<Boolean> _isHighlighted = new ObservableField<>(false);

    public ObservableField<String> get_name() {
        return _name;
    }

    public ObservableField<Boolean> get_isHighlighted() {
        return _isHighlighted;
    }

    public TodoItem(String name) {
        _name = new ObservableField<>(name);
    }

    public void makeNameLonger()
    {
        _name.set(_name.get() + "_");
    }
}