package xin.xiaoa.englishlearn.worddictation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class WordDictationListAdapter extends BaseAdapter {


    private Context context;
    private List<WordDictationListItem> lists;
    private myItemListener mListener;

    private myTextListener mTextLis;
    private String mp3;
    private HolderView holderView = null;

    float textSize = 0;

    private TextPaint textPaint = new TextPaint();


    String stringlength(String get) {
        String str = "";
        return str;
    }

    public WordDictationListAdapter(Context context, List<WordDictationListItem> lists, myItemListener listener, myTextListener textLis) {
        super();
        this.context = context;
        this.lists = lists;
        mListener = listener;
        mTextLis = textLis;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return lists.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }


    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        // TODO Auto-generated method stub
        holderView = null;
        WordDictationListItem entity = lists.get(arg0);

        if (holderView == null) {
            holderView = new HolderView();
            arg1 = View.inflate(context, R.layout.worddictationlistitem, null);
            holderView.wordItemTextViewEnglish = (TextView) arg1.findViewById(R.id.wordItemTextViewEnglish);
            holderView.wordItemTextViewMeaning = (TextView) arg1.findViewById(R.id.wordItemTextViewMeaning);
            holderView.wordItemButtonMore = (Button) arg1.findViewById(R.id.worditemButtonMoer);
            holderView.wordItemTextViewMp = (TextView) arg1.findViewById(R.id.wordItemTextViewMp);
            holderView.id = arg0;
            textSize = textPaint.measureText(entity.getEnglish());

            if (textSize > 59)
                holderView.wordItemTextViewEnglish.setTextSize((int) (900 / textSize));
            holderView.wordItemTextViewEnglish.setText(entity.getEnglish());
            holderView.wordItemTextViewMeaning.setText(entity.getMeaning());
            holderView.wordItemButtonMore.setOnClickListener(mListener);
            //holderView.wordItemTextViewMp.setText(entity.getPronunciation());
            holderView.wordItemTextViewMp.setText("m)");
            holderView.wordItemTextViewEnglish.setOnTouchListener(mTextLis);
            //holderView.wordItemTextViewEnglish.setOnClickListener(mListener);

            if (!entity.getFlog()) {
                holderView.wordItemTextViewMeaning.setBackgroundColor(0xffF7F3F2);
                holderView.wordItemTextViewMeaning.setTextColor(0xff000000);
            } else {
                holderView.wordItemTextViewMeaning.setBackgroundColor(0xffa9a9a9);
                holderView.wordItemTextViewMeaning.setTextColor(0xffA9A9A9);
            }
            holderView.wordItemButtonMore.setTag(arg0);
            holderView.wordItemTextViewEnglish.setTag(arg0);
            arg1.setTag(arg0);
            System.out.println(entity.getEnglish() + "id--" + arg0);
        } else {
            holderView = (HolderView) arg1.getTag();
        }
        return arg1;
    }

    class HolderView {
        TextView wordItemTextViewEnglish;
        TextView wordItemTextViewMeaning;
        TextView wordItemTextViewMp;
        Button wordItemButtonMore;
        int id;
    }

    public static abstract class myItemListener implements View.OnClickListener {

        @Override
        public void onClick(View view)


        {
            myOnClick((Integer) view.getTag(), view);
        }
        public abstract void myOnClick(int position, View v);

    }

    public static abstract class myTextListener implements View.OnTouchListener {

        @Override
        public boolean onTouch(View p1, MotionEvent p2) {
            //System.out.println("汉字"+(Integer) p1.getTag());
            return myOnTouch(p1, p2, (Integer) p1.getTag());
        }
        public abstract boolean myOnTouch(View v, MotionEvent me, int a);
    }
}
