package buchoff.michael.packingapp.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Observable;
import java.util.Observer;

import buchoff.michael.packingapp.models.TodoList;
import buchoff.michael.packingapp.R;
import buchoff.michael.packingapp.viewmodels.TodoListItemViewModel;
import buchoff.michael.packingapp.viewmodels.TodoListViewModel;

public class TodoListAdapter extends RecyclerView.Adapter<TodoItemViewHolder> {
    TodoListViewModel _viewModel;

    public TodoListAdapter(TodoListViewModel viewModel)
    {
        _viewModel = viewModel;

        viewModel.addObserver(new Observer() {
            @Override
            public void update(Observable o, Object arg) {
                TodoListAdapter.this.notifyDataSetChanged();
            }
        });
    }

    @NonNull
    @Override
    public TodoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.todo_item, parent, false);
        return new TodoItemViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemViewHolder holder, int position) {
        TodoListItemViewModel todoListItemViewModel = _viewModel.get_todoItemViewModel(position);
        holder.bind(todoListItemViewModel);
    }

    @Override
    public int getItemCount() {
        return TodoList.get_instance().size();
    }
}