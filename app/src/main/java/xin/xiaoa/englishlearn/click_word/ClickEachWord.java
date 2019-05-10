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
    private AlertDialog dialogDis;
    private ListView listView;
    private TextView tvEnglish,tvYinbiao;
    private View dialogView;
    private ClickWordListAdapter clickWordListAdapter;
    private WordItem wordItem;
    private List<ClickWordListItem> Lists;
    private RelativeLayout relativeLayout;
    private String word,playFayin="";
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    mSetView();
                    break;
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ClickEachWord(Context cont, TextView tv) {
        context = cont;

        getEachWord(tv);
        tv.setMovementMethod(MyLinkMovementMethod.getInstance());
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
            LongClickableSpan longClickSpan = getLongClickableSpan();
            // to cater last/only word
            end = (i < indices.length ? indices[i] : spans.length());
            spans.setSpan(longClickSpan, start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            start = end + 1;
        }
        //改变选中文本的高亮颜色
        textView.setHighlightColor(Color.GREEN);
       // textView.setHintTextColor(Color.GREEN);
       // textView.setBackgroundColor(Color.GRAY);
    }
//    private ClickableSpan getClickableSpan(){
//        return new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//                TextView tv = (TextView) widget;
//                String s = tv
//                        .getText()
//                        .subSequence(tv.getSelectionStart(),
//                                tv.getSelectionEnd()).toString();
//                dialog(s);
//            }
//            @Override
//            public void updateDrawState(TextPaint ds) {
//                ds.setColor(Color.BLACK);
//                ds.setUnderlineText(false);
//            }
//        };
//    }

    private LongClickableSpan getLongClickableSpan(){
        return new LongClickableSpan() {

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
                   // dialog(s);
                }
                catch (Exception e){
                    System.out.println("点击文字ERR:"+e);
                }
            }

            @Override
            public void onLongClick(String click) {
                System.out.println("长按文字okok");
                dialog(click);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
//                ds.setColor(Color.BLUE);
//                ds.setUnderlineText(true);
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

    void mSetView(){
        if(wordItem == null) return;

        tvEnglish.setText(wordItem.getEnglish());
        tvYinbiao.setText(wordItem.getYinbiao());
        playFayin = wordItem.getFayin();
        Lists = new ArrayList<>();
        if(!"".equals(wordItem.getN()))
            Lists.add(new ClickWordListItem("[名]",wordItem.getN()));

        if(!"".equals(wordItem.getV()))
            Lists.add(new ClickWordListItem("[动]",wordItem.getV()));

        if(!"".equals(wordItem.getAdj()))
            Lists.add(new ClickWordListItem("[形]",wordItem.getAdj()));

        if(!"".equals(wordItem.getAdv()))
            Lists.add(new ClickWordListItem("[副]",wordItem.getAdv()));

        if(!"".equals(wordItem.getOther()))
            Lists.add(new ClickWordListItem("[其它]",wordItem.getOther()));

        clickWordListAdapter = new ClickWordListAdapter(context, Lists );
        listView.setAdapter(clickWordListAdapter);
    }

    void getWordMean(final String word){
        new Thread() {
            public void run() {
                WordMean wordMean = new WordMean(context);
                wordItem = wordMean.such(word);
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    void dialog(String str){
        word = str;
        System.out.println("tapped on:" + str);

        dialogView = LayoutInflater.from(context).inflate(
                R.layout.click_word_dialog, null);

        listView = dialogView.findViewById(R.id.click_word_dl_lv);
        tvEnglish = dialogView.findViewById(R.id.click_word_dl_english);
        tvYinbiao =  dialogView.findViewById(R.id.click_word_dl_yinbiao);
        relativeLayout = dialogView.findViewById(R.id.click_word_dl_fayin);
        relativeLayout.setOnClickListener(new MyClickListener());



        getWordMean(str);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
        //listView.setLayoutParams(lp);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

//              dialog.setIcon(R.drawable.ic_launcher);//窗口头图标
        //dialog.setTitle(str);//窗口名s

        dialog.setView(dialogView);
       // dialog.setView(listView);



//        dialog.setOnDismissListener(new DialogInterface.OnDismissListener()
//        { 	@Override
//        public void onDismiss(DialogInterface dialog)
//        {
//            butf5();
//        }
//        });

        dialog.setNegativeButton("加入生词本", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {

            }
        });
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                // TODO Auto-generated method stub

            }
        });

        dialogDis = dialog.show();
    }

    void fayin(){
        new PlayEnglish(playFayin,word,context);
    }

    class MyClickListener implements View.OnClickListener {
        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.click_word_dl_fayin:
                    fayin();
                    break;
            }
        }
    }

}
