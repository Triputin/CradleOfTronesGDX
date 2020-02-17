package by.android.cradle;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.NonNull;
import androidx.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.Collection;

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


public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler,IPlayServices  {

	protected AdView adView;
	private final int SHOW_ADS = 1;
	private final int HIDE_ADS = 0;
	//GPS second try
	private GameHelper gameHelper;
	private FirebaseAnalytics mFirebaseAnalytics;


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

	@Override
	public void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		// Create the libgdx View
		View gameView = initializeForView(new CradleGame(this,this), config);

		// Create and setup the AdMob view
		adView = new AdView(this);
		adView.setAdSize(AdSize.BANNER);
		//Test settings
		//adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
		//MobileAds.initialize(this, "ca-app-pub-3940256099942544/6300978111");

		//Work settings
		adView.setAdUnitId("ca-app-pub-6101517213308128/5757251577");
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



	}

	// This is the callback that posts a message for the handler
	@Override
	public void showAds(boolean show) {
		handler.sendEmptyMessage(show ? SHOW_ADS : HIDE_ADS);
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
	public void logEvent(String id, String name, String content_type){

		Bundle bundle = new Bundle();
		bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
		bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
		bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, content_type);
		mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
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

}