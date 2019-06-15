package xin.xiaoa.englishlearn.click_word;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.service.ELApplication;
import xin.xiaoa.englishlearn.service.PlayEnglish;

public class ClickWordDialog {

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
    private String strCmd;

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

    String reg = "[^a-zA-Z]";
    public ClickWordDialog(Context context, String word) {
        this.context = context;
        //this.word = word;
        this.word =word.replaceAll(reg,"");
        strCmd = "";
        dialog();
    }

    public ClickWordDialog(Context context, String word,String strCmd) {
        this.context = context;
        this.strCmd = strCmd;
        this.word =word.replaceAll(reg,"");
        dialog();
    }

    private void dialog(){
    //    System.out.println("tapped on:" + str);

        dialogView = LayoutInflater.from(context).inflate(
                R.layout.click_word_dialog, null);

        listView = dialogView.findViewById(R.id.click_word_dl_lv);
        tvEnglish = dialogView.findViewById(R.id.click_word_dl_english);
        tvYinbiao =  dialogView.findViewById(R.id.click_word_dl_yinbiao);
        relativeLayout = dialogView.findViewById(R.id.click_word_dl_fayin);
        relativeLayout.setOnClickListener(new MyClickListener());

        getWordMean(word);
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setView(dialogView);
        if("".equals(strCmd)){
            dialog.setNegativeButton("加入生词本", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    addToUnknownWords();
                }
            });
        }
        else {
            dialog.setNegativeButton("加入背诵计划", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    ELApplication.getStudyFragmentHandler().sendEmptyMessage(7);
                    addToReview();
                }
            });
        }

        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                // TODO Auto-generated method stub

            }
        });

        dialog.show();
//        dialogDis = dialog.show();
    }

    void addToReview(){
        @SuppressLint("SimpleDateFormat")
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        final String todayDate1 = dateFormat.format(calendar.getTime());
        new Thread() {
            public void run() {
                try {
                    SQLiteDatabase db = ELApplication.getDb();
                    // todayDate = "2019-04-15";
                    @SuppressLint("Recycle")
                    Cursor cursorWordList = db.rawQuery("select * from review where english = ? ORDER BY RANDOM()", new String[]{word});
//                     cursorWordList = db.rawQuery("select * from review  ORDER BY RANDOM()", new String[]{});
                    if(!cursorWordList.moveToNext()) {
                        System.out.println("kaishi 添加");
//                        SQLiteDatabase db = ELApplication.getDb();
                        String sql = "insert into review " +
                                "(id,english,memoryDatabase,nextdate) values " +
                                "(NULL, '"+
                                word +"' ,'0', '"+
                                todayDate1+" ')";
                        db.execSQL(sql);
                        ELApplication.getMyFragmentHandler().sendEmptyMessage(2);
                        System.out.println("添加到背诵成功");
                    }
                    cursorWordList.close();
                    System.out.println("结果集获取完毕  getUnitWordList()");
                } catch (Exception e) {
                    System.out.println("结果集获取失败问题" + e);
                }
            }
        }.start();
    }

    void addToUnknownWords(){
        new Thread() {
            public void run() {
                try {
                    SQLiteDatabase db = ELApplication.getDb();
                    // todayDate = "2019-04-15";
                    @SuppressLint("Recycle")
                    Cursor cursorWordList = db.rawQuery("select * from unknown_words where english = ? ORDER BY RANDOM()", new String[]{word});
//                     cursorWordList = db.rawQuery("select * from review  ORDER BY RANDOM()", new String[]{});
                    if(!cursorWordList.moveToNext()) {
                        System.out.println("kaishi 添加");
//                        SQLiteDatabase db = ELApplication.getDb();
                        String sql = "insert into unknown_words " +
                                "(id,english,learned) values " +
                                "(NULL, '"+
                                word +"','0')";
                        db.execSQL(sql);
                        ELApplication.getMyFragmentHandler().sendEmptyMessage(3);
                        System.out.println("添加到生词本成功");
                    }
                    cursorWordList.close();
                    System.out.println("结果集获取完毕  getUnitWordList()");
                } catch (Exception e) {
                    System.out.println("结果集获取失败问题" + e);
                }
            }
        }.start();
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
