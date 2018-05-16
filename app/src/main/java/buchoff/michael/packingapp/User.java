package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableField;
import android.view.View;

public class User extends ViewModel {
    public ObservableField<String> name = new ObservableField<>();
    public String hometown;

    public User(String name, String hometown) {
        this.name.set(name);
        this.hometown = hometown;
    }

    public void onClicksAreHard(View view) {
        name.set(name.get() + "A");
    }
}