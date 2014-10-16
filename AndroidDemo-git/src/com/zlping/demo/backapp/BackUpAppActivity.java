
package com.zlping.demo.backapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zlping.demo.R;

public class BackUpAppActivity extends Activity implements OnClickListener {
    private List<AppModel> mAppList;

    private SuperUser superUser;
    private View bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppList = listSystemApp();
        setContentView(R.layout.back_app);
        bar = findViewById(R.id.title_right_probar);
        ListView listView = (ListView) this.findViewById(R.id.list_app);
        ListAdapter appAdapter = new ListAdapter(this, mAppList);
        listView.setAdapter(appAdapter);
        findViewById(R.id.back_app_btn).setOnClickListener(this);
        superUser = new SuperUser();
        superUser.init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        superUser.destroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_app_btn:
                (new BackAppDataTask()).execute();
                break;
            default:
                break;
        }

    }

    private class BackAppDataTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setVisibility(View.VISIBLE);
        }
        @Override
        protected Boolean doInBackground(Void... params) {
            superUser.bakcApp(mAppList);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            if (result) {
                Toast.makeText(BackUpAppActivity.this, "备份成功", Toast.LENGTH_SHORT).show();
                bar.setVisibility(View.GONE);
            }
        }
    }

    private List<AppModel> listSystemApp() {
        List<AppModel> list = new ArrayList<AppModel>();
        PackageManager pm = getPackageManager();
        List<PackageInfo> pilist = pm.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        for (PackageInfo pi : pilist) {
            if (isUserApp(pi)) {
                AppModel model = new AppModel();
                model.setAppName(pi.packageName);
                Drawable appIcon = pi.applicationInfo.loadIcon(pm);
                model.setAppIcon(appIcon);
                model.setDataPath(pi.applicationInfo.dataDir);
                model.setPackageName(pi.packageName);
                list.add(model);
            }

        }
        return list;
    }

    class ListAdapter extends BaseAdapter {
        private List<AppModel> mAppList;

        private Context mContext;

        public ListAdapter(Context context, List<AppModel> list) {
            mAppList = list;
            mContext = context;
        }

        @Override
        public int getCount() {
            return mAppList.size();
        }

        @Override
        public Object getItem(int position) {
            return mAppList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) mContext
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.app_list_item, null);
            }
            final AppModel model = mAppList.get(position);
            ImageView appIcon = (ImageView) convertView.findViewById(R.id.app_icon);
            appIcon.setImageDrawable(model.getAppIcon());
            TextView appName = (TextView) convertView.findViewById(R.id.app_name);
            appName.setText(model.getAppName());
            CheckBox isCheck = (CheckBox) convertView.findViewById(R.id.app_check);
            isCheck.setChecked(model.isCheck());
            isCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    model.setCheck(isChecked);
                }
            });
            convertView.setOnTouchListener(new OnTouchListener() {
                
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Log.i("BackUpAppActivity", "onTouch....");
                    return true;
                }
            });
            return convertView;
        }

    }

    class AppModel {
        private String appName;

        private Drawable appIcon;

        private boolean isCheck;

        private String packageName;

        private String dataPath;

        public String getAppName() {
            return appName;
        }

        public void setAppName(String appName) {
            this.appName = appName;
        }

        public Drawable getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(Drawable appIcon) {
            this.appIcon = appIcon;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public String getDataPath() {
            return dataPath;
        }

        public void setDataPath(String dataPath) {
            this.dataPath = dataPath;
        }
    }

    /**
     * 是否是系统软件或者是系统软件的更新软件
     * 
     * @return
     */
    public boolean isSystemApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public boolean isSystemUpdateApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }

    public boolean isUserApp(PackageInfo pInfo) {
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));
    }

}
