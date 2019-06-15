package xin.xiaoa.englishlearn.fragment_all;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.activity.LoginActivity;
import xin.xiaoa.englishlearn.fragment_my.MyListViewAdapter;
import xin.xiaoa.englishlearn.fragment_my.MyListViewItem;
import xin.xiaoa.englishlearn.service.ELApplication;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {



    Context context;
    View view;
    List<MyListViewItem> infoList;
    ListView listView;
    TextView tvUsername;
    TextView tvEmail;
    TextView tvTip;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what){
                case 1: initInfoList();break;
                case 2: f52Review();break;
                case 3: f52Unkonw();break;
                default : break;
            }

        }
    };

    @SuppressLint("ValidFragment")
    public MyFragment(Context context) {
        this.context = context;
    }

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my, container, false);
        tvUsername = view.findViewById(R.id.my_tv_username);
        tvEmail = view.findViewById(R.id.my_tv_email);
        tvTip = view.findViewById(R.id.my_tv_tip);
        tvUsername.setText(ELApplication.getUserMassge().getUsername());
        tvEmail.setText(ELApplication.getUserMassge().getEmail());
        tvTip.setText(ELApplication.getUserMassge().getTip());
        ELApplication.setMyFragmentHandler(handler);
        listView = view.findViewById(R.id.my_listview);
        listView.setOnItemClickListener(new itemClickListener());
        initInfoList();
        return view;
    }

    void initInfoList(){

        infoList = new ArrayList<>();

        //打开软件时刷新，学完单词时刷新
        infoList.add(new MyListViewItem("已学单词",""+ELApplication.getUserMassge().getLearned()));  //review单词数
        infoList.add(new MyListViewItem("生 词 数",""+ELApplication.getUserMassge().getUnknow()));  //生词本中单词数
        infoList.add(new MyListViewItem("学习天数",""+ELApplication.getUserMassge().getAll())); //学习天数
        infoList.add(new MyListViewItem("剩余单词",""+ELApplication.getUserMassge().getRemainder())); //review单词数  -   记忆深度小于8的
        infoList.add(new MyListViewItem("熟知单词",""+ELApplication.getUserMassge().getKnow()));    //记忆深度大于7
        infoList.add(new MyListViewItem("模糊单词",""+ELApplication.getUserMassge().getFuzzy()));  //记忆深度小于8  且大于2的
        infoList.add(new MyListViewItem("顽固单词",""+ELApplication.getUserMassge().getStubborn()));  //记忆深度小于3的
        infoList.add(new MyListViewItem("quit","quit"));   //退出


        MyListViewAdapter myListViewAdapter = new MyListViewAdapter(context, infoList);
        listView.setAdapter(myListViewAdapter);
    }

    void quit(){
        ELApplication.setLogin("err");
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        startActivity(intent);
    }

    void f52Review(){
        System.out.println("刷新个人信息");
        new Thread() {
            public void run() {
                try {
                    SQLiteDatabase db = ELApplication.getDb();
                    Cursor cursorWordList = db.rawQuery("select memoryDatabase from review ", new String[]{});
                    int intKonw = 0;
                    int intFuzzy = 0;
                    int intStubborn = 0;

                    while (cursorWordList.moveToNext()) {
                        int tmp = cursorWordList.getInt(cursorWordList.getColumnIndex("memoryDatabase"));

                        if(tmp>7)  intKonw++;
                        else if(tmp>2) intFuzzy++;
                        else intStubborn++;
                    }
                    cursorWordList.close();

                    ELApplication.getUserMassge().setRemainder(intFuzzy + intStubborn);
                    ELApplication.getUserMassge().setLearned(intKonw + intFuzzy + intStubborn);
                    ELApplication.getUserMassge().setKnow(intKonw);
                    ELApplication.getUserMassge().setFuzzy(intFuzzy);
                    ELApplication.getUserMassge().setStubborn(intStubborn);

                    new Thread(){
                        public void run(){
                            //reload();
                            try { //链接数据库 if(name.equals("admin")){
                                if (!ELApplication.getSql().sqlStation())
                                    System.out.println("数据库连接连接已经断开。");
                                ELApplication.getSql().up("UPDATE user SET " +
                                        "_all = '"+ELApplication.getUserMassge().getAll()+"', "+
                                        "remainder = '"+ELApplication.getUserMassge().getRemainder()+"', "+
                                        "learned = '"+ELApplication.getUserMassge().getLearned()+"', "+
                                        "know = '"+ELApplication.getUserMassge().getKnow()+"', "+
                                        "fuzzy = '"+ELApplication.getUserMassge().getFuzzy()+"', "+
                                        "stubborn = '"+ELApplication.getUserMassge().getStubborn()+"' "+
                                        " WHERE id = '"+ELApplication.getUsernameId()+"'"); //查询

                            } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
                        }
                    }.start();


                } catch (Exception e) {
                    System.out.println("结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(1);
            }
        }.start();

    }


    void f52Unkonw(){


        new Thread() {
            public void run() {
                try {
                    SQLiteDatabase db = ELApplication.getDb();
                    Cursor cursorWordList = db.rawQuery("select english from unknown_words ", new String[]{});
                    int intUnkown=0;

                    while (cursorWordList.moveToNext()) {
                        intUnkown++;
                    }
                    cursorWordList.close();
                    ELApplication.getUserMassge().setUnknow(intUnkown);

                    new Thread(){
                        public void run(){
                            //reload();
                            try { //链接数据库 if(name.equals("admin")){
                                if (!ELApplication.getSql().sqlStation())
                                    System.out.println("数据库连接连接已经断开。");
                                ELApplication.getSql().up("UPDATE user SET unknow = '"+ELApplication.getUserMassge().getUnknow()+"' WHERE id = '"+ELApplication.getUsernameId()+"'"); //查询

                            } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
                        }
                    }.start();

                } catch (Exception e) {
                    System.out.println("结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(1);
            }
        }.start();

    }


    class itemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> adapter, View view, int i, long id) {
            MyListViewItem item = infoList.get(i);
            if("quit".equals(item.getKey())) quit();
        }
    }
}
