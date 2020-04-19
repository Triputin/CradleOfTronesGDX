package by.android.cradle;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.SyncStateContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.google.android.gms.common.internal.Constants;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static by.android.cradle.SettingsScreen.TAG;

public class AndroidWorker extends Worker {

    public AndroidWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @Override
    public ListenableWorker.Result doWork() {


        // Do the work here.
        Log.d(TAG, "doWork: start");

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "doWork: end");


        String title = getInputData().getString(AndroidLauncher.MessageTitleKey);
        String text = getInputData().getString(AndroidLauncher.MessageTextKey);
        int id = (int) getInputData().getLong(AndroidLauncher.MessageIdKey, 0);

        sendNotification(title, text, id);
        // Indicate whether the task finished successfully with the Result
        return ListenableWorker.Result.success();
    }

    private void sendNotification(String title, String text, int id) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap;
        //bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.ic_stat_outlined_flag, options);
        //String fname=new File(getApplicationContext().getFilesDir(), "logo.png").getAbsolutePath();
        //System.out.println("sendNotification ="+fname);
        //bitmap = BitmapFactory.decodeFile(fname,options);
        //bitmap = BitmapFactory.decodeFile(Gdx.files.internal("logo.png").file().getAbsolutePath()+"/"+"logo.png",options);
        //Pixmap pixmap200 = new Pixmap(Gdx.files.internal("logo.png"));
        InputStream bitmapStream = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }
        };

        try {
             bitmapStream=getApplicationContext().getAssets().open("logo.png");
             bitmap = BitmapFactory.decodeStream(bitmapStream);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            bitmap = null;
        }


        Intent intent = new Intent(getApplicationContext(), AndroidLauncher.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(AndroidLauncher.MessageIdKey, id);

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        //NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(NotificationManager.class);

        //System.out.println("android.os.Build.VERSION.SDK_INT"+android.os.Build.VERSION.SDK_INT);
        //System.out.println("android.os.Build.VERSION_CODES.O"+android.os.Build.VERSION_CODES.O);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(AndroidLauncher.Default_notification_channel_id, AndroidLauncher.Default_notification_channel_name, NotificationManager.IMPORTANCE_LOW);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }


        Notification notification;
        if (bitmap != null) {
             notification = new NotificationCompat.Builder(getApplicationContext(), AndroidLauncher.Default_notification_channel_id)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_stat_outlined_flag)
                    .setLargeIcon(bitmap)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(bitmap)
                            .bigLargeIcon(null))
                    .setAutoCancel(true)
                    .build();
        } else {
             notification = new NotificationCompat.Builder(getApplicationContext(), AndroidLauncher.Default_notification_channel_id)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(R.drawable.ic_stat_outlined_flag)
                    .setAutoCancel(true)
                    .build();
        }

        Objects.requireNonNull(notificationManager).notify(id, notification);
    }


    public static void scheduleReminder(long duration_minutes,String title, String message,String messageId, String tag, Context context) {
        //Prepare data
        /*
        System.out.println("AndroidWorker ----------------------------------------");
        System.out.println("AndroidWorker scheduleReminder messageId="+messageId);
        System.out.println("AndroidWorker scheduleReminder duration_minutes="+duration_minutes);
        System.out.println("AndroidWorker scheduleReminder title="+title);
        System.out.println("AndroidWorker scheduleReminder message="+message);
        System.out.println("AndroidWorker scheduleReminder tag="+tag);
*/
        Data myData = new Data.Builder()
                .putString(AndroidLauncher.MessageTitleKey, title)
                .putString(AndroidLauncher.MessageTextKey, message)
                .putString(AndroidLauncher.MessageIdKey, messageId)
                //.putInt("keyB", 1)
                .build();

        //Plan work
        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(AndroidWorker.class)
                .setInitialDelay(duration_minutes, TimeUnit.MINUTES)
                .addTag(tag)
                .setInputData(myData).build();

        WorkManager instance = WorkManager.getInstance(context);
        instance.enqueue(notificationWork);
    }

    public static void cancelReminder(String tag) {
        System.out.println("AndroidWorker cancelReminder tag="+tag);
        WorkManager instance = WorkManager.getInstance();
        instance.cancelAllWorkByTag(tag);
    }

}
