
package com.zlping.demo.share;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;

import android.content.Context;
import android.util.Log;

import com.zlping.demo.util.HttpClientUtils;

public class WeiboUtils {
    public static String sendWeibo(Context context, String url, String method, WeiboParameters params, String file, String token) {
        HttpClient client = HttpClientUtils.getDefaultClient();
        HttpPost post = new HttpPost(url);
        byte[] data = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 50);
        String postParam = Utility.encodeParameters(params);
        try {
            data = postParam.getBytes("UTF-8");
            bos.write(data);
            data = bos.toByteArray();
            bos.close();
            ByteArrayEntity formEntity = new ByteArrayEntity(data);
            post.setEntity(formEntity);
            post.setHeader("Content-Type", "multipart/form-data");
            String authHeader = Utility.getWeiboAuthHeader(token);
            if (authHeader != null) {
                post.setHeader("Authorization", authHeader);
            }

            HttpResponse response = client.execute(post);
            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();
            String res = readHttpResponse(response);
            Log.i("WeiboUtils", "statusCode="+statusCode+"|"+res);
            if (statusCode != 200) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
    private static String readHttpResponse(HttpResponse response) {
        String result = "";
        HttpEntity entity = response.getEntity();
        InputStream inputStream;
        try {
            inputStream = entity.getContent();
            ByteArrayOutputStream content = new ByteArrayOutputStream();

            Header header = response.getFirstHeader("Content-Encoding");
            if (header != null && header.getValue().toLowerCase().indexOf("gzip") > -1) {
                inputStream = new GZIPInputStream(inputStream);
            }

            int readBytes = 0;
            byte[] sBuffer = new byte[512];
            while ((readBytes = inputStream.read(sBuffer)) != -1) {
                content.write(sBuffer, 0, readBytes);
            }
            result = new String(content.toByteArray());
            return result;
        } catch (IllegalStateException e) {
        } catch (IOException e) {
        }
        return result;
    }
    
    public static String testRequest(){
        HttpClient client = HttpClientUtils.getDefaultClient();
        HttpPost post = new HttpPost("http://www.baidu.com");
        byte[] data = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1024 * 50);
        try {
            HttpResponse response = client.execute(post);
            StatusLine status = response.getStatusLine();
            int statusCode = status.getStatusCode();

            if (statusCode != 200) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
