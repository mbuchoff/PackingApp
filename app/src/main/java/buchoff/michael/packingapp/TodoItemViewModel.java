package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.View;

public class TodoItemViewModel extends ViewModel {
    private TodoItem _todoItem;

    public TodoItemViewModel(TodoItem todoItem)
    {
        _todoItem = todoItem;

        UpdateName();
        UpdateBackgroundColor();

        _todoItem._name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {UpdateName();
            }
        });
        _todoItem._isHighlighted.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                UpdateBackgroundColor();
            }
        });
    }

    public TodoItem getData() {
        return _todoItem;
    }

    public void UpdateName()
    {
        Name.set(_todoItem._name.get());
    }

    public void UpdateBackgroundColor()
    {
        if (_todoItem._isHighlighted.get()) {
            BackgroundColor.set(Color.rgb(200,255,200));
        } else {
            BackgroundColor.set(Color.rgb(255,255,255));
        }
    }

    public final ObservableField<String> Name = new ObservableField<>();
    public final ObservableField<Integer> TextInputVisibility = new ObservableField<>(View.VISIBLE);
    public final ObservableField<Integer> TextReadOnlyVisibility = new ObservableField<>(View.INVISIBLE);
    public final ObservableField<Integer> BackgroundColor = new ObservableField<>(Color.rgb(255, 255, 255));

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        _todoItem._name.set(s.toString());
        if (s.toString().contains("\n")) {
            TextInputVisibility.set(View.INVISIBLE);
            TextReadOnlyVisibility.set(View.VISIBLE);
            _todoItem._isHighlighted.set(true);
        }
    }

    public void PlayButtonClicked(View view) {
        TTSFactory.findTTS(view.getContext()).speak(_todoItem._name.get(), TextToSpeech.QUEUE_ADD, null);
    }

}