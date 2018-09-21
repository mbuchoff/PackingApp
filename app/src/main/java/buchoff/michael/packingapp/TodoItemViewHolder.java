package buchoff.michael.packingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import buchoff.michael.packingapp.ViewModels.TodoItemViewModel;
import buchoff.michael.packingapp.databinding.TodoItemBinding;

import static android.databinding.DataBindingUtil.bind;

public class TodoItemViewHolder extends RecyclerView.ViewHolder {
    final View _itemView;

    public TodoItemViewHolder(View itemView) {
        super(itemView);
        _itemView = itemView;
    }

    public void setViewModel(TodoItemViewModel viewModel)
    {
        TodoItemBinding todoItemBinding = bind(_itemView);
        todoItemBinding.setViewModel(viewModel);
    }
}
