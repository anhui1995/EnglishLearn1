package xin.xiaoa.englishlearn.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;

import xin.xiaoa.englishlearn.R;
import xin.xiaoa.englishlearn.service.ELApplication;
import xin.xiaoa.englishlearn.service.PreferencesUtils;

public class AddWordActivity extends Activity {

    TextView viewEnglish,viewN,viewV,viewAdj,viewAdv,viewOther,viewYinbiao,viewMainword,viewUnit;
    String strMainword;
    String strUnit;
    String word;
    Button butSuch,butAdd,butEmpty;
    int wordId=0;
    ResultSet sqlResultSet;
    int suchFlog=0;
    String strShow;
    String mUserName;//用户名
    String mPassword;//密码


    void showToast(){
        Toast.makeText( AddWordActivity.this,strShow, Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            int what = msg.what;
            switch (what) {
                case 1:
                    openRs();
                    break;
                case 2:
                    showToast();
                    break;
                default:
                    break;
            }
        }
    };


    protected void onCreate(Bundle savedInstanceState)
    {
        System.out.println("class_AddWord");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.addword);

        initView();
        mUserName = PreferencesUtils.getSharePreStr(this, "username");
        mPassword = PreferencesUtils.getSharePreStr(this, "pwd");

    }

    private void initView() {

        butSuch = this.findViewById(R.id.addwordButtonSuch);
        butAdd = this.findViewById(R.id.addwordButtonAdd);
        butEmpty= this.findViewById(R.id.addwordButtonEmpty);


        viewEnglish = this.findViewById(R.id.addwordEditTextEnglish);
        viewAdj= this.findViewById(R.id.addwordTextViewAdj);
        viewAdv= this.findViewById(R.id.addwordTextViewadv);
        viewN= this.findViewById(R.id.addwordTextViewN);
        viewV= this.findViewById(R.id.addwordTextViewV);
        viewOther= this.findViewById(R.id.addwordTextViewOther);
        viewUnit= this.findViewById(R.id.addwordEditTextUnit);
        viewMainword= this.findViewById(R.id.addwordEditTextMainword);
        viewYinbiao= this.findViewById(R.id.addwordTextViewYinbiao);



        butSuch.setOnClickListener(new MyClickListener());
        butAdd.setOnClickListener(new MyClickListener());
        butEmpty.setOnClickListener(new MyClickListener());
    }

    public void upword(String suchWord) {
        String uuu="http://dict-co.iciba.com/api/dictionary.php?w="+suchWord+"&type=json&key=24BF8398B2971CFC01004C0715A08D95";

        BufferedReader reader ;

        StringBuilder stringBuilder = new StringBuilder();
        try {
            System.out.println(uuu);
            //将字符串形式的path,转换成一个url
            URL url = new URL(uuu);
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
                //InputStream input = conn.getInputStream();
                InputStream is = conn.getInputStream();
                reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                String strRead ;
                while ((strRead = reader.readLine()) != null) {
                    stringBuilder.append(strRead);
                    stringBuilder.append("\n");
                }
                reader.close();
                String result = stringBuilder.toString();
                JSONObject jsonObj = new JSONObject(result);
                openJson(jsonObj);
                is.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void openJson(JSONObject json){
        try{
            String ph_en,ph_am,ph_en_mp3,ph_am_mp3,strWord;
            StringBuilder strN= new StringBuilder();
            StringBuilder strV= new StringBuilder();
            StringBuilder strAdv= new StringBuilder();
            StringBuilder strAdj= new StringBuilder();
            StringBuilder strOther= new StringBuilder();
            JSONObject symbols;
            JSONArray parts;
            symbols=json.getJSONArray("symbols").getJSONObject(0);
            strWord = json.getString("word_name");
            ph_en = symbols.getString("ph_en");
            ph_am = symbols.getString("ph_am");
            ph_en_mp3 = symbols.getString("ph_en_mp3");
            ph_am_mp3 = symbols.getString("ph_am_mp3");
            parts = symbols.getJSONArray("parts");
            JSONObject jsonTmp;
            JSONArray arrMeans;

            for(int i=0;i<parts.length();i++){
                StringBuilder strMeanTmp= new StringBuilder();
                jsonTmp = parts.getJSONObject(i);
                arrMeans = jsonTmp.getJSONArray("means");
                for(int j=0;j<arrMeans.length();j++){
                    strMeanTmp.append(arrMeans.getString(j));
                    strMeanTmp.append(",");
                }
                switch(jsonTmp.getString("part")){
                    case "n.":strN.append(strMeanTmp);break;
                    //case "n.":strN = strN + strMeanTmp;break;
                    case "vi.":strV.append(strMeanTmp);break;
                    case "vt.":strV.append(strMeanTmp);break;
                    case "v.":strV.append(strMeanTmp);break;
                    case "adj.":strAdj.append(strMeanTmp);break;
                    case "ajv.":strAdv.append(strMeanTmp);break;
                    default :strOther.append(strMeanTmp);break;
                }
            }
            //String checkStr = strN + strV + strAdv + strAdj + strOther;

            StringBuilder stress= new StringBuilder();
            stress.append(strN);
            stress.append(strV);
            stress.append(strV);
            stress.append(strAdj);
            stress.append(strOther);
            String checkStr= String.valueOf(strN);


            if(checkStr.equals("")) return;
            String tmp="INSERT INTO word (english, yinbiao, fayin, n, v, adj ,adv ,other ,times)VALUES ( '"+strWord+"','"+ph_en.replace("'","''")+"','"+ph_en_mp3+"','"+strN+"','"+strV+"','"+strAdv+"','"+strAdj+"','"+strOther+"','1')";

            try { //链接数据库 if(name.equals("admin")){
                if (!ELApplication.getSql().sqlStation())
                    System.out.println("数据库连接连接已经断开。");
                ELApplication.getSql().up(tmp);//已经获取结果集
                sqlResultSet = ELApplication.getSql().sel("SELECT * FROM word WHERE english='"+word+"'"); //查询

            } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
            handler.sendEmptyMessage(1);
			/*

			System.out.println(" ");
			System.out.println("单词-"+strWord);
			System.out.println(" ");
			System.out.println("发音-"+ph_en+ph_en_mp3+"\n"+ph_am+ph_am_mp3+"\n发音结束\n");
			System.out.println(" ");
			System.out.println("意思"+strN+strV+strAdv+strAdj+strOther+"\n意思结束\n");
*/
        }catch(Exception e){
            System.out.println("openJson错误-"+e);
        }
    }



    void such(){
        word=viewEnglish.getText().toString();

        suchFlog=0;
        if(word.equals("")) {
            Toast.makeText(
                    AddWordActivity.this,
                    "请输入单词", Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        new Thread(){
            public void run(){
                //reload();
                try { //链接数据库 if(name.equals("admin")){
                    if (!ELApplication.getSql().sqlStation())
                        System.out.println("数据库连接连接已经断开。");
                    sqlResultSet = ELApplication.getSql().sel("SELECT * FROM word WHERE english='"+word+"'"); //查询
                    System.out.println("已经获取结果集");

                } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
                handler.sendEmptyMessage(1);
            }
        }.start();
    }



    @SuppressLint("SetTextI18n")
    void openRs(){


        viewN.setText("");
        viewV.setText("");
        viewAdj.setText("");
        viewAdv.setText("");
        viewOther.setText("");
        viewYinbiao.setText("");

        //开始解析结果集
        try { //链接数据库 if(name.equals("admin")){

            if(sqlResultSet.next()) {
                wordId= sqlResultSet.getInt("id");
                //viewEnglish.setText(sqlResultSet.getString("english"));
                viewN.setText(sqlResultSet.getString("n"));
                viewV.setText(sqlResultSet.getString("v"));
                viewAdj.setText(sqlResultSet.getString("adj"));
                viewAdv.setText(sqlResultSet.getString("adv"));
                viewOther.setText(sqlResultSet.getString("other"));
                viewYinbiao.setText("[ "+ sqlResultSet.getString("yinbiao")+" ]");

            }
            else{
                suchFlog++;

                if(suchFlog==3){
                    Toast.makeText(
                            AddWordActivity.this,
                            "单词未收录", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    new Thread(){
                        public void run(){
                            upword(word);
                        }
                    }.start();

                }

            }


        } catch(Exception e) { System.out.println("结果集解析问题问题"+e); }
    }




    void add(){
        strUnit=viewUnit.getText().toString();
        strMainword=viewMainword.getText().toString();

        if(wordId==0) {
            strShow="请先搜索单词";
            handler.sendEmptyMessage(2);
            return;
        }

        if(strUnit.equals("")) {
            strShow="请输入单元";
            handler.sendEmptyMessage(2);
            return;
        }

        if(strMainword.equals("")) {
            strShow="请输入主词";
            handler.sendEmptyMessage(2);
            return;
        }

        new Thread(){
            public void run(){
                try { //链接数据库 if(name.equals("admin

                    //主词是否是本身
                    if(!strMainword.equals(word)){
                        if (!ELApplication.getSql().sqlStation())
                            System.out.println("数据库连接连接已经断开。");
                        //检查主词是否存在
                        sqlResultSet = ELApplication.getSql().sel("SELECT * FROM adminword WHERE word='"+strMainword+"'"); //查询
                        if(!sqlResultSet.next()) {
                            strShow="请先录入主词";
                            handler.sendEmptyMessage(2);


                            return;
                        }
                    }
                    //开始检查单词
                    //检查单词是否已经录入
                    sqlResultSet = ELApplication.getSql().sel("SELECT * FROM adminword WHERE word='"+word+"' AND mainword='"+strMainword+"'"); //查询
                    if(sqlResultSet.next()) {
                        strShow="单词已存在";
                        handler.sendEmptyMessage(2);

                        return;
                    }
                    //录入单词
                    String ldata="2018-10-21";
                    String fdata="2018-04-10";
                    int num=0;
                    int lnum=0;
                    //开始插入数据
                    ELApplication.getSql().up(" INSERT INTO adminword (word,ldata,fdata,num,lnum,wordid,unit,mainword) VALUES ('"+word+"','"+ldata+"','"+fdata+"','"+num+"','"+lnum+"','"+wordId+"','"+strUnit+"','"+strMainword+"') ");

                    viewN.setText("");
                    viewV.setText("");
                    viewAdj.setText("");
                    viewAdv.setText("");
                    viewOther.setText("");
                    viewYinbiao.setText("");
                    wordId=0;

                    strShow="添加完成";
                    handler.sendEmptyMessage(2);
                } catch(Exception e) { System.out.println("结果集获取失败问题"+e); }
            }
        }.start();





    }


    void empty(){
        viewEnglish.setText("");
        viewN.setText("");
        viewV.setText("");
        viewAdj.setText("");
        viewAdv.setText("");
        viewOther.setText("");
        viewYinbiao.setText("");
        wordId=0;
    }
    class MyClickListener implements View.OnClickListener {

        public void onClick(View arg0) {
            switch (arg0.getId()) {

                case R.id.addwordButtonAdd:
                    add();
                    break;
                case R.id.addwordButtonSuch:
                    such();
                    break;
                case R.id.addwordButtonEmpty:
                    empty();
                    break;
            }
        }
    }







}
