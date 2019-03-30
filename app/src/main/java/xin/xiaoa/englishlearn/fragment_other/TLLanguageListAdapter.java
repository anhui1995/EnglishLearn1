package xin.xiaoa.englishlearn.fragment_other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class TLLanguageListAdapter extends BaseAdapter {

    private Context context;
    private List<TLLanguageListItem> lists;

    public TLLanguageListAdapter(Context context, List<TLLanguageListItem> lists) {
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
        TLLanguageListItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new HolderView();
            arg1 = View.inflate(context, R.layout.other_tl_spinner_item, null);
            holderView.tvId = arg1.findViewById(R.id.other_tl_spi_item_textview);

            holderView.tvId.setText(entity.getLanguageName());

            arg1.setTag(holderView);
        } else {
            holderView = (HolderView) arg1.getTag();
        }
        return arg1;
    }

    @Override
    public int getCount() {
        // TODO: Implement this method
        return lists.size();
    }
    class HolderView {
        TextView tvId;
    }
}
