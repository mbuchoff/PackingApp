package buchoff.michael.packingapp.views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import buchoff.michael.packingapp.Models.TodoItem;
import buchoff.michael.packingapp.Models.TodoList;
import buchoff.michael.packingapp.R;
import buchoff.michael.packingapp.viewmodels.TodoItemDetailsViewModel;
import buchoff.michael.packingapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity implements TodoItemDetailsViewModel.Listener {

    public static final String INTENT_INDEX = "Todo Index";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int index = getIntent().getIntExtra(INTENT_INDEX, -1);
        TodoItem todoItem;
        boolean isNew;

        if (index != -1) {
            todoItem = TodoList.get_instance().get(index);
            isNew = false;
        } else {
            todoItem = new TodoItem("");
            isNew = true;
        }

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        TodoItemDetailsViewModel viewModel = new TodoItemDetailsViewModel(this, todoItem, isNew);
        binding.setViewModel(viewModel);
    }

    @Override
    public void hide() {
        finishFromChild(this);
    }
}
