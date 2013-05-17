package cn.ingenic.SettingsWidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Created by kli on 13-5-16.
 */
public class SettingsProvider extends AppWidgetProvider{
    public SettingsProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        /*
            桌面上可能含有多个该部件，通知所有部件更新。
         */
        for (int id : appWidgetIds){
            //实例化 RemoteViews, 指定小部件的layout
            RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.settings_widget);

            //配置adapter, 指定ListView id，和数据源WidgetService
            rv.setRemoteAdapter(R.id.lv_settings, new Intent(context, WidgetService.class));

            //配置小部件事件的处理对象WidgetService
            PendingIntent pi = PendingIntent.getService(context, 0, new Intent(context, WidgetService.class), 0);
            rv.setPendingIntentTemplate(R.id.lv_settings, pi);

            //更新小部件
            appWidgetManager.updateAppWidget(id, rv);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
