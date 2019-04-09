package xin.xiaoa.englishlearn.fragment_all;


import android.annotation.SuppressLint;
import android.content.Context;
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
import xin.xiaoa.englishlearn.example_sentence.ExampleSentenceLv;
import xin.xiaoa.englishlearn.fragment_study.StudyAllWordItem;
import xin.xiaoa.englishlearn.example_sentence.ExampleSentenceAdapter;
import xin.xiaoa.englishlearn.example_sentence.ExampleSentenceItem;
import xin.xiaoa.englishlearn.fragment_study.StudyMeanListAdapter;
import xin.xiaoa.englishlearn.fragment_study.StudyWordMeaningItem;
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
    StudyAllWordItem allWordItem;
    StudyMeanListAdapter studyMeanListAdapter;
    StudyWordMeaningItem studyWordMeaningItem;

    ExampleSentenceItem exampleSentenceItem;

    ResultSet sqlResultSet;
    String playEnglishName;
    String playFayinStr;

    int index=0;
    private List<StudyAllWordItem> Lists = new ArrayList<>();
    private List<StudyWordMeaningItem> studyWordMeaningItemLists;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    openRs();
                    break;
                case 2:
                    showEnglish();
                    break;
                case 3:
                    showToast(strShow);
                    break;
                case 4:
                    showExample();
                    break;
                default:
                    break;
            }
        }

        ;
    };
    void showExample(){

        List<ExampleSentenceItem> exampleSentenceItemLists = new ArrayList<>();

//        exampleSentenceItem = new ExampleSentenceItem();
//        exampleSentenceItem.setStrChinese("抱最好的希望，做最坏的打算。");
//        exampleSentenceItem.setStrEnglish("Hope for the best, prepare for the worst.");
//        exampleSentenceItemLists.add(exampleSentenceItem);
        System.out.println("k开始解析例句");
        try { //链接数据库 if(name.equals("admin")){
            while(sqlResultSet.next()) {
                System.out.println("k开始解析例句");
                System.out.println(sqlResultSet.getString("english"));
                exampleSentenceItem = new ExampleSentenceItem();
                exampleSentenceItem.setStrEnglish(sqlResultSet.getString("english"));
                exampleSentenceItem.setStrChinese(sqlResultSet.getString("chinese"));
                exampleSentenceItemLists.add(exampleSentenceItem);

            }
        } catch(Exception e) { System.out.println("结果集解析问题问题"+e); }

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
                    System.out.println("已经获取结果集"+word);

                } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
                handler.sendEmptyMessage(4);
            }
        }.start();
    }

    @SuppressLint("SetTextI18n")
    void showEnglish(){

        layoutCover.setVisibility(VISIBLE);
        layoutMean.setVisibility(INVISIBLE);

        allWordItem = Lists.get(index);
        getExample(allWordItem.getEnglish());
        System.out.println(allWordItem.getEnglish());


        studyWordMeaningItemLists = new ArrayList<>();
        index++;
        textviewEngling.setText(allWordItem.getEnglish());
        playEnglishName = allWordItem.getEnglish();
        playFayinStr = allWordItem.getFayin();

        textviewYinbiao.setText("["+allWordItem.getYinbiao()+"]");
        studyWordMeaningItem = new StudyWordMeaningItem();

        studyWordMeaningItem.setKey("[名]");
        studyWordMeaningItem.setString(allWordItem.getN());
        if(!"".equals(studyWordMeaningItem.getString())){
            studyWordMeaningItemLists.add(studyWordMeaningItem);
            studyWordMeaningItem = new StudyWordMeaningItem();
        }

        studyWordMeaningItem.setKey("[动]");
        studyWordMeaningItem.setString(allWordItem.getV());
        if(!"".equals(studyWordMeaningItem.getString())){
            studyWordMeaningItemLists.add(studyWordMeaningItem);
            System.out.println(allWordItem.getV());
            studyWordMeaningItem = new StudyWordMeaningItem();
        }

        studyWordMeaningItem.setKey("[形]");
        studyWordMeaningItem.setString(allWordItem.getAdj());
        if(!"".equals(studyWordMeaningItem.getString())){
            studyWordMeaningItemLists.add(studyWordMeaningItem);
            studyWordMeaningItem = new StudyWordMeaningItem();
        }

        studyWordMeaningItem.setKey("[副]");
        studyWordMeaningItem.setString(allWordItem.getAdv());
        if(!"".equals(studyWordMeaningItem.getString())){
            studyWordMeaningItemLists.add(studyWordMeaningItem);
            studyWordMeaningItem = new StudyWordMeaningItem();
        }

        studyWordMeaningItem.setKey("[其他]");
        studyWordMeaningItem.setString(allWordItem.getOther());
        if(!"".equals(studyWordMeaningItem.getString())){
            studyWordMeaningItemLists.add(studyWordMeaningItem);
        }

        studyMeanListAdapter = new StudyMeanListAdapter(context, studyWordMeaningItemLists);
        lvMean.setAdapter(studyMeanListAdapter);
    }

    void showToast(String sss) {
        Toast mToast =Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(sss);
        mToast.show();
    }

    void openRs() {

        Lists = new ArrayList<>();
        //Lists = new ArrayList<WordDictationListItem>();
        //text.setText("开始解析结果集");
        try { //链接数据库 if(name.equals("admin")){

            while (rsList.next()) {

                allWordItem = new StudyAllWordItem();
                String s;

                allWordItem.setAdj(rsList.getString("adj"));
                allWordItem.setAdv(rsList.getString("adv"));
                allWordItem.setEnglish(rsList.getString("english"));
                allWordItem.setFayin(rsList.getString("fayin"));
                allWordItem.setN(rsList.getString("n"));
                allWordItem.setOther(rsList.getString("other"));
                allWordItem.setV(rsList.getString("v"));
                allWordItem.setYinbiao(rsList.getString("yinbiao"));
                Lists.add(allWordItem);
            }
        } catch (Exception e) {
            System.out.println("结果集解析问题问题" + e);
        }
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

    void getUnitWordList() {
        new Thread() {
            public void run() {
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    rsList = ELApplication.getSql().sel("SELECT word.*,adminword.flog FROM word,adminword where adminword.word=word.english  AND adminword.flog='ok' ORDER BY RAND()"); //查询
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


    void butFunKnow(){
        showEnglish();
    }
    void butFunVague(){
        System.out.println("butFunVague");
    }
    void butFunForget(){
        System.out.println("butFunForget");
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
        showToast("butFunGoogle");
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
