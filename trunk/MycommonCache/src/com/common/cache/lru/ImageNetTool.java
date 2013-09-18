package com.common.cache.lru;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;

public class ImageNetTool {

	public static final String tag = "wangzy";

	private HttpClient httpClient;
	public boolean isLogin = false;
	public static ImageNetTool imgNetTool;
	private Context context;
	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 20; // 10MB
	private static final String DISK_CACHE_SUBDIR = "wangzy_cache";

	public ImageNetTool(Context context) {
		httpClient = this.createHttpClient();
		imgNetTool = this;
		this.context = context;
	}

	// 对外提供HttpClient实例
	public HttpClient getHttpClient() {
		return httpClient;
	}

	// 创建HttpClient实例
	private HttpClient createHttpClient() {
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
		HttpProtocolParams.setUseExpectContinue(params, true);
		HttpConnectionParams.setConnectionTimeout(params, 20 * 1000);
		HttpConnectionParams.setSoTimeout(params, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(params, 8192);
		SchemeRegistry schReg = new SchemeRegistry();
		schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		ClientConnectionManager connMgr = new ThreadSafeClientConnManager(params, schReg);
		return new DefaultHttpClient(connMgr, params);
	}

	// 关闭连接管理器并释放资源
	private void shutdownHttpClient() {
		if (httpClient != null && httpClient.getConnectionManager() != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	public static ImageNetTool getImgNetTool() {
		return imgNetTool;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		this.shutdownHttpClient();
	}

}
