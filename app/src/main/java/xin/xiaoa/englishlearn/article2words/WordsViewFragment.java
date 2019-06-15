package xin.xiaoa.englishlearn.article2words;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.click_word.ClickWordDialog;

public class WordsViewFragment extends Fragment {

    private List<A2wListviewItem> a2wListviewList = null;
    Context context;
    View view;
    ListView listView;
    String strCmd;
    @SuppressLint("ValidFragment")
    public WordsViewFragment(Context con,String strCmd) {
        context = con;
        this.strCmd = strCmd;
    }

    public WordsViewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.a2w_fragment_words_view, container, false);
        listView = view.findViewById(R.id.a2w_f_words_view_listview);
        listView.setOnItemClickListener(new MyOnItemClickListener());
        return view;
    }

    public ListView getListView(){
        return listView;
    }
    public void setA2wListviewList(List<A2wListviewItem> a2wListviewList){
        this.a2wListviewList = a2wListviewList;
    }

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String word = a2wListviewList.get(position).getName();
//            if("new_word".equals(strCmd)) new ClickWordDialog(context,word,strCmd);
//            else new ClickWordDialog(context,word);
            new ClickWordDialog(context,word,strCmd);
        }
    }

}
