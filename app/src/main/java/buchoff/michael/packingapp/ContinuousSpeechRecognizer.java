package buchoff.michael.packingapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

public class ContinuousSpeechRecognizer {
    SpeechRecognizer _speechRecognizer;
    Intent _speechIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    Context _context;
    int _prevVolume;
    AudioManager _audioManager;
    RecognitionListener _recognitionListener;

    ContinuousSpeechRecognizer(Context context){
        _context = context;
        _audioManager =(AudioManager)_context.getSystemService(Context.AUDIO_SERVICE);
        _speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context,
                ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));

        _speechIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);
        _speechIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 20L);

        _recognitionListener = new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {
                Log.e("SpeechRecognizer", "onReadyForSpeech");
            }

            @Override
            public void onBeginningOfSpeech() {
                Log.e("SpeechRecognizer", "onBeginningOfSpeech");
                unmute();
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
                if (error == SpeechRecognizer.ERROR_NETWORK) {
                    Log.e("SpeechRecognizer", "onError - ERROR_NETWORK");
                } else if (error == SpeechRecognizer.ERROR_RECOGNIZER_BUSY) {
                    Log.e("SpeechRecognizer", "onError - ERROR_RECOGNIZER_BUSY");
                } else if (error == SpeechRecognizer.ERROR_SPEECH_TIMEOUT) {
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
                Log.e("SpeechRecognizer", "onResults - " + wordsSpoken);
                restartListening();
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
        };
        _speechRecognizer.setRecognitionListener(_recognitionListener);
    }

    public void recoverFromError() {
        _speechRecognizer.destroy();
        _speechRecognizer = SpeechRecognizer.createSpeechRecognizer(_context,
                ComponentName.unflattenFromString("com.google.android.googlequicksearchbox/com.google.android.voicesearch.serviceapi.GoogleRecognitionService"));
        _speechRecognizer.setRecognitionListener(_recognitionListener);

        startListening();
    }

    public void startListening() {
        mute();
        restartListening();
    }

    private void restartListening() {
        _speechRecognizer.startListening(_speechIntent);
    }

    void mute() {
        _prevVolume = _audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        _audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
    }

    void unmute() {
        _audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, _prevVolume, AudioManager.ADJUST_SAME);
    }
}
