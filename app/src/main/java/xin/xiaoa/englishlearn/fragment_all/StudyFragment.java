package xin.xiaoa.englishlearn.fragment_all;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.activity.TestActivity;
import xin.xiaoa.englishlearn.example_sentence.ExampleSentenceLv;
import xin.xiaoa.englishlearn.fragment_study.StudyAllWordItem;
import xin.xiaoa.englishlearn.example_sentence.ExampleSentenceAdapter;
import xin.xiaoa.englishlearn.example_sentence.ExampleSentenceItem;
import xin.xiaoa.englishlearn.fragment_study.StudyMeanListAdapter;
import xin.xiaoa.englishlearn.fragment_study.StudyWordMeaningItem;
import xin.xiaoa.englishlearn.review.Review;
import xin.xiaoa.englishlearn.review.ReviewListItem;
import xin.xiaoa.englishlearn.service.ELApplication;
import xin.xiaoa.englishlearn.service.PlayEnglish;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class StudyFragment extends Fragment {

    View view;
    Button butKnow;
    Button butVague;
    Button butForget;
    Button butYoudao;
    Button butBaidu;
    Button butJinshan;
    Button butGoogle;

    ListView lvExample;
    ListView lvMean;

    TextView textviewEngling;
    TextView textviewYinbiao;

    LinearLayout layoutFayin;
    RelativeLayout layoutMean;
    LinearLayout layoutCover;

    String strShow;
    Context context;
    ResultSet rsList;
    StudyAllWordItem allWordItem1;
    StudyMeanListAdapter studyMeanListAdapter;
    StudyWordMeaningItem studyWordMeaningItem;

    ResultSet sqlResultSet;
    String playEnglishName;
    String playFayinStr;

    int index=0;
    private List<StudyAllWordItem> Lists = new ArrayList<>();
    private List<StudyWordMeaningItem> studyWordMeaningItemLists;
    private List<ReviewListItem> reviewLists;
    Review review;
    Cursor cursorWordDetail;
    Cursor cursorWordList;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    setUnitWordList();
                    break;
                case 2:
                    setNext();
                    break;
                case 3:
                    showToast(strShow);
                    break;
                case 4:
                    showExample();
                    break;
                case 5:
                    setWordDetail();
                    break;
                default:
                    break;
            }
        }

        ;
    };
    void showExample(){
        System.out.println("k开始解析例句11");
        List<ExampleSentenceItem> exampleSentenceItemLists = new ArrayList<>();
        ExampleSentenceItem exampleSentenceItem;
//        exampleSentenceItem = new ExampleSentenceItem();
//        exampleSentenceItem.setStrChinese("抱最好的希望，做最坏的打算。");
//        exampleSentenceItem.setStrEnglish("Hope for the best, prepare for the worst.");
//        exampleSentenceItemLists.add(exampleSentenceItem);
        System.out.println("k开始解析例句22");
        try { //链接数据库 if(name.equals("admin")){

            while(sqlResultSet.next()) {
                System.out.println("k开始解析例句33");
                System.out.println(sqlResultSet.getString("english"));
                exampleSentenceItem = new ExampleSentenceItem();
                exampleSentenceItem.setStrEnglish(sqlResultSet.getString("english"));
                exampleSentenceItem.setStrChinese(sqlResultSet.getString("chinese"));
                exampleSentenceItemLists.add(exampleSentenceItem);

            }
        } catch(Exception e) { System.out.println("例句解析问题问题"+e); }

        new ExampleSentenceLv(lvExample,context, exampleSentenceItemLists);
    }

    void getExample(final String word){
        new Thread(){
            public void run(){
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    sqlResultSet = ELApplication.getSql().sel("SELECT * FROM e_sentence WHERE word = '"+word+"'"); //查询
                    System.out.println("已经获取结果集 getExample()  "+word);

                } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
                handler.sendEmptyMessage(4);
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    void setWordDetail(){
        while (cursorWordDetail.moveToNext()) {
            playFayinStr  = cursorWordDetail.getString(cursorWordDetail.getColumnIndex("fayin"));
            studyWordMeaningItemLists = new ArrayList<>();

            textviewYinbiao.setText("["+cursorWordDetail.getString(cursorWordDetail.getColumnIndex("yinbiao"))+"]");
            studyWordMeaningItem = new StudyWordMeaningItem();

            studyWordMeaningItem.setKey("[名]");
            studyWordMeaningItem.setString(cursorWordDetail.getString(cursorWordDetail.getColumnIndex("n")));
            if(!"".equals(studyWordMeaningItem.getString())){
                studyWordMeaningItemLists.add(studyWordMeaningItem);
                studyWordMeaningItem = new StudyWordMeaningItem();
            }

            studyWordMeaningItem.setKey("[动]");
            studyWordMeaningItem.setString(cursorWordDetail.getString(cursorWordDetail.getColumnIndex("v")));
            if(!"".equals(studyWordMeaningItem.getString())){
                studyWordMeaningItemLists.add(studyWordMeaningItem);
                studyWordMeaningItem = new StudyWordMeaningItem();
            }

            studyWordMeaningItem.setKey("[形]");
            studyWordMeaningItem.setString(cursorWordDetail.getString(cursorWordDetail.getColumnIndex("adj")));
            if(!"".equals(studyWordMeaningItem.getString())){
                studyWordMeaningItemLists.add(studyWordMeaningItem);
                studyWordMeaningItem = new StudyWordMeaningItem();
            }

            studyWordMeaningItem.setKey("[副]");
            studyWordMeaningItem.setString(cursorWordDetail.getString(cursorWordDetail.getColumnIndex("adv")));
            if(!"".equals(studyWordMeaningItem.getString())){
                studyWordMeaningItemLists.add(studyWordMeaningItem);
                studyWordMeaningItem = new StudyWordMeaningItem();
            }

            studyWordMeaningItem.setKey("[其他]");
            studyWordMeaningItem.setString(cursorWordDetail.getString(cursorWordDetail.getColumnIndex("other")));
            if(!"".equals(studyWordMeaningItem.getString())){
                studyWordMeaningItemLists.add(studyWordMeaningItem);
            }

            studyMeanListAdapter = new StudyMeanListAdapter(context, studyWordMeaningItemLists);
            lvMean.setAdapter(studyMeanListAdapter);
        }
        cursorWordDetail.close();
    }

    void getWordDetail(final String english){
        new Thread() {
            public void run() {
                try {
                    SQLiteDatabase db = ELApplication.getDb();
                    cursorWordDetail = db.rawQuery("select * from word where english=?", new String[]{english});
                    System.out.println("获取结果集 getWordDetail");
                } catch (Exception e) {
                    System.out.println("结果集获取失败问题 getWordDetail" + e);
                }
                handler.sendEmptyMessage(5);
            }
        }.start();
    }
    @SuppressLint("SetTextI18n")
    void setNext(){
        String english = review.getNext();

        if("".equals(english)){
            System.out.println("今日单词复习完毕。");
            return;
        }
        layoutCover.setVisibility(VISIBLE);
        layoutMean.setVisibility(INVISIBLE);

        getExample(english);
        getWordDetail(english);
        textviewEngling.setText(english);
        playEnglishName = english;
        playFayinStr  = "";

    }

    void showToast(String sss) {
        Toast mToast =Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(sss);
        mToast.show();
    }

//    void openRs() {
//        fds
//                reviewLists = new ArrayList<>();
//        Lists = new ArrayList<>();
//        //Lists = new ArrayList<WordDictationListItem>();
//        //text.setText("开始解析结果集");
//        try { //链接数据库 if(name.equals("admin")){
//
//            while (rsList.next()) {
//
//                allWordItem = new StudyAllWordItem();
//                String s;
//
//                allWordItem.setAdj(rsList.getString("adj"));
//                allWordItem.setAdv(rsList.getString("adv"));
//                allWordItem.setEnglish(rsList.getString("english"));
//                allWordItem.setFayin(rsList.getString("fayin"));
//                allWordItem.setN(rsList.getString("n"));
//                allWordItem.setOther(rsList.getString("other"));
//                allWordItem.setV(rsList.getString("v"));
//                allWordItem.setYinbiao(rsList.getString("yinbiao"));
//                Lists.add(allWordItem);
//            }
//        } catch (Exception e) {
//            System.out.println("结果集解析问题问题" + e);
//        }
//        handler.sendEmptyMessage(2);
//    }

    //handler.sendEmptyMessage(1);
    void setUnitWordList() {
        System.out.println("开始解析结果集  openRs()");
        reviewLists = new ArrayList<>();
        while (cursorWordList.moveToNext()) {

            reviewLists.add(new ReviewListItem(
                    cursorWordList.getString(cursorWordList.getColumnIndex("id")),
                    cursorWordList.getString(cursorWordList.getColumnIndex("english")),
                    cursorWordList.getInt(cursorWordList.getColumnIndex("memoryDatabase")),
                    cursorWordList.getString(cursorWordList.getColumnIndex("nextdate")) ));
        }
        review.setReviewLists(reviewLists);
        cursorWordList.close();
        System.out.println("结果集解析完毕");
        handler.sendEmptyMessage(2);
    }

    @SuppressLint("CutPasteId")
    void initView(){

        lvExample = view.findViewById(R.id.study_lv_example);
        lvMean = view.findViewById(R.id.study_lv_mean);

        textviewEngling = view.findViewById(R.id.study_textview_english);
        textviewYinbiao = view.findViewById(R.id.study_textview_fayin);

        layoutFayin = view.findViewById(R.id.study_Layout_fayin);
        layoutMean = view.findViewById(R.id.study_layout_mean);
        layoutCover = view.findViewById(R.id.study_layout_cover);

        layoutMean.setVisibility(INVISIBLE);

        layoutFayin.setOnClickListener(new MyClickListener());
        layoutCover.setOnClickListener(new MyClickListener());

        butKnow = view.findViewById(R.id.study_button_know);
        butVague = view.findViewById(R.id.study_button_vague);
        butForget = view.findViewById(R.id.study_button_forget);
        butYoudao = view.findViewById(R.id.study_button_youdao);
        butBaidu = view.findViewById(R.id.study_button_baidu);
        butJinshan = view.findViewById(R.id.study_button_jinshan);
        butGoogle = view.findViewById(R.id.study_button_google);

        butKnow.setOnClickListener(new MyClickListener());
        butVague.setOnClickListener(new MyClickListener());
        butForget.setOnClickListener(new MyClickListener());
        butYoudao.setOnClickListener(new MyClickListener());
        butBaidu.setOnClickListener(new MyClickListener());
        butJinshan.setOnClickListener(new MyClickListener());
        butGoogle.setOnClickListener(new MyClickListener());
    }

//    void getUnitWordList() {
//        new Thread() {
//            public void run() {
//                try { //链接数据库 if(name.equals("admin")){
//                    if (!ELApplication.getSql().sqlStation())
//                        System.out.println("数据库连接连接已经断开。");
//                    rsList = ELApplication.getSql().sel("SELECT word.*,adminword.flog FROM word,adminword where adminword.word=word.english  AND adminword.flog='ok' ORDER BY RAND()"); //查询
//                } catch (Exception e) {
//                    System.out.println("结果集获取失败问题" + e);
//                }
//                handler.sendEmptyMessage(1);
//            }
//        }.start();
//    }

    void getUnitWordList() {
        new Thread() {
            public void run() {
                try {
                    SQLiteDatabase db = ELApplication.getDb();
                    cursorWordList = db.rawQuery("select * from review ORDER BY RANDOM()", new String[]{});
                    System.out.println("结果集获取完毕  getUnitWordList()");
                 } catch (Exception e) {
                    System.out.println("结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    @SuppressLint("ValidFragment")
    public StudyFragment(Context con) {
        context = con;
    }
    public StudyFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       // test();
        review = new Review();
        view = inflater.inflate(R.layout.fragment_study, container, false);
        try{
            initView();
            getUnitWordList();
        }
        catch (Exception e){
            System.out.println("onCreateView__"+e);
        }
        return view;
    }
    void test() {
        new Thread() {
            public void run() {
                try {
                    SQLiteDatabase db = ELApplication.getDb();

                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    rsList = ELApplication.getSql().sel("SELECT word.*,adminword.flog FROM word,adminword where adminword.word=word.english  AND adminword.flog='ok' ORDER BY RAND()"); //查询
                    System.out.println("开始写入数据库");

                    while (rsList.next()) {

//                        allWordItem = new StudyAllWordItem();
//                        allWordItem.setAdj(rsList.getString("adj"));
//                        allWordItem.setAdv(rsList.getString("adv"));
//                        allWordItem.setEnglish(rsList.getString("english"));
//                        allWordItem.setFayin(rsList.getString("fayin"));
//                        allWordItem.setN(rsList.getString("n"));
//                        allWordItem.setOther(rsList.getString("other"));
//                        allWordItem.setV(rsList.getString("v"));
//                        allWordItem.setYinbiao(rsList.getString("yinbiao"));

                        String sql = "insert into word " +
                                "(id,english,yinbiao,fayin,n,v,adj,adv,other) values " +
                                "(NULL,'"+ rsList.getString("english")+"','"+
                                rsList.getString("yinbiao").replace("'","''")+"','"+
                                rsList.getString("fayin")+"','"+
                                rsList.getString("n")+"','"+
                                rsList.getString("v")+"','"+
                                rsList.getString("adj")+"','"+
                                rsList.getString("adv")+"','"+
                                rsList.getString("other")+"')";
                        db.execSQL(sql);
                        System.out.println("写入 -- "+rsList.getString("english"));
                    }
                    System.out.println("写入数据库完毕");
                } catch (Exception e) {
                    System.out.println("结果集获取失败问题" + e);
                }
            }
        }.start();
    }

    //认识按钮
    void butFunKnow(){
        review.setMemory(3);
        setNext();
    }
    //模糊按钮
    void butFunVague(){
        review.setMemory(2);
        setNext();
    }
    //忘记按钮
    void butFunForget(){
        review.setMemory(1);
        setNext();
    }
    void butFunYoudao(){
        System.out.println("butFunYoudao");
    }
    void butFunBaidu(){
        System.out.println("butFunBaidu");
    }
    void butFunJinshan(){
        showToast("butFunJinshan");
    }
    void butFunGoogle(){
        Intent intent = new Intent();
        intent.setClass(context,TestActivity.class);
        startActivity(intent);
    }
    void layFunFayin(){
        playFayin(playFayinStr,playEnglishName);
    }
    void layFunChenge(){
        try{
            layoutCover.setVisibility(INVISIBLE);
            layoutMean.setVisibility(VISIBLE);
            playFayin(playFayinStr,playEnglishName);
        }
        catch (Exception e){
            System.out.println("layFunChenge:"+e);
        }
    }

    void playFayin(String playFayin,String playEnglish){
        new PlayEnglish(playFayin,playEnglish,context);
    }

    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.study_button_know:
                    butFunKnow();break;
                case R.id.study_button_baidu:
                    butFunBaidu();break;
                case R.id.study_button_forget:
                    butFunForget();break;
                case R.id.study_button_vague:
                    butFunVague();break;
                case R.id.study_button_jinshan:
                    butFunJinshan();break;
                case R.id.study_button_google:
                    butFunGoogle();break;
                case R.id.study_button_youdao:
                    butFunYoudao();break;
                case R.id.study_Layout_fayin:
                    layFunFayin();break;
                case R.id.study_layout_cover:
                    layFunChenge();break;
            }
        }
    }

}
