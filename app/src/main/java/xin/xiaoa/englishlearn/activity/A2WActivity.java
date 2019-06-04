package xin.xiaoa.englishlearn.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.article2words.A2WListAdapter;
import xin.xiaoa.englishlearn.article2words.A2wListviewItem;
import xin.xiaoa.englishlearn.article2words.Article2Words;
import xin.xiaoa.englishlearn.click_word.ClickWordListAdapter;
import xin.xiaoa.englishlearn.click_word.ClickWordListItem;
import xin.xiaoa.englishlearn.click_word.WordItem;
import xin.xiaoa.englishlearn.click_word.WordMean;

public class A2WActivity extends Activity {

    TextView tvArticle;
    Button butGet;
    Button butAdd;
    Article2Words article2Words;
    ListView listView;
    A2WListAdapter a2WListAdapter;

    Context context;
    private String word,playFayin="";
    private AlertDialog dialogDis;
    private View dialogView;
    private TextView tvEnglish,tvYinbiao;
    private RelativeLayout relativeLayout;
    private WordItem wordItem;
    private ClickWordListAdapter clickWordListAdapter;
    private List<ClickWordListItem> dialogLists;
    private List<A2wListviewItem> a2wLVLists;

     String content  = "kolya is one of the richest films i've seen in some time." +
            "zdenek sverak plays a confirmed old bachelor ( who's likely to" +
            " remain so ) , who finds his life as a czech cellist increasingly " +
            "impacted by the five-year old boy that he's taking care of . though " +
            "it ends rather abruptly-- and i'm whining , 'cause i wanted to spend " +
            "more time with these characters-- the acting , writing , and production " +
            "values are as high as , if not higher than , comparable american dramas. " +
            "this father-and-son delight-- sverak also wrote the script , while his son , " +
            "jan , directed-- won a golden globe for best foreign language film and , " +
            "a couple days after i saw it , walked away an oscar . in czech and russian , " +
            "with english subtitles . ";

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_a2w);
        viewInit();
        tvArticle.setText(content);

        try{
            //listView.setAdapter(a2WListAdapter);
        }
        catch (Exception e){
            System.out.println("提取单词问题："+e);
        }

    }
    void viewInit(){
        tvArticle = findViewById(R.id.a2w_tv_article);
        butGet = findViewById(R.id.a2w_but_get);
        butAdd = findViewById(R.id.a2w_but_add);
        listView = findViewById(R.id.a2w_lv);
        listView.setOnItemClickListener(new MyOnItemClickListener());
        butGet.setOnClickListener(new MyClickListener());
        butAdd.setOnClickListener(new MyClickListener());
    }

    void buttonGet(){

        article2Words = new Article2Words(tvArticle.getText().toString());
        article2Words.splitWord();
        article2Words.countWordFreq();
        article2Words.sort();
        a2wLVLists = article2Words.getLists();
        a2WListAdapter = new A2WListAdapter(this,a2wLVLists);
        listView.setAdapter(a2WListAdapter);
    }
    void buttonAdd(){

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
    void mSetView(){
        if(wordItem == null) return;

        tvEnglish.setText(wordItem.getEnglish());
        tvYinbiao.setText(wordItem.getYinbiao());
        playFayin = wordItem.getFayin();
        dialogLists = new ArrayList<>();
        if(!"".equals(wordItem.getN()))
            dialogLists.add(new ClickWordListItem("[名]",wordItem.getN()));

        if(!"".equals(wordItem.getV()))
            dialogLists.add(new ClickWordListItem("[动]",wordItem.getV()));

        if(!"".equals(wordItem.getAdj()))
            dialogLists.add(new ClickWordListItem("[形]",wordItem.getAdj()));

        if(!"".equals(wordItem.getAdv()))
            dialogLists.add(new ClickWordListItem("[副]",wordItem.getAdv()));

        if(!"".equals(wordItem.getOther()))
            dialogLists.add(new ClickWordListItem("[其它]",wordItem.getOther()));

        clickWordListAdapter = new ClickWordListAdapter(context, dialogLists );
        listView.setAdapter(clickWordListAdapter);
    }
    void dialog(String str){
        word = str;
        System.out.println("tapped on:" + str);

        dialogView = LayoutInflater.from(this).inflate(
                R.layout.click_word_dialog, null);

        listView = dialogView.findViewById(R.id.click_word_dl_lv);
        tvEnglish = dialogView.findViewById(R.id.click_word_dl_english);
        tvYinbiao =  dialogView.findViewById(R.id.click_word_dl_yinbiao);
        relativeLayout = dialogView.findViewById(R.id.click_word_dl_fayin);
        relativeLayout.setOnClickListener(new MyClickListener());

        getWordMean(str);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setView(dialogView);
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

    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            switch (arg0.getId()) {

                case R.id.a2w_but_get:
                    buttonGet();
                    break;
                case R.id.a2w_but_add:
                    buttonAdd();
            }
        }
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String word = a2wLVLists.get(position).getName().toString();
            dialog(word);
        }
    }
}
