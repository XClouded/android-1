package com.zlping.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarFile;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.TextView;

import com.zlping.demo.common.DecompressXML;
import com.zlping.demo.common.EntityUtils;
import com.zlping.demo.common.HttpCache;
import com.zlping.demo.common.HttpCache.HttpModel;
import com.zlping.demo.util.HttpClientUtils;
import com.zlping.demo.util.Md5Util;
import com.zlping.demo.webproxy.WebViewActivity;

public class HttpClientActivity extends Activity {
	private static final String TAG = "HttpClientActivity";
	private TextView text;
	private BroadcastReceiver mTicketReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mTicketReceiver res = " + intent);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.http_client);
		text = (TextView) findViewById(R.id.response_result);
		Button button1 = (Button) this.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new getTicketID2().execute();
			}
		});
		Log.i("HttpCache", "f=" + getFilesDir().getAbsolutePath());
		Button button2 = (Button) this.findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo info = conManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
				String currentAPN = info.getExtraInfo();
				String host = Proxy.getDefaultHost();
				int port = Proxy.getDefaultPort();
				// boolean res = ProxySettings.setProxy(getApplicationContext(),
				// "10.0.0.172", 80);
				// Log.i("WebViewActivity", "onresume setproxy="+res);
				Log.i("HttpClientActivity", "currentAPN = " + currentAPN);
				Log.i("HttpClientActivity", "proxyHost = " + host + " proxyPort=" + port);
			}
		});

		Button button3 = (Button) this.findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setComponent(new ComponentName("com.sec.android.wallet",
						"com.sec.android.wallet.ui.activity.ticket.ExternalTicketDownloadActivity"));
				intent.putExtra("TICKET_ID", "28134e32-af12-3617-b139-9309d0408a54");
				intent.putExtra("BOUNCE_ID", "" + System.currentTimeMillis());
				intent.putExtra("RESULT_ACTION", "com.sample.partners.action.DOWNLOAD_TICKET_RESULT");
				startActivity(intent);
				// sendBroadcast(intent);
				Log.i(TAG, "down load ...");
			}
		});
		Button button4 = (Button) this.findViewById(R.id.button4);
		button4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Do304Task().execute();
			}
		});
		Button button5 = (Button) this.findViewById(R.id.button5);
		button5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					// XmlResourceParser parser =
					// getAssets().openXmlResourceParser("AndroidManifest.xml");
					// InputStream istr =
					// getAssets().open("AndroidManifest.xml");
					// XmlPullParserFactory factory =
					// XmlPullParserFactory.newInstance();
					// factory.setNamespaceAware(true);
					// XmlPullParser parser = factory.newPullParser();
					// parser.setInput(istr, "UTF-8");
					//
					android.content.pm.ApplicationInfo applicationInfo = getPackageManager().getApplicationInfo(
							getPackageName(), PackageManager.GET_META_DATA);

					JarFile jarfile = new JarFile(applicationInfo.sourceDir);
					InputStream inputstream = jarfile.getInputStream(jarfile.getEntry("AndroidManifest.xml"));
					byte data[] = new byte[inputstream.available()];
					inputstream.read(data);
					String s = DecompressXML.decompressXML(data);
					XmlPullParserFactory xmlpullparserfactory = XmlPullParserFactory.newInstance();
					xmlpullparserfactory.setNamespaceAware(true);
					XmlPullParser parser = xmlpullparserfactory.newPullParser();
					parser.setInput(new StringReader(s));
					AttributeSet attributeset = Xml.asAttributeSet(parser);

					 String pacage = parsePackageName(parser);
					Log.i(TAG, "pacage = " + pacage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		final AnimLinearLayout layout = (AnimLinearLayout)findViewById(R.id.animLayout);
		final AnimView animView = (AnimView)findViewById(R.id.animView);
		Button button6 = (Button) this.findViewById(R.id.button6);
		button6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layout.requestLayout();
			}
		});
		
		Button button7 = (Button) this.findViewById(R.id.button7);
		button7.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				layout.moveLayout();
				animView.moveView();
			}
		});

		IntentFilter filter = new IntentFilter();
		filter.addAction("com.sample.partners.action.CHECK_TICKET_RESULT");
		filter.addAction("com.sample.partners.action.DOWNLOAD_TICKET_RESULT");
		registerReceiver(mTicketReceiver, filter);
	}

	private static String parsePackageName(XmlPullParser parser) throws IOException, XmlPullParserException {
		int type;
		while ((type = parser.next()) != parser.START_TAG && type != parser.END_DOCUMENT) {
			;
		}

		if (type != parser.START_TAG) {
			return null;
		}
		if (!parser.getName().equals("manifest")) {
			return null;
		}
		String pkgName = parser.getAttributeValue(null, "package");
		if (pkgName == null || pkgName.length() == 0) {
			return null;
		}
		return pkgName.intern();
	}

	private static String inputStream2String(InputStream is) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}
		return buffer.toString();
	}

	class Do304Task extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			String url = "http://fanqie.test.58v5.cn/customerserver/restaurant/city/support";
			DefaultHttpClient httpclient = HttpClientUtils.getDefaultClient();
			String content = null;
			try {
				HttpModel model = HttpCache.getIntence(getApplicationContext()).get(url);
				HttpPost httpost = new HttpPost(url);
				if (model != null && !TextUtils.isEmpty(model.getEtag())) {
					httpost.addHeader("If-None-Match", model.getEtag());
				}
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("latesttime", "2013-12-23 12:28:08"));
				nvps.add(new BasicNameValuePair("name", "5354"));
				httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
				httpost.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);
				String param = EntityUtils.toString(httpost.getEntity());
				Log.i("HttpClientActivity", "param=" + param);
				HttpResponse response = httpclient.execute(httpost);
				int statusCode = response.getStatusLine().getStatusCode();
				Log.i("HttpClientActivity", "statusCode=" + statusCode);

				if (statusCode == 304) {
					content = HttpCache.getIntence(getApplicationContext()).get(url).getData();
				} else if (statusCode == 200) {
					content = EntityUtils.toString(response.getEntity());
					Header[] header = response.getHeaders("ETag");
					if (header.length > 0) {
						String etag = header[0].getValue();
						model = new HttpModel();
						model.setEtag(etag);
						model.setData(content);
						HttpCache.getIntence(getApplicationContext()).put(url, model);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return content;
		}

		@Override
		protected void onPostExecute(String result) {
			text.setText(result);
		}
	}

	class getTicketID2 extends AsyncTask<Void, Void, String> {
		@Override
		protected String doInBackground(Void... params) {
			String body = null;
			try {
				DefaultHttpClient httpclient = HttpClientUtils.getDefaultClient();
				HttpPost httppost = new HttpPost("http://api.wallet.samsung.com/tkt/tickets");
				httppost.setHeader("Content-Type", "application/json");
				httppost.setHeader("charset", "UTF-8");
				httppost.setHeader("X-TKT-Protocol-Version", "1.1");

				String associateID = "b5107041666748f9891790dc9fe479ed";
				String associatePasscode = "74e8e03ff681429b9266b950bca7c1e2";

				String authorization = associateID + ":" + associatePasscode;
				// String encodedAuthorization =
				// StringUtil.base64Encode(authorization.getBytes());
				String encodedAuthorization = "";
				encodedAuthorization = encodedAuthorization.replaceAll("\n", "");

				httppost.setHeader("Authorization", "Basic " + encodedAuthorization);
				httppost.setEntity(new StringEntity("212312", "UTF-8"));
				HttpResponse httpresponse = httpclient.execute(httppost);

				HttpEntity entity = httpresponse.getEntity();
				body = EntityUtils.toString(entity, "UTF-8");
				System.out.println(body);
			} catch (Exception e) {

			}

			return body;
		}

		@Override
		protected void onPostExecute(String result) {
			text.setText(result);
		}
	}

	public static void getTicketID2() throws Exception {

	}

	private void doRequest(DefaultHttpClient httpclient, String url) {
		try {
			HttpPost httpost = new HttpPost(url);
			String username = "zlping123";
			String password = "123123";
			String mid = "45-12-34-54-23";
			String ctype = "2";
			String vcode = Md5Util.getMd5Pwd(username + mid + ctype + "58V5").substring(8, 16);
			System.out.println("vcode=" + vcode);
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("username", username));
			nvps.add(new BasicNameValuePair("password", password));
			nvps.add(new BasicNameValuePair("mid", mid));

			nvps.add(new BasicNameValuePair("vcode", vcode));
			nvps.add(new BasicNameValuePair("ctype", ctype));

			httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			httpost.getParams().setParameter("http.protocol.cookie-policy", CookiePolicy.BROWSER_COMPATIBILITY);

			HttpResponse response = httpclient.execute(httpost);
			HttpEntity entity = response.getEntity();

			Log.i("HttpClientActivity", "Login form get: " + response.getStatusLine());
			String content = inputStream2String(response.getEntity().getContent());
			Log.i("HttpClientActivity", "entity: " + content);
			// EntityUtils.consume(entity);

			Log.i("HttpClientActivity", "Post logon cookies:");
			List<Cookie> cookies = httpclient.getCookieStore().getCookies();
			if (cookies.isEmpty()) {
				Log.i("HttpClientActivity", "None");
			} else {
				CookieSyncManager.createInstance(getApplicationContext());
				CookieManager cookieManager = CookieManager.getInstance();
				cookieManager.removeAllCookie();
				for (Cookie cookie : cookies) {
					StringBuffer c = new StringBuffer();
					c.append(cookie.getName());
					c.append("=\"");
					c.append(cookie.getValue());
					c.append("\"");
					Log.i("HttpClientActivity", "setCookie=" + c.toString());
					cookieManager.setCookie(".58.com", c.toString());
				}
				CookieSyncManager.getInstance().sync();
				Log.i("HttpClientActivity", "getCookie=" + cookieManager.getCookie(".58.com"));
				Intent it = new Intent(getApplicationContext(), WebViewActivity.class);
				startActivity(it);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// When HttpClient instance is no longer needed,
			// shut down the connection manager to ensure
			// immediate deallocation of all system resources
			httpclient.getConnectionManager().shutdown();
		}
	}

	@Override
	protected void onDestroy() {
		Log.i("HttpClientActivity", "onDestroy ... ");
		HttpCache.getIntence(getApplicationContext()).distroy();
		unregisterReceiver(mTicketReceiver);
		super.onDestroy();
	}
}
