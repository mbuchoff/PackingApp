package buchoff.michael.packingapp;

// TODO:  Run Lint
// TODO:  Stop Listening Button
// TODO:  Crash after iterating past end of list
// TODO:  Save/Load

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

import buchoff.michael.packingapp.Models.TodoItem;
import buchoff.michael.packingapp.ViewModels.TodoItemViewModel;
import buchoff.michael.packingapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private LinearLayout _linearLayout;
    private RecyclerView _recyclerView;
    private LayoutInflater _layoutInflater;
    private TodoListAdapter _adapter;
    private Button _listenButton;
    private TodoItemsTraverser _todoItemsTraverser;

    ActivityMainBinding _binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _linearLayout = findViewById(R.id.LinearLayout);
        _recyclerView = findViewById(R.id.recyclerView);
        _layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _listenButton = findViewById(R.id.listenButton);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        _recyclerView.setLayoutManager(layoutManager);
        TodoListAdapter adapter = new TodoListAdapter();
        _recyclerView.setAdapter(adapter);

        //_todoItemsTraverser = new TodoItemsTraverser(this, _adapter);
        //_todoItemsTraverser.setListener(new TodoItemsTraverser.Listener() {
        //    @Override
        //    public void onWordsSpoken(String wordsSpoken) {
        //        _listenButton.setText(wordsSpoken);
        //    }
        //
        //    @Override
        //    public void onSpeechRecognitionReady() {
        //        _listenButton.setText("HIT IT!");
        //    }
        //});
    }

    public void plusButtonClicked(View view) {
        TodoItem todoItem = new TodoItem("Hello");
        _listenButton.setEnabled(true);

        Intent intent = new Intent(this, DetailActivity.class);
        startActivity(intent);
    }

    public void listenButtonClicked(View view) {
        _listenButton.setText("Starting speech recognition...");
        _listenButton.setEnabled(false);
        _todoItemsTraverser.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
