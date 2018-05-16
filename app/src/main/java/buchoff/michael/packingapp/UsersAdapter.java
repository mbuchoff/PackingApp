package buchoff.michael.packingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

import static android.databinding.DataBindingUtil.bind;

public class UsersAdapter extends ArrayAdapter<User> {
    public UsersAdapter(Context context, ArrayList<User> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        User user = getItem(position);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.todo_item, parent, false);
        }

        buchoff.michael.packingapp.databinding.TodoItemBinding todoItemBinding = bind(convertView);
        todoItemBinding.setUser(user);

        // Return the completed view to render on screen
        return convertView;
    }
}