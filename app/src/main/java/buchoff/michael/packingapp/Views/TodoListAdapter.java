package buchoff.michael.packingapp.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import buchoff.michael.packingapp.Models.TodoList;
import buchoff.michael.packingapp.R;
import buchoff.michael.packingapp.ViewModels.TodoItemViewModel;
import buchoff.michael.packingapp.ViewModels.TodoListViewModel;

public class TodoListAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {
    TodoListViewModel _viewModel;

    public TodoListAdapter(TodoListViewModel viewModel)
    {
        _viewModel = viewModel;
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoItemViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
        TodoItemViewModel todoItemViewModel = _viewModel.get_todoItemViewModel(position);
        holder.bind(todoItemViewModel);
    }

    @Override
    public int getItemCount() {
        return TodoList.get_instance().size();
    }
}