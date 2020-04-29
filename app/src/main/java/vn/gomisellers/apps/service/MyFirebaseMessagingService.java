package vn.gomisellers.apps.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import vn.gomisellers.apps.R;
import vn.gomisellers.apps.main.MainActivity;
import vn.gomisellers.apps.utils.GomiConstants;
import vn.gomisellers.apps.utils.LogUtils;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String TAG = MyFirebaseMessagingService.class.getName();

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        LogUtils.d(TAG, "onNewToken: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        LogUtils.d(TAG, "onMessageReceived: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            LogUtils.d(TAG, "payload: " + new Gson().toJson(remoteMessage.getData()));
            sendNotification(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String title, String messageBody) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, GomiConstants.REQUEST_NOTIFY_ENTER, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int color = ContextCompat.getColor(this, R.color.colorAccent);
        String channelId = getString(R.string.default_notification_channel_id);
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.ic_stat_ic_notification)
                .setContentTitle(title)
                .setContentText(messageBody)
                .setColor(color)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .setChannelId(channelId)
                .setAutoCancel(true)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelName = getString(R.string.default_notification_channel_name);
            String channelDes = getString(R.string.default_notification_channel_description);
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channelDes);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(getID(), notification);
    }

    public static int notifyId = 0;

    private int getID() {
        return notifyId++;
    }
}
