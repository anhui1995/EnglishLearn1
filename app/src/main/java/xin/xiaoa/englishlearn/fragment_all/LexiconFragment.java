package xin.xiaoa.englishlearn.fragment_all;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.fragment_lexicon.ChildsItem;
import xin.xiaoa.englishlearn.fragment_lexicon.GroupsItem;
import xin.xiaoa.englishlearn.fragment_lexicon.MyExpandableListViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class LexiconFragment extends Fragment {

    ExpandableListView expandableListView;
    View view;
    MyExpandableListViewAdapter myExpandableListViewAdapter;
    Context context;
    List<GroupsItem> groupsLists;
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
        groupsLists = init();
        myExpandableListViewAdapter = new MyExpandableListViewAdapter(context,groupsLists);
        expandableListView.setAdapter(myExpandableListViewAdapter);
        return view;
    }

    class MyOnChildClickListener implements ExpandableListView.OnChildClickListener {

        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            showToast(groupsLists.get(groupPosition).getChilds().get(childPosition).getContent());
            return false;
        }
    }

    List<GroupsItem> init(){
        List<GroupsItem> groupsLists= new ArrayList<>();
        List<ChildsItem> childsLists;
        ChildsItem childsItem;
        GroupsItem groupsItem;

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
