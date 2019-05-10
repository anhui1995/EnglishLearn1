package xin.xiaoa.englishlearn.fragment_other;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;


public class ArtListViewAdapter extends BaseAdapter {
    private Context context;
    private List<ArtListViewItem> lists;

    public ArtListViewAdapter(Context context, List<ArtListViewItem> lists) {
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

        ArtListViewAdapter.HolderView holderView = null;
        ArtListViewItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new ArtListViewAdapter.HolderView();
            arg1 = View.inflate(context, R.layout.other_art_lv_item, null);
            holderView.tvName = arg1.findViewById(R.id.other_art_lv_item_tv);


            holderView.tvName.setText("《"+entity.getName()+"》");

            arg1.setTag(holderView);
        } else {
            holderView = (ArtListViewAdapter.HolderView) arg1.getTag();
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
        TextView tvName;
    }


}

