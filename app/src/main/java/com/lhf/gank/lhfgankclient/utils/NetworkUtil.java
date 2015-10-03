package com.lhf.gank.lhfgankclient.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.lhf.gank.lhfgankclient.app.GankApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 网络请求入口
 * 
 * @author 小孩子a QQ：1065885952 csdn：http://blog.csdn.net/u013705351 2014年2月19日
 * */
public class NetworkUtil extends GankApp {

	private static Context mContext;
	private String TAG = "";
	private View root;

	public NetworkUtil(Context mContext) {
		this.mContext = mContext;
	}

	public NetworkUtil(Context mContext, String tag) {
		this.mContext = mContext;
		this.TAG = tag;
	}

	public void setRoot(View root) {
		this.root = root;
	}

	public boolean isConnected() {
		ConnectivityManager connectivityManager = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (null != connectivityManager) {
			NetworkInfo networkInfo[] = connectivityManager.getAllNetworkInfo();

			if (null != networkInfo) {
				for (NetworkInfo info : networkInfo) {
					if (info.getState() == NetworkInfo.State.CONNECTED) {
						LogUtil.e(TAG, "the net is connected");
						return true;
					}
				}
			}
		}
		return false;
	}

	public void getJSONObjectForGet(String url, Listener<JSONObject> success,
			ErrorListener error) {

		try {
			if (!isConnected()) {
//				Toast.makeText(mContext, "网络不可用，请检查网络连接！！~", Toast.LENGTH_LONG)
//						.show();
				Snackbar.make(root,"网络不可用，请检查网络连接！！~",Snackbar.LENGTH_LONG).show();
				return;

			}
			JsonObjectRequest jsonReq = new JsonObjectRequest(url, null,
					success, error);

			addToRequestQueue(jsonReq, this.TAG);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getStringForGet(String url, HashMap<String, String> params,
			Listener<String> success, ErrorListener error) {

		try {
			if (!isConnected()) {
//				Toast.makeText(mContext, "网络不可用，请检查网络连接！！~", Toast.LENGTH_LONG)
//						.show();
				Snackbar.make(root,"网络不可用，请检查网络连接！！~",Snackbar.LENGTH_LONG).show();
				return;

			}
			// post
			StringRequest jsonReq = new StringRequest(Request.Method.GET, url,
					success, error) {

				/*
				 * @Override protected Map<String, String> getParams() {
				 * Map<String, String> params = new HashMap<String, String>();
				 * params.put("userName", "xjs"); params.put("password",
				 * "123456"); params.put("phoneType", "android"); return params;
				 * }
				 */

				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					HashMap<String, String> headers = new HashMap<String, String>();
//					addCommHeaders(headers);
					return headers;
				}

				@Override
				protected Response<String> parseNetworkResponse(
						NetworkResponse response) {
					Response<String> superResponse = super
							.parseNetworkResponse(response);
					Map<String, String> responseHeaders = response.headers;
					hasCookie(responseHeaders);
					return superResponse;
				}
			};

			// Test 超时时间
			int socketTimeout = 30000;// 30 seconds - change to what you want
			RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
					DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
					DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
			jsonReq.setRetryPolicy(policy);

			getmRequestQueue();
//			getInstance().setCookie();

			addToRequestQueue(jsonReq, this.TAG);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

//	public void getByteForPost(String url, HashMap<String, String> params,
//			Listener<byte[]> success, ErrorListener error) {
//
//		try {
//			if (!isConnected()) {
//				Toast.makeText(mContext, "网络不可用，请检查网络连接！！~", Toast.LENGTH_LONG)
//						.show();
//				return;
//
//			}
//			ByteArrayRequest jsonReq = new ByteArrayRequest(Request.Method.GET,
//					url, success, error) {
//
//				/*
//				 * @Override protected Map<String, String> getParams() {
//				 * Map<String, String> params = new HashMap<String, String>();
//				 * params.put("userName", "mamingguo"); params.put("password",
//				 * "qwerty"); params.put("phoneType", "android");
//				 * params.put("ajax", "true"); return params; }
//				 */
//
//				// @Override
//				// protected byte[] getParams() throws AuthFailureError {
//				// // TODO Auto-generated method stub
//				// JSONObject json = new JSONObject();
//				// byte[] postBody = null;
//				// try {
//				// json.put("userName", "mamingguo");
//				// json.put("password", "qwerty");
//				// json.put("ajax", true);
//				// json.put("phoneType", "android");
//				// postBody = json.toString().getBytes();
//				// }catch(Exception e){}
//				// return postBody;
//				// }
//
//				@Override
//				public Map<String, String> getHeaders() throws AuthFailureError {
//					HashMap<String, String> headers = new HashMap<String, String>();
//					addCommHeaders(headers);
//					return headers;
//				}
//
//				@Override
//				protected Response<byte[]> parseNetworkResponse(
//						NetworkResponse response) {
//					Response<byte[]> superResponse = super
//							.parseNetworkResponse(response);
//					/*
//					 * byte[] data = response.data; try { data =
//					 * PalPlatform.gzipDecompress(data); LogUtil.e(TAG,
//					 * "Response++ = " +new String(data)); } catch (IOException
//					 * e) { // TODO Auto-generated catch block
//					 * e.printStackTrace(); }
//					 */
//					Map<String, String> responseHeaders = response.headers;
//					hasCookie(responseHeaders);
//					return superResponse;
//				}
//			};
//
//			getmRequestQueue();
////			getInstance().setCookie();
//
//			addToRequestQueue(jsonReq, this.TAG);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

//	public void getStringObjectForPost(String url,
//			HashMap<String, String> params, Listener<String> success,
//			ErrorListener error) {
//
//		try {
//			if (!isConnected()) {
//				Toast.makeText(mContext, "网络不可用，请检查网络连接！！~", Toast.LENGTH_LONG)
//						.show();
//				return;
//
//			}
//			StringRequest jsonReq = new StringRequest(Request.Method.POST, url,
//					success, error) {
//				/*
//				 * @Override protected Map<String, String> getParams() {
//				 * Map<String, String> params = new HashMap<String, String>();
//				 * params.put("userName", "xjs"); params.put("password",
//				 * "123456"); params.put("phoneType", "android"); return params;
//				 * }
//				 */
//
//				@Override
//				public Map<String, String> getHeaders() throws AuthFailureError {
//					HashMap<String, String> headers = new HashMap<String, String>();
////					addCommHeaders(headers);
//					return headers;
//				}
//
//				@Override
//				protected Response<String> parseNetworkResponse(
//						NetworkResponse response) {
//					Response<String> superResponse = super
//							.parseNetworkResponse(response);
//					Map<String, String> responseHeaders = response.headers;
//					hasCookie(responseHeaders);
//					return superResponse;
//				}
//			};
//
//			getmRequestQueue();
////			getInstance().setCookie();
//
//			addToRequestQueue(jsonReq, this.TAG);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}

	public void getJSONObjectForPost(String url,
			HashMap<String, String> params, Listener<JSONObject> success,
			ErrorListener error) {

		try {
			if (!isConnected()) {
//				Toast.makeText(mContext, "网络不可用，请检查网络连接！！~", Toast.LENGTH_LONG)
//						.show();
				Snackbar.make(root,"网络不可用，请检查网络连接！！~",Snackbar.LENGTH_LONG).show();
				return;

			}
			JsonObjectRequest jsonReq = new JsonObjectRequest(url,
					new JSONObject(params), success, error) {
				@Override
				public Map<String, String> getHeaders() throws AuthFailureError {
					// if (cookies != null && cookies.length() > 0) {
					HashMap<String, String> headers = new HashMap<String, String>();
//					addCommHeaders(headers);
					return headers;
					// }
					// return super.getHeaders();
				}

				// 如何解析服务端设置的cookie
				// http://stackoverflow.com/questions/20702178/android-volley-access-http-response-header-fields
				// http://blog.csdn.net/hpb21/article/details/12163371
				@Override
				protected Response<JSONObject> parseNetworkResponse(
						NetworkResponse response) {
					Response<JSONObject> superResponse = super
							.parseNetworkResponse(response);
					Map<String, String> responseHeaders = response.headers;
					String rawCookies = responseHeaders.get("Set-Cookie");
					// 服务端返回是
					// set-cookie:JSESSIONID=D90B58454550B4D37C4B66A76BF27B93;
					// Path=/otn BIGipServerotn=2564030730.64545.0000; path=/
					// String part1 = substring(rawCookies, ALG.NULL_NUMBER,
					// ";");
					// String part2 = substring(rawCookies, "\n", ";");
					// // 客户端需要的是
					// cookie:JSESSIONID=D90B58454550B4D37C4B66A76BF27B93;
					// // BIGipServerotn=2564030730.64545.0000;
					// cookies = part1 + "; " + part2 + ";";

					Log.d(TAG, "rawCookies = " + rawCookies);
					// hasCookie(responseHeaders);
					return superResponse;
				}
			};

			getmRequestQueue();
//			getInstance().setCookie();

			addToRequestQueue(jsonReq, this.TAG);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getJSONArrayForGet(String url, Listener<JSONArray> success,
			ErrorListener error) {
		try {
			if (!isConnected()) {
//				Toast.makeText(mContext, "网络不可用，请检查网络连接！！~", Toast.LENGTH_LONG)
//						.show();
				Snackbar.make(root,"网络不可用，请检查网络连接！！~",Snackbar.LENGTH_LONG).show();
				return;

			}
			JsonArrayRequest jsonArrReq = new JsonArrayRequest(url, success,
					error);

			addToRequestQueue(jsonArrReq, this.TAG);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载网络图片
	 * 
	 * @param mImageView
	 *            显示图片的image控件
	 * @param url
	 *            网络图片地址
	 * @param waitImage
	 *            下载等待图片
	 * @param errorImage
	 *            下载出错图片
	 * */
	public void showImageByImageLoader(ImageView mImageView, String url,
			int waitImage, int errorImage) {

		try {

			if (mImageView == null || url == null) {
				return;
			}

			if (!isConnected()) {
//				Toast.makeText(mContext, "网络不可用，请检查网络连接！！~", Toast.LENGTH_LONG)
//						.show();
				Snackbar.make(root,"网络不可用，请检查网络连接！！~",Snackbar.LENGTH_LONG).show();
				return;
			}

			// ImageLoader mImageLoader = new ImageLoader(getmRequestQueue(),
			// new BitmapCache());

			// ImageListener listener = ImageLoader.getImageListener(mImageView,
			// waitImage, errorImage);
			// mImageLoader.get(url, listener);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showImageByNetwork(NetworkImageView mImageView, String url,
			int waitImage, int errorImage) {

		try {

			if (mImageView == null || url == null) {
				return;
			}

			if (!isConnected()) {
//				Toast.makeText(mContext, "网络不可用，请检查网络连接！！~", Toast.LENGTH_LONG)
//						.show();
				Snackbar.make(root,"网络不可用，请检查网络连接！！~",Snackbar.LENGTH_LONG).show();
				return;
			}

			// ImageLoader mImageLoader = new ImageLoader(getmRequestQueue(),
			// new BitmapCache());

			// ImageListener listener = ImageLoader.getImageListener(mImageView,
			// waitImage, errorImage);
			// mImageLoader.get(url, listener);

			// mImageView.setTag("url");
			// mImageView.setImageUrl(url, mImageLoader);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancelRequest() {
		cancelPendingRequests(this.TAG);
	}

//	public static void addCommHeaders(HashMap<String, String> request) {
//		if (cookies == null)
//			cookies = "";
//		// request.put("Cookie", cookies);
//		request.put("Accept",
//				"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2");
//		request.put("Connection", "keep-alive");
//		// request.put("Content-type",
//		// "application/x-www-form-urlencoded");
//		request.put("x-requested-with", " XMLHttpRequest");
//		request.put("Content-Type", "application/json");
//		request.put("Content-Encoding", "utf-8");
//		request.put("x-phoneType", "android");
//
//		// 添加推送所需要用的参数 13-12-10 qiufeng 添加
//		// request.put("x-token", newRandomUUID());
//		request.put("x-version", InfoShouji.getVersion(mContext) + "");// 应用版本
//		request.put("x-osVersion", InfoShouji.getAndroidVersion() + "");// 手机版本
//		request.put("x-phoneModel", InfoShouji.getShoujixinghao() + "");// 手机型号
//
//		request.put("x-osVersion", android.os.Build.VERSION.RELEASE + "");// 系统版本
//		request.put("x-phoneModel", android.os.Build.DEVICE + "");// 手机型号
//
//		String temp = GlobalContainer.getInstance().getParam(
//				Constants.YXBCOOKIE, String.class);
//		if (TextUtils.isEmpty(temp)) {
//			temp = "";
//		}
//		request.put("x-YxbCookie", temp + "");// cookie
//
//		String imsi = InfoShouji.getImsi(YXBaoApplication.getInstance()
//				.getApplicationContext()) + "";
//		if (TextUtils.isEmpty(imsi)) {
//			imsi = "";
//		}
//		request.put("x-imsi", imsi);// imsi
//
//		String imei = InfoShouji.getImei(YXBaoApplication.getInstance()
//				.getApplicationContext()) + "";
//		request.put("x-imei", imei);// imei
//
//		request.put(
//				"x-simNumber",
//				InfoShouji.getPhoneNumber(YXBaoApplication.getInstance()
//						.getApplicationContext()) + "");
//
//		request.put("x-deviceToken", "");
//
//		request.put(
//				"x-channeId",
//				ParseXMLtoBaiduChannel.getInstance(mContext
//						.getApplicationContext()).baiduChannel + "");// 渠道号
//
//		String regId = GlobalContainer.getInstance().getParam(
//				Constants.ANDROID_DEVICE_REGID, String.class);
//		if (TextUtils.isEmpty(regId)) {
//			regId = "";
//		}
//		request.put("x-androidDeviceToken", regId);// 小米推送
//	}

	private static String newRandomUUID() {
		String uuidRaw = UUID.randomUUID().toString();
		return uuidRaw.replaceAll("-", "");
	}

	static String cookies;

	private void hasCookie(Map<String, String> http) {
		String encoding = http.get("Content-Encoding");
		cookies = http.get("Set-Cookie");
		Log.d(TAG, cookies == null ? "cookie = null" : cookies);
		if (encoding != null && encoding.equals("gzip")) {
			Log.d(TAG, "encoding.equalsgzip)  " + encoding);
			long before = System.currentTimeMillis();
			long after = System.currentTimeMillis() - before;
			Log.d(TAG, "===eeee=encoding before  " + encoding + "  耗时" + after);
		}
	}

	public static String substring(String src, String fromString,
			String toString) {
		int fromPos = 0;
		if (fromString != null && fromString.length() > 0) {
			fromPos = src.indexOf(fromString);
			fromPos += fromString.length();
		}
		int toPos = src.indexOf(toString, fromPos);
		return src.substring(fromPos, toPos);
	}
}
