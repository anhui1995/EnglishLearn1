package xin.xiaoa.englishlearn.fragment_other;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class TLHistoryListViewAdapter extends BaseAdapter {
    private Context context;
    private List<TLHistoryListViewItem> lists;

    public TLHistoryListViewAdapter(Context context, List<TLHistoryListViewItem> lists) {
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

        TLHistoryListViewAdapter.HolderView holderView = null;
        TLHistoryListViewItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new TLHistoryListViewAdapter.HolderView();
            arg1 = View.inflate(context, R.layout.tl_history_lv_item, null);
            holderView.tvText = arg1.findViewById(R.id.tl_history_lv_item_tv);


            holderView.tvText.setText(entity.getText());

            arg1.setTag(holderView);
        } else {
            holderView = (TLHistoryListViewAdapter.HolderView) arg1.getTag();
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
        TextView tvText;
    }


}


