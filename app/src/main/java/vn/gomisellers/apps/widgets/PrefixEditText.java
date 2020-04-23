package vn.gomisellers.apps.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class PrefixEditText extends AppCompatEditText {

    private String prefix = "";
    private Rect prefixRect = new Rect();

    public PrefixEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        getPaint().getTextBounds(prefix, 0, prefix.length(), prefixRect);
        getPaint().setColor(Color.LTGRAY);
        prefixRect.right += getPaint().measureText("");

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(prefix, super.getCompoundPaddingLeft(), getBaseline(), getPaint());
    }

    @Override
    public int getCompoundPaddingLeft() {
        return super.getCompoundPaddingLeft() + prefixRect.width();
    }
}
