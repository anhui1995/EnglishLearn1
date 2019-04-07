package xin.xiaoa.englishlearn.click_word;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class ClickWordListAdapter  extends BaseAdapter{
    private Context context;
    private List<ClickWordListItem> lists;

    public ClickWordListAdapter(Context context, List<ClickWordListItem> lists) {
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
        ClickWordListItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new HolderView();
            arg1 = View.inflate(context, R.layout.click_word_list_item, null);
            holderView.tvKey = arg1.findViewById(R.id.click_word_list_tv_key);
            holderView.tvStr = arg1.findViewById(R.id.click_word_list_tv_str);

            holderView.tvKey.setText(entity.getKey());
            holderView.tvStr.setText(entity.getStr());

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
        TextView tvKey;
        TextView tvStr;
    }
}
