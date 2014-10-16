package com.zlping.demo.util;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import com.zlping.demo.SSLSocketFactory;

public class HttpClientUtils {
    public static DefaultHttpClient getDefaultClient(){
        DefaultHttpClient httpclient = null;
        try {
            SchemeRegistry supportedSchemes = new SchemeRegistry(); //注册协议类型
            SocketFactory sf = PlainSocketFactory.getSocketFactory();
            supportedSchemes.register(new Scheme("http", sf, 80));
            
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {}
                    public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {}
                    public X509Certificate[] getAcceptedIssuers() {return null;}
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx.getSocketFactory(),SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//          SSLSocketFactory ssf = new SSLSocketFactory(null, null, null, null, null, null);
            supportedSchemes.register(new Scheme("https", ssf, 443));

            HttpParams params = new BasicHttpParams();
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
            HttpProtocolParams.setUseExpectContinue(params, true);
//          HttpProtocolParams.setUserAgent(params,"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
            // 超时设置
            /* 从连接池中取连接的超时时间 */
            ConnManagerParams.setTimeout(params, 1000);
            /* 连接超时 */
            HttpConnectionParams.setConnectionTimeout(params, 2000);
            /* 请求超时 */
            HttpConnectionParams.setSoTimeout(params, 4000);
            final ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, supportedSchemes);
            httpclient = new DefaultHttpClient(ccm, params);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return httpclient;
    }
}
