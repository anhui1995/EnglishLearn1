package xin.xiaoa.englishlearn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
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

@SuppressLint("Registered")
public class Search extends Activity {

    TextView viewN,viewV,viewAdj,viewAdv,viewOther,viewYinbiao;
    AutoCompleteTextView viewEnglish;
    String word;
    Button butSuch,butEmpty;
    int wordId=0;
    ResultSet sqlResultSet;
    int suchFlog=0;
    String strShow;
    String mUserName;//用户名
    String mPassword;//密码
    Context context;
    private SQLiteDatabase db;
    private AutoCompleteTextView actvWord;
    void showToast(){
        Toast.makeText( Search.this,strShow, Toast.LENGTH_SHORT).show();
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
        context = this;
        System.out.println("class_Search");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.search);

        initView();
        mUserName = PreferencesUtils.getSharePreStr(this, "username");
        mPassword = PreferencesUtils.getSharePreStr(this, "pwd");

        final SQLiteService sqLiteService = new SQLiteService(this);
        System.err.println("开始SQLite");
        sqLiteService.init();
        db = sqLiteService.getDb();
    }

    private void initView() {

        butSuch = this.findViewById(R.id.searchButtonSuch);
        butEmpty= this.findViewById(R.id.searchButtonEmpty);

        viewAdj= this.findViewById(R.id.searchTextViewAdj);
        viewAdv= this.findViewById(R.id.searchTextViewadv);
        viewN= this.findViewById(R.id.searchTextViewN);
        viewV= this.findViewById(R.id.searchTextViewV);
        viewOther= this.findViewById(R.id.searchTextViewOther);
        viewYinbiao= this.findViewById(R.id.searchTextViewYinbiao);



        viewEnglish = this.findViewById(R.id.searchEditTextEnglish);
        viewEnglish.addTextChangedListener(new myTextWatcher());
       // actvWord = (AutoCompleteTextView) findViewById(R.id.seek);


        butSuch.setOnClickListener(new MyClickListener());
        butEmpty.setOnClickListener(new MyClickListener());
    }


    class myTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            System.out.println("afterTextChanged："+s.toString());
            // 必须将english字段的别名设为_id
            Cursor cursor = db.rawQuery(
                    "select word as _id from ONLY_WORD where word like ?",
                    new String[] { s.toString() + "%" });
            DictionaryAdapter dictionaryAdapter = new DictionaryAdapter(context,
                    cursor, true);

            try{
                viewEnglish.setAdapter(dictionaryAdapter);
            }
            catch (Exception e){
                System.out.println("actvWord大问题："+e);
            }
        }
    }


    public class DictionaryAdapter extends CursorAdapter {
        private LayoutInflater layoutInflater;

        public CharSequence convertToString(Cursor cursor) {
            return cursor == null ? "" : cursor.getString(cursor
                    .getColumnIndex("_id"));
        }

        private void setView(View view, Cursor cursor) {
            TextView tvWordItem = (TextView) view;
            tvWordItem.setText(cursor.getString(cursor.getColumnIndex("_id")));
            tvWordItem.setPadding(15, 10, 10, 15);
            tvWordItem.setTextSize(18);
        }

        public DictionaryAdapter(Context context, Cursor c, boolean autoRequery) {
            super(context, c, autoRequery);
            // TODO Auto-generated constructor stub
            layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            // TODO Auto-generated method stub
            setView(view, cursor);
        }

        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = new TextView(Search.this);
            setView(view, cursor);
            return view;
        }

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
            String ph_en,ph_en_mp3,strWord;
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
           // ph_am = symbols.getString("ph_am");
            ph_en_mp3 = symbols.getString("ph_en_mp3");
          //  ph_am_mp3 = symbols.getString("ph_am_mp3");
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
                    Search.this,
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
                            Search.this,
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

                case R.id.searchButtonSuch:
                    such();
                    break;
                case R.id.searchButtonEmpty:
                    empty();
                    break;
            }
        }
    }







}
