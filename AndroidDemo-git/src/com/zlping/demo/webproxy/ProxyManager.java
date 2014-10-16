package com.zlping.demo.webproxy;

import java.lang.reflect.Field;

import org.apache.http.HttpHost;

import android.util.Log;

public class ProxyManager {
	/**
	 * 给webview设置代理
	 * @param host
	 * @param port
	 * @return
	 */
    public static boolean setWebViewProxy(String host, int port) {
        boolean ret = false;
        try {
        	HttpHost httpHost = new HttpHost(host, port, "http");
        	ret = setProxyHostField(httpHost);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    /**
     * 取消代理
     * @return
     */
    public static boolean cancelProxy(){
        boolean ret = false;
        try {
        	ret = setProxyHostField(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }
    
	private static boolean setProxyHostField(HttpHost proxyServer) {
		// Getting network
		Class networkClass = null;
		Object network = null;
		try {
			networkClass = Class.forName("android.webkit.Network");
			Log.i("ProxyManager", "Class.forName");
			Field networkField = networkClass.getDeclaredField("sNetwork");
			Log.i("ProxyManager", "networkClass.getDeclaredField");
			network = getFieldValueSafely(networkField, null);
			Log.i("ProxyManager", "getFieldValueSafely");
		} catch (Exception ex) {
			Log.e(ProxyManager.class.getName(), "error getting network");
			return false;
		}
		if (network == null) {
			Log.e(ProxyManager.class.getName(), "error getting network : null");
			return false;
		}
		Object requestQueue = null;
		try {
			Field requestQueueField = networkClass
					.getDeclaredField("mRequestQueue");
			requestQueue = getFieldValueSafely(requestQueueField, network);
		} catch (Exception ex) {
			Log.e(ProxyManager.class.getName(), "error getting field value");
			return false;
		}
		if (requestQueue == null) {
			Log.e(ProxyManager.class.getName(), "Request queue is null");
			return false;
		}
		Field proxyHostField = null;
		try {
			Class requestQueueClass = Class
					.forName("android.net.http.RequestQueue");
			proxyHostField = requestQueueClass.getDeclaredField("mProxyHost");
		} catch (Exception ex) {
			Log.e(ProxyManager.class.getName(),
					"error getting proxy host field");
			return false;
		}

		boolean temp = proxyHostField.isAccessible();
		try {
			proxyHostField.setAccessible(true);
			proxyHostField.set(requestQueue, proxyServer);
		} catch (Exception ex) {
			Log.e(ProxyManager.class.getName(), "error setting proxy host");
		} finally {
			proxyHostField.setAccessible(temp);
		}

		return true;
	}

	private static Object getFieldValueSafely(Field field, Object classInstance)
			throws IllegalArgumentException, IllegalAccessException {
		boolean oldAccessibleValue = field.isAccessible();
		field.setAccessible(true);
		Object result = field.get(classInstance);
		field.setAccessible(oldAccessibleValue);
		return result;
	}
}
