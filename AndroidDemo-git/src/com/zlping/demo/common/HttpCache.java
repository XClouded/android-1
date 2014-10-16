package com.zlping.demo.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

import com.zlping.demo.common.HttpCache.HttpModel;

public class HttpCache extends LruCache<String, HttpModel> {
	private static HttpCache httpCache;
	private static Context mContext;
	public HttpCache(int maxSize) {
		super(maxSize);
	}

	public static synchronized HttpCache getIntence(Context context) {
		if(httpCache==null){
			mContext = context;
			httpCache = new HttpCache(1000 * 5000);
			ObjectInputStream in = null;
			try {
				File file = new File(mContext.getFilesDir().getAbsolutePath()+"httpcache.cache");
				if(file.exists()){
					in = new ObjectInputStream(new FileInputStream(file));
					Map<String, HttpModel> map = (Map<String, HttpModel>)in.readObject();
					if(!map.isEmpty()){
						Iterator<Entry<String, HttpModel>> it = map.entrySet().iterator();
						while(it.hasNext()){
							Entry<String, HttpModel> entry = it.next();
							httpCache.put(entry.getKey(), entry.getValue());
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return httpCache;
	}

	@Override
	protected int sizeOf(String key, HttpModel value) {
		int res = value == null || TextUtils.isEmpty(value.getData()) ? 0 : value.getData().getBytes().length;
		return res;
	}

	public static class HttpModel implements Serializable {
		private static final long serialVersionUID = 1L;
		private String etag;
		private String data;

		public String getEtag() {
			return etag;
		}

		public void setEtag(String etag) {
			this.etag = etag;
		}

		public String getData() {
			return data;
		}

		public void setData(String data) {
			this.data = data;
		}
	}

	public void distroy() {
		Map<String, HttpModel> map = snapshot();
		ObjectOutputStream out = null;
		try {
			File file = new File(mContext.getFilesDir().getAbsolutePath()+"httpcache.cache");
			if(!file.exists()){
				file.createNewFile();
			}
			out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		httpCache = null;

	}

}
