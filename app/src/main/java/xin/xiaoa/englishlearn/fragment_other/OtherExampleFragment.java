package xin.xiaoa.englishlearn.fragment_other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.example_sentence.ExampleSentenceItem;
import xin.xiaoa.englishlearn.example_sentence.ExampleSentenceLv;

public class OtherExampleFragment extends Fragment {

    View view;
    Context context;
    ListView listView;
    Spinner spinner;
    List<OtherSpinnerItem> fromList = new ArrayList<>();
    @SuppressLint("ValidFragment")
    public OtherExampleFragment(Context cont) {
        context = cont;
    }
    public OtherExampleFragment() { }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.other_example_fragment, container, false);
        listView = view.findViewById(R.id.other_EG_listview);
        spinner = view.findViewById(R.id.other_EG_spinner);
        spinner.setPopupBackgroundResource(R.drawable.other_art_spinner_bg);
        setExample();
        spinnerInit();
        return view;
    }


    void spinnerInit(){

        spinner.setOnItemSelectedListener(new MySpinnerSelectListener());

        fromList.add(new OtherSpinnerItem("BBC","en"));
        fromList.add(new OtherSpinnerItem("全球财经","zh"));
        fromList.add(new OtherSpinnerItem("TED","yue"));
        fromList.add(new OtherSpinnerItem("每日邮报","wyw"));
        fromList.add(new OtherSpinnerItem("时代周刊","jb"));
        fromList.add(new OtherSpinnerItem("南方周末","kor"));
        fromList.add(new OtherSpinnerItem("北方周波","fra"));
        fromList.add(new OtherSpinnerItem("财富","spa"));
        fromList.add(new OtherSpinnerItem("人民日报","th"));
        fromList.add(new OtherSpinnerItem("中国之声","ara"));

        //OtherSpinnerItem

        OtherSpinnerAdapter otherSpinnerAdapterAdapter = new OtherSpinnerAdapter(context, fromList);
        spinner.setAdapter(otherSpinnerAdapterAdapter);

    }
    class MySpinnerSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {


        }
        @Override
        public void onNothingSelected(AdapterView<?> p1) { }
    }

    void setExample(){
        List<ExampleSentenceItem> exampleSentenceItemLists = new ArrayList<>();
        ExampleSentenceItem exampleSentenceItem;

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("不要此生匆匆过，但求每日都成长。");
        exampleSentenceItem.setStrEnglish("Don't go through life,grow through life.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("生活不是等待着暴风雨的过去，而是学会在雨中跳舞。");
        exampleSentenceItem.setStrEnglish("Life is not about waiting for the storms to pass,it's about learning to dance in the rain.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("爱是一种遇见，不能等待也不能准备。");
        exampleSentenceItem.setStrEnglish("Love is sort of encounter.It can be neither waited nor prepared.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("要想无可取代，必须时刻与众不同。");
        exampleSentenceItem.setStrEnglish("In order to be irreplaceable, one must always be different.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("我们接受有限的失望，但绝不放弃无限的希望。");
        exampleSentenceItem.setStrEnglish("We must accept finite dissapointment ,but we never lose infinite hope.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("我只是一朵向日葵，等待属于我的唯一的阳光。");
        exampleSentenceItem.setStrEnglish("I'm just a sunflower, waiting for belong to me only sunshine.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("大人的世界里没有容易二字。");
        exampleSentenceItem.setStrEnglish("Easy doesn't enter into grown-up life.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("我只愿他记得我当初的样子。");
        exampleSentenceItem.setStrEnglish("I would rather he remember me the way I was.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("当全世界都在说“放弃”的时候，希望却在耳边轻轻地说：“再试一次吧”！");
        exampleSentenceItem.setStrEnglish("When the world says,\"Give up!\"Hope whispers,\"Try it one more time.\"");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("无所求则无所获。");
        exampleSentenceItem.setStrEnglish("Nothing seek, nothing find.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("今后二十年你会因为没做某事，而不是做了某事而失望。");
        exampleSentenceItem.setStrEnglish("Twenty years from now you will be more disappointed by the things that you didn't do than by the things you did.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        exampleSentenceItem = new ExampleSentenceItem();
        exampleSentenceItem.setStrChinese("抱最好的希望，做最坏的打算。");
        exampleSentenceItem.setStrEnglish("Hope for the best, prepare for the worst.");
        exampleSentenceItemLists.add(exampleSentenceItem);

        new ExampleSentenceLv(listView,context, exampleSentenceItemLists);
    }

}