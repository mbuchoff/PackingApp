package buchoff.michael.packingapp.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import buchoff.michael.packingapp.Models.TodoItem;
import buchoff.michael.packingapp.R;

public class DetailActivity extends AppCompatActivity {

    public static final String INTENT_TODOITEM = "Todo Item";
    public static final String INTENT_ISNEW = "Is new";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        TodoItem todoItem = (TodoItem) getIntent().getSerializableExtra(INTENT_TODOITEM);
        boolean isNew = getIntent().getBooleanExtra(INTENT_ISNEW, true);
    }
}
