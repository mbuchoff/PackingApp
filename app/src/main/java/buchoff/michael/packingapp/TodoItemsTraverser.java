package buchoff.michael.packingapp;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.HashMap;

public class TodoItemsTraverser {
    ArrayAdapter<TodoItemViewModel> _todoItemViewModels;
    int _todoItemIndex = 0;
    ContinuousSpeechRecognizer _continuousSpeechRecognizer;
    String _results = "";
    String _partialResults;
    Listener _listener = null;
    TextToSpeech _tts;
    Handler _uiHandler;

    interface Listener
    {
        void onWordsSpoken(String wordsSpoken);
    }

    void setListener(Listener listener)
    {
        _listener = listener;
    }

    public TodoItemsTraverser(Activity activity, ArrayAdapter<TodoItemViewModel> todoItemViewModels) {
        _uiHandler = new Handler();
        _tts = TTSFactory.findTTS(activity.getApplicationContext());
        _todoItemViewModels = todoItemViewModels;
        _continuousSpeechRecognizer = new ContinuousSpeechRecognizer(activity);
        _continuousSpeechRecognizer.setListener(new ContinuousSpeechRecognizer.Listener() {
            @Override
            public void onResults(String results) {
                _results = _results + " " + results;

                if (_listener != null) {
                    _listener.onWordsSpoken(_results + " " + _partialResults);
                }
            }

            @Override
            public void onPartialResults(String partialResults) {
                _partialResults = partialResults;

                if (_listener != null) {
                    _listener.onWordsSpoken(_results + " " + _partialResults);
                }
            }
        });
    }

    public void start()
    {
        TodoItem todoItem = _todoItemViewModels.getItem(_todoItemIndex).getData();
        todoItem.get_isHighlighted().set(true);
        _tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String utteranceId) {

            }

            @Override
            public void onDone(String utteranceId) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        _continuousSpeechRecognizer.startListening();
                    }
                };

                _uiHandler.post(runnable);
            }

            @Override
            public void onError(String utteranceId) {

            }
        });
        HashMap<String, String> ttsParams = new HashMap<>();
        ttsParams.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "Finished speaking");
        _tts.speak(todoItem.get_name().get(), TextToSpeech.QUEUE_FLUSH, ttsParams);
    }
}
