package by.android.cradle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import by.android.cradle.CradleGame;
import by.android.cradle.IActivityRequestHandler;


public class DesktopLauncher implements IActivityRequestHandler {
	private static DesktopLauncher application;
	public static void main (String[] argv) {
		if (application == null) {
			application = new DesktopLauncher();
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1000;
		config.height = 800;
		new LwjglApplication(new CradleGame(application), config);

	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub

	}
}