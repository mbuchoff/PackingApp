package buchoff.michael.packingapp;

import android.database.Observable;
import android.databinding.ObservableField;

public class TodoItem {
    public enum Status { PENDING, ACTIVE, FINISHED };

    private ObservableField<String> _name;
    private ObservableField<Status> _status = new ObservableField<>(Status.PENDING);

    public ObservableField<String> get_name() {
        return _name;
    }

    public ObservableField<Status> get_status() {
        return _status;
    }

    public TodoItem(String name) {
        _name = new ObservableField<>(name);
    }
}