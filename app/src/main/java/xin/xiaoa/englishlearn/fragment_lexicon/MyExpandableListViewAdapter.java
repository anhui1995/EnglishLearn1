package xin.xiaoa.englishlearn.fragment_lexicon;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

//为ExpandableListView自定义适配器
public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

    private List<GroupsItem> groupsLists;
    private View.OnClickListener onClickListener;
    //List<ChildsItem> childs
    private Context context;
    public MyExpandableListViewAdapter(Context context, List<GroupsItem> groups,View.OnClickListener onClickListener) {
        this.context = context;
        this.groupsLists = groups;
        this.onClickListener = onClickListener;
    }

    //返回一级列表的个数
    @Override
    public int getGroupCount() {
        return groupsLists.size();
    }

    //返回每个二级列表的个数
    @Override
    public int getChildrenCount(int groupPosition) { //参数groupPosition表示第几个一级列表
        return groupsLists.get(groupPosition).getChilds().size();
    }

    //返回一级列表的单个item（返回的是对象）
    @Override
    public Object getGroup(int groupPosition) {
        return groupsLists.get(groupPosition);
    }

    //返回二级列表中的单个item（返回的是对象）
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groupsLists.get(groupPosition).getChilds().get(childPosition);  //不要误写成groups[groupPosition][childPosition]
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    //每个item的id是否是固定？一般为true
    @Override
    public boolean hasStableIds() {
        return true;
    }



    //【重要】填充一级列表
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String name = groupsLists.get(groupPosition).getStrName();
        if (convertView == null) {
            convertView = View.inflate(context,R.layout.expandable_listview_item_group, null);
        } else {

        }
        TextView tv_group = convertView.findViewById(R.id.expandable_listview_item_group_textView);
        tv_group.setText(name);

        Button button = convertView.findViewById(R.id.expandable_listview_item_group_button);

        if(groupsLists.get(groupPosition).isFirst()){
            button.setVisibility(VISIBLE);
            button.setFocusable(false);
            button.setOnClickListener(onClickListener);
        }
        else {
            button.setVisibility(INVISIBLE);
        }


        return convertView;
    }

    //【重要】填充二级列表
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = View.inflate(context,R.layout.expandable_listview_item_childs, null);
        }

        TextView tvChildName = convertView.findViewById(R.id.expandable_listview_item_childs_name);
      //  TextView tvChildContent = convertView.findViewById(R.id.expandable_listview_item_childs_content);

        tvChildName.setText(groupsLists.get(groupPosition).getChilds().get(childPosition).getName());
      //  tvChildContent.setText(groupsLists.get(groupPosition).getChilds().get(childPosition).getContent());
        return convertView;
    }

    //二级列表中的item是否能够被选中？可以改为true
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}