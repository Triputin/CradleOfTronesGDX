package by.android.cradle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import by.android.cradle.CradleGame;
import by.android.cradle.IActivityRequestHandler;
import by.android.cradle.INotification;
import by.android.cradle.IPlayServices;
import by.android.cradle.SplashScreen;


public class DesktopLauncher implements IActivityRequestHandler, IPlayServices, INotification {
	private static DesktopLauncher application;
	public static void main (String[] argv) {
		if (application == null) {
			application = new DesktopLauncher();
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		//config.width = 854;
		//config.height = 480;

		config.width = 900;
		config.height = 600;
		CradleGame cradleGame = new CradleGame(application,application,application);
		new LwjglApplication(cradleGame, config);

	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStartMethod() {

	}

	@Override
	public void signIn() {

	}

	@Override
	public void signOut() {

	}

	@Override
	public void rateGame() {

	}

	@Override
	public void unlockAchievement(String str) {

	}

	@Override
	public void submitScore(String LeaderBoard, int highScore) {

	}

	@Override
	public void submitLevel(int highLevel) {

	}

	@Override
	public void showAchievement() {

	}

	@Override
	public void showScore(String LeaderBoard) {

	}

	@Override
	public void showLevel() {

	}

	@Override
	public boolean isSignedIn() {
		return false;
	}

	@Override
	public void logEvent(String id, String name, String content_type){
	}

	@Override
	public void logLevelUpEvent(String id, String name, String content_type){
	}

	@Override
	public void connectUs() {

	}

	@Override
	public void scheduleReminder(long duration_minutes,String title, String message,String messageId, String tag){

	}

	@Override
	public void cancelReminder(String tag){

	}
}