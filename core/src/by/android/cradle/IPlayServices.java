package by.android.cradle;


public interface IPlayServices
{
    public void onStartMethod();
    public void signIn();
    public void signOut();
    public void rateGame();
    public void unlockAchievement(String str);
    public void submitScore(String LeaderBoard,int highScore);
    public void submitLevel(int highLevel);
    public void showAchievement();
    public void showScore(String LeaderBoard);
    public void showLevel();
    public boolean isSignedIn();

    public void logEvent(String id, String name, String content_type);
    public void logLevelUpEvent(long idLevel);
    public void logLevelStartEvent(long levelName);
    public void logLevelEndEvent( long levelName, String success);

    public void connectUs();
}