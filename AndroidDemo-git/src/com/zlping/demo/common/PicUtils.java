package com.zlping.demo.common;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class PicUtils {
	/**
	 * 裁剪图片为正方形，同时压缩图片
	 * 
	 * @param nativeImagePath
	 *            图片的本地地址
	 * @param minSideLength
	 *            正方形的边长，单位为像素，如果为-1，则不按照边来压缩图片
	 * @param maxNumOfPixels
	 *            这张片图片最大像素值，单位为byte，如100*1024
	 * @param options
	 *            可以不用传，不用传，则为null
	 * @return 如果出错，则为null
	 */
	public static Bitmap makeRectangleBitmap(String nativeImagePath,
			int minSideLength, int maxNumOfPixels) {
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(nativeImagePath, options);
			if (options.mCancel || options.outWidth == -1
					|| options.outHeight == -1) {
				return null;
			}
			options.inSampleSize = computeSampleSize(options, minSideLength,
					maxNumOfPixels);
			options.inJustDecodeBounds = false;
			options.inDither = false;
			options.inPreferredConfig = Bitmap.Config.ARGB_8888;

			Bitmap b1 = BitmapFactory.decodeFile(nativeImagePath, options);
			Bitmap b2 = null;
			if (b1.getHeight() > b1.getWidth()) {
				b2 = Bitmap.createBitmap(b1, 0,
						(b1.getHeight() - b1.getWidth()) / 2, b1.getWidth(),
						b1.getWidth());
			} else if (b1.getHeight() < b1.getWidth()) {
				b2 = Bitmap.createBitmap(b1,
						(b1.getWidth() - b1.getHeight()) / 2, 0,
						b1.getHeight(), b1.getHeight());
			} else {
				b2 = b1;
			}
			if (b2 != b1) {
				b1.recycle();
				b1 = null;
			}
			return b2;
		} catch (OutOfMemoryError ex) {
			return null;
		}
	}
	
	/**
	 * 图片压缩比例计算
	 * 
	 * @param options  BitmapFactory.Options
	 * @param minSideLength 小边长，单位为像素，如果为-1，则不按照边来压缩图片
	 * @param maxNumOfPixels 这张片图片最大像素值，单位为byte，如100*1024
	 * @return 压缩比例 
	 */
	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}
		return roundedSize;
	}
	
	/**
	 * 计算图片的压缩比例，用于图片压缩
	 * 
	 * @param options BitmapFactory.Options
	 * @param minSideLength 小边长，单位为像素，如果为-1，则不按照边来压缩图片
	 * @param maxNumOfPixels 这张片图片最大像素值，单位为byte，如100*1024
	 * @return 压缩比例
	 */
	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

}
