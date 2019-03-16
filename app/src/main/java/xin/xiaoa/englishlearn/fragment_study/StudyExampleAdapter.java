package xin.xiaoa.englishlearn.fragment_study;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

public class StudyExampleAdapter extends BaseAdapter {
    private Context context;
    private List<StudyExampleItem> lists;

    public StudyExampleAdapter(Context context, List<StudyExampleItem> lists) {
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

        StudyExampleAdapter.HolderView holderView = null;
        StudyExampleItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new StudyExampleAdapter.HolderView();
            arg1 = View.inflate(context, R.layout.study_example_item, null);
            holderView.tvEnglish = arg1.findViewById(R.id.study_example_item_english);
            holderView.tvChinese = arg1.findViewById(R.id.study_example_item_chinese);

            holderView.tvEnglish.setText(entity.getStrEnglish());
            holderView.tvChinese.setText(entity.getStrChinese());

            arg1.setTag(holderView);
        } else {
            holderView = (StudyExampleAdapter.HolderView) arg1.getTag();
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
        TextView tvChinese;
        TextView tvEnglish;
    }
}
