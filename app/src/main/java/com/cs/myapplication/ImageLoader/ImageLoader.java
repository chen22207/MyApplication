package com.cs.myapplication.ImageLoader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.LruCache;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by chenshuai12619 on 2015/9/8 14:08.
 */
public class ImageLoader {
	private static final int DEFAULT_THREAD_COUNT = 1;
	private static ImageLoader mInstance;
	/**
	 * 图片缓存
	 */
	private LruCache<String, Bitmap> mLruCache;
	private ExecutorService mThreadPool;
	private Type mType = Type.LIFO;

	private LinkedList<Runnable> mTaskQueue;

	private Thread mPoolThread;
	private Handler mPoolThreadHandler;

	private Handler mUIHandler;

	private Semaphore mSemaphorePoolThreadHandler = new Semaphore(0);
	private Semaphore mSemaphoreThreadPool;

	private ImageLoader(int threadCount, Type type) {
		init(threadCount, type);
	}

	public static ImageLoader getInstance() {
//        if (mInstance == null) {
//            synchronized (ImageLoader.class) {
//                if (mInstance == null) {
//                    mInstance = new ImageLoader(DEFAULT_THREAD_COUNT, Type.LIFO);
//                }
//            }
//        }
//        return mInstance;

		return getInstance(DEFAULT_THREAD_COUNT, Type.LIFO);
	}

	public static ImageLoader getInstance(int threadCount, Type type) {
		if (mInstance == null) {
			synchronized (ImageLoader.class) {
				if (mInstance == null) {
					mInstance = new ImageLoader(threadCount, type);
				}
			}
		}
		return mInstance;
	}

	private void init(int threadCount, Type type) {

		mPoolThread = new Thread() {
			@Override
			public void run() {
				Looper.prepare();
				mPoolThreadHandler = new Handler() {
					@Override
					public void handleMessage(Message msg) {
						mThreadPool.execute(getTask());
						try {
							mSemaphoreThreadPool.acquire();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
				mSemaphorePoolThreadHandler.release();
				Looper.loop();
			}
		};
		mPoolThread.start();

		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheMemory = maxMemory / 8;
		mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
			@Override
			protected int sizeOf(String key, Bitmap value) {
				return value.getRowBytes() * value.getHeight();
			}
		};

		mThreadPool = Executors.newFixedThreadPool(threadCount);
		mTaskQueue = new LinkedList<>();
		mType = type;

		mSemaphoreThreadPool = new Semaphore(threadCount);
	}

	private Runnable getTask() {
		if (mType == Type.FIFO) {
			return mTaskQueue.removeFirst();
		} else if (mType == Type.LIFO) {
			return mTaskQueue.removeLast();
		}
		return null;
	}

	public void loadImage(final String path, final ImageView imageView) {
		imageView.setTag(path);
		if (mUIHandler == null) {
			mUIHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					ImageBeanHolder holder = (ImageBeanHolder) msg.obj;
					Bitmap bm = holder.bitmap;
					ImageView imageview = holder.imageView;
					String path = holder.path;

					if (imageview.getTag().toString().equals(path)) {
						imageview.setImageBitmap(bm);
					}
				}
			};
		}

		Bitmap bm = getBitmapFromLruCache(path);
		if (bm != null) {
			refreashBitmap(bm, path, imageView);
		} else {
			addTask(new Runnable() {
				@Override
				public void run() {
					//获得图片的宽高
					ImageSize imageSize = getImageViewSize(imageView);
					//压缩图片
					Bitmap bm = decodeSimpledBitmapFromPath(path, imageSize.width, imageSize.height);
					//加入到缓存
					addBitmapToLruCache(path, bm);

					refreashBitmap(bm, path, imageView);

					mSemaphoreThreadPool.release();
				}
			});
		}
	}

	private void refreashBitmap(Bitmap bm, String path, ImageView imageView) {
		Message message = Message.obtain();
		ImageBeanHolder holder = new ImageBeanHolder();
		holder.path = path;
		holder.bitmap = bm;
		holder.imageView = imageView;
		message.obj = holder;
		mUIHandler.sendMessage(message);
	}

	private void addBitmapToLruCache(String path, Bitmap bm) {
		if (getBitmapFromLruCache(path) == null) {
			if (bm != null) {
				mLruCache.put(path, bm);
			}
		}
	}

	private Bitmap decodeSimpledBitmapFromPath(String path, int reqWidth, int reqHeight) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);

		options.inSampleSize = caculateInSimpleSize(options, reqWidth, reqHeight);

		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	private int caculateInSimpleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		int width = options.outWidth;
		int height = options.outHeight;

		int inSimpleSize = 1;

		if (width > reqWidth || height > reqHeight) {
			int widthRadio = Math.round(width * 1.0f / reqWidth);
			int heightRadio = Math.round(height * 1.0f / reqHeight);

			inSimpleSize = Math.max(widthRadio, heightRadio);
		}
		return inSimpleSize;
	}

	private ImageSize getImageViewSize(ImageView imageView) {
		ImageSize imageSize = new ImageSize();

		DisplayMetrics metrics = imageView.getContext().getResources().getDisplayMetrics();


		ViewGroup.LayoutParams lp = imageView.getLayoutParams();
		int width = imageView.getWidth();
		if (width <= 0) {
			width = lp.width;
		}
		if (width <= 0) {
			width = imageView.getMaxWidth();
		}
		if (width <= 0) {
			width = metrics.widthPixels;
		}

		int height = imageView.getHeight();
		if (height <= 0) {
			height = lp.width;
		}
		if (height <= 0) {
			height = imageView.getMaxHeight();
		}
		if (height <= 0) {
			height = metrics.heightPixels;
		}

		imageSize.width = width;
		imageSize.height = height;

		return imageSize;
	}

	private synchronized void addTask(Runnable runnable) {
		mTaskQueue.add(runnable);
		try {
			if (mPoolThreadHandler == null)
				mSemaphorePoolThreadHandler.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mPoolThreadHandler.sendEmptyMessage(0);
	}

	private Bitmap getBitmapFromLruCache(String key) {
		return mLruCache.get(key);
	}

	public enum Type {
		FIFO, LIFO
	}

	private class ImageBeanHolder {
		String path;
		Bitmap bitmap;
		ImageView imageView;
	}

	private class ImageSize {
		int width;
		int height;
	}
}
