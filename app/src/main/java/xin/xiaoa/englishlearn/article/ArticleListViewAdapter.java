package xin.xiaoa.englishlearn.article;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.click_word.ClickEachWord;

public class ArticleListViewAdapter extends BaseAdapter {
    private Context context;
    private List<ArticleListViewItem> lists;

    public ArticleListViewAdapter(Context context, List<ArticleListViewItem> lists) {
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
    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        ArticleListViewAdapter.HolderView holderView = null;
        ArticleListViewItem item = lists.get(arg0);
        if (holderView == null) {
            holderView = new ArticleListViewAdapter.HolderView();
            arg1 = View.inflate(context, R.layout.activity_article_listview_item, null);
            holderView.textView = arg1.findViewById(R.id.activity_article_lv_item_tv);



            try{
                if("english".equals(item.getKey())) {
                    holderView.textView.setText(item.getContent(),TextView.BufferType.SPANNABLE);
                    new ClickEachWord(context,holderView.textView);
                }
                else if("title".equals(item.getKey())) holderView.textView.setText("《"+item.getContent()+"》");
                else holderView.textView.setText(item.getContent());
            }
            catch (Exception ignored){
                System.out.println("啥子问题呀"+ignored);
            }
            arg1.setTag(holderView);
        } else {
            holderView = (ArticleListViewAdapter.HolderView) arg1.getTag();
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
        TextView textView;
    }


}

