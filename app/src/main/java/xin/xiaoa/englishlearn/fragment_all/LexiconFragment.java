package xin.xiaoa.englishlearn.fragment_all;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.activity.A2WActivity;
import xin.xiaoa.englishlearn.activity.SearchActivity;
import xin.xiaoa.englishlearn.click_word.SearchWord;
import xin.xiaoa.englishlearn.fragment_lexicon.ChildsItem;
import xin.xiaoa.englishlearn.fragment_lexicon.GroupsItem;
import xin.xiaoa.englishlearn.fragment_lexicon.MyExpandableListViewAdapter;
import xin.xiaoa.englishlearn.service.ELApplication;

/**
 * A simple {@link Fragment} subclass.
 */
public class LexiconFragment extends Fragment {

    ExpandableListView expandableListView;
    View view;
    MyExpandableListViewAdapter myExpandableListViewAdapter;
    Context context;
    Button butAllWord;
    Button butNewWord;
//    Button butSpecialWord;
    List<GroupsItem> groupsLists;
    ResultSet publicRsList, privateRsList;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    openPublicRsList();
                    break;
                case 2:
                    openPrivateRsList();
                    break;
                case 3:
                    getPrivateList();
                    break;
                default:
                    break;
            }
        }

    };

    public Handler getHandler(){
        return handler;
    }
    @SuppressLint("ValidFragment")
    public LexiconFragment(Context cont) {
        context = cont;
    }
    public LexiconFragment() {
        // Required empty public constructor
    }
    void showToast(String sss) {
        Toast mToast =Toast.makeText(context, null, Toast.LENGTH_SHORT);
        mToast.setText(sss);
        mToast.show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_lexicon, container, false);
        expandableListView = view.findViewById(R.id.lexicon_ExpandableListView);
        expandableListView.setOnChildClickListener(new MyOnChildClickListener());
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = expandableListView
                        .getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {// 关闭其他分组
                        expandableListView.collapseGroup(i);
                    }
                }
            }
        });
        getPrivateList();
//        groupsLists = init();
//        myExpandableListViewAdapter = new MyExpandableListViewAdapter(context,groupsLists,new MyClickListener());
//        expandableListView.setAdapter(myExpandableListViewAdapter);

        butAllWord = view.findViewById(R.id.lexicon_all_word);
        butNewWord = view.findViewById(R.id.lexicon_new_word);
      ///  butSpecialWord = view.findViewById(R.id.lexicon_special_word);

        butAllWord.setOnClickListener(new MyClickListener());
        butNewWord.setOnClickListener(new MyClickListener());
      ///  butSpecialWord.setOnClickListener(new MyClickListener());

        return view;
    }

    class MyOnChildClickListener implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            //showToast(groupsLists.get(groupPosition).getChilds().get(childPosition).getContent());
            if(groupPosition==0) editMyLexicon("first",groupsLists.get(groupPosition).getChilds().get(childPosition).getContent() ,
                    groupsLists.get(groupPosition).getChilds().get(childPosition).getName() );
            else editMyLexicon("other",groupsLists.get(groupPosition).getChilds().get(childPosition).getContent() ,
                    groupsLists.get(groupPosition).getChilds().get(childPosition).getName() );
            return false;
        }
    }
   public void editMyLexicon(String strCmd, String key, String title){
        ELApplication.setLexiconFragmentHandle(handler);
        Intent intent = new Intent();
        intent.setClass(context, A2WActivity.class);

        Bundle bundle = new Bundle();
        bundle.putString("cmd", strCmd);
        bundle.putString("key", key);
        bundle.putString("title", title);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    void funAllWord(){
//        Intent intent = new Intent();
//        intent.setClass(context, SearchActivity.class);
//        context.startActivity(intent);
        new SearchWord(context);
    }
    void funA2W(){
        editMyLexicon("add","","我的词库");
    }
    void funNewWord(){ //生词本点击事件
        editMyLexicon("new_word","个人生词本" ,"个人生词本" );
    }

    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            switch (arg0.getId()) {
                case R.id.lexicon_all_word:
                    funAllWord();break;
                case R.id.lexicon_new_word:
                    funNewWord();break;
//                case R.id.lexicon_special_word:
//                    funSpecialWord();break;
                case R.id.expandable_listview_item_group_button:
                    funA2W();break;
            }
        }
    }

    void getPrivateList(){
        new Thread() {
            public void run() {
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    //已经获取结果unit
                    privateRsList = ELApplication.getSql().sel("SELECT DISTINCT * FROM "+ELApplication.getPrefix()+"a2w_article "); //查询

                } catch (Exception e) {
                    System.out.println("unit结果集获取失败问题" + e);
                }
               // handler.sendEmptyMessage(2);
                openPrivateRsList();
            }
        }.start();
    }
    void openPrivateRsList(){
        groupsLists= new ArrayList<>();
        List<ChildsItem> childsLists = new ArrayList<>();
        try {
            while (privateRsList.next()) {
                String key = privateRsList.getString("id");
                String name = privateRsList.getString("title");
                childsLists.add(new ChildsItem(name,key));
            }
        } catch (Exception e) {
            System.out.println("结果集解析问题问题" + e);
        }
        groupsLists.add(  new GroupsItem(  "我的词库",  childsLists  ,true  )  );
        getPublicList();
    }

    void getPublicList(){
        new Thread() {
            public void run() {
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    //已经获取结果unit
                    publicRsList = ELApplication.getSql().sel("SELECT DISTINCT source FROM lexicon "); //查询

                } catch (Exception e) {
                    System.out.println("unit结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    void openPublicRsList(){
        //List<GroupsItem>

        try {
            while (publicRsList.next()) {
                String tmp = publicRsList.getString("source");
                String[] rawWords = tmp.split("_");
                int i=0;
                for(i=0;i<groupsLists.size();i++){
                    if(groupsLists.get(i).getStrName().equals(rawWords[0])){
                        groupsLists.get(i).addChildsItem(new ChildsItem(rawWords[1],tmp));
                        break;
                    }
                }
                if(i==groupsLists.size()){

                    groupsLists.add(  new GroupsItem(  rawWords[0],new ChildsItem(rawWords[1],tmp)  )  );
                }
            }
        } catch (Exception e) {
            System.out.println("结果集解析问题问题" + e);
        }
       // groupsLists = init();
        myExpandableListViewAdapter = new MyExpandableListViewAdapter(context,groupsLists,new MyClickListener());
        expandableListView.setAdapter(myExpandableListViewAdapter);

    }

    List<GroupsItem> init(){
        List<GroupsItem> groupsLists= new ArrayList<>();
        List<ChildsItem> childsLists;
        ChildsItem childsItem;
        GroupsItem groupsItem;

        groupsItem = new GroupsItem();
        groupsItem.setStrName("个人词库");
        groupsItem.setFirst(true);
        childsLists= new ArrayList<>();

        childsItem = new ChildsItem();
        childsItem.setName("个人词库一");
        childsItem.setContent("一");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("个人词库二");
        childsItem.setContent("二");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("个人词库三");
        childsItem.setContent("三");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("个人词库四");
        childsItem.setContent("四");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("个人词库五");
        childsItem.setContent("五");
        childsLists.add(childsItem);

        groupsItem.setChilds(childsLists);
        groupsLists.add(groupsItem);
        //##########################################################################################
        groupsItem = new GroupsItem();
        groupsItem.setStrName("大学");
        childsLists= new ArrayList<>();

        childsItem = new ChildsItem();
        childsItem.setName("四级词汇");
        childsItem.setContent("四级");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("六级词汇");
        childsItem.setContent("六级");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("英语专四");
        childsItem.setContent("专四");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("英语专八");
        childsItem.setContent("英语专八");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("考研词汇");
        childsItem.setContent("考研");
        childsLists.add(childsItem);

        groupsItem.setChilds(childsLists);
        groupsLists.add(groupsItem);
        //##########################################################################################

        groupsItem = new GroupsItem();
        groupsItem.setStrName("高中");
        childsLists= new ArrayList<>();

        childsItem = new ChildsItem();
        childsItem.setName("人教版一上");
        childsItem.setContent("高一上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版一下");
        childsItem.setContent("高一下");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版二上");
        childsItem.setContent("高二上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版二下");
        childsItem.setContent("高二下");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("高三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三下");
        childsItem.setContent("高三下");
        childsLists.add(childsItem);

        groupsItem.setChilds(childsLists);
        groupsLists.add(groupsItem);
        //##########################################################################################

        groupsItem = new GroupsItem();
        groupsItem.setStrName("初中");
        childsLists= new ArrayList<>();

        childsItem = new ChildsItem();
        childsItem.setName("人教版一上");
        childsItem.setContent("初一上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版一下");
        childsItem.setContent("初一下");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版二上");
        childsItem.setContent("初二上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版二下");
        childsItem.setContent("初二下");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("初三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三下");
        childsItem.setContent("初三下");
        childsLists.add(childsItem);

        groupsItem.setChilds(childsLists);
        groupsLists.add(groupsItem);
        //##########################################################################################

        groupsItem = new GroupsItem();
        groupsItem.setStrName("小学");
        childsLists= new ArrayList<>();

        childsItem = new ChildsItem();
        childsItem.setName("人教版一上");
        childsItem.setContent("小一上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版一下");
        childsItem.setContent("小一下");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版二上");
        childsItem.setContent("小二上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版二下");
        childsItem.setContent("小二下");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三上");
        childsItem.setContent("小三上");
        childsLists.add(childsItem);

        childsItem = new ChildsItem();
        childsItem.setName("人教版三下");
        childsItem.setContent("小三下");
        childsLists.add(childsItem);

        groupsItem.setChilds(childsLists);
        groupsLists.add(groupsItem);
        //##########################################################################################
        return groupsLists;
    }



}
