package com.zlping.demo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
  
/** 
 * 加载本地文件的内容提供者 
 *  
 * @author a7 
 *  
 */  
public class LocalFileContentProvider extends ContentProvider {  
  
    private static final String URI_PREFIX = "content://com.zlping.demo.localfile";  
  
    public static String constructUri(String url) {  
        Uri uri = Uri.parse(url);  
        Log.i("LocalFileContentProvider", "constructUri URI="+url);
        return uri.isAbsolute() ? url : URI_PREFIX + url;  
    }  
  
//    @Override  
    /** 
     * 直接读取程序中的资源文件<br> 
     * 取sd卡文件实现openfile方法即可,需要用到ParcelFileDescriptor  
     *  
     */  
//    public AssetFileDescriptor openAssetFile(Uri uri, String mode)  
//            throws FileNotFoundException {  
//        AssetManager am = getContext().getAssets();  
//        String path = uri.getPath().substring(1);  
//        Log.i("LocalFileContentProvider", "openAssetFile path="+path);
//        try {  
//            AssetFileDescriptor afd = am.openFd(path);  
//            Log.i("LocalFileContentProvider", "openAssetFile path="+afd.toString());
//            return afd;  
//        } catch (IOException e) {  
//            Log.e("LocalFileContentProvider", "error"+e);
//        }  
//        return super.openAssetFile(uri, mode);  
//    }  
    
    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode) throws FileNotFoundException {
        try {
            String path = uri.getPath().substring(1);  
            File file = new File("/data/data/com.wuba.hybrid/cache/"+path);
            if(file.exists()){
                
            }else{
                
            }
            ParcelFileDescriptor pd = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
            Log.i("LocalFileContentProvider", "openAssetFile path="+pd.toString());
            return pd;  
        } catch (IOException e) {  
            Log.e("LocalFileContentProvider", "error"+e);
        } 
        return super.openFile(uri, mode);
    }
  
    
    @Override  
    public boolean onCreate() {  
        return true;  
    }  
  
    @Override  
    public int delete(Uri uri, String s, String[] as) {  
        throw new UnsupportedOperationException(  
                "Not supported by this provider");  
    }  
  
    @Override  
    public String getType(Uri uri) {  
        throw new UnsupportedOperationException(  
                "Not supported by this provider");  
    }  
  
    @Override  
    public Uri insert(Uri uri, ContentValues contentvalues) {  
        throw new UnsupportedOperationException(  
                "Not supported by this provider");  
    }  
  
    @Override  
    public Cursor query(Uri uri, String[] as, String s, String[] as1, String s1) {  
        throw new UnsupportedOperationException(  
                "Not supported by this provider");  
    }  
  
    @Override  
    public int update(Uri uri, ContentValues contentvalues, String s,  
            String[] as) {  
        throw new UnsupportedOperationException(  
                "Not supported by this provider");  
    }  
  
}  