package xin.xiaoa.englishlearn.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.click_word.SearchWord;
import xin.xiaoa.englishlearn.worddictation.WordDictationListAdapter;
import xin.xiaoa.englishlearn.worddictation.WordDictationListAdapter.myTextListener;
import xin.xiaoa.englishlearn.worddictation.WordDictationListAdapter.myItemListener;
import xin.xiaoa.englishlearn.service.ELApplication;
import xin.xiaoa.englishlearn.unitlist.UnitListAdapter;
import xin.xiaoa.englishlearn.unitlist.UnitListItem;
import xin.xiaoa.englishlearn.worddictation.WordDictationListItem;

public class WordDictationActivity extends Activity {


    private ListView listView;
    private Spinner spinner;
    private Button mAdd;
    private Button mTest;
    private Button mSearch;
    private WordDictationListAdapter listAdapt;
    private UnitListAdapter unitAdapt;

    ResultSet rsList, rsList1;
    String strShow;
    String mUserName;//用户名
    String mPassword;//密码
    String mp3path;
    String show;
    public MediaPlayer mMediaPlayer = null;

    Context context;
    private List<WordDictationListItem> Lists = new ArrayList<>();
    private List<UnitListItem> unitList = new ArrayList<>();
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    openRs();
                    break;
                case 2:
                    openRs2();
                    break;
                case 3:
                    showToast();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    void showToast() {
        Toast.makeText(WordDictationActivity.this, strShow, Toast.LENGTH_SHORT).show();
    }


    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("class_WordDictationList_onCreate");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.worddictationlist);
        context = this;
        mp3path = ELApplication.getWordPath();
        mMediaPlayer = new MediaPlayer();
        initView();
        mUserName = xin.xiaoa.englishlearn.service.PreferencesUtils.getSharePreStr(this, "username");
        mPassword = xin.xiaoa.englishlearn.service.PreferencesUtils.getSharePreStr(this, "pwd");
        spinner = findViewById(R.id.unitlistSpinner1);
        spinner.setOnItemSelectedListener(new myItemSelectListener());
        reload();
        listView = findViewById(R.id.unitlistListView1);
        listView.setOnItemClickListener(new itemClickListener());
        listView.setOnItemLongClickListener(new itemClickLongListener());
    }

    private void initView() {
        mAdd = this.findViewById(R.id.unitlistButtonAdd);
        mTest = this.findViewById(R.id.unitlistButtonTest);
        mSearch = this.findViewById(R.id.unitlistButtonSearch);

        mAdd.setOnClickListener(new MyClickListener());
        mTest.setOnClickListener(new MyClickListener());
        mSearch.setOnClickListener(new MyClickListener());
    }

    void reload() {//获取unit
        new Thread() {
            public void run() {
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    //已经获取结果unit
                    rsList1 = ELApplication.getSql().sel("SELECT DISTINCT unit FROM adminword "); //查询

                } catch (Exception e) {
                    System.out.println("unit结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(2);
            }
        }.start();
    }


    void openRs() {

        Lists = new ArrayList<>();
        //Lists = new ArrayList<WordDictationListItem>();
        //text.setText("开始解析结果集");
        try { //链接数据库 if(name.equals("admin")){

            while (rsList.next()) {

                WordDictationListItem wordDictationListItem = new WordDictationListItem();
                String s;

                if (!rsList.getString("n").equals("")) {
                    int len = rsList.getString("n").length();
                    s = "n:" + rsList.getString("n").substring(1, len);
                    wordDictationListItem.setN(s);
                }

                if (!rsList.getString("v").equals("")) {
                    int len = rsList.getString("v").length();
                    s = "v:" + rsList.getString("v").substring(1, len);
                    wordDictationListItem.setV(s);
                }

                if (!rsList.getString("adj").equals("")) {
                    int len = rsList.getString("adj").length();
                    s = "adj:" + rsList.getString("adj").substring(1, len);
                    wordDictationListItem.setAdj(s);
                }

                if (!rsList.getString("adv").equals("")) {
                    int len = rsList.getString("adv").length();
                    s = "adv:" + rsList.getString("adv").substring(1, len);
                    wordDictationListItem.setAdv(s);
                }

                if (!rsList.getString("other").equals("")) {
                    int len = rsList.getString("other").length();
                    s = "other:" + rsList.getString("other").substring(1, len);
                    wordDictationListItem.setOther(s);
                }

                wordDictationListItem.setFlog(true);
                wordDictationListItem.setId((long) rsList.getInt("id"));
                wordDictationListItem.setShow(rsList.getString("flog"));
                wordDictationListItem.setFayin(rsList.getString("fayin"));
                wordDictationListItem.setYinbiao((rsList.getString("yinbiao")));
                wordDictationListItem.setEnglish(rsList.getString("english"));
                //System.out.println(sqlResultSet.getChinese("english"));
                Lists.add(wordDictationListItem);

            }
        } catch (Exception e) {
            System.out.println("结果集解析问题问题" + e);
        }
        /*
        setAdapter
        listAdapt = new wordAdatpter(this, Lists);
        */
        listAdapt = new WordDictationListAdapter(this, Lists, mListener, mTextLis);
        listView.setAdapter(listAdapt);
    }


    void openRs2() {
        //开始解析单元结果集
        unitList = new ArrayList<>();
        UnitListItem unitListItem = new UnitListItem();
        unitListItem.setId("收藏");
        unitList.add(unitListItem);
        unitListItem = new UnitListItem();
        unitListItem.setId("全部");
        unitList.add(unitListItem);
        //text.setText("开始解析unitunitunit");
        try { //链接数据库 if(name.equals("admin")){

            while (rsList1.next()) {

                unitListItem = new UnitListItem();
                //System.out.println("第几单元"+rsList1.getChinese("unit"));
                unitListItem.setId(rsList1.getString("unit"));
                unitList.add(unitListItem);

            }
        } catch (Exception e) {
            System.out.println("结unit果集解析问题问题" + e);
        }
        //setAdapter
        //listAdapt = new wordAdatpter(this, Lists);
        unitAdapt = new UnitListAdapter(this, unitList);
        spinner.setAdapter(unitAdapt);
    }

    // + ",内容是-->" + Lists.get(position)
    private myItemListener mListener = new myItemListener() {


        public void myOnClick(int i, View v) {
            WordDictationListItem tmp = Lists.get(i);
            System.out.println("info;" + i);
            Intent intent = new Intent();
            intent.setClass(WordDictationActivity.this, WordDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("n", tmp.getN());
            bundle.putString("v", tmp.getV());
            bundle.putString("adj", tmp.getAdj());
            bundle.putString("adv", tmp.getAdv());
            bundle.putString("other", tmp.getOther());
            bundle.putString("english", tmp.getEnglish());
            bundle.putString("yinbiao", tmp.getYinbiao());
            bundle.putString("fayin", tmp.getFayin());
            //bundle.putString("meaning",tmp.getMeaning());
            bundle.putLong("id", tmp.getId());
            intent.putExtras(bundle);
            startActivity(intent);
        }


    };

    void getUnitWordList(String strId) {
        final String id;
        id = strId;
        //Lists = new ArrayList<wordList>();
        new Thread() {
            public void run() {
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");

                    switch (id) {
                        case "收藏":
                            //sqlResultSet=lication.sql.sel("SELECT * FROM word where english in (SELECT word from adminword where show = 'show') ORDER BY RAND()"); //查询
                            rsList = ELApplication.getSql().sel("SELECT word.*,adminword.flog FROM word,adminword where adminword.word=word.english  AND adminword.flog='ok' ORDER BY RAND()"); //查询
                            break;
                        case "全部":
                            //sqlResultSet=lication.sql.sel("SELECT * FROM word where english in (SELECT word from adminword) ORDER BY RAND()"); //查询
                            rsList = ELApplication.getSql().sel("SELECT word.*,adminword.flog FROM word,adminword where adminword.word=word.english ORDER BY RAND()"); //查询
                            break;
                        default:
                            //sqlResultSet=lication.sql.sel("SELECT * FROM word where english in (SELECT word from adminword where unit = '"+id+"') ORDER BY RAND()"); //查询
                            rsList = ELApplication.getSql().sel("SELECT word.*,adminword.flog FROM word,adminword where adminword.word=word.english and unit = '" + id + "' ORDER BY RAND()"); //查询
                            break;
                    }
                } catch (Exception e) {
                    System.out.println("结果集获取失败问题" + e);
                }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }

    /**
     * 判断文件是否存在
     */
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    // 播放指定路径中的音乐
    private void playMusic(String path) {
        try {   //显示歌曲名
            //MusicName();
            // 重置多媒体
            mMediaPlayer.reset();
            //读取mp3文件
            mMediaPlayer.setDataSource(path);
            //准备播放
            mMediaPlayer.prepare();
            //开始播放
            mMediaPlayer.start();
            //更新进度条时间
            //mPercentHandler.post(updatesb);
            // 循环播放
            mMediaPlayer.setLooping(false);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.prepare();
        } catch (Exception e) {
            System.out.println("播放问题" + e);
        }
    }

    /**
     * 下载指定路径的文件，并写入到指定的位置
     * 返回0表示下载成功，返回1表示下载出错
     */
    public int downloadFile(String dirName, String fileName, String urlStr) {


        OutputStream output = null;
        try {
            //将字符串形式的path,转换成一个url
            URL url = new URL(urlStr);
            //得到url之后，将要开始连接网络，以为是连接网络的具体代码
            //首先，实例化一个HTTP连接对象conn
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //定义请求方式为GET，其中GET的大小写不要搞错了。
            conn.setRequestMethod("GET");
            //定义请求时间，在ANDROID中最好是不好超过10秒。否则将被系统回收。
            conn.setConnectTimeout(6 * 1000);
            //请求成功之后，服务器会返回一个响应码。如果是GET方式请求，服务器返回的响应码是200，post请求服务器返回的响应码是206（貌似）。
            if (conn.getResponseCode() == 200) {
                //返回码为真
                //从服务器传递过来数据，是一个输入的动作。定义一个输入流，获取从服务器返回的数据
                InputStream input = conn.getInputStream();
                File file = createFile(dirName + fileName);
                output = new FileOutputStream(file);
                //读取大文件
                byte[] buffer = new byte[1024 * 20];
                //记录读取内容

                int len = 0;
                //从输入六中读取数据,读到缓冲区中
                while ((len = input.read(buffer)) > 0) {
                    output.write(buffer, 0, len);
                }

                output.flush();
                input.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                output.close();
                return 0;
            } catch (IOException e) {
                System.out.println("fail");
                e.printStackTrace();
            }
        }
        return 1;
    }

    /**
     * 在SD卡的指定目录上创建文件
     *
     * @param fileName
     */
    public File createFile(String fileName) {
        File file = new File(fileName);
        try {
            if (file.createNewFile())
                System.out.println("createFile_OK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private myTextListener mTextLis = new myTextListener() {

        public boolean myOnTouch(View v, MotionEvent me, int a) {
            TextView tv = v.findViewById(R.id.wordItemTextViewEnglish);
            switch (me.getAction()) {
                case 0:
                    tv.setTextColor(0xffaaaaaa);
                    break;
                case 3:
                case 1:
                    tv.setTextColor(0xff000000);
                    WordDictationListItem tmp = Lists.get(a);
                    final String name = tmp.getEnglish();
                    final String fayin = tmp.getFayin();
                    if (tmp.getFayin().equals("")) {
                        Toast.makeText(
                                WordDictationActivity.this,
                                "不存在发音", Toast.LENGTH_SHORT)
                                .show();
                        break;
                    }
                    if (fileIsExists(mp3path + name + ".mp3")) {
                        playMusic(mp3path + name + ".mp3");
                    } else {
                        new Thread() {
                            public void run() {
                                if (downloadFile(mp3path, name + ".mp3", fayin) == 1)
                                    System.out.println("下载音频错误");
                                else playMusic(mp3path + name + ".mp3");
                            }
                        }.start();
                    }
                    break;
                default:
                    break;
            }
            return true;
        }

    };

    void buttonAdd() {
        Intent intent = new Intent();
        intent.setClass(WordDictationActivity.this, AddWordActivity.class);
        startActivity(intent);
    }

    void buttonSearch() {
        System.out.println("buttonSearch");
        new SearchWord(context);
//        Intent intent = new Intent();
//        intent.setClass(WordDictationActivity.this, SearchActivity.class);
//        startActivity(intent);
    }


    void buttonTest() {
        Intent intent = new Intent();
        intent.setClass(WordDictationActivity.this, TestActivity.class);
        startActivity(intent);
    }

    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            switch (arg0.getId()) {

                case R.id.unitlistButtonAdd:
                    buttonAdd();
                    break;
                case R.id.unitlistButtonSearch:
                    buttonSearch();
                    break;
                case R.id.unitlistButtonTest:
                    buttonTest();
                    break;
                default:
                    break;

            }
        }
    }

    class itemClickLongListener implements AdapterView.OnItemLongClickListener {

        public boolean onItemLongClick(AdapterView<?> adapter, View view, int i, long id) {

            WordDictationListItem tmp = Lists.get(i);
            final String str = tmp.getEnglish();

            new Thread() {
                public void run() {

                    if (show.equals("ok")) {
                        ELApplication.getSql().up(" UPDATE adminword SET flog = ' ' WHERE word = '" + str + "' ");

                        strShow = "取消收藏";
                    } else {
                        ELApplication.getSql().up("UPDATE adminword SET flog = 'ok' WHERE word = '" + str + "' ");
                        //lication.sql.up(" INSERT INTO adminshow (word) VALUES ('"+str+"') ");
                        strShow = "添加收藏";
                    }
                    handler.sendEmptyMessage(3);
                }
            }.start();
            //text2.setText("长i"+i+"id"+id);
            return true;
        }

    }

    class itemClickListener implements AdapterView.OnItemClickListener {

        public void onItemClick(AdapterView<?> adapter, View view, int i, long id) {
            WordDictationListItem tmp = Lists.get(i);
            TextView tv = view.findViewById(R.id.wordItemTextViewMeaning);

            if (tmp.getFlog()) {
                tmp.setFlog(false);
                tv.setBackgroundColor(0xffF7F3F2);
                tv.setTextColor(0xff000000);
            } else {
                tmp.setFlog(true);
                tv.setBackgroundColor(0xffa9a9a9);
                tv.setTextColor(0xffA9A9A9);
            }
        }
    }


    class myItemSelectListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapter, View view, int pos, long id) {
            String strUnit;
            UnitListItem item = unitList.get(pos);
            strUnit = item.getId();
            if (strUnit.equals("收藏"))
                show = "ok";
            else
                show = "";
            getUnitWordList(strUnit);
        }

        @Override
        public void onNothingSelected(AdapterView<?> p1) {
            // TODO: Implement this method
        }


    }


}
