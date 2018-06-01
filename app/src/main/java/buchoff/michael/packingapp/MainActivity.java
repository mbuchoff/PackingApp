package buchoff.michael.packingapp;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Message;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    LinearLayout _linearLayout;
    ListView _listView;
    LayoutInflater _layoutInflater;
    TodoListAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        _linearLayout = findViewById(R.id.LinearLayout);
        _listView = findViewById(R.id.listView);
        _layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        _adapter = new TodoListAdapter(this, new ArrayList<TodoItemViewModel>());
        _listView.setAdapter(_adapter);
    }

    public void plusButtonClicked(View view) {
        TodoItem todoItem = new TodoItem("Hello");
        _adapter.add(new TodoItemViewModel(todoItem));
    }

    final int MY_PERMISSIONS_REQUEST_RECORD_MICROPHONE = 2468;

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RECORD_MICROPHONE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    public void listenButtonClicked(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECORD_AUDIO}, MY_PERMISSIONS_REQUEST_RECORD_MICROPHONE);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.INTERNET}, MY_PERMISSIONS_REQUEST_RECORD_MICROPHONE);
        }

        final SpeechRecognizer speechRecognizer = SpeechRecognizer.createSpeechRecognizer(view.getContext(),
                ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));
        speechRecognizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.e("SpeechRecognizer", "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.e("SpeechRecognizer", "onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
//                Log.e("SpeechRecognizer", "onRmsChanged");
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                Log.e("SpeechRecognizer", "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech() {
                Log.e("SpeechRecognizer", "onEndOfSpeech");
            }

            @Override
            public void onError(int error) {
                // SpeechRecognizer.ERROR_NETWORK == 2

                if (error == SpeechRecognizer.ERROR_NETWORK) {
                    Log.e("SpeechRecognizer", "onError - ERROR_NETWORK");
                } else {
                    Log.e("SpeechRecognizer", "onError - " + Integer.valueOf(error).toString());
                }
            }

            @Override
            public void onResults(Bundle results) {
                String wordsSpoken = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                Log.e("SpeechRecognizer", "onResults - " + wordsSpoken);
                speechRecognizer.destroy();
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                String wordsSpoken = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                Log.e("SpeechRecognizer", "onPartialResults - " + wordsSpoken);
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                Log.e("SpeechRecognizer", "onEvent");
            }
        });
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        speechRecognizer.startListening(intent);
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
