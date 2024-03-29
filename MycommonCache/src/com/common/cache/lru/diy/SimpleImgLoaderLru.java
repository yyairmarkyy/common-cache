package com.common.cache.lru.diy;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.common.cache.lru.DiskLruCache;
import com.common.cache.lru.ImageManagerLru;
import com.common.cache.lru.MD5;
import com.common.uitl.DrawTool;
import com.common.uitl.StringUtils;

public class SimpleImgLoaderLru {

	public LruCache<String, Bitmap> mMemoryCache;
	public DiskLruCache mDiskCache;
	private Context context;

	public static final int MSG_DOWN = 100;
	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 20; // 10MB
	private static final String DISK_CACHE_SUBDIR = "thumbnails";
	private ExecutorService pools = null;
	private static SimpleImgLoaderLru imageManager;

	public SimpleImgLoaderLru(Context context) {
		this.context = context;
		int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
		memClass = memClass > 32 ? 32 : memClass;
		// 使用可用内存的1/8作为图片缓存
		final int cacheSize = 1024 * 1024 * memClass / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getRowBytes() * bitmap.getHeight();
			}
		};
		File cacheDir = DiskLruCache.getDiskCacheDir(context, DISK_CACHE_SUBDIR);
		mDiskCache = DiskLruCache.openCache(context, cacheDir, DISK_CACHE_SIZE);
		pools = Executors.newCachedThreadPool();// 线程池
	}

	public static SimpleImgLoaderLru from(Context context) {
		// 如果不在ui线程中，则抛出异常
		if (Looper.myLooper() != Looper.getMainLooper()) {
			throw new RuntimeException("Cannot instantiate outside UI thread.");
		}
		if (imageManager == null) {
			imageManager = new SimpleImgLoaderLru(context);
		}
		return imageManager;
	}

	/**
	 * 显示图片
	 * 
	 * @param imageView
	 * @param url
	 * @param resId
	 */
	public void displayImage(ImageView imageView, String url, int resId) {
		if (imageView == null) {
			return;
		}
		if (imageView.getTag() != null && imageView.getTag().toString().equals(url)) {
			return;
		}
		if (url == null || url.equals("")) {
			return;
		}
		// 添加url tag
		imageView.setTag(url);
		// 读取map缓存
		Bitmap bitmap = mMemoryCache.get(url);
		if (bitmap != null) {
			setImageBitmap(imageView, bitmap, true);
			return;
		} else {
			imageView.setImageResource(resId);
		}
		// 生成文件名,如果缓存不存在
		String filePath = urlToFilePath(url);
		if (filePath == null) {
			return;
		}
		pools.submit(new ImgDownLoadRunnable(new ImageRef(imageView, url, filePath, resId), mHanlder));
	}

	public void displayImage(ImageView imageView, String url, int resId, int width, int height) {
		if (imageView == null) {
			return;
		}
		if (resId >= 0) {
			if (imageView.getBackground() == null) {
				imageView.setBackgroundResource(resId);
			}
			imageView.setImageDrawable(null);
		}
		if (url == null || url.equals("")) {
			return;
		}

		// 添加url tag
		imageView.setTag(url);
		// 读取map缓存
		Bitmap bitmap = mMemoryCache.get(url + width + height);
		if (bitmap != null) {
			setImageBitmap(imageView, bitmap, true);
			return;
		}
		// 生成文件名
		String filePath = urlToFilePath(url);
		if (filePath == null) {
			return;
		}
		pools.submit(new ImgDownLoadRunnable(new ImageRef(imageView, url, filePath, resId,width,height), mHanlder));
	}
	/**
	 * 根据url生成缓存文件完整路径名
	 * 
	 * @param url
	 * @return
	 */
	public String urlToFilePath(String url) {
		// 扩展名位置
		int index = url.lastIndexOf('.');
		if (index == -1) {
			return null;
		}
		StringBuilder filePath = new StringBuilder();
		// 图片存取路径
		filePath.append(context.getCacheDir().toString()).append('/');
		// 图片文件名
		filePath.append(MD5.Md5(url)).append(url.substring(index));
		return filePath.toString();
	}

	private void setImageBitmap(ImageView imageView, Bitmap bitmap, boolean isTran) {
		imageView.setImageBitmap(bitmap);
	}

	private void setImageDrable(ImageView imageView, Drawable drawable, boolean isTran) {
		imageView.setImageDrawable(drawable);
	}

	class ImgDownLoadRunnable implements Runnable {

		ImageRef imgRef;
		Handler mHandler;

		public ImgDownLoadRunnable(ImageRef imgRef, Handler mHandler) {
			this.imgRef = imgRef;
			this.mHandler = mHandler;
		}

		@Override
		public void run() {
			try {
				URL url = new URL(imgRef.url);
				InputStream fin = url.openStream();
				if (null != fin) {
					BitmapFactory.Options opt = new BitmapFactory.Options();
					opt.inSampleSize = 1;
					opt.inPurgeable = true; // 可回收，占用内存
					opt.inInputShareable = true;
					opt.inPreferredConfig = Bitmap.Config.RGB_565;
					opt.inJustDecodeBounds = false;
					Bitmap bitmap = BitmapFactory.decodeStream(fin, null, opt);
					if (null != bitmap) {
						if (imgRef.width != 0 && imgRef.height != 0) {
							bitmap = ThumbnailUtils.extractThumbnail(bitmap, imgRef.width, imgRef.height,
									ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
						}
						if (imgRef.isRound) {
							bitmap = DrawTool.toRoundCorner(bitmap, imgRef.cornor);
						}
						if (bitmap != null && !StringUtils.isEmpty(imgRef.url)) {
							// 写入SD卡
							if (imgRef.width != 0 && imgRef.height != 0) {
								mDiskCache.put(imgRef.url + imgRef.width + imgRef.height, bitmap);
								mMemoryCache.put(imgRef.url + imgRef.width + imgRef.height, bitmap);
							} else {
								mDiskCache.put(imgRef.url, bitmap);
								mMemoryCache.put(imgRef.url, bitmap);
							}
						}
						if (mHandler != null) {
							Message message = mHandler.obtainMessage(MSG_DOWN, imgRef);
							mHandler.sendMessage(message);
						}
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				return;
			}

		}
	}

	private Handler mHanlder = new Handler() {
		public void handleMessage(android.os.Message msg) {
			int what = msg.what;
			switch (what) {
			case MSG_DOWN:// 图片下载完成
				if (null != msg.obj) {
					ImageRef imgRef = (ImageRef) msg.obj;
					String key = imgRef.url;
					if (imgRef.width != 0 && imgRef.height != 0) {
						key = imgRef.url + imgRef.width + imgRef.height;
					}
					Bitmap bmap = mMemoryCache.get(key);
					if (null != bmap) {
						imgRef.imageView.setImageBitmap(bmap);
					} else {
						bmap = mDiskCache.get(key);
						if (null != bmap) {
							imgRef.imageView.setImageBitmap(bmap);
						}
					}
				}
				break;
			}
		};
	};

	/**
	 * 存放图片信息
	 */
	class ImageRef implements Serializable {

		/** 图片对应ImageView控件 */
		ImageView imageView;
		/** 图片URL地址 */
		String url;
		/** 图片缓存路径 */
		String filePath;
		/** 默认图资源ID */
		int resId;
		int width = 0;
		int height = 0;
		boolean isScaleWidth;
		boolean isRound;
		int cornor;

		/**
		 * 构造函数
		 * 
		 * @param imageView
		 * @param url
		 * @param resId
		 * @param filePath
		 */
		ImageRef(ImageView imageView, String url, String filePath, int resId) {
			this.imageView = imageView;
			this.url = url;
			this.filePath = filePath;
			this.resId = resId;
		}

		ImageRef(ImageView imageView, String url, String filePath, int resId, boolean isScaleWidth) {
			this.imageView = imageView;
			this.url = url;
			this.filePath = filePath;
			this.resId = resId;
			this.isScaleWidth = isScaleWidth;
		}

		ImageRef(ImageView imageView, String url, String filePath, int resId, int width, int height) {
			this.imageView = imageView;
			this.url = url;
			this.filePath = filePath;
			this.resId = resId;
			this.width = width;
			this.height = height;
		}
	}
}
