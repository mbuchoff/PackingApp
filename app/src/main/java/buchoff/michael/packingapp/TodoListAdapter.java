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

import buchoff.michael.packingapp.Models.TodoList;
import buchoff.michael.packingapp.ViewModels.TodoItemViewModel;
import buchoff.michael.packingapp.ViewModels.TodoListViewModel;
import buchoff.michael.packingapp.databinding.TodoItemBinding;
import java.util.ArrayList;

import static android.databinding.DataBindingUtil.*;

public class TodoListAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {
    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoItemViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
        TodoListViewModel todoListViewModel = TodoListViewModel.get_instance();
        TodoItemViewModel todoItemViewModel = todoListViewModel.get_todoItemViewModel(position);
        holder.setViewModel(todoItemViewModel);
    }

    @Override
    public int getItemCount() {
        return TodoList.get_instance().size();
    }
}