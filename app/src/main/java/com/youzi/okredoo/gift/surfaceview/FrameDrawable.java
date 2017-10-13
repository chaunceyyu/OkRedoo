package com.youzi.okredoo.gift.surfaceview;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import java.io.File;


final public class FrameDrawable {
    private static final String TAG = FrameDrawable.class.getSimpleName();

//    private String mDrawableResPath;
    private File file;
    private Matrix mMatrix;
    private Paint mPaint;

    float mX;
    float mY;
    float mScale = 1.0f;
    float mAlpha = 1.0f;
    long mDuration; //当前帧持续显示时间

    public FrameDrawable(File file, long duration) {
//        mDrawableResPath = drawableResPath;
        this.file = file;
        mDuration = duration;
        mMatrix = new Matrix();
        mPaint = new Paint();
    }

    public Bitmap draw(Canvas canvas, long start) {
        //这里是从assets中获取bitmap，当然也可以从sdcard中获取，这样就可以热更了帧动画了
//        final Bitmap frameBitmap = Utils.loadBitmap(mDrawableResPath);
        final Bitmap frameBitmap = Utils.fileToBitmap(file);
        if (null != frameBitmap) {
            final float scaleX = (float) canvas.getWidth() / frameBitmap.getWidth();
            final float scaleY = (float) canvas.getHeight() / frameBitmap.getHeight();
            mMatrix.setTranslate(mX, mY);
            mMatrix.preScale(scaleX * mScale, scaleY * mScale, 0, 0);
            mPaint.setAlpha((int) (mAlpha * 255));
            canvas.drawBitmap(frameBitmap, mMatrix, mPaint);
        }
        return frameBitmap;
    }
}
