package xin.xiaoa.englishlearn.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import android.os.Handler;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.article2words.A2WListAdapter;
import xin.xiaoa.englishlearn.article2words.A2WViewPagerAdapter;
import xin.xiaoa.englishlearn.article2words.A2wListviewItem;
import xin.xiaoa.englishlearn.article2words.Article2Words;
import xin.xiaoa.englishlearn.article2words.EditArticleFragment;
import xin.xiaoa.englishlearn.article2words.WordsViewFragment;
import xin.xiaoa.englishlearn.service.ELApplication;

import static android.view.View.FOCUSABLE_AUTO;
import static android.view.View.INVISIBLE;

public class A2WActivity extends AppCompatActivity {

    boolean isSave = false;
    Button butSave;
    Button butView;
    Button butBack;
    TextView a2wFragTitle;
    Article2Words article2Words;
    ListView wordsViewFragmentListView;
    A2WListAdapter a2WListAdapter;
    private List<A2wListviewItem> a2wListviewList = null;
    Context context;
    AppCompatActivity thisActivity;
    ViewPager viewPager;
    String article;
    String title;
    String strCmd = "";
    String strKey = "";
    String strTitle = "";
    Handler lexiconFragmentHandle;
    private List<Fragment> viewPagerList; //ViewPager的数据源
    EditArticleFragment editArticleFragment;
    WordsViewFragment wordsViewFragment;
    ResultSet rsListWords, rsListArticle;

    int pageFlog=0;


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    openArticleRsList();
                    break;
                case 2:
                    openWordsRsList();
                    break;
                 case 3:
                     lexiconFragmentHandle.sendEmptyMessage(3);
                     thisActivity.finish();
                    break;
                default:
                    break;
            }
        }

    };


//     String content  = "kolya is one of the richest films i've seen in some time." +
//            "zdenek sverak plays a confirmed old bachelor ( who's likely to" +
//            " remain so ) , who finds his life as a czech cellist increasingly " +
//            "impacted by the five-year old boy that he's taking care of . though " +
//            "it ends rather abruptly-- and i'm whining , 'cause i wanted to spend " +
//            "more time with these characters-- the acting , writing , and production " +
//            "values are as high as , if not higher than , comparable american dramas. " +
//            "this father-and-son delight-- sverak also wrote the script , while his son , " +
//            "jan , directed-- won a golden globe for best foreign language film and , " +
//            "a couple days after i saw it , walked away an oscar . in czech and russian , " +
//            "with english subtitles . ";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        context = this;
        thisActivity = this;
        Bundle bundle = this.getIntent().getExtras();
        assert bundle != null;
        strCmd = bundle.getString("cmd");
        strKey = bundle.getString("key");
        strTitle = bundle.getString("title");
        lexiconFragmentHandle = ELApplication.getLexiconFragmentHandle();
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_a2w);
//        a2wFragTitle.setFocusable(FOCUSABLE_AUTO);
        System.out.println("A2WActivity:"+strCmd+"--"+strKey);
        if("other".equals(strCmd)) {
            viewInit(false);
            a2wFragTitle.setFocusable(false);
            viewPagerInit(false);
            getWords();
        }
        else {
            viewInit(true);
            viewPagerInit(true);
            a2wFragTitle.clearFocus();
        }

        //a2wFragTitle.setFocusable(FOCUSABLE_AUTO);
        if("first".equals(strCmd)){
            getArticle();
        }

    }


    void getArticle(){
        new Thread() {
            public void run() {
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    //已经获取结果unit
                    rsListArticle = ELApplication.getSql().sel("SELECT  * FROM a2w_article where id='"+strKey+"' "); //查询

                } catch (Exception e) {
                    System.out.println("unit结果集获取失败问题" + e);
                }
                 handler.sendEmptyMessage(1);
            }
        }.start();
    }

    void openArticleRsList(){
        try {
            while (rsListArticle.next()) {
                article = rsListArticle.getString("article");
                title = rsListArticle.getString("title");
                editArticleFragment.setArticle(article);
                a2wFragTitle.setText(title);
            }
        } catch (Exception e) {
            System.out.println("结果集解析问题问题" + e);
        }
    }

    void getWords(){
        String str = "SELECT word.*,adminword.flog FROM word,adminword where adminword.word=word.english  AND adminword.flog='ok' ORDER BY RAND()";
        new Thread() {
            public void run() {
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    //已经获取结果unit
                    rsListWords = ELApplication.getSql().sel("SELECT  * FROM lexicon where source='"+strKey+"' "); //查询

                } catch (Exception e) {
                    System.out.println("unit结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(2);
            }
        }.start();
    }

    void openWordsRsList(){
        a2wListviewList = new ArrayList<>();
        try {
            while (rsListWords.next()) {
                String key = rsListWords.getString("word");
                a2wListviewList.add(new A2wListviewItem(key,1));
                wordsViewFragmentListView = wordsViewFragment.getListView();
                wordsViewFragment.setA2wListviewList(a2wListviewList);
                a2WListAdapter = new A2WListAdapter(this,a2wListviewList);
                wordsViewFragmentListView.setAdapter(a2WListAdapter);
            }
        } catch (Exception e) {
            System.out.println("结果集解析问题问题" + e);
        }

    }



    void viewPagerInit(boolean isPrivate){

        viewPagerList = new ArrayList<>();
        wordsViewFragment = new WordsViewFragment(context);
        editArticleFragment = new EditArticleFragment(context);
        if(isPrivate) viewPagerList.add(editArticleFragment);
        viewPagerList.add(wordsViewFragment);

        viewPager = findViewById(R.id.a2w_viewPager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.addOnPageChangeListener(new VPChangeListener());
        A2WViewPagerAdapter mainAdapter = new A2WViewPagerAdapter(thisActivity.getSupportFragmentManager(), viewPagerList);
        viewPager.setAdapter(mainAdapter); //视图加载适配器
    }


    void viewInit(boolean click){

        a2wFragTitle = findViewById(R.id.a2w_f_title);

        butSave = findViewById(R.id.a2w_but_save);
        butBack = findViewById(R.id.a2w_but_back);
        butView = findViewById(R.id.a2w_but_view);

        if(click){
            butSave.setOnClickListener(new MyClickListener());
            butSave.setOnLongClickListener(new MyLongClickListener());
            butBack.setOnClickListener(new MyClickListener());
            butView.setOnClickListener(new MyClickListener());
        }
        else {
            a2wFragTitle.setText(strKey);
            butSave.setVisibility(INVISIBLE);
            butBack.setVisibility(INVISIBLE);
            butView.setVisibility(INVISIBLE);;
        }

    }

    void buttonGet(){
        wordsViewFragmentListView = wordsViewFragment.getListView();
        article = editArticleFragment.getArtilce();
        article2Words = new Article2Words(article);
        a2wListviewList = article2Words.getLists();
        wordsViewFragment.setA2wListviewList(a2wListviewList);
        a2WListAdapter = new A2WListAdapter(this,a2wListviewList);
        wordsViewFragmentListView.setAdapter(a2WListAdapter);
    }

    void buttonAdd(){
        System.out.println("插入文章");
        if(a2wListviewList == null) return;

        insertArticle(article,title,a2wListviewList);
    }

    void buttonBack(){
        System.out.println("buttonBack()");
        this.finish();
    }
    void buttonView(){
        System.out.println("buttonView()");
        if(pageFlog==0){
            butView.setText("预览");
            pageFlog = 1;
            buttonGet();
            a2wFragTitle.clearFocus();
            viewPager.setCurrentItem(1);
        }
        else {
            butView.setText("编辑");
            pageFlog = 0;
            viewPager.setCurrentItem(0);
        }
    }
    void buttonSave(){
        System.out.println("buttonSave()");
        if(isSave) return;
        buttonGet();
        title = a2wFragTitle.getText().toString();
        insertArticle(article,title,a2wListviewList);
        isSave = true;
    }
    void insertArticle(final String article, final String title, final List<A2wListviewItem> a2wListviewList){

        new Thread(){
            public void run(){
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");

                    int res = ELApplication.getSql().up("INSERT INTO  a2w_article (article,title) VALUES ('"+article.replace("'","''")+"','"+title.replace("'","''")+"' )");

                    String cmdStr = "";
                    StringBuilder sb = new StringBuilder("INSERT INTO  a2w_words (article_id,word) VALUES ");

                    for(int i=0;i<a2wListviewList.size()-1;i++){
                        sb.append(" ('").append(res).append("','").append(a2wListviewList.get(i).getName().replace("'", "''")).append("' ), ");
                    }
                    sb.append(" ('").append(res).append("','").append(a2wListviewList.get(a2wListviewList.size() - 1).getName().replace("'", "''")).append("' )");

                    ELApplication.getSql().up(sb.toString());
                    System.out.println("文章更新成功");
                    lexiconFragmentHandle.sendEmptyMessage(3);

                } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }

            }
        }.start();
    }

    void deletePrivate(){
        new Thread() {
            public void run() {
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    //已经获取结果unit
                    ELApplication.getSql().delete("DELETE FROM a2w_article where id='"+strKey+"' "); //查询
                    ELApplication.getSql().delete("DELETE FROM a2w_words where article_id='"+strKey+"' "); //查询
                } catch (Exception e) {
                    System.out.println("unit结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(2);
            }
        }.start();
    }


    void showDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("确定删除词库："+strTitle);
//        TextView textView = new TextView(context);
//        textView.setText(content);
        dialog.setNegativeButton("确定删除", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                deletePrivate();
                handler.sendEmptyMessage(3);
            }
        });
        dialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which)
            {
                // TODO Auto-generated method stub

            }
        });

        dialog.show();

    }

    class MyLongClickListener implements View.OnLongClickListener{

        @Override
        public boolean onLongClick(View v) {
            if("first".equals(strCmd)) showDialog();
            return false;
        }
    }
    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            switch (arg0.getId()) {

                case R.id.a2w_but_save:
                    buttonSave();
                    break;
                case R.id.a2w_but_back:
                    buttonBack();
                    break;
                    case R.id.a2w_but_view:
                    buttonView();
                    break;
            }
        }
    }

    class VPChangeListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            // bottomNavigationBar.selectTab(position);
            //System.out.println("ScrolledselectTab"+position);
        }

        @Override
        public void onPageSelected(int position) {
            //ViewPager滑动

            pageFlog = position;
            a2wFragTitle.clearFocus();
            if(position==0) butView.setText("预览");
            else {
                butView.setText("编辑");
                buttonGet();
            }
            System.out.println("selectTab"+position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //System.out.println("onPageScrollStateChanged");
        }
    }
    //隐藏键盘之类相关方法(下面)
    private boolean isSoftShowing() {
        //获取当前屏幕内容的高度
        int screenHeight = getWindow().getDecorView().getHeight();
        //获取View可见区域的bottom
        Rect rect = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);

        return screenHeight - rect.bottom != 0;
    }
    //是否需要隐藏键盘
    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isSoftShowing() && isShouldHideInput(v, ev)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return super.dispatchTouchEvent(ev);
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    //隐藏键盘之类相关方法(上面)

}



