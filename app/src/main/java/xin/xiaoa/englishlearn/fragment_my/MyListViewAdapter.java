package xin.xiaoa.englishlearn.fragment_my;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class MyListViewAdapter  extends BaseAdapter {
    private Context context;
    private List<MyListViewItem> lists;

    public MyListViewAdapter(Context context, List<MyListViewItem> lists) {
        super();
        this.context = context;
        this.lists = lists;
    }


    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return lists.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        MyListViewAdapter.HolderView holderView = null;
        MyListViewItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new MyListViewAdapter.HolderView();
            if("set".equals(entity.getKey())){
                arg1 = View.inflate(context, R.layout.my_lv_item_button, null);
                holderView.button = arg1.findViewById(R.id.my_lv_item_but);
            }
            else{
                arg1 = View.inflate(context, R.layout.my_lv_item, null);
                holderView.tvKey = arg1.findViewById(R.id.my_lv_item_tv_key);
                holderView.tvKey.setText(entity.getKey());

                holderView.tvValue = arg1.findViewById(R.id.my_lv_item_tv_value);
                holderView.tvValue.setText(entity.getValue());
            }


            arg1.setTag(holderView);
        } else {
            holderView = (MyListViewAdapter.HolderView) arg1.getTag();
        }
        return arg1;
        // TODO: Implement this method
        //return null;
    }

    @Override
    public int getCount() {
        // TODO: Implement this method
        return lists.size();
    }


    //  UnitListItem

    class HolderView {
        TextView tvKey;
        TextView tvValue;
        Button button;
    }


}

