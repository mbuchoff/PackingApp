package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.os.Bundle;

public class TodoItem extends ViewModel {
    private static int i = 0;

    public TodoItem() { firstName = new Integer(i++).toString();}

    public String firstName;
    public final String name = "TheName";
}