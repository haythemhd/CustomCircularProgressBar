package haythem.hd.customprogress;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

public class CircleProgressBar extends View {

    private float mStrokeWidth = 3;
    private float progress = 0;

    private int mStartAngle = -90;
    private int mColor = Color.CYAN;
    private int mProgressColor = Color.BLUE;

    private RectF rectF;

    private Paint mBackgroundPaint;
    private Paint mForegroundPaint;
    private Paint mTextPaint;


    public CircleProgressBar(Context context) {
        super(context);
    }

    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        rectF = new RectF();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CircleProgressBar,
                0, 0);

        //Reading values from the XML layout
        mStrokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_progressBarThickness, mStrokeWidth);
        progress = typedArray.getFloat(R.styleable.CircleProgressBar_progress, progress);
        mColor = typedArray.getInt(R.styleable.CircleProgressBar_progressbarColor, mColor);
        mProgressColor = typedArray.getInt(R.styleable.CircleProgressBar_progressColor, mProgressColor);

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setColor(Color.GRAY);
        mBackgroundPaint.setStyle(Paint.Style.STROKE);
        mBackgroundPaint.setStrokeWidth(mStrokeWidth);


        mForegroundPaint = new Paint();
        mForegroundPaint.setColor(mColor);
        mForegroundPaint.setStyle(Paint.Style.STROKE);
        mForegroundPaint.setStrokeWidth(mStrokeWidth * 2);


        mTextPaint = new Paint();
        mTextPaint.setColor(mProgressColor);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setTextSize(100);

        animate(progress);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        final int width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        final int min = Math.min(width, height) - (int) mStrokeWidth;
        setMeasuredDimension(min, min);
        rectF.set(0 + mStrokeWidth / 2, 0 + mStrokeWidth / 2, min - mStrokeWidth / 2, min - mStrokeWidth / 2);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i("onDraw", "onDraw");
        canvas.drawArc(rectF, 0, 360, false, mBackgroundPaint);
        float angle = 360 * progress / 100;
        canvas.drawArc(rectF, mStartAngle, angle, false, mForegroundPaint);
        Rect bounds = new Rect();

        mTextPaint.getTextBounds(String.format("%.00f", progress), 0, String.format("%.00f", progress).length(), bounds);

        canvas.drawText(String.format("%.00f", progress) + " %", rectF.centerX(), rectF.centerY() + bounds.height() / 2, mTextPaint);
    }

    public void setProgress(float mProgress) {
        this.progress = mProgress;
        invalidate();
    }

    public void AddTenWithAnimation(Context context) {
        if (progress + 10 <= 100) {
            animate(this.progress + 10);
        } else {
            Toast.makeText(context, "Full ", Toast.LENGTH_SHORT).show();
        }
    }

    private void animate(float v) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", v);
        objectAnimator.setDuration(Constantes.DURATION);
        objectAnimator.setInterpolator(new DecelerateInterpolator());
        objectAnimator.start();
    }
}