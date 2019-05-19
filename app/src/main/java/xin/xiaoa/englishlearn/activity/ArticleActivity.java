package xin.xiaoa.englishlearn.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ListView;

import java.io.File;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.article.ArticleListViewAdapter;
import xin.xiaoa.englishlearn.article.ArticleListViewItem;
import xin.xiaoa.englishlearn.service.ELApplication;
import xin.xiaoa.englishlearn.service.MySqlServer;


public class ArticleActivity extends AppCompatActivity {

    Context context;
    private List<ArticleListViewItem> lists;
    ResultSet sqlResultSet;
    ArticleListViewAdapter articleListViewAdapter;
    ListView listView;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    openRs();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("class_MainActivity");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_article);
        Bundle bundle = this.getIntent().getExtras();
        try{
            getArticle(bundle != null ? bundle.getString("id") : null);
        }
        catch (Exception ignored){}
        listView = findViewById(R.id.article_listview);
        context = this;


    }

    void getArticle(final String id){
        new Thread(){
            public void run(){
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    sqlResultSet = ELApplication.getSql().sel("SELECT * FROM article WHERE id='"+id+"'"); //查询
                    System.out.println("已经获取结果集");

                } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }
    @SuppressLint("SetTextI18n")
    void openRs(){
        //开始解析结果集
        try { //链接数据库 if(name.equals("admin")){
            lists = new ArrayList<>();
            while(sqlResultSet.next()) {
                lists.add(new ArticleListViewItem("title",sqlResultSet.getString("title")));
                lists.add(new ArticleListViewItem("subtitle",sqlResultSet.getString("subtitle")));
                lists.add(new ArticleListViewItem("other",sqlResultSet.getString("tip")));
                lists.add(new ArticleListViewItem("english",sqlResultSet.getString("english")));
                lists.add(new ArticleListViewItem("other",sqlResultSet.getString("chinese")));
            }
            articleListViewAdapter = new ArticleListViewAdapter(context, lists);
            listView.setAdapter(articleListViewAdapter);
        } catch(Exception e) { System.out.println("结果集解析问题问题"+e); }
    }
}
