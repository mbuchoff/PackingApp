package buchoff.michael.packingapp;

public interface RequestPermissionsListener {
    boolean checkMicrophonePermissions();
    void requestMicrophonePermissions();
    boolean checkInternetPermissions();
    void requestInternetPermissions();
}
