package buchoff.michael.packingapp.Views;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import buchoff.michael.packingapp.Models.TodoItem;
import buchoff.michael.packingapp.R;
import buchoff.michael.packingapp.ViewModels.TodoItemDetailsViewModel;
import buchoff.michael.packingapp.databinding.ActivityDetailBinding;

public class DetailActivity extends AppCompatActivity implements TodoItemDetailsViewModel.Listener {

    public static final String INTENT_TODOITEM = "Todo Item";
    public static final String INTENT_ISNEW = "Is new";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TodoItem todoItem = (TodoItem) getIntent().getSerializableExtra(INTENT_TODOITEM);
        boolean isNew = getIntent().getBooleanExtra(INTENT_ISNEW, true);

        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        TodoItemDetailsViewModel viewModel = new TodoItemDetailsViewModel(this, todoItem, isNew);
        binding.setViewModel(viewModel);
    }

    @Override
    public void hide() {
        finishFromChild(this);
    }
}
