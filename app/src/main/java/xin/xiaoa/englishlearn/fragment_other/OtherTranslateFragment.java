package xin.xiaoa.englishlearn.fragment_other;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.baidu.translate.demo.TransApi;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;

public class OtherTranslateFragment extends Fragment {
    View view;
    Context context;
    Button butTranslate;
    TextView textViewTop,textViewBot;
    private static final String APP_ID = "20170912000082220";
    private static final String SECURITY_KEY = "wKyCxkhO1k7F2VOzqXwH";
    TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    List<TLLanguageListItem> languageListRight = new ArrayList<>();
    List<TLLanguageListItem>  languageListLeft = new ArrayList<>();
    String str_top = "";
    String str_bot = "";
    String fromLanguage = "auto";
    String toLanguage = "en";
    int leftSpinnerpos = 0;
    int rightSpinnerpos = 0;
    TLLanguageListItem lastListItemLeft;
    TLLanguageListItem lastListItemRight;
    Spinner spinnerLeft;
    Spinner spinnerRight;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    showEnglish();
                    break;
                default:
                    break;
            }
        }
    };
    void showEnglish() {
        textViewBot.setText(str_bot);
    }
    @SuppressLint("ValidFragment")
    public OtherTranslateFragment(Context con) {
        context = con;
    }

    public OtherTranslateFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.other_translate_fragment, container, false);
        viewInit();
        spinnerInit();
        return view;
    }

    void translate(){
        str_top = textViewTop.getText().toString();
        new Thread() {
            public void run() {              //开始翻译
                try {
                    JSONObject jsonObj = new JSONObject(api.getTransResult(str_top, fromLanguage, toLanguage));
                    openJson(jsonObj);
                } catch (Exception e) {
                    System.out.println("句子翻译问题" + e);
                }

            }
        }.start();
    }

    void openJson(JSONObject json) {
        try {
            JSONObject symbols;
            symbols = json.getJSONArray("trans_result").getJSONObject(0);
            str_bot = symbols.getString("dst");//英文结果
            handler.sendEmptyMessage(1);
        } catch (Exception e) {
            System.out.println("openJson错误-" + e);
        }
    }

    void viewInit(){
        butTranslate = view.findViewById(R.id.other_tl_button);
        butTranslate.setOnClickListener(new MyButtonClickListener());

        textViewTop = view.findViewById(R.id.other_tl_textView_top);
        textViewBot = view.findViewById(R.id.other_tl_textView_bot);
    }
    void spinnerInit(){
        spinnerLeft = view.findViewById(R.id.other_tl_spinner_left);
        spinnerRight = view.findViewById(R.id.other_tl_spinner_right);

        spinnerLeft.setOnItemSelectedListener(new MyLeftSpinnerSelectListener());
        spinnerRight.setOnItemSelectedListener(new MyRightSpinnerSelectListener());

        languageListRight.add(new TLLanguageListItem("英语","en"));
        languageListRight.add(new TLLanguageListItem("汉语","zh"));

        languageListRight.add(new TLLanguageListItem("粤语","yue"));
        languageListRight.add(new TLLanguageListItem("文言文","wyw"));
        languageListRight.add(new TLLanguageListItem("日语","jb"));
        languageListRight.add(new TLLanguageListItem("韩语","kor"));
        languageListRight.add(new TLLanguageListItem("法语","fra"));
        languageListRight.add(new TLLanguageListItem("西班牙语","spa"));
        languageListRight.add(new TLLanguageListItem("泰语","th"));
        languageListRight.add(new TLLanguageListItem("阿拉伯语","ara"));
        languageListRight.add(new TLLanguageListItem("葡萄牙语","pt"));
        languageListRight.add(new TLLanguageListItem("德语","de"));
        languageListRight.add(new TLLanguageListItem("意大利语","it"));
        languageListRight.add(new TLLanguageListItem("繁体中文","cht"));


        TLLanguageListAdapter languageListAdapterRight = new TLLanguageListAdapter(context, languageListRight);
        spinnerRight.setAdapter(languageListAdapterRight);

        languageListLeft.add(new TLLanguageListItem("自动检测","auto"));
        languageListLeft.add(new TLLanguageListItem("汉语","zh"));
        languageListLeft.add(new TLLanguageListItem("英语","en"));

        languageListLeft.add(new TLLanguageListItem("粤语","yue"));
        languageListLeft.add(new TLLanguageListItem("文言文","wyw"));
        languageListLeft.add(new TLLanguageListItem("日语","jb"));
        languageListLeft.add(new TLLanguageListItem("韩语","kor"));
        languageListLeft.add(new TLLanguageListItem("法语","fra"));
        languageListLeft.add(new TLLanguageListItem("西班牙语","spa"));
        languageListLeft.add(new TLLanguageListItem("泰语","th"));
        languageListLeft.add(new TLLanguageListItem("阿拉伯语","ara"));
        languageListLeft.add(new TLLanguageListItem("葡萄牙语","pt"));
        languageListLeft.add(new TLLanguageListItem("德语","de"));
        languageListLeft.add(new TLLanguageListItem("意大利语","it"));
        languageListLeft.add(new TLLanguageListItem("繁体中文","cht"));

        TLLanguageListAdapter languageListAdapterLeft = new TLLanguageListAdapter(context, languageListLeft);
        spinnerLeft.setAdapter(languageListAdapterLeft);

    }

    class MyLeftSpinnerSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
            String tmp;
            TLLanguageListItem item = languageListLeft.get(pos);
            try{
                if(toLanguage.equals(item.getLanguageString())){ //不允许选择
                    spinnerLeft.setSelection(leftSpinnerpos);
                }
                else {
                    fromLanguage = item.getLanguageString();
                    leftSpinnerpos = pos;
                }
            }
            catch (Exception e){}
        }
        @Override
        public void onNothingSelected(AdapterView<?> p1) { }
    }

    class MyRightSpinnerSelectListener implements AdapterView.OnItemSelectedListener {
        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
            TLLanguageListItem item = languageListRight.get(pos);
            try{
                if(fromLanguage.equals(item.getLanguageString())){ //不允许选择
                    spinnerRight.setSelection(rightSpinnerpos);
                }
                else {
                    toLanguage = item.getLanguageString();
                    rightSpinnerpos = pos;
                }
            }
            catch (Exception e){}
//            strUnit = item.getId();
//            if (strUnit.equals("收藏"))
//                show = "ok";
//            else
//                show = "";
//            getUnitWordList(strUnit);
        }
        @Override
        public void onNothingSelected(AdapterView<?> p1) {}
    }

    class MyButtonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View arg0) {
            translate();
//            switch (arg0.getId()) {
//                case R.id.other_button_article:
//                    funButArticle();break;
//            }
        }
    }
}
