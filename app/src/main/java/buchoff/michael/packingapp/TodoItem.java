package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.view.View;

public class TodoItem extends ViewModel {
    private static int i = 0;

    public TodoItem() { firstName.set(Integer.valueOf(i++).toString());}

    public ObservableField<String> firstName = new ObservableField<>();
    public final String name = "TheName";

    public void onClicksAreHard(View view) {
        firstName.set(firstName.get() + "A");
    }
}