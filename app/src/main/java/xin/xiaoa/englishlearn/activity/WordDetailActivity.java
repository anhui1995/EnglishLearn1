package xin.xiaoa.englishlearn.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.otherwordlist.OtherWordListAdapter;
import xin.xiaoa.englishlearn.otherwordlist.OtherWordListItem;
import xin.xiaoa.englishlearn.service.ELApplication;
import xin.xiaoa.englishlearn.service.PreferencesUtils;
import xin.xiaoa.englishlearn.worddetail.WordDetailAdapter;

public class WordDetailActivity extends Activity {

    private TextView english;
    private TextView fayin;
    private ListView wordMeanListView;
    private ListView otherWordListView;
    String strEnglish, strFayin, strYinbiao, strMainWord;

    String n = "", v = "", adj = "", adv = "", other = "";

    int wordId;
    ResultSet sqlResultSet;

    String mUserName;//用户名
    String mPassword;//密码
    // QQApplication lication= (QQApplication)this.getApplication();
    private List<String> Lists = new ArrayList<String>();
    private List<OtherWordListItem> ListsO = new ArrayList<OtherWordListItem>();

    /**
     * 集合
     */
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 2:
                    openRsOther();
                    break;

                default:
                    break;
            }
        }

        ;
    };

    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("class_WordDetail");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.worddetail);
        //接收数据
        Bundle bundle = this.getIntent().getExtras();
        wordId = bundle.getInt("id");
        strEnglish = bundle.getString("english");
        strFayin = bundle.getString("fayin");
        strYinbiao = bundle.getString("yinbiao");

        n = bundle.getString("n");
        v = bundle.getString("v");
        adj = bundle.getString("adj");
        adv = bundle.getString("adv");
        other = bundle.getString("other");


        initView();
        mUserName = PreferencesUtils.getSharePreStr(this, "username");
        mPassword = PreferencesUtils.getSharePreStr(this, "pwd");


    }


    void initView() {
        //mSet = (Button)this.findViewById(R.id.listButtonSet);

        english = (TextView) this.findViewById(R.id.wordTextViewEnglish);
        fayin = (TextView) this.findViewById(R.id.wordTextViewFayin);
        wordMeanListView = (ListView) this.findViewById(R.id.wordListViewMean);
        otherWordListView = (ListView) this.findViewById(R.id.otherWordListView);
        otherWordListView.setOnItemClickListener(new itemClickListener());
        english.setText(strEnglish);
        fayin.setText("[" + strYinbiao + "]");
        suchOther(strEnglish);
        Lists = new ArrayList<String>();
        if (!n.equals("")) Lists.add(n);
        if (!v.equals("")) Lists.add(v);
        if (!adj.equals("")) Lists.add(adj);
        if (!adv.equals("")) Lists.add(adv);
        if (!other.equals("")) Lists.add(other);
        WordDetailAdapter listAdapt = new WordDetailAdapter(this, Lists);
        wordMeanListView.setAdapter(listAdapt);
        //meaning.setText(strMeaning);
        //mSet.setOnClickListener(new MyClickListener());

    }


    void suchOther(final String suchWord) {
        new Thread() {
            public void run() {          //开始获取近义词
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    sqlResultSet = ELApplication.getSql().sel("SELECT * FROM word where english IN (SELECT word FROM adminword where mainword=(select mainword from adminword where word ='" + suchWord + "'))"); //查询
                    //已经获取结果集
                } catch (Exception e) {
                    System.out.println("结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(2);
            }
        }.start();
    }


    void openRsOther() {
        try { //链接数据库 if(name.equals("admin")){
            ListsO = new ArrayList<OtherWordListItem>();
            while (sqlResultSet.next()) {

                OtherWordListItem peo = new OtherWordListItem();

                String s = "";

                if (!sqlResultSet.getString("n").equals("")) {
                    int len = sqlResultSet.getString("n").length();
                    s = "n:" + sqlResultSet.getString("n").substring(1, len);
                    peo.setN(s);
                }

                if (!sqlResultSet.getString("v").equals("")) {
                    int len = sqlResultSet.getString("v").length();
                    s = "v:" + sqlResultSet.getString("v").substring(1, len);
                    peo.setV(s);
                }

                if (!sqlResultSet.getString("adj").equals("")) {
                    int len = sqlResultSet.getString("adj").length();
                    s = "adj:" + sqlResultSet.getString("adj").substring(1, len);
                    peo.setAdj(s);
                }

                if (!sqlResultSet.getString("adv").equals("")) {
                    int len = sqlResultSet.getString("adv").length();
                    s = "adv:" + sqlResultSet.getString("adv").substring(1, len);
                    peo.setAdv(s);
                }

                if (!sqlResultSet.getString("other").equals("")) {
                    int len = sqlResultSet.getString("other").length();
                    s = "other:" + sqlResultSet.getString("other").substring(1, len);
                    peo.setOther(s);

                }
                peo.setEnglish(sqlResultSet.getString("english"));

                //	otherWordList tmp=new otherWordList();

                ListsO.add(peo);

            }
        } catch (Exception e) {
            System.out.println("结果集解析问题问题" + e);
        }


        OtherWordListAdapter listAdapt = new OtherWordListAdapter(this, ListsO);
        otherWordListView.setAdapter(listAdapt);

    }


    class itemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> adapter, View view, int i, long id) {
            //点到的单词
            OtherWordListItem tmp = ListsO.get(i);
            System.out.println(tmp.getMeaning());

        }
    }


}
