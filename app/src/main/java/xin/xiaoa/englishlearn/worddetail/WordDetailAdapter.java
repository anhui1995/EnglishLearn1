package xin.xiaoa.englishlearn.worddetail;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class WordDetailAdapter extends BaseAdapter {

    private Context context;
    private List<String> lists;

    public WordDetailAdapter(Context context, List<String> lists) {
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
    public View getView(int arg0, View arg1, ViewGroup arg2)
    {

        HolderView holderView = null;
        String entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new HolderView();
            arg1 = View.inflate(context, R.layout.wordmeanitem,null);
            holderView.tvId = (TextView) arg1.findViewById(R.id.worameanitemTextView1);
            holderView.tvId.setText(entity);
            //System.out.println("适配器"+entity);

            arg1.setTag(holderView);
        } else {
            holderView = (HolderView) arg1.getTag();
        }

        return arg1;



        // TODO: Implement this method
        //return null;
    }

    @Override
    public int getCount()
    {
        // TODO: Implement this method
        return lists.size();
    }




    //  unitListItem

    class HolderView {
        TextView tvId;
    }

}
