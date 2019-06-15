package xin.xiaoa.englishlearn.fragment_study;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.activity.WordDetailActivity;
import xin.xiaoa.englishlearn.activity.WordDictationActivity;
import xin.xiaoa.englishlearn.click_word.ClickWordDialog;
import xin.xiaoa.englishlearn.service.PlayEnglish;
import xin.xiaoa.englishlearn.worddictation.WordDictationListAdapter;
import xin.xiaoa.englishlearn.worddictation.WordDictationListItem;

public class WordDictation {
    private List<WordDictationListItem> Lists = new ArrayList<>();
    Context context;
    ListView listView;

    void setDictationLists(List<WordDictationListItem> Lists){
        this.Lists = Lists;
    }

    public WordDictation(Context context, ListView listView){
        this.listView = listView;
        this.context = context;
        initListView();
    }

    private void initListView(){

        listView.setOnItemClickListener(new itemClickListener());
        listView.setOnItemLongClickListener(new itemClickLongListener());
    }
    @SuppressLint("SetTextI18n")
    public void setWordDetail(Cursor cursorWordDetail){
        Lists = new ArrayList<>();
        while (cursorWordDetail.moveToNext()) {

            WordDictationListItem wordDictationListItem = new WordDictationListItem();
            String s,tmp;

            tmp = "";
            tmp  = cursorWordDetail.getString(cursorWordDetail.getColumnIndex("n"));
            if (!"".equals(tmp)) {
                int len = tmp.length();
                s = "n:" + tmp.substring(1, len);
                wordDictationListItem.setN(s);
            }

            tmp = "";
            tmp  = cursorWordDetail.getString(cursorWordDetail.getColumnIndex("v"));
            if (!"".equals(tmp)) {
                int len = tmp.length();
                s = "v:" + tmp.substring(1, len);
                wordDictationListItem.setV(s);
            }

            tmp = "";
            tmp  = cursorWordDetail.getString(cursorWordDetail.getColumnIndex("adj"));
            if (!"".equals(tmp)) {
                int len = tmp.length();
                s = "adj:" + tmp.substring(1, len);
                wordDictationListItem.setAdj(s);
            }

            tmp = "";
            tmp  = cursorWordDetail.getString(cursorWordDetail.getColumnIndex("adv"));
            if (!"".equals(tmp)) {
                int len = tmp.length();
                s = "adv:" + tmp.substring(1, len);
                wordDictationListItem.setAdv(s);
            }

            tmp = "";
            tmp  = cursorWordDetail.getString(cursorWordDetail.getColumnIndex("other"));
            if (!"".equals(tmp)) {
                int len = tmp.length();
                s = "other:" + tmp.substring(1, len);
                wordDictationListItem.setOther(s);
            }

            wordDictationListItem.setFlog(true);
            wordDictationListItem.setId((long) cursorWordDetail.getInt(cursorWordDetail.getColumnIndex("id")));
            wordDictationListItem.setFayin(cursorWordDetail.getString(cursorWordDetail.getColumnIndex("fayin")));
            wordDictationListItem.setYinbiao(cursorWordDetail.getString(cursorWordDetail.getColumnIndex("yinbiao")));
            wordDictationListItem.setEnglish(cursorWordDetail.getString(cursorWordDetail.getColumnIndex("english")));

            Lists.add(wordDictationListItem);
        }
        WordDictationListAdapter listAdapt = new WordDictationListAdapter(context, Lists, mListener, mTextLis);
        listView.setAdapter(listAdapt);
    }


    class itemClickLongListener implements AdapterView.OnItemLongClickListener {

        public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long id) {

            WordDictationListItem tmp = Lists.get(i);
            final String str = tmp.getEnglish();
            new ClickWordDialog(context,str);
            //text2.setText("长i"+i+"id"+id);
            return true;
        }

    }

    class itemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> adapter, View view, int i, long id) {
            WordDictationListItem tmp = Lists.get(i);
            TextView tv = view.findViewById(R.id.wordItemTextViewMeaning);

            if (tmp.getFlog()) {
                tmp.setFlog(false);
                tv.setBackgroundColor(0xffF7F3F2);
                tv.setTextColor(0xff000000);
            } else {
                tmp.setFlog(true);
                tv.setBackgroundColor(0xffa9a9a9);
                tv.setTextColor(0xffA9A9A9);
            }
        }
    }

    private WordDictationListAdapter.myTextListener mTextLis = new WordDictationListAdapter.myTextListener() {

        public boolean myOnTouch(View v, MotionEvent me, int a) {
            TextView tv = v.findViewById(R.id.wordItemTextViewEnglish);
            switch (me.getAction()) {
                case 0:
                    tv.setTextColor(0xffaaaaaa);
                    break;
                case 3:
                case 1:
                    tv.setTextColor(0xff000000);
                    WordDictationListItem tmp = Lists.get(a);
                    final String name = tmp.getEnglish();
                    final String fayin = tmp.getFayin();
                    if (tmp.getFayin().equals("")) {
                        Toast.makeText(
                                context,
                                "不存在发音", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    }
                    new PlayEnglish(fayin,name,context);
                    break;
                default:
                    break;
            }
            return true;
        }

    };


    private WordDictationListAdapter.myItemListener mListener = new WordDictationListAdapter.myItemListener() {


        public void myOnClick(int i, View v) {
            WordDictationListItem tmp = Lists.get(i);
            System.out.println("info;" + i);
            new ClickWordDialog(context,tmp.getEnglish());
          //  bundle.putString("n", tmp.getN());

        }


    };
}
