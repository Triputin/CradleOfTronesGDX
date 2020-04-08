package by.android.cradle;

public interface INotification {
    public void scheduleReminder(long duration_minutes,String title, String message,String messageId, String tag);
    public void cancelReminder(String tag);
}
