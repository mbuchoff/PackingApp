package buchoff.michael.packingapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import buchoff.michael.packingapp.databinding.TodoItemBinding;

import static android.databinding.DataBindingUtil.bind;

public class TodoListViewHolder extends RecyclerView.ViewHolder {
    View mItemView;

    public TodoListViewHolder(View itemView) {
        super(itemView);
        mItemView = itemView;
    }

    public void setViewModel(TodoItemViewModel viewModel)
    {
        TodoItemBinding todoItemBinding = bind(mItemView);
        todoItemBinding.setViewModel(viewModel);
    }
}
