package xin.xiaoa.englishlearn.fragment_other;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
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
    List<OtherSpinnerItem> fromList;
    Spinner spinner;
    ListView listView;
    Context context;
    ResultSet sqlResultSet;
    String spinnerName = "";
    ResultSet spinnerRS;
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
                case 2:
                    openRsSpinner();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.other_article_fragment, container, false);
        spinnerInit();
        listViewInit();
        getFromList();
        return view;
    }

    void listViewInit(){
        listView = view.findViewById(R.id.other_art_listview);
        listView.setOnItemClickListener(new MyOnItemClickListener());
    }

    void getArticleList(){
        new Thread(){
            public void run(){
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    sqlResultSet = ELApplication.getSql().sel("SELECT id,title,subtitle,tip FROM article WHERE source='"+spinnerName+"'"); //查询
                    System.out.println("已经获取结果集 getArticleList()");
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
                //viewN.setText(sqlResultSet.getString("n"));subtitle
                lists.add(new ArtListViewItem(sqlResultSet.getString("title"),sqlResultSet.getString("subtitle"),sqlResultSet.getString("id")));
            }
            artListViewAdapter = new ArtListViewAdapter(context, lists);
            listView.setAdapter(artListViewAdapter);
        } catch(Exception e) { System.out.println("结果集解析问题OtherArticleFragment"+e); }
    }
    void getFromList(){
        new Thread(){
            public void run(){
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    spinnerRS = ELApplication.getSql().sel("SELECT DISTINCT source FROM article "); //查询
                    System.out.println("已经获取结果集getFromList()");
                } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
                handler.sendEmptyMessage(2);
            }
        }.start();
    }
    @SuppressLint("SetTextI18n")
    void openRsSpinner(){
        //开始解析结果集
        try { //链接数据库 if(name.equals("admin")){
            fromList = new ArrayList<>();
            while(spinnerRS.next()) {
                fromList.add(new OtherSpinnerItem(spinnerRS.getString("source"),""));
            }
            OtherSpinnerAdapter otherSpinnerAdapterAdapter = new OtherSpinnerAdapter(context, fromList);
            spinner.setAdapter(otherSpinnerAdapterAdapter);
            // android:background="@drawable/linearlayout_line"

            //spinner.setPopupBackgroundDrawable(R.drawable.linearlayout_line);
           spinnerName = ((OtherSpinnerItem)spinner.getSelectedItem()).getName();
            getArticleList();
        } catch(Exception e) { System.out.println("结果集解析问题问题openRsSpinner"+e); }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void spinnerInit(){
        spinner = view.findViewById(R.id.other_art_spinner);
        spinner.setPopupBackgroundResource(R.drawable.other_art_spinner_bg);
        spinner.setOnItemSelectedListener(new MySpinnerSelectListener());
    }

    class MySpinnerSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
            OtherSpinnerItem item = fromList.get(pos);
            spinnerName = item.getName();
            getArticleList();
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
