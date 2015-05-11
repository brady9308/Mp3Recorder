package silicar.brady.mp3recorder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 自定义音量视图
 * Created by Brady on 2015/4/16.
 * @Version 1.0
 */
public class Volume extends View {
    /**
     * 第一圈的颜色
     */
    private int mFirstColor;

    /**
     * 第二圈的颜色
     */
    private int mSecondColor;
    /**
     * 圈的宽度
     */
    private int mCircleWidth;
    /**
     * 当前进度
     */
    private int mCurrentCount = 3;
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 中间的图片
     */
    private Bitmap mImage;
    /**
     * 每个块块间的间隙
     */
    private int mSplitSize;
    /**
     * 个数
     */
    private int mCount;

    private Rect mRect;
    public Volume(Context context) {
        this(context,null);
    }

    public Volume(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Volume(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.Volume, defStyleAttr, 0);
        //初始化设置
        mFirstColor = a.getColor(R.styleable.Volume_firstColor, 0xff555555);
        mSecondColor = a.getColor(R.styleable.Volume_secondColor, 0xffeeeeee);
        mImage = BitmapFactory.decodeResource(getResources(), a.getResourceId(R.styleable.Volume_bg, 0));
        mCircleWidth = a.getDimensionPixelSize(R.styleable.Volume_circleWidth,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 20, getResources().getDisplayMetrics()));
        mCount = a.getInt(R.styleable.Volume_dotCount, 24);
        mSplitSize = a.getInt(R.styleable.Volume_splitSize, 20);


        mPaint = new Paint();
        mRect = new Rect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStrokeWidth(mCircleWidth); // 设置圆环的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        int centre = getWidth() / 2; // 获取圆心的x坐标
        int radius = centre - mCircleWidth / 2;// 半径
        /**
         * 画音量块
         */
        drawOval(canvas, centre, radius);
        /**
         * 绘制中间图片
         */
        //计算内切正方形的位置
        int relRadius = radius - mCircleWidth / 2;// 获得内圆的半径
        // 内切正方形的距离顶部 = mCircleWidth + relRadius - √2 / 2
        mRect.left = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        //内切正方形的距离左边 = mCircleWidth + relRadius - √2 / 2
        mRect.top = (int) (relRadius - Math.sqrt(2) * 1.0f / 2 * relRadius) + mCircleWidth;
        mRect.bottom = (int) (mRect.left + Math.sqrt(2) * relRadius);
        mRect.right = (int) (mRect.left + Math.sqrt(2) * relRadius);
        // 如果图片比较小，那么根据图片的尺寸放置到正中心
        if (mImage.getWidth() < Math.sqrt(2) * relRadius)
        {
            mRect.left = (int) (mRect.left + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getWidth() * 1.0f / 2);
            mRect.top = (int) (mRect.top + Math.sqrt(2) * relRadius * 1.0f / 2 - mImage.getHeight() * 1.0f / 2);
            mRect.right = (int) (mRect.left + mImage.getWidth());
            mRect.bottom = (int) (mRect.top + mImage.getHeight());

        }
        // 绘图
        canvas.drawBitmap(mImage, null, mRect, mPaint);
    }

    /**
     * 根据参数画音量块
     * @param canvas
     * @param centre
     * @param radius
     */
    private void drawOval(Canvas canvas, int centre, int radius) {
        /**
         * 根据需要画的个数以及间隙计算每个块块所占的比例*360
         */
        float itemSize = (360 * 1.0f - mCount * mSplitSize) / mCount;

        RectF oval = new RectF(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

        mPaint.setColor(mFirstColor); // 设置圆环的颜色
        for (int i = 0; i < mCount-4; i++)
        {
            canvas.drawArc(oval, 130+ i * (itemSize + mSplitSize), itemSize, false, mPaint); // 根据进度画圆弧
        }

        mPaint.setColor(mSecondColor); // 设置圆环的颜色
        for (int i = 0; i < mCurrentCount; i++)
        {
            canvas.drawArc(oval, 130+i * (itemSize + mSplitSize), itemSize, false, mPaint); // 根据进度画圆弧
        }
    }

    public void setCurrentCount(int count)
    {
        mCurrentCount = count;
        postInvalidate();
    }
}