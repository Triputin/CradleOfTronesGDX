package by.android.cradle;

public interface IGoogleServices {

    public boolean hasVideoLoaded();
    public void loadRewardedVideoAd();
    public void showRewardedVideoAd();
    public void setVideoEventListener(IVideoEventListener listener);
}
