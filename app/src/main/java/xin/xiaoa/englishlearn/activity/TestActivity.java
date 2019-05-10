package xin.xiaoa.englishlearn.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.service.ELApplication;

public class TestActivity extends Activity {


    EditText etFrom;
    EditText etEnglish;
    EditText etTitle;
    EditText etSubtitle;
    EditText etDescribe;
    EditText etChinese;

    Button but;

    String strFrom="";
    String strEnglish="";
    String strChinese="";
    String strTitle="";
    String strSubtitle="";
    String strDescribe="";




    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(Message msg)
        {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 2:insertOk();break;
                default : break;
            }

        };
    };

    void initView()
    {
        etFrom = (EditText)this.findViewById(R.id.main_et_from);
        etEnglish = (EditText)this.findViewById(R.id.main_et_english);
        etTitle = (EditText)this.findViewById(R.id.main_et_title);
        etSubtitle = (EditText)this.findViewById(R.id.main_et_subtitle);
        etDescribe = (EditText)this.findViewById(R.id.main_et_describe);
        etChinese = (EditText)this.findViewById(R.id.main_et_chinese);

        but = (Button)this.findViewById(R.id.mainButton);
        but.setOnClickListener(new myOnClickListener());
    }


    void insertOk()
    {
        etFrom.setText("科学美国人");
        etSubtitle.setText("测试");
        etEnglish.setText("test one times");
        etChinese.setText("测试一下");
        etTitle.setText("test");
        etDescribe.setText("描述测试");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        initView();
        insertOk();
    }

    class myOnClickListener implements View.OnClickListener
    {

        @Override
        public void onClick(View p1)
        {
            update();
        }


    }

    void update()
    {
        System.out.println("开始添加");

        strSubtitle = etSubtitle.getText().toString().replace("'","''");
        strTitle = etTitle.getText().toString().replace("'","''");
        strChinese = etChinese.getText().toString().replace("'","''");
        strEnglish = etEnglish.getText().toString().replace("'","''");
        strDescribe = etDescribe.getText().toString().replace("'","''");
        strFrom = etFrom.getText().toString().replace("'","''");

        try
        {
            new Thread(){
                public void run()
                {
                    int t=0;

                    if (!ELApplication.getSql().sqlStation()) {
                        System.out.println("数据库连接连接已经断开。");
                        return;
                    }
                        t = ELApplication.getSql().up("INSERT INTO article (source,english,chinese,title,subtitle,tip) VALUES ('" + strFrom + "','" + strEnglish + "','" + strChinese + "','" + strTitle + "','" + strSubtitle + "','" + strDescribe + "') ");
                    System.out.println("插入成功");
//                    if (t == 0)
//                    {
//                        System.out.println("error插入错误");
//                        return;
//                    }

                    handler.sendEmptyMessage(2);
                }
            }.start();
        }
        catch (Exception e)
        {
            System.out.println("线程错误" + e);
        }

    }

}
