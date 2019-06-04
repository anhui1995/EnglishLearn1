package xin.xiaoa.englishlearn.article2words;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class A2WListAdapter  extends BaseAdapter {
    private Context context;
    private List<A2wListviewItem> lists;

    public A2WListAdapter(Context context, List<A2wListviewItem> lists) {
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


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        A2WListAdapter.HolderView holderView = null;
        A2wListviewItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new A2WListAdapter.HolderView();
            arg1 = View.inflate(context, R.layout.a2w_lv_item, null);
            holderView.tvName = arg1.findViewById(R.id.a2w_lv_item_tv_name);
//            holderView.tvFreq = arg1.findViewById(R.id.a2w_lv_item_tv_freq);

            holderView.tvName.setText(entity.getName());
//            holderView.tvFreq.setText(entity.getFreq()+"");
            System.out.println(entity.getName()+"-"+entity.getFreq());

            arg1.setTag(holderView);
        } else {
            holderView = (A2WListAdapter.HolderView) arg1.getTag();
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

    class HolderView {
        TextView tvName;
        TextView tvFreq;
    }
}
