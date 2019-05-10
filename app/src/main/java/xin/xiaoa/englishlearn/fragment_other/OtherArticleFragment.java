package xin.xiaoa.englishlearn.fragment_other;
import android.content.Intent;
import android.os.Handler;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.activity.ArticleActivity;
import xin.xiaoa.englishlearn.service.ELApplication;

public class OtherArticleFragment extends Fragment {
    View view;
    List<OtherSpinnerItem> fromList = new ArrayList<>();
    Spinner spinner;
    ListView listView;
    Context context;
    ResultSet sqlResultSet;
    ArtListViewAdapter artListViewAdapter;
    private List<ArtListViewItem> lists;
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
//        textView1.setText(Html.fromHtml(text1), TextView.BufferType.SPANNABLE);
    @SuppressLint("ValidFragment")
    public OtherArticleFragment(Context cont) {
        context = cont;
    }

    public OtherArticleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.other_article_fragment, container, false);
        spinnerInit();
        listViewInit();
        getArticleList("科学美国人");
        return view;
    }

    void listViewInit(){
        listView = view.findViewById(R.id.other_art_listview);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    void getArticleList(final String source){
        new Thread(){
            public void run(){
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    sqlResultSet = ELApplication.getSql().sel("SELECT id,title,subtitle,tip FROM article WHERE source='"+source+"'"); //查询
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
                //viewN.setText(sqlResultSet.getString("n"));
                lists.add(new ArtListViewItem(sqlResultSet.getString("title"),sqlResultSet.getString("id")));
            }
            artListViewAdapter = new ArtListViewAdapter(context, lists);
            listView.setAdapter(artListViewAdapter);
        } catch(Exception e) { System.out.println("结果集解析问题问题"+e); }
    }
    void spinnerInit(){
        spinner = view.findViewById(R.id.other_art_spinner);

        spinner.setOnItemSelectedListener(new MySpinnerSelectListener());

        fromList.add(new OtherSpinnerItem("BBC","en"));
        fromList.add(new OtherSpinnerItem("全球财经","zh"));
        fromList.add(new OtherSpinnerItem("TED","yue"));
        fromList.add(new OtherSpinnerItem("每日邮报","wyw"));
        fromList.add(new OtherSpinnerItem("时代周刊","jb"));
        fromList.add(new OtherSpinnerItem("南方周末","kor"));
        fromList.add(new OtherSpinnerItem("北方周波","fra"));
        fromList.add(new OtherSpinnerItem("财富","spa"));
        fromList.add(new OtherSpinnerItem("人民日报","th"));
        fromList.add(new OtherSpinnerItem("中国之声","ara"));

        OtherSpinnerAdapter otherSpinnerAdapterAdapter = new OtherSpinnerAdapter(context, fromList);
        spinner.setAdapter(otherSpinnerAdapterAdapter);

    }

    class MySpinnerSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
            OtherSpinnerItem item = fromList.get(pos);


        }
        @Override
        public void onNothingSelected(AdapterView<?> p1) {}
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            ArtListViewItem item = lists.get(position);

            Intent intent = new Intent();
            intent.setClass(context, ArticleActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", item.getId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }
}
