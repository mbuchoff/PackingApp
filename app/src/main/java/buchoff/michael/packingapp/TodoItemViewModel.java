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
    private boolean _isEditing = true;

    public TodoItemViewModel(TodoItem todoItem)
    {
        _todoItem = todoItem;

        UpdateName();
        UpdateBackgroundColor();
        UpdateEditingMode();

        _todoItem.get_name().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {UpdateName();
            }
        });
        _todoItem.get_isHighlighted().addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                UpdateBackgroundColor();
            }
        });
    }

    private void UpdateEditingMode()
    {
        if (_isEditing) {
            TextInputVisibility.set(View.VISIBLE);
            TextReadOnlyVisibility.set(View.INVISIBLE);
        } else {
            TextInputVisibility.set(View.INVISIBLE);
            TextReadOnlyVisibility.set(View.VISIBLE);
        }
    }

    public TodoItem getData() {
        return _todoItem;
    }

    public void UpdateName()
    {
        Name.set(_todoItem.get_name().get());
    }

    public void UpdateBackgroundColor()
    {
        if (_todoItem.get_isHighlighted().get()) {
            BackgroundColor.set(Color.rgb(200,255,200));
        } else {
            BackgroundColor.set(Color.rgb(255,255,255));
        }
    }

    public final ObservableField<String> Name = new ObservableField<>();
    public final ObservableField<Integer> TextInputVisibility = new ObservableField<>();
    public final ObservableField<Integer> TextReadOnlyVisibility = new ObservableField<>();
    public final ObservableField<Integer> BackgroundColor = new ObservableField<>();

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        _todoItem.get_name().set(s.toString());
        if (s.toString().contains("\n")) {
            _isEditing = false;
            UpdateEditingMode();
        }
    }

    public void PlayButtonClicked(View view) {
        TTSFactory.findTTS(view.getContext()).speak(_todoItem.get_name().get(), TextToSpeech.QUEUE_FLUSH, null);
    }

}