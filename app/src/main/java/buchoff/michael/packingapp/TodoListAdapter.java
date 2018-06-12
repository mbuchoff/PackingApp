package buchoff.michael.packingapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import buchoff.michael.packingapp.databinding.TodoItemBinding;
import java.util.ArrayList;

import static android.databinding.DataBindingUtil.*;

public class TodoListAdapter extends ArrayAdapter<TodoItemViewModel> {

    @Override
    public void add(@Nullable TodoItemViewModel object)
    {
        super.add(object);

        object.set_listener(new TodoItemViewModel.Listener() {
            @Override
            public void requestDeletion(TodoItemViewModel viewModel) {
                TodoListAdapter.this.remove(viewModel);
            }
        });
    }

    public TodoListAdapter(Context context, ArrayList<TodoItemViewModel> viewModels) {
        super(context, 0, viewModels);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TodoItemViewModel viewModel = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.todo_item, parent, false);
        }

        TodoItemBinding todoItemBinding = bind(convertView);
        todoItemBinding.setViewModel(viewModel);

        // Return the completed view to render on screen
        return convertView;
    }
}