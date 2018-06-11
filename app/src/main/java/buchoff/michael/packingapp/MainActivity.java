package buchoff.michael.packingapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    LinearLayout _linearLayout;
    ListView _listView;
    LayoutInflater _layoutInflater;
    TodoListAdapter _adapter;
    Button _listenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _linearLayout = findViewById(R.id.LinearLayout);
        _listView = findViewById(R.id.listView);
        _layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        _listenButton = findViewById(R.id.listenButton);

        _adapter = new TodoListAdapter(this, new ArrayList<TodoItemViewModel>());
        _listView.setAdapter(_adapter);
    }

    public void plusButtonClicked(View view) {
        TodoItem todoItem = new TodoItem("Hello");
        _adapter.add(new TodoItemViewModel(todoItem));
    }

    public void listenButtonClicked(View view) {
        new TodoItemsTraverser(_adapter);

        ContinuousSpeechRecognizer continuousSpeechRecognizer = new ContinuousSpeechRecognizer(this);
        continuousSpeechRecognizer.setListener(new ContinuousSpeechRecognizer.Listener() {
            @Override
            public void onResults(String results) {
                _listenButton.setText(results);
            }

            @Override
            public void onPartialResults(String partialResults) {
                _listenButton.setText(partialResults);
            }
        });
        continuousSpeechRecognizer.startListening();

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
