package buchoff.michael.packingapp.viewmodels;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;

import buchoff.michael.packingapp.models.TodoItem;
import buchoff.michael.packingapp.models.TodoList;

public class TodoItemDetailsViewModel extends ViewModel {
    public interface Listener
    {
        void hide();
    }

    private TodoItem _model;
    private boolean _isNew;
    private Listener _listener;

    public TodoItemDetailsViewModel(Listener listener, TodoItem model, boolean isNew) {
        _model = model;
        _isNew = isNew;
        _listener = listener;

        name.set(model.get_name().get());

        if (_isNew)
        {
            okButtonText.set("Add");
        }
        else
        {
            okButtonText.set("Apply");
        }
    }

    public void okButtonClicked()
    {
        _model.get_name().set(this.name.get());

        if (_isNew) {
            TodoList.get_instance().add(_model);
        }

        _listener.hide();
    }

    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> okButtonText = new ObservableField<>();
}
