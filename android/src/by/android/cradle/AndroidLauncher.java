package by.android.cradle;

import android.app.AlertDialog;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.badlogic.gdx.Gdx;
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


//import com.yandex.metrica.YandexMetrica;
//import com.yandex.metrica.YandexMetricaConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

//GPS
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.games.AchievementsClient;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.EventsClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.PlayersClient;
import com.google.android.gms.games.event.Event;
import com.google.android.gms.games.event.EventBuffer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.example.games.basegameutils.GameHelper;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler,IPlayServices,INotification, IGoogleServices, RewardedVideoAdListener  {

	public static String Default_notification_channel_id="channel01";
	public static String Default_notification_channel_name="Information";

	public static String MessageTitleKey = "Title";
	public static String MessageTextKey = "Text";
	public static String MessageIdKey = "Id";
	private static long back_pressed;

	protected AdView adView;
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	private View gameView;
	private RelativeLayout.LayoutParams gameViewParams;
	private AndroidLauncher self;
	//GPS second try
	private GameHelper gameHelper;
	private FirebaseAnalytics mFirebaseAnalytics;
	private CradleGame cradleGame;

	private RewardedVideoAd adRewardedVideoView;
	private static final String REWARDED_VIDEO_AD_UNIT_ID = "ca-app-pub-6101517213308128/2984805776";
	//private static final String REWARDED_VIDEO_AD_UNIT_ID ="ca-app-pub-3940256099942544/5224354917"; // Test video ad
	private IVideoEventListener vel;
	private boolean is_video_ad_loaded;

	//GPS First try
	// Client used to sign in with Google APIs
	//private GoogleSignInClient mGoogleSignInClient;

	// Client variables
/*
	private AchievementsClient mAchievementsClient;
	private LeaderboardsClient mLeaderboardsClient;
	private EventsClient mEventsClient;
	private PlayersClient mPlayersClient;

	// request codes we use when invoking an external activity
	private static final int RC_UNUSED = 5001;
	private static final int RC_SIGN_IN = 9001;
*/
	// tag for debug logging
	private static final String TAG = "CradleOfThrones";


	protected Handler handler = new Handler()
	{
		@Override
		public void handleMessage(Message msg) {
			if (adView != null) {
				switch (msg.what) {
					case SHOW_ADS: {
						adView.setVisibility(View.VISIBLE);
						gameViewParams.bottomMargin = AdSize.BANNER.getHeightInPixels(self) + 2;
						gameView.setLayoutParams(gameViewParams);
						break;
					}
					case HIDE_ADS: {
						adView.setVisibility(View.GONE);
						gameViewParams.bottomMargin = 0;
						gameView.setLayoutParams(gameViewParams);
						break;
					}
				}
			}
		}
	};

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		self = this;
		//GPS second try start
		gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);
		gameHelper.enableDebugLog(true);


		GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
			@Override
			public void onSignInFailed() {
				GdxLog.print("onSignInFailed","gameHelperListener");
			}

			@Override
			public void onSignInSucceeded() {
				GdxLog.print("onSignInSucceeded","gameHelperListener");

			}
		};
		//GPS Second try
		gameHelper.setup(gameHelperListener);
		//GPS second try finish


		// Obtain the FirebaseAnalytics instance.
		mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);


		// Create the layout
		RelativeLayout layout = new RelativeLayout(this);

		// Do the stuff that initialize() would do for you
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		// Create the libgdx View
		cradleGame = new CradleGame(this,this,this,this);
		gameView = initializeForView(cradleGame, config);

		// Create and setup the AdMob view for banner
		//adView = new AdView(this);
		//adView.setAdSize(AdSize.BANNER);

		//Test settings
		//adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		//MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

		//Work settings
		//adView.setAdUnitId("ca-app-pub-6101517213308128/5757251577");
		//MobileAds.initialize(this, "ca-app-pub-6101517213308128~2009578256");

/*
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

		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("10D929FF85B7803BB2D5CEE273F4FBFE")
				.addTestDevice("51E3C88D530A640ED6513B1BD4C9DB62")
				.build();
		adView.loadAd(adRequest);
*/

		// Add the libgdx view
		gameViewParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);


		layout.addView(gameView,gameViewParams);

		// Add the AdMob view
		RelativeLayout.LayoutParams adParams =
				new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
						RelativeLayout.LayoutParams.WRAP_CONTENT);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		adParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

		//layout.addView(adView, adParams);
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


// Create the client used to sign in to Google services.
/*
		mGoogleSignInClient = GoogleSignIn.getClient(this,
				new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).build());

		signInSilently2();
*/

/*
		// Creating an extended library configuration.
		YandexMetricaConfig configY = YandexMetricaConfig.newConfigBuilder("6f7d31e8-fe9d-426c-823e-cbcfecc493b7").build();
		// Initializing the AppMetrica SDK.
		YandexMetrica.activate(getApplicationContext(), configY);
		// Automatic tracking of user activity.
		YandexMetrica.enableActivityAutoTracking((Application) this);

*/

		// Hook it all up
		setContentView(layout);

		setupRewarded();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			// Create channel to show notifications.
			String channelId  = AndroidLauncher.Default_notification_channel_id;
			String channelName = AndroidLauncher.Default_notification_channel_name;
			NotificationManager notificationManager = getSystemService(NotificationManager.class);
			NotificationChannel channel = new NotificationChannel(channelId, channelName,
					NotificationManager.IMPORTANCE_LOW);
			notificationManager.createNotificationChannel(channel);
		}

		// If a notification message is tapped, any data accompanying the notification
		// message is available in the intent extras. In this sample the launcher
		// intent is fired when the notification is tapped, so any accompanying data would
		// be handled here. If you want a different intent fired, set the click_action
		// field of the notification message to the desired intent. The launcher intent
		// is used when no click_action is specified.
		//
		// Handle possible data accompanying notification message.
		// [START handle_data_extras]
		if (getIntent().getExtras() != null) {
			for (String key : getIntent().getExtras().keySet()) {
				Object value = getIntent().getExtras().get(key);
				Log.d(TAG, "Key: " + key + " Value: " + value);
			}
		}
		// [END handle_data_extras]



		// Get token
		// [START retrieve_current_token]
		FirebaseInstanceId.getInstance().getInstanceId()
				.addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
					@Override
					public void onComplete(@NonNull Task<InstanceIdResult> task) {
						if (!task.isSuccessful()) {
							Log.w(TAG, "getInstanceId failed", task.getException());
							return;
						}

						// Get new Instance ID token
						String token = task.getResult().getToken();

						// Log and toast
						System.out.println("Token: "+token);
					}
				});
		// [END retrieve_current_token]



	}

	// This is the callback that posts a message for the handler
	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
	}
	public boolean isAdMobViewCreated(){
		if(adView!=null){
			return  true;
		}else{
			return false;
		}
	}

	public boolean isSignedIn() {
		//return GoogleSignIn.getLastSignedInAccount(this) != null;
		return gameHelper.isSignedIn();
	}

	/*
	private void signInSilently() {
		Log.d(TAG, "signInSilently()");

		mGoogleSignInClient.silentSignIn().addOnCompleteListener(this,
				new OnCompleteListener<GoogleSignInAccount>() {
					@Override
					public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
						if (task.isSuccessful()) {
							Log.d(TAG, "signInSilently(): success");
							onConnected(task.getResult());
						} else {
							Log.d(TAG, "signInSilently(): failure", task.getException());
							onDisconnected();
						}
					}
				});
	}

	private void signInSilently2() {
		GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
		GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
		if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
			// Already signed in.
			// The signed in account is stored in the 'account' variable.
			GoogleSignInAccount signedInAccount = account;
		} else {
			// Haven't been signed-in before. Try the silent sign-in first.
			GoogleSignInClient signInClient = GoogleSignIn.getClient(this, signInOptions);
			signInClient
					.silentSignIn()
					.addOnCompleteListener(
							this,
							new OnCompleteListener<GoogleSignInAccount>() {
								@Override
								public void onComplete(@NonNull Task<GoogleSignInAccount> task) {
									if (task.isSuccessful()) {
										// The signed in account is stored in the task's result.
										GoogleSignInAccount signedInAccount = task.getResult();
									} else {
										// Player will need to sign-in explicitly using via UI.
										// See [sign-in best practices](http://developers.google.com/games/services/checklist) for guidance on how and when to implement Interactive Sign-in,
										// and [Performing Interactive Sign-in](http://developers.google.com/games/services/android/signin#performing_interactive_sign-in) for details on how to implement
										// Interactive Sign-in.
										startSignInIntent();
									}
								}
							});
		}
	}

	private void startSignInIntent() {
		startActivityForResult(mGoogleSignInClient.getSignInIntent(), RC_SIGN_IN);
	}


	private void startSignInIntent2() {
		GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
				GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
		Intent intent = signInClient.getSignInIntent();
		startActivityForResult(intent, RC_SIGN_IN);
	}
	private void onConnected(GoogleSignInAccount googleSignInAccount) {
		Log.d(TAG, "onConnected(): connected to Google APIs");

		mAchievementsClient = Games.getAchievementsClient(this, googleSignInAccount);
		mLeaderboardsClient = Games.getLeaderboardsClient(this, googleSignInAccount);
		mEventsClient = Games.getEventsClient(this, googleSignInAccount);
		mPlayersClient = Games.getPlayersClient(this, googleSignInAccount);


		//loadAndPrintEvents();
	}

	private void onDisconnected() {
		Log.d(TAG, "onDisconnected()");

		mAchievementsClient = null;
		mLeaderboardsClient = null;
		mPlayersClient = null;

	}
*/
	/*
	@Override
	protected void onResume() {
		super.onResume();
		Log.d(TAG, "onResume()");

		// Since the state of the signed in user can change when the activity is not active
		// it is recommended to try and sign in silently from when the app resumes.
		signInSilently();
	}


	public void signOut() {
		Log.d(TAG, "signOut()");

		if (!isSignedIn()) {
			Log.w(TAG, "signOut() called, but was not signed in!");
			return;
		}

		mGoogleSignInClient.signOut().addOnCompleteListener(this,
				new OnCompleteListener<Void>() {
					@Override
					public void onComplete(@NonNull Task<Void> task) {
						boolean successful = task.isSuccessful();
						Log.d(TAG, "signOut(): " + (successful ? "success" : "failed"));

						onDisconnected();
					}
				});
	}



	protected void onActivityResult2(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RC_SIGN_IN) {
			GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
			if (result.isSuccess()) {
				// The signed in account is stored in the result.
				GoogleSignInAccount signedInAccount = result.getSignInAccount();
			} else {
				String message = result.getStatus().getStatusMessage();
				if (message == null || message.isEmpty()) {
					message = "Другая ошибка при Sign in";
				}
				new AlertDialog.Builder(this).setMessage(message)
						.setNeutralButton(android.R.string.ok, null).show();
			}
		}
	}

*/

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		gameHelper.onActivityResult(requestCode, resultCode, data);
	}


	@Override
	protected void onStop() {
		super.onStop();
		gameHelper.onStop();

	}

	@Override
	protected void onStart() {
		super.onStart();
		gameHelper.onStart(this); // You will be logged in to google play services as soon as you open app , i,e on start
	}

	@Override
	public void onStartMethod() {
		super.onStart();
		gameHelper.onStart(this); // This is similar method but I am using this if i wish to login to google play services
		// from any other screen and not from splash screen of my code

	}



	@Override
	public void signIn() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					gameHelper.beginUserInitiatedSignIn();

				}
			});
		} catch (Exception e) {

		}
	}

	@Override
	public void signOut() {
		try {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {

					gameHelper.signOut();
				}
			});
		} catch (Exception e) {

		}
	}

	@Override
	public void rateGame() {
		String str = "https://play.google.com/store/apps/details?id=by.android.cradle";
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
	}

	@Override
	public void unlockAchievement(String str) {
// открыть достижение с  achievementId = str
		Games.Achievements.unlock(gameHelper.getApiClient(), str);
	}

	@Override
	public void submitScore(String LeaderBoard, int highScore) {
		if (isSignedIn()) {

			Games.Leaderboards.submitScore(gameHelper.getApiClient(), LeaderBoard, highScore);
			//LeaderboardsClient.LeaderboardScores.submitScore
/*
			Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
					.submitScore(LeaderBoard, highScore);
*/

		}
		else{
			System.out.println(" Not signin Yet ");
		}
	}

	@Override
	public void submitLevel(int highLevel) {

	}

	@Override
	public void showAchievement() {
// вызвать Activity с достижениями
		startActivityForResult(
				Games.Achievements.getAchievementsIntent(gameHelper
						.getApiClient()), 101);
	}

	@Override
	public void showScore(String LeaderBoard) {
// вызвать Activity для всех зарегистрированных таблиц рекордов. Так же
		// можно вызывать Activity под конкретную таблицу
		startActivityForResult(
				Games.Leaderboards.getAllLeaderboardsIntent(gameHelper
						.getApiClient()), 100);
	}

	@Override
	public void showLevel() {

	}

	@Override
	public void logEvent( String id, String name, String content_type){

		Bundle bundle = new Bundle();
		bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
		bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
		bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content_type);
		mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM, bundle);
	}

	@Override
	public void logLevelUpEvent( long idLevel){

		Bundle bundle = new Bundle();
		bundle.putLong(FirebaseAnalytics.Param.LEVEL, idLevel);
		mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_UP, bundle);
	}


	@Override
	public void logLevelStartEvent( long levelName){

		Bundle bundle = new Bundle();
		bundle.putLong(FirebaseAnalytics.Param.LEVEL_NAME, levelName);
		mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_START, bundle);
	}

	@Override
	public void logLevelEndEvent( long levelName, String success){

		Bundle bundle = new Bundle();
		bundle.putLong(FirebaseAnalytics.Param.LEVEL_NAME, levelName);
		bundle.putString(FirebaseAnalytics.Param.SUCCESS, success);
		mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.LEVEL_END, bundle);
	}

	@Override
	public void connectUs() {

		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"cradleofthrones@gmail.com"});
		intent.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
		intent.putExtra(Intent.EXTRA_TEXT, "Your text");
		try {
			startActivity(Intent.createChooser(intent, "Send mail"));
		} catch (android.content.ActivityNotFoundException e) {
			//Toast.makeText(MyActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void scheduleReminder(long duration_minutes,String title, String message,String messageId, String tag){
		AndroidWorker.scheduleReminder(duration_minutes,title,message,messageId,tag, getApplicationContext());
	}

	@Override
	public void cancelReminder(String tag){
		AndroidWorker.cancelReminder(tag);
	}

	@Override
	public void onBackPressed() {
		System.out.println("Android Launcher onBackPressed");
		if (back_pressed + 2000 > System.currentTimeMillis()) {
			if (cradleGame!=null) {
				System.out.println("Android Launcher onBackPressed - scheduleReminder");
				cradleGame.scheduleReminder(20);
			}
			super.onBackPressed();
		}
		else
			if ((cradleGame!=null) && cradleGame.getLanguageStrings()!=null){
				String s = cradleGame.getLanguageStrings().get("pressonceagaintoexit");
				Toast.makeText(getBaseContext(), s,
						Toast.LENGTH_SHORT).show();

			}

		back_pressed = System.currentTimeMillis();
	}

	/*
	private void openQuitDialog() {
		AlertDialog.Builder quitDialog = new AlertDialog.Builder(
				this);
		quitDialog.setTitle("Выход: Вы уверены?");

		quitDialog.setPositiveButton("Таки да!", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				finish();
			}
		});

		quitDialog.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
			}
		});

		quitDialog.show();
	}

*/


	//Method is called when user pressed home button
	@Override
	protected void onUserLeaveHint() {
		//Toast toast = Toast.makeText(getApplicationContext(), "Нажата кнопка HOME", Toast.LENGTH_SHORT);
		//toast.show();
		System.out.println("Android Launcher onUserLeaveHint");
		if ((cradleGame!=null) && cradleGame.getLanguageStrings()!=null){
			cradleGame.scheduleReminder(20);
		}
		super.onUserLeaveHint();
	}



	public void loadRewardedVideoAd() {
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
				.addTestDevice("10D929FF85B7803BB2D5CEE273F4FBFE")
				.addTestDevice("51E3C88D530A640ED6513B1BD4C9DB62")
				.build();
		adRewardedVideoView.loadAd(REWARDED_VIDEO_AD_UNIT_ID, adRequest);

	}

	public void setupRewarded() {
		adRewardedVideoView = MobileAds.getRewardedVideoAdInstance(this);
		adRewardedVideoView.setRewardedVideoAdListener(this);
		loadRewardedVideoAd();
	}

	public boolean hasVideoLoaded(){
		if(is_video_ad_loaded) {
			return true;
		}
		runOnUiThread(new Runnable() {
			public void run() {
				if (!adRewardedVideoView.isLoaded()) {
					loadRewardedVideoAd();
				}
			}
		});
		return false;
	}

	public void showRewardedVideoAd(){
		runOnUiThread(new Runnable() {
			public void run() {
				if (adRewardedVideoView.isLoaded()) {
					adRewardedVideoView.show();
				} else {
					loadRewardedVideoAd();
				}
			}
		});
	}


	@Override
	public void onRewarded(RewardItem reward) {
		if(vel != null) {
			// The type and the amount can be set in your AdMob console
			vel.onRewardedEvent(reward.getType(), reward.getAmount());
		}
	}

	// Each time the video ends we need to load a new one
	@Override
	public void onRewardedVideoAdClosed() {
		is_video_ad_loaded = false;
		loadRewardedVideoAd();
		if(vel != null) {
			vel.onRewardedVideoAdClosedEvent();
		}
	}

	@Override
	public void onRewardedVideoAdLoaded() {
		if(vel != null) {
			vel.onRewardedVideoAdLoadedEvent();
		}
		is_video_ad_loaded = true;
	}

	@Override
	public void onRewardedVideoAdOpened() {

	}

	@Override
	public void onRewardedVideoStarted() {

	}
	@Override
	public void onRewardedVideoAdLeftApplication() {

	}

	@Override
	public void onRewardedVideoAdFailedToLoad(int i) {

	}

	@Override
	public void onRewardedVideoCompleted() {

	}

	public void setVideoEventListener (IVideoEventListener listener) {
		this.vel = listener;
	}

}