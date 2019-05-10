package xin.xiaoa.englishlearn.example_sentence;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.click_word.ClickEachWord;

import static android.view.View.FOCUSABLE;
import static android.view.View.FOCUSABLE_AUTO;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ExampleSentenceAdapter extends BaseAdapter {
    private Context context;
    private List<ExampleSentenceItem> lists;

    public ExampleSentenceAdapter(Context context, List<ExampleSentenceItem> lists) {
        super();
        this.context = context;
        this.lists = lists;
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        ExampleSentenceAdapter.HolderView holderView = null;
        ExampleSentenceItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new ExampleSentenceAdapter.HolderView();
            arg1 = View.inflate(context, R.layout.study_example_item, null);
            holderView.tvEnglish = arg1.findViewById(R.id.study_example_item_english);
            holderView.tvChinese = arg1.findViewById(R.id.study_example_item_chinese);

            holderView.tvChinese.setVisibility(entity.getVisiable());

            holderView.tvChinese.setText(entity.getStrChinese());

            holderView.tvChinese.setOnClickListener(new MyOnClickListener());

   //         holderView.tvEnglish.setText(entity.getStrEnglish());
            holderView.tvEnglish.setText(entity.getStrEnglish(),TextView.BufferType.SPANNABLE);
            new ClickEachWord(context,holderView.tvEnglish);
           // holderView.tvEnglish.setFocusable(FOCUSABLE_AUTO);
            arg1.setTag(holderView);
        } else {
            holderView = (ExampleSentenceAdapter.HolderView) arg1.getTag();
        }
        return arg1;
        // TODO: Implement this method
        //return null;
    }

    @Override
    public int getCount() {
        // TODO: Implement this method
        return lists.size();
    }


    //  UnitListItem

    class HolderView {
        TextView tvChinese;
        TextView tvEnglish;
    }


    class MyOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View tv) {
            System.out.println("中文点击1234");
            tv.setVisibility(tv.getVisibility()==INVISIBLE ? VISIBLE :INVISIBLE );
        }
    }


}
