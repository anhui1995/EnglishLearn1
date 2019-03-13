package xin.xiaoa.englishlearn.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.baidu.translate.demo.TransApi;

import org.json.JSONObject;

import xin.xiaoa.englishlearn.R;

public class SentenceTranslationActivity extends Activity {


    private static final String APP_ID = "20170912000082220";
    private static final String SECURITY_KEY = "wKyCxkhO1k7F2VOzqXwH";

    TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    TextView textChinese, textEnglish;
    Button butTrans;


    String str_ch = "";
    String str_en = "";

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

    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("class_SentenceTranslation");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.sentencetranslation);
        init();


    }

    void init() {

        butTrans = this.findViewById(R.id.testButtonTranslate);
        butTrans.setOnClickListener(new MyClickListener());

        textEnglish = this.findViewById(R.id.testEditTextEnglish);
        textChinese = this.findViewById(R.id.testEditTextChinese);

    }

    void showEnglish() {
        textEnglish.setText(str_en);
    }

    void openJson(JSONObject json) {
        try {

            JSONObject symbols;
            symbols = json.getJSONArray("trans_result").getJSONObject(0);
            str_en = symbols.getString("dst");//英文结果
            handler.sendEmptyMessage(1);
        } catch (Exception e) {
            System.out.println("openJson错误-" + e);
        }
    }

    void trans() {

        str_ch = textChinese.getText().toString();
        System.out.println("中文:" + str_ch);
        new Thread() {
            public void run() {              //开始翻译
                try {
                    JSONObject jsonObj = new JSONObject(api.getTransResult(str_ch, "auto", "en"));
                    openJson(jsonObj);
                } catch (Exception e) {
                    System.out.println("句子翻译问题" + e);
                }
                //catch (JSONException e){}
            }
        }.start();
    }


    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            switch (arg0.getId()) {

                case R.id.testButtonTranslate:
                    trans();
                    break;
            }
        }
    }


}
