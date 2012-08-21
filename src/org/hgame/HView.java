package org.hgame;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class HView extends View {

	//TODO Why is a needed to override the all constructors?
	public HView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

    public HView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

    public HView(Context context) {
		super(context);
	}

    private Paint tmpPaint = new Paint();
    Rect tmpRect = new Rect();

	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		tmpPaint.setColor(Color.GREEN);
		canvas.drawLine(0, 0,  getWidth(), getHeight(), tmpPaint);
		//canvas.drawRect(0, 0, getWidth(), getHeight(), tmpPaint);
	}
}
