package buchoff.michael.packingapp;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

public class ContinuousSpeechRecognizer {
    private SpeechRecognizer _speechRecognizer;
    private Intent _speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    private Context _context;
    private int _prevVolume;
    private AudioManager _audioManager;
    private RecognitionListener _recognitionListener;
    private Listener _listener;
    private boolean _shouldListen = false;
    private boolean _shouldReportSpeechRecognitionReady;

    public interface Listener
    {
        void onResults(String results);
        void onPartialResults(String partialResults);
        void onSpeechRecognitionReady();
        boolean checkMicrophonePermissions();
        void requestMicrophonePermissions();
        boolean checkInternetPermissions();
        void requestInternetPermissions();
    }

    ContinuousSpeechRecognizer(Context context, Listener listener){
        _context = context;

        _audioManager =(AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        _speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context,
                ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));

        _speechIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        _speechIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20L);
        _listener = listener;

        _recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.e("SpeechRecognizer", "onReadyForSpeech");
                if (_shouldReportSpeechRecognitionReady && _listener != null) {
                    _listener.onSpeechRecognitionReady();
                    _shouldReportSpeechRecognitionReady = false;
                }
            }

            @Override
            public void onBeginningOfSpeech() {
                mute();
                Log.e("SpeechRecognizer", "onBeginningOfSpeech");
            }

            @Override
            public void onRmsChanged(float rmsdB) {
//                Log.e("SpeechRecognizer", "onRmsChanged");
            }

            @Override
            public void onBufferReceived(byte[] buffer) {
                //Log.e("SpeechRecognizer", "onBufferReceived");
            }

            @Override
            public void onEndOfSpeech() {
                Log.e("SpeechRecognizer", "onEndOfSpeech");
            }

            @Override
            public void onError(int error) {
                if (error == SpeechRecognizer.ERROR_NETWORK) {
                    Log.e("SpeechRecognizer", "onError - ERROR_NETWORK");
                } else if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
                    Log.e("SpeechRecognizer", "onError - ERROR_RECOGNIZER_BUSY");
                } else if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
                    mute();
                    Log.e("SpeechRecognizer", "onError - ERROR_SPEECH_TIMEOUT");
                    recoverFromError();
                } else if (error == SpeechRecognizer.ERROR_CLIENT) {
                    Log.e("SpeechRecognizer", "onError - ERROR_CLIENT");
                } else if (error == SpeechRecognizer.ERROR_NO_MATCH) {
                    Log.e("SpeechRecognizer", "onError - No recognition result matched.");
                    restartListening();
                } else {
                    Log.e("SpeechRecognizer", "onError - " + Integer.valueOf(error).toString());
                }
            }

            @Override
            public void onResults(Bundle results) {
                String wordsSpoken = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                if (_listener != null)
                {
                    _listener.onResults(wordsSpoken);
                }

                //Log.e("SpeechRecognizer", "onResults - " + wordsSpoken);

                if (_shouldListen) {
                    restartListening();
                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {
                String wordsSpoken = partialResults.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION).get(0);
                //Log.e("SpeechRecognizer", "onPartialResults - " + wordsSpoken);
                if (_listener != null)
                {
                    _listener.onPartialResults(wordsSpoken);
                }
            }

            @Override
            public void onEvent(int eventType, Bundle params) {
                //Log.e("SpeechRecognizer", "onEvent");
            }
        };
        _speechRecognizer.setRecognitionListener(_recognitionListener);
    }

    public void recoverFromError() {
        _speechRecognizer.destroy();
        _speechRecognizer = SpeechRecognizer.createSpeechRecognizer(_context, ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));
        _speechRecognizer.setRecognitionListener(_recognitionListener);

        restartListening();
    }

    public void startListening() {
        _shouldListen = true;
        _shouldReportSpeechRecognitionReady = true;

        if (!_listener.checkMicrophonePermissions()) {
            _listener.requestMicrophonePermissions();
        }

        if (!_listener.checkInternetPermissions()) {
            _listener.requestInternetPermissions();
        }

        restartListening();
    }

    private void restartListening() {
        _speechRecognizer.startListening(_speechIntent);
    }

    void mute() {
        Log.e("SpeechRecognizer", "mute");
        int volume = _audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        if (volume != 0) {
            _prevVolume = volume;
            _audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
        }
    }

    void unmute() {
        Log.e("SpeechRecognizer", "unmute");
        _audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, _prevVolume, AudioManager.ADJUST_SAME);
    }

    public void stopListening()
    {
        _shouldListen = false;
        unmute();
        _speechRecognizer.stopListening();
    }
}
