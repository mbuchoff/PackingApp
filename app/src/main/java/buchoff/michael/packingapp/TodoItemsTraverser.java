package buchoff.michael.packingapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.Arrays;
import java.util.HashMap;

import buchoff.michael.packingapp.models.TodoItem;
import buchoff.michael.packingapp.models.TodoList;

public class TodoItemsTraverser {
    private int _todoItemIndex = 0;
    private ContinuousSpeechRecognizer _continuousSpeechRecognizer;
    private String _results = "";
    private Listener _listener;
    private TextToSpeech _tts;
    private Handler _uiHandler;
    private boolean _keyphraseDetectedThisResultsSession;

    private Runnable _speechRecognizerStartRunnable = new Runnable() {
        @Override
        public void run() {
            _continuousSpeechRecognizer.startListening();
        }
    };

    private UtteranceProgressListener _utteranceProgressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) { }

        @Override
        public void onDone(String utteranceId) {
            _uiHandler.post(_speechRecognizerStartRunnable);
        }

        @Override
        public void onError(String utteranceId) { }
    };

    public interface Listener
    {
        void onWordsSpoken(String wordsSpoken);
        void onSpeechRecognitionReady();
        boolean checkMicrophonePermissions();
        void requestMicrophonePermissions();
        boolean checkInternetPermissions();
        void requestInternetPermissions();
    }

    public TodoItemsTraverser(final Context context, Listener listener) {
        _listener = listener;
        _uiHandler = new Handler();
        _tts = TTSFactory.findTTS(context);
        ContinuousSpeechRecognizer.Listener continuousSpeechRecognizerListener = new ContinuousSpeechRecognizer.Listener() {
            @Override
            public void onResults(String results) {
                if (!_keyphraseDetectedThisResultsSession)
                {
                    _results = _results + " " + results;
                }

                wordsSpoken(_results);

                _keyphraseDetectedThisResultsSession = false;
            }

            @Override
            public void onPartialResults(String partialResults) {
                wordsSpoken(_results + " " + partialResults);
            }

            @Override
            public void onSpeechRecognitionReady() {
                if (_listener != null) {
                    _listener.onSpeechRecognitionReady();
                }
            }

            @Override
            public boolean checkMicrophonePermissions() {
                return _listener.checkMicrophonePermissions();
            }

            @Override
            public void requestMicrophonePermissions() {
                _listener.requestMicrophonePermissions();
            }

            @Override
            public boolean checkInternetPermissions() {
                return _listener.checkInternetPermissions();
            }

            @Override
            public void requestInternetPermissions() {
                _listener.requestInternetPermissions();
            }

            void wordsSpoken(String words)
            {
                if (_listener != null) {
                    _listener.onWordsSpoken(words);
                }

                if ( !_keyphraseDetectedThisResultsSession ) {
                    if (Arrays.asList(words.toLowerCase().split(" ")).contains("check")) {
                        endTodoItem();
                        _todoItemIndex++;
                        beginTodoItem();

                        _results = "";
                        _keyphraseDetectedThisResultsSession = true;
                    }
                }
            }
        };

        _continuousSpeechRecognizer = new ContinuousSpeechRecognizer(context, continuousSpeechRecognizerListener);
    }

    public void start()
    {
        _keyphraseDetectedThisResultsSession = false;
        beginTodoItem();
    }

    private void beginTodoItem()
    {
        TodoItem todoItem = get_todoItem();
        todoItem.get_status().set(TodoItem.Status.ACTIVE);
        _tts.setOnUtteranceProgressListener(_utteranceProgressListener);
        HashMap<String, String> ttsParams = new HashMap<>();
        ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "Finished speaking");
        _tts.speak(todoItem.get_name().get(), TextToSpeech.QUEUE_FLUSH, ttsParams);
    }

    private void endTodoItem()
    {
        TodoItem todoItem = get_todoItem();
        todoItem.get_status().set(TodoItem.Status.FINISHED);
        _continuousSpeechRecognizer.stopListening();
    }

    private TodoItem get_todoItem()
    {
        return TodoList.get_instance().get(_todoItemIndex);
    }
}
