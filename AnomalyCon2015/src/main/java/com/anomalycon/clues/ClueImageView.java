package com.anomalycon.clues;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by alanbly on 3/15/15.
 */
public class ClueImageView extends ImageView {

    public ClueImageView(Context context) {
        super(context);
    }

    public ClueImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClueImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final Drawable clueImage = getDrawable();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        if(clueImage != null) {
            System.out.println("Clue is: "+clueImage.getIntrinsicHeight()+" x "+clueImage.getIntrinsicWidth());
            int height = width * clueImage.getIntrinsicHeight() / clueImage.getIntrinsicWidth();
            System.out.println("  Set width to "+width+" x "+height);
            setMeasuredDimension(width, height);
        }
        else {
            System.out.println("Clue is null");
            System.out.println("  Set width to "+width+" x "+width);
            setMeasuredDimension(width, width);
        }
    }
}
