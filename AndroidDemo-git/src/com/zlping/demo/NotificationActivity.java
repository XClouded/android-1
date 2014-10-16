package com.zlping.demo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RemoteViews;

@SuppressLint("NewApi")
public class NotificationActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notify_activity);
		findViewById(R.id.notify_btn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showNotify();
			}
		});
	}

	private void showNotify() {
		// Constructs the Builder object.
		Intent resultIntent = new Intent(this, DingActivity.class);
		// Because clicking the notification opens a new ("special") activity,
		// there's
		// no need to create an artificial back stack.
		PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		RemoteViews remoteViews = new RemoteViews("com.zlping.demo", R.layout.notify_layout);

		NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle().addLine("123").addLine("456");
		inboxStyle.setBigContentTitle("bigtitle");
		inboxStyle.setSummaryText("summarytext");
		
		NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
		bigTextStyle.bigText("完全可以实现，这用到了Android的Selector（根据组件的状态显示该状态对应的图片做为显示背景）。");
		
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
				.setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("测试通知")
				.setContentText("完全可以实现，这用到了Android的Selector ");
//				.setDefaults(Notification.DEFAULT_ALL)
				// requires VIBRATE permission
				/*
				 * Sets the big view "big text" style and supplies the text (the
				 * user's reminder message) that will be displayed in the detail
				 * area of the expanded notification. These calls are ignored by
				 * the support library for pre-4.1 devices.
				 */
//				.setStyle(inboxStyle)
//				.setContent(view);
//				.setTicker("aasf", view)
//				.addAction(R.drawable.ic_launcher, "取消", resultPendingIntent);
//				.setContentInfo("qrqwerqwreqwrqwerwqre")

//				.addAction(R.drawable.ic_launcher, "确认2", resultPendingIntent);
//				.addAction(R.drawable.ic_launcher, "确认", resultPendingIntent);

		// Sets an ID for the notification
		int mNotificationId = 001;
		// Gets an instance of the NotificationManager service
		NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		// Builds the notification and issues it.
		Notification notification = builder.build();
		notification.bigContentView = remoteViews;
		
		mNotifyMgr.notify(mNotificationId, notification);

	}
}
