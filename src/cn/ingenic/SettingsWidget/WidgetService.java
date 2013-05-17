package cn.ingenic.SettingsWidget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by kli on 13-5-16.
 */
public class WidgetService extends RemoteViewsService {
    public WidgetService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*
            处理Item点击事件
         */
        String action = intent.getStringExtra("action");
        if(!TextUtils.isEmpty(action)){
            Intent setting_intent = new Intent(action);
            setting_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(setting_intent);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return super.onBind(intent);
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetFactory(this.getApplicationContext(), intent);
    }

    class WidgetFactory implements RemoteViewsFactory {
        private Context mContext;
        private String[] titles;
        private int[] icons;
        private String[] actions;

        WidgetFactory(Context context, Intent intent){
            mContext = context;
        /*
            初始化数据（图标，标题，事件）
         */

            //标题
            titles = mContext.getResources().getStringArray(R.array.settings_name);

            //图标
            TypedArray array = mContext.getResources().obtainTypedArray(R.array.settings_icon);
            icons = new int[array.length()];
            for(int i=0; i<icons.length; i++){
                TypedValue value = array.peekValue(i);
                icons[i] = value == null ? 0 : value.resourceId;
            }

            //点击事件
            actions = mContext.getResources().getStringArray(R.array.settings_actions);
        }

        @Override
        public void onCreate() {
        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            //数据长度
            return titles.length;
        }

        @Override
        public RemoteViews getViewAt(int i) {
        /*
            返回一个Item view
         */
            //实例化item view
            RemoteViews rv = new RemoteViews(this.mContext.getPackageName(), R.layout.setting_item);
            //图标
            rv.setImageViewResource(R.id.iv_icon, icons[i]);
            //标题
            rv.setTextViewText(R.id.tv_title, titles[i]);
            //点击事件
            Intent intent = new Intent();
            intent.putExtra("action", actions[i]);
            rv.setOnClickFillInIntent(R.id.ll_settings_item, intent);
            return rv;
        }

        @Override
        public RemoteViews getLoadingView() {
        /*
            正在加载视图
            return null， 显示默认视图（“正在加载”）
         */
            return null;
        }

        @Override
        public int getViewTypeCount() {
        /*
            adapter的 view type，
            return 1， 所有item返回相同的type
            return 0， 会出现无法加载内容的情况。
         */
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}

