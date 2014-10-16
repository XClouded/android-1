package com.zlping.demo.backapp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import android.os.Environment;
import android.util.Log;

import com.zlping.demo.backapp.BackUpAppActivity.AppModel;
import com.zlping.demo.util.FileUtils;

public class SuperUser {
    public static String BACKUP_PATH;
    
    private DataOutputStream shellCmd;

    private Process process;

    private BufferedReader cmdResult;
    public void init(){
        new Thread(){
            public void run() {
                try {
                    process = Runtime.getRuntime().exec("su");
                    cmdResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    shellCmd = new DataOutputStream(process.getOutputStream());
                    executeShell("pwd\n");
                    // os.writeBytes("cd /data/data");
                    // os.writeBytes("ls -l");
                    // os.writeBytes("exit\n");
                    // os.flush();
//                     process.waitFor();
//                    String line;
//                    while ((line = cmdResult.readLine()) != null) {
//                        Log.d("BackAppActivity", "cmd result=" + line);
//                    }
                } catch (Exception e) {
                    
                }
            };
        }.start();
        File sdCardDir = Environment.getExternalStorageDirectory();//获取SDCard目录,2.2的时候为:/mnt/sdcard  2.1的时候为：/sdcard，所以使用静态方法得到路径会好一点。
        String sdcard = sdCardDir.getAbsolutePath();
        BACKUP_PATH = sdcard+"/"+"backappdata"+"/";
        FileUtils.mkFileDir(BACKUP_PATH);
    }
    
    public int bakcApp(List<AppModel> appList){
        if(appList!=null){
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                for(AppModel model : appList){
                    if(model.isCheck()){
                        String dataPath = model.getDataPath();
                        String newPath = BACKUP_PATH+model.getPackageName();
                        Log.d("BackAppUtils", "|apppath="+dataPath+"|begin backapp");
                        String shell = FileUtils.getCopyFolderShell(dataPath, newPath);
                        executeShell(shell);
                        Log.d("BackAppUtils", "|toPath="+newPath+"|end backapp");
                    }
                }
            }

        }
        return 0;
    }
    
    public void executeShell(String shell){
        try {
            shellCmd.writeBytes(shell + "\n");
//            shellCmd.flush();
//            process.waitFor();
//            String line;
//            while ((line = cmdResult.readLine()) != null) {
//                Log.d("BackAppActivity", "shell result=" + line);
//            }
        } catch (Exception e) {
            Log.e("BackAppActivity", "execute error", e);
        }
    }
    
    public void destroy(){
        try {
            if (shellCmd != null) {
                shellCmd.writeBytes("exit\n");
                shellCmd.close();
            }
            process.destroy();
        } catch (Exception e) {
        }
    }
}
