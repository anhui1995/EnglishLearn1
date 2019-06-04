package xin.xiaoa.englishlearn.fragment_all;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.fragment_my.MyListViewAdapter;
import xin.xiaoa.englishlearn.fragment_my.MyListViewItem;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends Fragment {



    Context context;
    View view;
    List<MyListViewItem> infoList;
    ListView listView;

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

        initInfoList();
        return view;
    }

    void initInfoList(){
        listView = view.findViewById(R.id.my_listview);
        infoList = new ArrayList<>();

        infoList.add(new MyListViewItem("已学单词","3"));
        infoList.add(new MyListViewItem("计划单词","12"));
        infoList.add(new MyListViewItem("学习天数","123"));
        infoList.add(new MyListViewItem("连续天数","1234"));
        infoList.add(new MyListViewItem("剩余单词","12345"));
        infoList.add(new MyListViewItem("熟知单词","33"));
        infoList.add(new MyListViewItem("模糊单词","333"));
        infoList.add(new MyListViewItem("顽固单词","3333"));
        infoList.add(new MyListViewItem("set","set"));

        MyListViewAdapter myListViewAdapter = new MyListViewAdapter(context, infoList);
        listView.setAdapter(myListViewAdapter);
    }

}
