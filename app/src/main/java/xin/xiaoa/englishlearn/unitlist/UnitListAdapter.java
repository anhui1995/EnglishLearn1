package xin.xiaoa.englishlearn.unitlist;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class UnitListAdapter extends BaseAdapter {


    private Context context;
    private List<UnitListItem> lists;

    public UnitListAdapter(Context context, List<UnitListItem> lists) {
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


    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        HolderView holderView = null;
        UnitListItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new HolderView();
            arg1 = View.inflate(context, R.layout.unitlistitem, null);
            holderView.tvId = arg1.findViewById(R.id.spitemTextView1);

            if (entity.getId().equals("全部") || entity.getId().equals("收藏"))
                holderView.tvId.setText(entity.getId());
            else
                holderView.tvId.setText("单元" + entity.getId());
            //System.out.println("适配器"+entity.getId());
            arg1.setTag(holderView);
        } else {
            holderView = (HolderView) arg1.getTag();
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
        TextView tvId;
    }
}
