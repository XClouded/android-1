
package com.zlping.demo.plugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.zlping.demo.R;

import dalvik.system.DexClassLoader;

public class MyPluginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_plugin);
        Button button = (Button)this.findViewById(R.id.myplugin);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent it = new Intent();
                    it.setClassName("com.wuba", "com.wuba.activity.launch.LaunchActivit");
                    PackageManager pm = getPackageManager();
                    List<ResolveInfo> plugins = pm.queryIntentActivities(it, 0);
                    ApplicationInfo appinfo = pm.getApplicationInfo("com.wuba", 0);
                    PackageInfo pinfo = pm.getPackageInfo("com.wuba", 0);
                    List<InstrumentationInfo> insList = pm.queryInstrumentation("com.wuba", 0);
                    String pagename = pm.getInstallerPackageName("com.wuba");
//                    Intent intent = new Intent("android.intent.action.MAIN");
//                    intent.setClassName("com.wuba", "com.wuba.activity.launch.LaunchActivity");
//                    intent.setPackage("com.wuba");
//                    ComponentName comp = new ComponentName("com.wuba","com.wuba.activity.launch.LaunchActivity"); 
//                    intent.addCategory("android.intent.category.LAUNCHER");
//                    intent.setComponent(comp);
//                    startActivity(intent);
                    
                    ResolveInfo rinfo = plugins.get(0);
                    ActivityInfo ainfo = rinfo.activityInfo;
                    
                    String div = System.getProperty("path.separator");
                    String packageName = ainfo.packageName;
                    String dexPath = ainfo.applicationInfo.sourceDir;
                    String dexOutputDir = getApplicationInfo().dataDir;
                    String libPath = ainfo.applicationInfo.sourceDir;
                    
                    DexClassLoader cl = new DexClassLoader(dexPath, dexOutputDir, libPath, this.getClass().getClassLoader());
                    Class<?> classzz = cl.loadClass(packageName+".PluginClass");
                    Object obj = classzz.newInstance();
                    Class[] param = new Class[2];
                    param[0] = Integer.TYPE;
                    param[1] = Integer.TYPE;
                    Method action = classzz.getMethod("function1", param);
                    action.invoke(obj, 12,34);
                    
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }catch (NameNotFoundException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                
            }
        });
    }
}
