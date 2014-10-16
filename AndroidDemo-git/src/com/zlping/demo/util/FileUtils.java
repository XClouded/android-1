package com.zlping.demo.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.util.Log;

public class FileUtils {
    public static boolean mkFileDir(String dirPath){
        File file = new File(dirPath);
        boolean res = false;
        if(!file.exists()){
           res = file.mkdir();
        }else{
            res = true;
        }
        return res;
    }
    
    public static String getCopyFolderShell(String oldPath, String newPath){
        File a = new File(oldPath);
        String res = null;
        if(a.exists()){
            res = "cp -r "+oldPath+" "+newPath+"\n";
        }
        return res;
    }
    
    public static void copyFolderByShell(DataOutputStream shell,BufferedReader shellResult,String oldPath, String newPath){
        File a = new File(oldPath);
        if(a.exists()){
            try {
                String cmd = "cp -r "+oldPath+" "+newPath+"\n";
                shell.writeBytes(cmd);
//                String line;
//                while ((line = shellResult.readLine()) != null) {
//                    Log.d("FileUtils", "copyFolderByShell result=" + line);
//                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /* 复制整个文件夹内容   
     *    
     * @param oldPath   
     *            String 原文件路径 如：c:/old 
     * @param newPath   
     *            String 复制后路径 如：f:/new 
     * @return boolean   
     */    
    public static void copyFolder(String oldPath, String newPath) {     
        
        try {     
            (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹     
            File a = new File(oldPath);
            if(a.exists()&&a.canRead()){
                String[] file = a.list();     
                File temp = null;     
                for (int i = 0; i < file.length; i++) {     
                    if (oldPath.endsWith(File.separator)) {     
                        temp = new File(oldPath + file[i]);     
                    } else {     
                        temp = new File(oldPath + File.separator + file[i]);     
                    }     
                    if (temp.isFile()) {     
                        FileInputStream input = new FileInputStream(temp);     
                        FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());     
                        byte[] b = new byte[1024 * 5];     
                        int len;     
                        while ((len = input.read(b)) != -1) {     
                            output.write(b, 0, len);     
                        }     
                        output.flush();     
                        output.close();     
                        input.close();     
                    }     
                    if (temp.isDirectory()) {// 如果是子文件夹     
                        copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);     
                    }     
                }     
            }else{
                Log.e("FileUtils", "你的权限不足");
            }
        } catch (Exception e) { 
            Log.e("FileUtils", "|复制整个文件夹内容操作出错",e);
        }     
    }    
}
