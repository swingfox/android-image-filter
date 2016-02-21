package utils.imageprocessing;

/**
 * Created by David on 02/07/2016.
 */
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import android.graphics.RectF;
import android.view.View;

public class GraphicsView extends View {
    private static final String MY_TEXT = "xjaphx: Draw Text on Curve";
    private Path mArc;

    private Paint mPaintText;

    public GraphicsView(Context context) {
        super(context);

        mArc = new Path();
        RectF oval = new RectF(50,100,200,250);;
        mArc.addArc(oval, -180, 200);
        mPaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintText.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintText.setColor(Color.WHITE);
        mPaintText.setTextSize(20f);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawTextOnPath(MY_TEXT, mArc, 0, 20, mPaintText);
        invalidate();
    }
}