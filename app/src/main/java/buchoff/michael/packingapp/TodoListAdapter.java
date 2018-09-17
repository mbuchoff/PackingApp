package buchoff.michael.packingapp;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
//import buchoff.michael.packingapp.databinding.TodoItemBinding;
import java.util.ArrayList;

import static android.databinding.DataBindingUtil.*;

public class TodoListAdapter extends RecyclerView.Adapter<TodoListViewHolder> {
    // TODO:  Move the array out of here and make it observable
    ArrayList<TodoItemViewModel> _viewModels = new ArrayList<>();

    @NonNull
    @Override
    public TodoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoListViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListViewHolder holder, int position) {
        while (_viewModels.size() <= position)
        {
            _viewModels.add(new TodoItemViewModel(new TodoItem("default name")));
        }

        holder.setViewModel(_viewModels.get(position));
    }

    @Override
    public int getItemCount() {
        return _viewModels.size();
    }
}