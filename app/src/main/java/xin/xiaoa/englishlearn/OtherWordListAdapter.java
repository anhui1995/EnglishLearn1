package xin.xiaoa.englishlearn;

import android.content.Context;
import android.text.TextPaint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class OtherWordListAdapter extends BaseAdapter {


    private Context context;
    private List<OtherWordListItem> lists;
    float textSize = 0;
    TextPaint textPaint = new TextPaint();

    public OtherWordListAdapter(Context context, List<OtherWordListItem> lists) {
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

        HolderView holderView = null;
        OtherWordListItem entity = lists.get(arg0);
        if (holderView == null) {
            holderView = new HolderView();
            arg1 = View.inflate(context, R.layout.otherwordlistitem, null);
            holderView.tvId = arg1.findViewById(R.id.otherworditemTextView1);
            holderView.Mean = arg1.findViewById(R.id.otherworditemTextViewMean);
            holderView.Mean.setText(entity.getMeaning());

            textSize = textPaint.measureText(entity.getEnglish());

            if (textSize > 59)
                holderView.tvId.setTextSize((int) (900 / textSize));
            holderView.tvId.setText(entity.getEnglish());
            //  System.out.println("字体大小"+entity.getEnglish()+textSize);
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


    //  unitListItem

    class HolderView {
        TextView tvId;
        TextView Mean;
    }
}
