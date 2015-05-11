package silicar.brady.mp3recorder;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义录音质量视图
 * Created by Brady on 2015/4/16.
 * @Version 1.0
 */
public class RecordQuality extends View {

    /**
     * 背景颜色
     */
    private int mBackgroundColor;

    /**
     * 选中颜色
     */
    private int mSelectColor;

    private float mCircleWidth = 20;

    private int select = 3;
    /**
     * 画笔
     */
    private Paint mPaint;

    public RecordQuality(Context context) {
        this(context, null);
    }

    public RecordQuality(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RecordQuality(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.RecordQuality, defStyleAttr, 0);
        //初始化设置
        mBackgroundColor = a.getColor(R.styleable.RecordQuality_backgroundColor, 0xff555555);
        mSelectColor = a.getColor(R.styleable.RecordQuality_selectColor, 0xffeeeeee);
        a.recycle();
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setAntiAlias(true); // 消除锯齿
        mPaint.setStrokeWidth(mCircleWidth); // 设置宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 定义线段断电形状为圆头
        mPaint.setStyle(Paint.Style.STROKE); // 设置空心
        mPaint.setColor(mBackgroundColor); // 设颜色
        float Y = getHeight()/2;
        float Start = (getWidth()-(9*30+10))/2;
        //绘制背景
        for(int i = 0;i<10;i++)
        {
            canvas.drawLine( Start+i*30, Y, Start+i*30+10,Y, mPaint);
        }
        //绘制选中
        mPaint.setColor(mSelectColor); // 设置圆颜色
        for(int i = 0;i<select;i++)
        {
            canvas.drawLine( Start+i*30, Y, Start+i*30+10,Y, mPaint);
        }
    }

    public void setSelect(int select)
    {
        this.select = select;
        postInvalidate();
    }

    public int getSelect()
    {
        return select;
    }
}