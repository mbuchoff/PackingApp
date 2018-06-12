package buchoff.michael.packingapp;

import android.database.Observable;
import android.databinding.ObservableField;

public class TodoItem {
    private ObservableField<String> _name;
    private ObservableField<Boolean> _isHighlighted = new ObservableField<>(false);

    public ObservableField<String> get_name() {
        return _name;
    }

    public ObservableField<Boolean> get_isHighlighted() {
        return _isHighlighted;
    }

    public TodoItem(String name) {
        _name = new ObservableField<>(name);
    }
}