package cn.edu.nuc.mycallname.mainview;


import com.teacher.helper.R;

import cn.edu.nuc.mycallname.mainview.MainActivity4;
//import cn.edu.nuc.lewen.service.WidgetService;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class ExampleAppWidgetProvider extends AppWidgetProvider {

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
//		Intent intent = new Intent(context, WidgetService.class);
//		context.startService(intent);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		// TODO Auto-generated method stub
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		
		RemoteViews views = new RemoteViews(context.getPackageName(),
				R.layout.widget_touchme);
		Intent intent = new Intent(context, MainActivity4.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(context, 100,
				intent, 0);
		views.setOnClickPendingIntent(R.id.tv_time, pendingIntent);
		// ¸üÐÂwidget
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.i("i", "  onDeleted ");
	}

	@Override
	public void onDisabled(Context context) {
		super.onDisabled(context);
		Log.i("i", "  onDisabled ");

//		Intent intent = new Intent(context, WidgetService.class);
//		context.stopService(intent);
	}
}
