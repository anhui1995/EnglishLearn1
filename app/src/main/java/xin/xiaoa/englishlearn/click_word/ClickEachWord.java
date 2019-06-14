package xin.xiaoa.englishlearn.click_word;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.service.PlayEnglish;

import static android.view.View.FOCUSABLE_AUTO;

public class ClickEachWord {
    private Context context;

    @SuppressLint("HandlerLeak")


    @RequiresApi(api = Build.VERSION_CODES.O)
    public ClickEachWord(Context cont, TextView tv) {
        context = cont;
        getEachWord(tv);
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        tv.setFocusable(FOCUSABLE_AUTO);
    }

    public void getEachWord(TextView textView){
        Spannable spans = (Spannable)textView.getText();
        Integer[] indices = getIndices(
                textView.getText().toString().trim(), ' ');
        int start = 0;
        int end = 0;
        // to cater last/only word loop will run equal to the length of indices.length
        for (int i = 0; i <= indices.length; i++) {
            ClickableSpan clickSpan = getClickableSpan();
            // to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(clickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        //改变选中文本的高亮颜色
 //       textView.setHighlightColor(Color.GREEN);
       // textView.setHintTextColor(Color.GREEN);
       // textView.setBackgroundColor(Color.GRAY);
    }
    private ClickableSpan getClickableSpan(){
        return new ClickableSpan() {

            @Override
            public void onClick(View widget) {
                System.out.println("点击文字okok1234");
                try {
                    TextView tv = (TextView) widget;
                    // System.out.println("长按123文字" + tv.getText());
                    String s = tv
                            .getText()
                            .subSequence(tv.getSelectionStart(),
                                    tv.getSelectionEnd()).toString();
//                    dialog(s);
                    new ClickWordDialog(context,s);
                }
                catch (Exception e){
                    System.out.println("点击文字ERR:"+e);
                }
            }

            @Override
            public void updateDrawState(TextPaint textPaint) {
//                ds.setColor(Color.BLUE);
//                ds.setUnderlineText(true);
 //              int textColor = isPressed ? pressedTextColor : normalTextColor;
//                textPaint.setColor(textColor);
                textPaint.bgColor = Color.TRANSPARENT;
            }
        };
    }

    public static Integer[] getIndices(String s, char c) {
        int pos = s.indexOf(c, 0);
        List<Integer> indices = new ArrayList<Integer>();
        while (pos != -1) {
            indices.add(pos);
            pos = s.indexOf(c, pos + 1);
        }
        return (Integer[]) indices.toArray(new Integer[0]);
    }

}
