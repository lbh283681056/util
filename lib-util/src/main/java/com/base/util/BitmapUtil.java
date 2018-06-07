package com.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Base64;
import android.view.View;

/**
 * 图片工具类
 * 
 * 类名: BitmapUtil
 * 描述
 * 作者 Comsys-linbinghuang
 * 时间 2014-11-3 下午1:46:55
 *
 */
public class BitmapUtil {

	/**
	 * 图片Base64
	 * 
	 * 属性 bitmap
	 * @return
	 */
	public static String convertIconToString(Bitmap bitmap) {
		return Base64
				.encodeToString(getBitmapByte(bitmap, 100), Base64.DEFAULT);
	}
	
	/**
	 * webp格式上传
	 * 属性 bitmap
	 * @return
	 */
	public static String convertIconToStringForWebP(Bitmap bitmap) {
		return Base64
				.encodeToString(getBitmapByteForWebp(bitmap, 50), Base64.DEFAULT);
	}

	/**
	 * 根据图片uri修饰图片,不占用内存
	 * 
	 * 属性 context
	 * 属性 uri
	 * 属性 dw
	 * 属性 dh
	 * @return
	 */
	public static Bitmap optionsBitmap(Context context, Uri uri, int dw, int dh) {
		Bitmap bmp = null;
		try {
			BitmapFactory.Options factory = new BitmapFactory.Options();
			factory.inJustDecodeBounds = true; // 当为true时 允许查询图片不为 图片像素分配内存
			bmp = BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(uri), null, factory);
			int hRatio = (int) Math.ceil(factory.outHeight / (float) dh); // 图片是高度的几倍
			int wRatio = (int) Math.ceil(factory.outWidth / (float) dw); // 图片是宽度的几倍
			// 缩小到 1/ratio的尺寸和 1/ratio^2的像素
			if (hRatio > 1 || wRatio > 1) {
				if (hRatio > wRatio) {
					factory.inSampleSize = hRatio;
				} else
					factory.inSampleSize = wRatio;
			}
			factory.inJustDecodeBounds = false;
			bmp = BitmapFactory.decodeStream(context.getContentResolver()
					.openInputStream(uri), null, factory);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return bmp;

	}

	/**
	 * 如果指定的大小超过原图尺寸，则按原图尺寸返回
	 * 
	 * 属性 ctx
	 * 属性 uri
	 * 属性 targetWidth
	 * 属性 targetHeight
	 * @return
	 * 异常 Exception
	 */
	public static Bitmap optionsBitmap2(Context ctx, Uri uri, int targetWidth,
			int targetHeight) throws Exception {
		Bitmap tmpBmp = null;
		try {
			if (uri == null)// 文件不存在
				return null;
			ContentResolver cr = ctx.getContentResolver();
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			// 先测量图片的尺寸
			if (uri.toString().indexOf("content") != -1)
				BitmapFactory.decodeStream(cr.openInputStream(uri), null, opts);
			else
				BitmapFactory.decodeFile(uri.toString(), opts);

			int imWidth = opts.outWidth; // 图片宽
			int imHeight = opts.outHeight; // 图片高

			int scale = 1;
			if (imWidth > imHeight)
				scale = Math.round((float) imWidth / targetWidth);
			else
				scale = Math.round((float) imHeight / targetHeight);
			scale = scale == 0 ? 1 : scale;

			opts.inJustDecodeBounds = false;
			opts.inSampleSize = scale;
			if (uri.toString().indexOf("content") != -1)
				tmpBmp = BitmapFactory.decodeStream(cr.openInputStream(uri),
						null, opts);
			else {
				FileInputStream fis = new FileInputStream(new File(
						uri.toString()));
				tmpBmp = BitmapFactory.decodeStream(fis, null, opts);
			}
		} catch (OutOfMemoryError e) {
			e.printStackTrace();
		}
		return tmpBmp;
	}

	// /**
	// * 剪切图片
	// * 描述
	// * 属性 属性 bm 图像
	// * 属性 属性 newWidth 宽
	// * 属性 属性 newHeight 高
	// * 属性 @return
	// * @return Bitmap
	// * 异常
	// */
	// public static Bitmap zoomBitmap(Bitmap bm, int newWidth ,int newHeight){
	// // 获得图片的宽高
	// int width = bm.getWidth();
	// int height = bm.getHeight();
	// // 计算缩放比例
	// float scaleWidth = ((float) newWidth) / width;
	// float scaleHeight = ((float) newHeight) / height;
	// // 取得想要缩放的matrix参数
	// Matrix matrix = new Matrix();
	// matrix.postScale(scaleWidth, scaleHeight);
	// // 得到新的图片
	// Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
	// true);
	// return newbm;
	// }

	/**
	 * 剪切图片
	 * 
	 * 属性 bmp
	 * 属性 newWidth
	 * 属性 newHeiht
	 * @return
	 */
	public static Bitmap resizeImage(Bitmap bmp, int newWidth, int newHeiht) {
		if (bmp == null) {
			return null;
		}
		int originWidth = bmp.getWidth();
		int originHeight = bmp.getHeight();
		if (originWidth == newWidth && originHeight == newHeiht)
			return bmp;

		float scaleWidth = ((float) newWidth) / originWidth;
		float scaleHeight = ((float) newHeiht) / originHeight;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizeBitmap = Bitmap.createBitmap(bmp, 0, 0, originWidth,
				originHeight, matrix, true);
		return resizeBitmap;
	}

	/**
	 * 剪切图片
	 * 
	 * 属性 bmp
	 * 属性 scale
	 * @return
	 */
	public static Bitmap resizeImage(Bitmap bmp, float scale) {
		if (bmp == null) {
			return null;
		}
		int originWidth = bmp.getWidth();
		int originHeight = bmp.getHeight();
		Matrix matrix = new Matrix();
		matrix.postScale(scale, scale);
		return Bitmap.createBitmap(bmp, 0, 0, originWidth, originHeight,
				matrix, true);
	}

	/**
	 * 等比例直接缩放图片
	 * 
	 * 属性 bitmapOrg
	 * 属性 newWidth
	 * 属性 newHeight
	 * @return
	 */
	public static Bitmap toSizeBitmap(Bitmap bitmapOrg, int newWidth,
			int newHeight) {

		if (null == bitmapOrg) {
			return null;
		}

		// 获取这个图片的宽和高
		int w = bitmapOrg.getWidth();
		int h = bitmapOrg.getHeight();

		int x, y = 0;

		int wTemp = newWidth * h / newHeight;
		if (wTemp > w) {
			// 以宽度
			h = newHeight * w / newWidth;
			x = 0;
			y = (bitmapOrg.getHeight() - h) / 2;
		} else {
			w = wTemp;
			y = 0;
			x = (bitmapOrg.getWidth() - wTemp) / 2;
		}

		float scaleWidth, scaleHeight = 0;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		Bitmap resizedBitmap;

		// 将整个头像按比例缩放绘制到屏幕中
		// 计算缩放率，新尺寸除原始尺寸
		scaleWidth = ((float) newWidth) / w;
		scaleHeight = ((float) newHeight) / h;

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 创建新的图片
		resizedBitmap = Bitmap
				.createBitmap(bitmapOrg, x, y, w, h, matrix, true);
		// 此壁纸在S5830回收原图后会有问题 caizp 2012-8-31
		// BitmapUtils.destoryBitmap(bitmapOrg);
		return resizedBitmap;
	}

	/**
	 * 等比例直接缩放图片
	 * 
	 * 属性 bitmapOrg
	 * 属性 newWidth
	 * 属性 newHeight
	 * @return
	 */
	public static Bitmap toSizeBitmapForWidth(Bitmap bitmapOrg, int newWidth,
			int newHeight) {

		if (null == bitmapOrg) {
			return null;
		}

		// 获取这个图片的宽和高
		int w = bitmapOrg.getWidth();
		int h = bitmapOrg.getHeight();

		int x, y = 0;

		int wTemp = newWidth * h / newHeight;
		// if (wTemp > w) {
		// 以宽度
		h = newHeight * w / newWidth;
		x = 0;
		y = (bitmapOrg.getHeight() - h) / 2;
		// } else {
		// w = wTemp;
		// y = 0;
		// x = (bitmapOrg.getWidth() - wTemp) / 2;
		// }

		float scaleWidth, scaleHeight = 0;

		// 创建操作图片用的matrix对象
		Matrix matrix = new Matrix();
		Bitmap resizedBitmap;

		// 将整个头像按比例缩放绘制到屏幕中
		// 计算缩放率，新尺寸除原始尺寸
		scaleWidth = ((float) newWidth) / w;
		scaleHeight = ((float) newHeight) / h;

		// 缩放图片动作
		matrix.postScale(scaleWidth, scaleHeight);

		// 创建新的图片
		resizedBitmap = Bitmap
				.createBitmap(bitmapOrg, x, y, w, h, matrix, true);
		// 此壁纸在S5830回收原图后会有问题 caizp 2012-8-31
		// BitmapUtils.destoryBitmap(bitmapOrg);
		return resizedBitmap;
	}

	/**
	 * 
	 * 描述
	 * 属性 属性 bitmap
	 * 属性 @return
	 * @return byte[]
	 * 异常
	 */
	/**
	 * bitmap转换成2进制
	 * 
	 * 属性 bitmap
	 * 属性 quality
	 * @return
	 */
	public static byte[] getBitmapByte(Bitmap bitmap, int quality) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.JPEG, quality, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}
	
	/**
	 * bitmap转换成2进制
	 * 
	 * 属性 bitmap
	 * 属性 quality
	 * @return
	 */
	@SuppressLint("NewApi")
	public static byte[] getBitmapByteForWebp(Bitmap bitmap, int quality) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.WEBP, quality, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	/**
	 * 将Bitmap转换成InputStream
	 * 
	 * 属性 bm
	 * 属性 quality
	 *            品质一般为100
	 * @return
	 */
	public static InputStream Bitmap2InputStream(Bitmap bm, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(CompressFormat.PNG, quality, baos);
		InputStream is = new ByteArrayInputStream(baos.toByteArray());
		return is;
	}

	/**
	 * 将InputStream转换成Bitmap
	 * 
	 * 属性 is
	 * @return
	 */
	public static Bitmap InputStream2Bitmap(InputStream is) {
		return BitmapFactory.decodeStream(is);
	}

	/**
	 * Drawable转换成InputStream
	 * 
	 * 属性 d
	 * @return
	 */
	public static InputStream Drawable2InputStream(Drawable d) {
		Bitmap bitmap = drawable2Bitmap(d);
		return Bitmap2InputStream(bitmap, 100);
	}

	/**
	 * InputStream转换成Drawable
	 * 
	 * 属性 is
	 * 属性 res
	 * @return
	 */
	public static Drawable InputStream2Drawable(InputStream is, Resources res) {
		Bitmap bitmap = InputStream2Bitmap(is);
		return bitmap2Drawable(bitmap, res);
	}

	/**
	 * Bitmap转换成Drawable
	 * 
	 * 属性 bitmap
	 * 属性 res
	 * @return
	 */
	public static Drawable bitmap2Drawable(Bitmap bitmap, Resources res) {
		BitmapDrawable bd = new BitmapDrawable(res, bitmap);
		Drawable d = bd;
		return d;
	}

	/**
	 * bitmap转换为byte[]
	 * 
	 * 属性 bm
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			bm.compress(CompressFormat.PNG, 100, baos);
			byte[] result = baos.toByteArray();
			return result;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * drawable转换成2进制
	 * 
	 * 属性 drawable
	 * @return
	 */
	public static byte[] drawable2Bytes(Drawable drawable) {
		ByteArrayOutputStream baos = null;
		try {
			baos = new ByteArrayOutputStream();
			if (drawable instanceof BitmapDrawable) {
				((BitmapDrawable) drawable).getBitmap().compress(
						CompressFormat.PNG, 100, baos);
			}
			byte[] result = baos.toByteArray();
			return result;
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * byte[]ת转换为bitmap
	 * 
	 * 属性 b
	 * @return
	 */
	public static Bitmap bytes2Bitmap(byte[] b) {
		if (b != null && b.length != 0) {
			return BitmapFactory.decodeByteArray(b, 0, b.length);
		} else {
			return null;
		}
	}

	/**
	 * drawable转换为bitmap
	 * 
	 * 属性 drawable
	 * @return
	 */
	public static Bitmap drawable2Bitmap(Drawable drawable) {
		if (null == drawable)
			return null;
		Bitmap bitmap = Bitmap
				.createBitmap(
						drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
								: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	/**
	 * 获取图像的宽高
	 * 
	 * 属性 path
	 * @return
	 */
	public static int[] getImageWH(String path) {
		int[] wh = { -1, -1 };
		if (path == null) {
			return wh;
		}
		File file = new File(path);
		if (file.exists() && !file.isDirectory()) {
			try {
				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = true;
				InputStream is = new FileInputStream(path);
				BitmapFactory.decodeStream(is, null, options);
				wh[0] = options.outWidth;
				wh[1] = options.outHeight;
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return wh;
	}

	/**
	 * 获取图像的宽高
	 * 
	 * 属性 is
	 * @return
	 */
	public static int[] getImageWH(InputStream is) {
		int[] wh = { -1, -1 };
		if (is == null) {
			return wh;
		}
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(is, null, options);
			wh[0] = options.outWidth;
			wh[1] = options.outHeight;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return wh;
	}

	/**
	 * 计算矩形的上下左右坐标
	 * 
	 * 属性 x
	 * 属性 y
	 * 属性 width
	 * 属性 height
	 * @return
	 */
	public static Rect caculateRect(float x, float y, float width, float height) {
		int left = Math.round(x - width / 2);
		int top = Math.round(y - height / 2);
		int right = Math.round(left + width);
		int bottom = Math.round(top + height);
		return new Rect(left, top, right, bottom);
	}

	/**
	 * 复制图片
	 * 
	 * 属性 bitmapOrg
	 * @return
	 */
	public static Bitmap copyBitmap(Bitmap bitmapOrg) {
		if (null == bitmapOrg)
			return null;
		Bitmap resultBitmap = Bitmap.createScaledBitmap(bitmapOrg,
				bitmapOrg.getWidth(), bitmapOrg.getHeight(), true);
		Canvas canvas = new Canvas();
		canvas.setBitmap(resultBitmap);
		Paint paint = new Paint();
		paint.setFilterBitmap(true);
		paint.setAntiAlias(true);
		canvas.drawBitmap(bitmapOrg, 0, 0, paint);
		return resultBitmap;
	}

	/**
	 * 生成高亮投影
	 * 
	 * 属性 ctx
	 * 属性 sourceBmp
	 *            图片
	 * 属性 bmpScale
	 *            影的缩放比例
	 * @return
	 */
	public static Bitmap createBlurBitmap(Context ctx, Bitmap sourceBmp,
			float bmpScale) {
		if (null == sourceBmp)
			return null;

		final float scale = ScreenUtil.getDisplayMetrics(ctx).density;

		// calculate the inner blur
		Canvas srcDstCanvas = new Canvas();
		srcDstCanvas.setBitmap(sourceBmp);
		srcDstCanvas.drawColor(0xFF000000, Mode.SRC_OUT);
		BlurMaskFilter innerBlurMaskFilter = new BlurMaskFilter(scale * 2.0f,
				BlurMaskFilter.Blur.NORMAL);
		Paint mBlurPaint = new Paint();
		mBlurPaint.setFilterBitmap(true);
		mBlurPaint.setAntiAlias(true);
		mBlurPaint.setMaskFilter(innerBlurMaskFilter);
		int[] thickInnerBlurOffset = new int[2];
		Bitmap thickInnerBlur = sourceBmp.extractAlpha(mBlurPaint,
				thickInnerBlurOffset);

		// mask out the inner blur
		Paint mErasePaint = new Paint();
		mErasePaint
				.setXfermode(new PorterDuffXfermode(Mode.DST_OUT));
		mErasePaint.setFilterBitmap(true);
		mErasePaint.setAntiAlias(true);
		srcDstCanvas.setBitmap(thickInnerBlur);
		srcDstCanvas.drawBitmap(sourceBmp, -thickInnerBlurOffset[0],
				-thickInnerBlurOffset[1], mErasePaint);
		srcDstCanvas.drawRect(0, 0, -thickInnerBlurOffset[0],
				thickInnerBlur.getHeight(), mErasePaint);
		srcDstCanvas.drawRect(0, 0, thickInnerBlur.getWidth(),
				-thickInnerBlurOffset[1], mErasePaint);

		// draw the inner and outer blur
		Paint mHolographicPaint = new Paint();
		mHolographicPaint.setFilterBitmap(true);
		mHolographicPaint.setAntiAlias(true);
		mHolographicPaint.setAlpha(150);
		srcDstCanvas.setBitmap(sourceBmp);
		srcDstCanvas.drawColor(0, Mode.CLEAR);
		final int outlineColor = Color.parseColor("#33b5e5");
		mHolographicPaint.setColor(outlineColor);
		srcDstCanvas.drawBitmap(thickInnerBlur, thickInnerBlurOffset[0],
				thickInnerBlurOffset[1], mHolographicPaint);
		thickInnerBlur.recycle();

		//
		BlurMaskFilter outerBlurMaskFilter = new BlurMaskFilter(scale * 2.0f,
				BlurMaskFilter.Blur.OUTER);
		mBlurPaint.setMaskFilter(outerBlurMaskFilter);
		int[] outerBlurOffset = new int[2];
		Bitmap thickOuterBlur = sourceBmp.extractAlpha(mBlurPaint,
				outerBlurOffset);
		srcDstCanvas.drawBitmap(thickOuterBlur, outerBlurOffset[0],
				outerBlurOffset[1], mHolographicPaint);
		thickOuterBlur.recycle();

		// draw the bright outline
		mHolographicPaint.setColor(outlineColor);
		BlurMaskFilter sThinOuterBlurMaskFilter = new BlurMaskFilter(
				scale * 1.0f, BlurMaskFilter.Blur.OUTER);
		mBlurPaint.setMaskFilter(sThinOuterBlurMaskFilter);
		int[] brightOutlineOffset = new int[2];
		Bitmap brightOutline = sourceBmp.extractAlpha(mBlurPaint,
				brightOutlineOffset);
		srcDstCanvas.drawBitmap(brightOutline, brightOutlineOffset[0],
				brightOutlineOffset[1], mHolographicPaint);
		brightOutline.recycle();

		Matrix matrix = new Matrix();
		matrix.postScale(bmpScale, bmpScale); // 长和宽放大缩小的比例
		Bitmap bitmap = Bitmap.createBitmap(sourceBmp, 0, 0,
				sourceBmp.getWidth(), sourceBmp.getHeight(), matrix, true);
		sourceBmp.recycle();
		return bitmap;
	}

	/**
	 * 资源转成bitmap
	 * 
	 * 先通过BitmapFactory.decodeStream方法，创建出一个bitmap，再将其设为ImageView的 source，
	 * decodeStream最大的秘密在于其直接调用JNI>>nativeDecodeAsset()来完成decode，
	 * 无需再使用java层的createBitmap，从而节省了java层的空间。
	 * 如果在读取时加上图片的Config参数，可以跟有效减少加载的内存，从而跟有效阻止抛out of Memory异常
	 * 另外，decodeStream直接拿的图片来读取字节码了， 不会根据机器的各种分辨率来自动适应
	 * 使用了decodeStream之后，需要在hdpi和mdpi，ldpi中配置相应的图片资源，
	 * 否则在不同分辨率机器上都是同样大小（像素点数量），显示出来的大小就不对了<br>
	 * 
	 * 属性 context
	 * 属性 resId
	 * 属性 insampleSize
	 * @return
	 */
	public static Bitmap decodeStreamABitmap(Context context, int resId,
			int insampleSize) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = false;
		opts.inSampleSize = insampleSize;
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opts);
	}

	/**
	 * 资源转成bitmap
	 * 
	 * 属性 context
	 * 属性 resId
	 * 属性 targetWidth
	 * 属性 targetHeight
	 * @return
	 */
	public static Bitmap decodeStreamABitmap(Context context, int resId,
			int targetWidth, int targetHeight) {
		InputStream is = context.getResources().openRawResource(resId);
		int[] imageWH = getImageWH(is);
		if (imageWH == null)
			return null;

		int scale = 1;
		if (imageWH[0] > imageWH[1])
			scale = Math.round((float) imageWH[0] / targetWidth);
		else
			scale = Math.round((float) imageWH[1] / targetHeight);
		scale = scale == 0 ? 1 : scale;

		return decodeStreamABitmap(context, resId, scale);
	}

	/**
	 * 生成图片文件
	 * 
	 * 属性 d
	 * 属性 filePath
	 * @return
	 */
	public static boolean saveDrawable2file(Drawable d, String filePath) {
		Bitmap b = drawable2Bitmap(d);
		return saveBitmap2file(b, filePath, CompressFormat.PNG);
	}

	/**
	 * 生成图片文件
	 * 
	 * 属性 bmp
	 * 属性 filePath
	 * @return
	 */
	public static boolean saveBitmap2file(Bitmap bmp, String filePath) {
		CompressFormat format = CompressFormat.JPEG;
		int quality = 100;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (null == stream)
			return false;
		return bmp.compress(format, quality, stream);
	}

	/**
	 * 生成图片文件
	 * 
	 * 属性 bmp
	 * 属性 filePath
	 * @return
	 */
	public static boolean saveBitmap2filePng(Bitmap bmp, String filePath) {
		CompressFormat format = CompressFormat.PNG;
		int quality = 100;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(filePath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (null == stream)
			return false;
		return bmp.compress(format, quality, stream);
	}

	/**
	 * 生成图片文件
	 * 
	 * 属性 bmp
	 * 属性 filePath
	 * 属性 format
	 * @return
	 */
	public static boolean saveBitmap2file(Bitmap bmp, String filePath,
			CompressFormat format) {
		if (bmp == null || bmp.isRecycled())
			return false;

		int quality = 100;
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (null == stream)
			return false;

		return bmp.compress(format, quality, stream);
	}

	/**
	 * 安全摧毁图片
	 * 
	 * 属性 drawable
	 */
	public static void safeDestoryDrawable(Drawable drawable) {
		if (null == drawable)
			return;
		drawable.setCallback(null);
		drawable = null;
	}

	/**
	 * 回收资源
	 * 
	 * 属性 bmp
	 */
	public static void destoryBitmap(Bitmap bmp) {
		if (null != bmp && !bmp.isRecycled()) {
			bmp.recycle();
			bmp = null;
		}
	}

	/**
	 * 摧毁图片
	 * 
	 * 属性 drawable
	 */
	public static void destoryDrawable(Drawable drawable) {
		if (null == drawable)
			return;
		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable b = (BitmapDrawable) drawable;
			destoryBitmap(b.getBitmap());
		}
		drawable.setCallback(null);
		drawable = null;
	}

	/**
	 * 图片合成
	 * 
	 * 属性 src
	 * 属性 dst
	 * @return
	 */
	public static Bitmap craeteComposeBitmap(Bitmap src, Bitmap dst) {
		Bitmap newb = Bitmap.createBitmap(src.getWidth(), src.getHeight(),
				Config.ARGB_8888);
		Canvas cv = new Canvas(newb);
		cv.drawBitmap(src, 0, 0, null);
		cv.drawBitmap(dst, 0, 0, null);
		return newb;
	}

	/**
	 * 按照指定尺寸获取点9图
	 * 
	 * 属性 ctx
	 * 属性 w
	 * 属性 h
	 * 属性 resId
	 * @return
	 */
	public static Bitmap getNinePatchBitmap(Context ctx, int w, int h, int resId) {
		Drawable maskDrawable = ctx.getResources().getDrawable(resId);
		Bitmap bitmap = Bitmap
				.createBitmap(
						w,
						h,
						maskDrawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
								: Config.RGB_565);
		Canvas canvas = new Canvas(bitmap);
		maskDrawable.setBounds(0, 0, w, h);
		maskDrawable.draw(canvas);
		return bitmap;
	}

	// /**
	// * 获取加载状态图
	// *
	// * 属性 ctx
	// * 属性 resId
	// * @return
	// */
	// private static final HashMap<String, Bitmap> bitmaps = new
	// HashMap<String, Bitmap>();
	// public static Bitmap getLoadStateBitmap(Context ctx, int resId) {
	// final String key = String.valueOf(resId);
	// Bitmap b = bitmaps.get(key);
	// if (b != null) {
	// return b;
	// }
	// b = decodeStreamABitmap(ctx, resId);
	// bitmaps.put(key, b);
	// return b;
	// }
	/**
	 * 以最省内存的方式读取图片资源
	 * 
	 * 属性 context
	 * 属性 resId
	 * @return
	 */
	public static Bitmap readBitmap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取图片资源
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);
	}

	/**
	 * 获取view的bitmap 在内存溢出的情况下，返回null
	 */
	public static Bitmap readBitmapForView(View v) {

		v.clearFocus();
		v.setPressed(false);
		boolean willNotCache = v.willNotCacheDrawing();
		v.setWillNotCacheDrawing(false);
		int color = v.getDrawingCacheBackgroundColor();
		v.setDrawingCacheBackgroundColor(0);
		if (color != 0) {
			v.destroyDrawingCache();
		}
		v.buildDrawingCache();
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap == null) {
			return null;
		}
		Bitmap bitmap = null;
		try {
			bitmap = Bitmap.createBitmap(cacheBitmap);
		} catch (Throwable th) {
			th.printStackTrace();
		}
		v.destroyDrawingCache();
		v.setWillNotCacheDrawing(willNotCache);
		v.setDrawingCacheBackgroundColor(color);

		return bitmap;
	}

	/**
	 * <p>
	 * 获取view的cache
	 * </p>
	 * <br>
	 * <p>
	 * 注意:此处仅获取view的缓存，没有create新的bitmap，所以建议不要调用Bitmap.recycle()显式回收
	 * </p>
	 * 
	 * <p>
	 * date: 2012-8-16 下午06:02:15
	 * 
	 * 属性 v
	 * @return
	 */
	public static Bitmap readBitmapForViewCache(View v) {
		Bitmap cacheBitmap = v.getDrawingCache();
		if (cacheBitmap != null)
			return cacheBitmap;

		v.clearFocus();
		v.setPressed(false);

		v.setWillNotCacheDrawing(false);
		v.setDrawingCacheEnabled(true);

		int color = v.getDrawingCacheBackgroundColor();

		if (color != 0) {
			v.destroyDrawingCache();
			v.setDrawingCacheBackgroundColor(0);
		}
		v.buildDrawingCache();
		cacheBitmap = v.getDrawingCache();

		if (cacheBitmap == null) {
			return null;
		}
		return cacheBitmap;
	}

	/**
	 * 增加图蒙版
	 * 
	 * 属性 bitmap
	 *            原图
	 * 属性 bgResId
	 *            背景图
	 * 属性 cutResId
	 *            遮罩图
	 * @return
	 * 描述
	 */
	@SuppressLint("WrongConstant")
	public Bitmap toMask(Context context, Bitmap bitmap, int bgResId,
						 int cutResId) {
		Paint paint = new Paint();
		Bitmap bgBm = decodeBitmap(context, bgResId);
		Bitmap cutBm = decodeBitmap(context, cutResId);

		int cutWidth = cutBm.getWidth();
		int cutHeight = cutBm.getHeight();
		int bgWidth = bgBm.getWidth();
		int bgHeight = bgBm.getHeight();
		Bitmap outputBm = Bitmap.createBitmap(bgWidth, bgHeight,
				Config.ARGB_8888);
		bitmap = BitmapUtil.toSizeBitmap(bitmap, cutWidth, cutHeight);
		Canvas canvas = new Canvas(outputBm);
		canvas.drawBitmap(bgBm, 0, 0, paint);

		int bleft = bgWidth / 2 - cutWidth / 2;
		int btop = bgHeight / 2 - cutHeight / 2;

		int saveFlags = Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG
				| Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
				| Canvas.FULL_COLOR_LAYER_SAVE_FLAG
				| Canvas.CLIP_TO_LAYER_SAVE_FLAG;
		canvas.saveLayer(bleft, btop, cutWidth + bleft, cutHeight + btop, null,
				saveFlags);

		canvas.drawBitmap(cutBm, bleft, btop, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		int left = cutWidth / 2 - bitmap.getWidth() / 2;
		int top = cutHeight / 2 - bitmap.getHeight() / 2;
		canvas.drawBitmap(bitmap, bleft + left, btop + top, paint);
		paint.setXfermode(null);
		canvas.restore();
		return outputBm;
	}

	/**
	 * 资源转换成resId
	 * 
	 * 属性 context
	 * 属性 resId
	 * @return
	 */
	public static Bitmap decodeBitmap(Context context, int resId) {
		return BitmapFactory.decodeResource(context.getResources(), resId);
	}

	/**
	 * 图片圆角处理
	 * 
	 * 属性 bitmap
	 * 属性 pixels
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {// h
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
				bitmap.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, pixels, pixels, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		return output;
	}

	/**
	 * 图片圆角处理
	 * 
	 * 属性 bitmap
	 * 属性 roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 改变图片颜色
	 * 
	 * 属性 bitmap
	 * 属性 newColor
	 * @return
	 */
	public Bitmap changeBitmapColor(Bitmap bitmap, int newColor) {
		if (bitmap == null) {
			return bitmap;
		}
		int bitmapW = bitmap.getWidth();
		int bitmapH = bitmap.getHeight();
		int[] pixels = new int[bitmapW * bitmapH];
		int alph = 0;
		int currColor = 0;
		bitmap.getPixels(pixels, 0, bitmapW, 0, 0, bitmapW, bitmapH);
		newColor &= 0x00ffffff;
		for (int i = 0; i < pixels.length; i++) {
			currColor = pixels[i];
			if (currColor == 0) {
				continue;
			}
			alph = currColor & 0xff000000;
			if (alph == 0) {
				continue;
			}
			currColor = newColor | alph;
			pixels[i] = currColor;
		}
		bitmap = Bitmap
				.createBitmap(pixels, bitmapW, bitmapH, Config.ARGB_8888);
		return bitmap;
	}
}
