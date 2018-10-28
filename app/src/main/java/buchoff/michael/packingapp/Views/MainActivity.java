package buchoff.michael.packingapp.views;

// TODO:  Run Lint
// TODO:  Stop Listening Button
// TODO:  Crash after iterating past end of list
// TODO:  Save/Load

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import buchoff.michael.packingapp.R;
import buchoff.michael.packingapp.TodoItemsTraverser;
import buchoff.michael.packingapp.viewmodels.TodoListViewModel;
import buchoff.michael.packingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements TodoListViewModel.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        TodoListViewModel viewModel = new TodoListViewModel(this, this);
        binding.setViewModel(viewModel);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        TodoListAdapter adapter = new TodoListAdapter(viewModel);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void editTodoListItem(int index) {
        showTodoItemDetailActivity(index);
    }

    @Override
    public void addTodoListItem() {
        showTodoItemDetailActivity(-1);
    }

    private void showTodoItemDetailActivity(int index){
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(DetailActivity.INTENT_INDEX, index);
        startActivity(intent);
    }
}
