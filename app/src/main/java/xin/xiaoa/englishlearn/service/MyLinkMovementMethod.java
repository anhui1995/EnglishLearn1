package xin.xiaoa.englishlearn.service;

import android.graphics.Color;
import android.text.Layout;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.view.MotionEvent;
import android.widget.TextView;

public class MyLinkMovementMethod  extends LinkMovementMethod {

    private long lastClickTime;
    private boolean isDown = false;
    private static final long CLICK_DELAY = 500l;

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {

        //System.out.println("123456789087654321457890");

        if(isDown){
           // widget.setTextColor(Color.YELLOW);
           /// widget.select
        }

        int action = event.getAction();
        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {


            System.out.println("所有文章："+ widget.getText().toString());
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            LongClickableSpan[] link = buffer.getSpans(off, off, LongClickableSpan.class);
            if (link.length != 0) {

                if (action == MotionEvent.ACTION_UP) {

                    //long tmp = System.currentTimeMillis() - lastClickTime;

                    //System.out.println("时间："+ tmp);
                    if (System.currentTimeMillis() - lastClickTime < CLICK_DELAY) {
//                        Log.e("test", "点击");
                        //点击事件
                        link[0].onClick(widget);

                    } else {
//                        Log.e("test", "长按");
                        //长按事件


                        String s = widget
                                .getText()
                                .subSequence(widget.getSelectionStart(),
                                        widget.getSelectionEnd()).toString();

                        link[0].onLongClick(s);

                    }

                } else if (action == MotionEvent.ACTION_DOWN) {
                    Selection.setSelection(buffer,
                            buffer.getSpanStart(link[0]),
                            buffer.getSpanEnd(link[0]));
                    isDown = true;
                    lastClickTime = System.currentTimeMillis();
                }

                return true;
            } else {
                Selection.removeSelection(buffer);
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    private static MyLinkMovementMethod sInstance;

    public static MovementMethod getInstance() {
        if (sInstance == null)
            sInstance = new MyLinkMovementMethod();

        return sInstance;
    }

}
