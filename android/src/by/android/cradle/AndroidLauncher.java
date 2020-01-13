package by.android.cradle;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import by.android.cradle.IActivityRequestHandler;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
//import com.mobclix.android.sdk.MobclixMMABannerXLAdView;


public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler  {

	protected AdView adView;

	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;

	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
				case SHOW_ADS:
				{
					adView.setVisibility(View.VISIBLE);
					break;
				}
				case HIDE_ADS:
				{
					adView.setVisibility(View.GONE);
					break;
				}
			}
		}
	};

	@Override public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		// Create the libgdx View
		View gameView = initializeForView(new CradleGame(this), config);

		// Create and setup the AdMob view
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		//Test settings
		//adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		//MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");
		//Work settings
		adView.setAdUnitId("ca-app-pub-6101517213308128/5757251577");
		//MobileAds.initialize(this, "ca-app-pub-6101517213308128/5757251577");
		MobileAds.initialize(this, "ca-app-pub-6101517213308128~2009578256");


		MobileAds.initialize(this, new OnInitializationCompleteListener() {
			@Override
			public void onInitializationComplete(InitializationStatus initializationStatus) {
				System.out.println(initializationStatus.toString());
				Collection<AdapterStatus> mp = initializationStatus.getAdapterStatusMap().values();
				for (AdapterStatus a:mp) {
					System.out.println(a.getInitializationState());
					System.out.println(a.getDescription());;
				}
			}
		});

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);

		// Add the libgdx view
		layout.addView(gameView);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		layout.addView(adView, adParams);
/*
		// Add the test view
		RelativeLayout.LayoutParams params =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        TextView view = new TextView();

		layout.addView(view, params);
*/

		// Hook it all up
		setContentView(layout);
	}

	// This is the callback that posts a message for the handler
	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
}