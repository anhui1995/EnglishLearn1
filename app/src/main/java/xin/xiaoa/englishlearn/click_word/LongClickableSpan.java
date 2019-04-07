package xin.xiaoa.englishlearn.click_word;

import android.text.style.ClickableSpan;

public abstract class LongClickableSpan extends ClickableSpan {
    /**
     * 长按事件
     * @param click
     */
    public abstract void onLongClick(String click);
}
