package com.zlping.demo.webproxy;
import android.content.Context;
import android.net.Proxy;
import android.util.Log;

import org.apache.http.HttpHost;

import com.zlping.demo.AplicationDemo;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
public class ProxySettings {
	
	public static void setProxy(Context ctx){
		Log.i("ProxySettings", "COUNT="+AplicationDemo.FRIST+"|"+AplicationDemo.CURAPN);
        if("cmwap".equals(AplicationDemo.CURAPN)){
        	setProxy(ctx, "10.0.0.172", 80);
        }if("cmnet".equals(AplicationDemo.CURAPN)){
        	
        }else{
//        	String host = Proxy.getDefaultHost();
//            int port = Proxy.getDefaultPort();
//        	setProxy(ctx, host, port);
        }
	}
	   /**
     * Override WebKit Proxy settings
     *
     * @param ctx Android ApplicationContext
     * @param host
     * @param port
     * @return  true if Proxy was successfully set
     */
    public static boolean setProxy(Context ctx, String host, int port) {
        boolean ret = false;
        try {
            Object requestQueueObject = getRequestQueue(ctx);
            if (requestQueueObject != null) {
                //Create Proxy config object and set it into request Q
                HttpHost httpHost = new HttpHost(host, port, "http");
                setDeclaredField(requestQueueObject, "mProxyHost", httpHost);
                ret = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
    public static String getProxyHostname(Context ctx){
    	String res = null;
    	try {
            Object requestQueueObject = getRequestQueue(ctx);
            if (requestQueueObject != null) {
            	Object fild = getDeclaredField(requestQueueObject,"mProxyHost");
            	if(fild!=null){
            		HttpHost host = (HttpHost)fild;
            		res = host.getHostName();
            	}
            }
    	 } catch (Exception e) {
             e.printStackTrace();
         }
        return res;
    }

    public static void resetProxy(Context ctx){
    	try {
        Object requestQueueObject = getRequestQueue(ctx);
        if (requestQueueObject != null) {
//        	String host = Proxy.getDefaultHost();
//	        int port = Proxy.getDefaultPort();
//        	HttpHost httpHost = new HttpHost(host, port, "http");
            setDeclaredField(requestQueueObject, "mProxyHost", null);
        }
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getRequestQueue(Context ctx) throws Exception {
        Object ret = null;
        Class networkClass = Class.forName("android.webkit.Network");
        if (networkClass != null) {
            Object networkObj = invokeMethod(networkClass, "getInstance", new Object[]{ctx}, Context.class);
            if (networkObj != null) {
                ret = getDeclaredField(networkObj, "mRequestQueue");
            }
        }
        return ret;
    }

    private static Object getDeclaredField(Object obj, String name)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getDeclaredField(name);
        f.setAccessible(true);
        Object out = f.get(obj);
        //System.out.println(obj.getClass().getName() + "." + name + " = "+ out);
        return out;
    }

    private static void setDeclaredField(Object obj, String name, Object value)
            throws SecurityException, NoSuchFieldException,
            IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getDeclaredField(name);
        f.setAccessible(true);
        f.set(obj, value);
    }

    private static Object invokeMethod(Object object, String methodName, Object[] params, Class... types) throws Exception {
        Object out = null;
        Class c = object instanceof Class ? (Class) object : object.getClass();
        if (types != null) {
            Method method = c.getMethod(methodName, types);
            out = method.invoke(object, params);
        } else {
            Method method = c.getMethod(methodName);
            out = method.invoke(object);
        }
        //System.out.println(object.getClass().getName() + "." + methodName + "() = "+ out);
        return out;
    }
}
