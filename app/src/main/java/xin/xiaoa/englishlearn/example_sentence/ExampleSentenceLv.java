package xin.xiaoa.englishlearn.example_sentence;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import xin.xiaoa.englishlearn.R;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class ExampleSentenceLv {

    private ListView lvExample;
    private Context context;
    private ExampleSentenceAdapter exampleSentenceAdapter;
    private List<ExampleSentenceItem> exampleSentenceItemLists;

    public ExampleSentenceLv(ListView lvExample, Context context,List<ExampleSentenceItem> exampleSentenceItemLists) {
        this.lvExample = lvExample;
        this.context = context;
        this.exampleSentenceItemLists = exampleSentenceItemLists;

        exampleSentenceAdapter = new ExampleSentenceAdapter(context, exampleSentenceItemLists);
        lvExample.setAdapter(exampleSentenceAdapter);
        lvExample.setOnItemClickListener(new itemClickListener());

    }





    class itemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> adapter, View view, int i, long id) {
            //ExampleSentenceItem tmp = exampleSentenceItemLists.get(i);
            TextView tv = view.findViewById(R.id.study_example_item_chinese);
            ExampleSentenceItem item = exampleSentenceItemLists.get(i);
            tv.setVisibility(tv.getVisibility()==INVISIBLE ? VISIBLE :INVISIBLE );
            item.setVisiable(tv.getVisibility());
        }
    }
}
