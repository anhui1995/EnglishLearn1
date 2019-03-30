package xin.xiaoa.englishlearn.fragment_other;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


        import xin.xiaoa.englishlearn.R;

public class OtherArticleFragment extends Fragment {
    View view;
    List<ARTFromListItem> fromList = new ArrayList<>();
    Spinner spinner;
    Context context;

    @SuppressLint("ValidFragment")
    public OtherArticleFragment(Context cont) {
        context = cont;
    }

    public OtherArticleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.other_article_fragment, container, false);
        spinnerInit();
        return view;
    }

    void spinnerInit(){
        spinner = view.findViewById(R.id.other_art_spinner);

        spinner.setOnItemSelectedListener(new MySpinnerSelectListener());

        fromList.add(new ARTFromListItem("BBC","en"));
        fromList.add(new ARTFromListItem("全球财经","zh"));
        fromList.add(new ARTFromListItem("TED","yue"));
        fromList.add(new ARTFromListItem("每日邮报","wyw"));
        fromList.add(new ARTFromListItem("时代周刊","jb"));
        fromList.add(new ARTFromListItem("南方周末","kor"));
        fromList.add(new ARTFromListItem("北方周波","fra"));
        fromList.add(new ARTFromListItem("财富","spa"));
        fromList.add(new ARTFromListItem("人民日报","th"));
        fromList.add(new ARTFromListItem("中国之声","ara"));


        ARTFromListAdapter artFromListAdapter = new ARTFromListAdapter(context, fromList);
        spinner.setAdapter(artFromListAdapter);

    }

    class MySpinnerSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
            ARTFromListItem item = fromList.get(pos);


        }
        @Override
        public void onNothingSelected(AdapterView<?> p1) {}
    }
}
