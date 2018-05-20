package buchoff.michael.packingapp;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.databinding.Observable;
import android.databinding.ObservableField;
import android.speech.tts.TextToSpeech;
import android.view.View;

import org.w3c.dom.Text;

import java.util.Locale;

public class TodoItemViewModel extends ViewModel {
    private TodoItem _todoItem;
    private Context _context;

    public TodoItemViewModel(Context context, TodoItem todoItem)
    {
        _todoItem = todoItem;
        _context = context;

        UpdateName();
        _todoItem._name.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                UpdateName();
            }
        });
    }

    public void UpdateName()
    {
        Name.set(_todoItem._name.get());
    }

    public final ObservableField<String> Name = new ObservableField<>();
    public final ObservableField<Integer> TextInputVisibility = new ObservableField<>(View.VISIBLE);
    public final ObservableField<Integer> TextReadOnlyVisibility = new ObservableField<>(View.INVISIBLE);

    public void onTextChanged(CharSequence s, int start, int before, int count) {
        _todoItem._name.set(s.toString());
        if (s.toString().contains("\n"))
        {
            TextInputVisibility.set(View.INVISIBLE);
            TextReadOnlyVisibility.set(View.VISIBLE);
        }
    }

    TextToSpeech t1 = null;

    public void PlayButtonClicked(View view)
    {
        t1=new TextToSpeech(_context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                    t1.speak(_todoItem._name.get(), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
}