package xin.xiaoa.englishlearn.fragment_study;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;


public class StudyMeanListAdapter extends BaseAdapter {

    private Context context;
    private List<StudyWordMeaningItem> lists;

    public StudyMeanListAdapter(Context context, List<StudyWordMeaningItem> lists) {
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

        StudyMeanListAdapter.HolderView holderView = null;
        StudyWordMeaningItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new StudyMeanListAdapter.HolderView();
            arg1 = View.inflate(context, R.layout.word_meaning_item, null);
            holderView.tvKey = arg1.findViewById(R.id.word_meaning_item_key);
            holderView.tvString = arg1.findViewById(R.id.word_meaning_item_string);

            holderView.tvKey.setText(entity.getKey());
            holderView.tvString.setText(entity.getString());

            arg1.setTag(holderView);
        } else {
            holderView = (StudyMeanListAdapter.HolderView) arg1.getTag();
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
        TextView tvString;
    }
}
