package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.view.View;

public class TodoItemViewModel extends ViewModel {
    private static int i = 0;

    public TodoItemViewModel()
    {
        firstName = new ObservableField<>(new Integer(i++).toString());
    }

    public ObservableField<String> firstName;
    public final String name = "TheName";

    public void onClick(View view)
    {
        firstName.set(new Integer(Integer.parseInt(firstName.get()) + 1).toString());
    }
}