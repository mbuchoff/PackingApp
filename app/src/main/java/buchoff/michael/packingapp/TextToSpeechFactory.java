package buchoff.michael.packingapp;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.Semaphore;

class TTSFactory {
    static TextToSpeech findTTS(Context context) {
        for (TTSContainer ttsContainer : ttsContainers) {
            if (ttsContainer._context == context) {
                return ttsContainer._tts;
            }
        }

        TTSContainer ttsContainer = new TTSContainer(context);
        ttsContainers.add(ttsContainer);
        return ttsContainer._tts;
    }

    private static ArrayList<TTSContainer> ttsContainers = new ArrayList<>();

    private static class TTSContainer {
        private TextToSpeech _tts;
        private Context _context;
        private Semaphore _ttsLock = new Semaphore(1);

        TTSContainer(Context context) {
            _context = context;
            if (_tts == null) {
                try {
                    _ttsLock.acquire();

                    if (_tts == null) {
                        _tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                            @Override
                            public void onInit(int status) {
                                if (status != TextToSpeech.ERROR) {
                                    _tts.setLanguage(Locale.US);
                                    _ttsLock.release();
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    Toast.makeText(context, "Unable to initialize text-to-speech!", Toast.LENGTH_LONG).show();
                    _ttsLock.release();
                }
            }
        }
    }
}
