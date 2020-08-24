package by.android.cradle;

public interface IVideoEventListener {
    void onRewardedEvent(String type, int amount);
    void onRewardedVideoAdLoadedEvent();
    void onRewardedVideoAdClosedEvent();
}
